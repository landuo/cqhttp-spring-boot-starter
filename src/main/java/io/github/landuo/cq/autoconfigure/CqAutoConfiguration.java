package io.github.landuo.cq.autoconfigure;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import io.github.landuo.cq.CqContext;
import io.github.landuo.cq.annotations.Command;
import io.github.landuo.cq.annotations.Listener;
import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.controller.CQController;
import io.github.landuo.cq.properties.CqHttpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author accidia
 */
@AutoConfiguration
@EnableConfigurationProperties(CqHttpProperties.class)
@ComponentScan("io.github.landuo.cq.controller")
@Slf4j
public class CqAutoConfiguration {

    private final CqHttpProperties configProperties;

    public CqAutoConfiguration(CqHttpProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public CqContext cqContext() {
        CqContext cqContext = new CqContext();
        Map<String, Class<?>> msgMap = new HashMap<>();
        Map<String, Method> commandMethods = new HashMap<>();
        Map<String, String> commandSamples = new HashMap<>();
        Map<String, Method> listenerMethods = new HashMap<>();

        Set<Class<?>> msgClazz = ClassUtil.scanPackage("io.github.landuo.cq.msg");
        msgClazz.stream().parallel().filter(e -> e.isAnnotationPresent(MsgType.class)).forEach(clazz -> {
            MsgType msgType = clazz.getAnnotation(MsgType.class);
            String stringBuilder = msgType.post_type() + ":" +
                    msgType.message_type().toString().toLowerCase() + ":" +
                    msgType.request_type().toString() + ":" +
                    msgType.notice_type().toString() + ":" +
                    msgType.notice_sub_type().toString() + ":" +
                    msgType.meta_event_type().toString();
            msgMap.put(stringBuilder, clazz);
        });
        // 从栈里获取Springboot启动类信息
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String startupClassName = stackTrace[stackTrace.length - 1].getClassName();
        Set<Class<?>> allClasses = ClassUtil.scanPackage(startupClassName.substring(0, startupClassName.lastIndexOf(".")));
        // 扫描所有Command方法
        allClasses.stream().parallel().filter(cl -> Arrays.stream(ReflectUtil.getMethods(cl))
                .anyMatch(m -> m.isAnnotationPresent(Command.class))).forEach(clazz -> {
            Method[] methods = ReflectUtil.getMethods(clazz, method -> method.isAnnotationPresent(Command.class));
            Arrays.stream(methods).parallel().forEach(method -> {
                Command command = method.getAnnotation(Command.class);
                boolean containsKey = commandMethods.containsKey(command.value());
                if (containsKey) {
                    throw new RuntimeException(command.value() + " 命令已存在, 请检查");
                }
                commandMethods.put(command.value(), method);
                commandSamples.put(command.value(), command.sample());
            });
        });

        // 扫描所有Listener方法
        allClasses.stream().parallel().filter(cl -> Arrays.stream(ReflectUtil.getMethods(cl))
                .anyMatch(m -> m.isAnnotationPresent(Listener.class))).forEach(clazz -> {
            Method[] methods = ReflectUtil.getMethods(clazz, method -> method.isAnnotationPresent(Listener.class));
            Arrays.stream(methods).parallel().forEach(method -> {
                Listener listener = method.getAnnotation(Listener.class);
                boolean containsKey = listenerMethods.containsKey(listener.getClass().getName());
                if (containsKey) {
                    throw new RuntimeException(listener.value() + " 监听器已存在, 请检查");
                }
                listenerMethods.put(listener.value().getName(), method);
            });
        });

        cqContext.setMsgMap(msgMap);
        cqContext.setCommandSamples(commandSamples);
        cqContext.setCommandMethods(commandMethods);
        cqContext.setListenerMethods(listenerMethods);
        return cqContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public CQController cqController(CqContext cqContext) {
        CQController cqController = new CQController();
        cqController.setCqContext(cqContext);
        return cqController;
    }


    @Bean
    public RouterFunction<ServerResponse> routerFunction(CQController controller) {
        return RouterFunctions.route(RequestPredicates.POST(configProperties.getEndPoint()),
                requestHandler(controller));
    }

    private HandlerFunction<ServerResponse> requestHandler(CQController controller) {
        return request -> {
            Object response = controller.handle(request);
            return response == null ? ServerResponse.noContent().build() : ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        };
    }
}
