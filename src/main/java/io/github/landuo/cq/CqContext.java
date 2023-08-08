package io.github.landuo.cq;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import io.github.landuo.cq.annotations.Command;
import io.github.landuo.cq.annotations.Listener;
import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.msg.common.BaseMsg;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author accidia
 */
@Data
public class CqContext implements ApplicationContextAware {
    private Map<String, Class<?>> msgMap = new HashMap<>();
    private Map<String, Method> commandMethods = new HashMap<>();
    private Map<String, String> commandDesc = new HashMap<>();
    private Map<String, Method> listenerMethods = new HashMap<>();
    public final static ThreadLocal<BaseMsg> ORIGIN_MSG = new ThreadLocal<>();

    public Method getCommandMethod(String command) {
        Optional<String> optional = commandMethods.keySet().stream().filter(command::startsWith).findFirst();
        return optional.map(s -> commandMethods.get(s)).orElse(null);
    }

    @Command(value = "/help")
    private String help() {
        StringBuilder sb = new StringBuilder();
        sb.append("可用命令如下: \n");
        this.commandDesc.forEach((k, v) -> sb.append(k).append("\t").append("-").append("\t").append(v).append("\n"));
        return sb.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 从栈里获取Springboot启动类信息
        Map<String, Object> annotatedBeans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        String mainClassPackage = annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass().getPackageName();
        Set<Class<?>> allClasses = ClassUtil.scanPackage(mainClassPackage);
        // 扫描所有Command方法
        allClasses.stream().parallel().filter(cl -> Arrays.stream(ReflectUtil.getMethods(cl))
                .anyMatch(m -> m.isAnnotationPresent(Command.class))).forEach(clazz -> {
            Method[] methods = ReflectUtil.getMethods(clazz, method -> method.isAnnotationPresent(Command.class));
            Arrays.stream(methods).parallel().forEach(method -> {
                Command command = method.getAnnotation(Command.class);
                boolean containsKey = commandMethods.containsKey(command.value());
                if (containsKey) {
                    throw new CQException(command.value() + " 命令已存在, 请检查");
                }
                commandMethods.put(command.value(), method);
                commandDesc.put(command.value(), command.sample());
            });
        });
        Method help = ReflectUtil.getMethod(this.getClass(), "help");
        commandMethods.put("/help", help);
        commandDesc.put("/help", "查看帮助");
        // 扫描所有Listener方法
        allClasses.stream().parallel().filter(cl -> Arrays.stream(ReflectUtil.getMethods(cl))
                .anyMatch(m -> m.isAnnotationPresent(Listener.class))).forEach(clazz -> {
            Method[] methods = ReflectUtil.getMethods(clazz, method -> method.isAnnotationPresent(Listener.class));
            Arrays.stream(methods).parallel().forEach(method -> {
                Listener listener = method.getAnnotation(Listener.class);
                boolean containsKey = listenerMethods.containsKey(listener.getClass().getName());
                if (containsKey) {
                    throw new CQException(listener.value() + " 监听器已存在, 请检查");
                }
                listenerMethods.put(listener.value().getName(), method);
            });
        });
    }

    @PostConstruct
    public void init() {
        Set<Class<?>> msgClazz = ClassUtil.scanPackage("io.github.landuo.cq.msg",
                aClass -> aClass.isAnnotationPresent(MsgType.class));
        msgClazz.stream().parallel().forEach(clazz -> {
            MsgType msgType = clazz.getAnnotation(MsgType.class);
            String stringBuilder = msgType.post_type() + ":" + msgType.message_type().toString().toLowerCase()
                    + ":" + msgType.request_type().toString() + ":" + msgType.notice_type().toString() + ":"
                    + msgType.notice_sub_type().toString() + ":" + msgType.meta_event_type().toString();
            msgMap.put(stringBuilder, clazz);
        });
    }

    public <T extends BaseMsg> T getOriginMessage(String body) {
        Map param = JSONUtil.toBean(body, Map.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(param.getOrDefault("post_type", "NULL").toString().replace("_sent", "")).append(":").append(param.getOrDefault("message_type", "NULL").toString().toLowerCase()).append(":").append(param.getOrDefault("request_type", "NULL").toString()).append(":").append(param.getOrDefault("notice_type", "NULL").toString()).append(":").append(param.getOrDefault("notice_sub_type", "NULL").toString()).append(":").append(param.getOrDefault("meta_event_type", "NULL").toString());
        if (msgMap.containsKey(stringBuilder.toString())) {
            return (T) JSONUtil.toBean(body, msgMap.get(stringBuilder.toString()));
        } else {
            throw new CQException("未配置策略");
        }
    }
}
