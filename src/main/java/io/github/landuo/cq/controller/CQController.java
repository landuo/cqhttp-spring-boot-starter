package io.github.landuo.cq.controller;

import cn.hutool.core.util.ReflectUtil;
import io.github.landuo.cq.CqContext;
import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.msg.QuickReply;
import io.github.landuo.cq.msg.common.BaseMsg;
import io.github.landuo.cq.msg.common.MessageMsg;
import jakarta.servlet.ServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author accidia
 */
@Data
@Slf4j
public class CQController {
    private CqContext cqContext;

    public Object handle(ServerRequest request) throws IOException {
        String body = getRequestBody(request.servletRequest());
        BaseMsg msg = cqContext.getMsg(body);
        if (msg instanceof MessageMsg messageMsg) {
            String[] split = messageMsg.getMessage().split("\\s+");
            if ("/help".equalsIgnoreCase(split[0])) {
                StringBuilder sb = new StringBuilder();
                cqContext.getCommandSamples().forEach((k, v) -> sb.append(k).append("\t").append("-").append("\t").append(v).append("\n"));
                return QuickReply.builder().reply(sb.toString()).atSender(true).build();
            }
            Method method = cqContext.getCommandMethod(messageMsg.getMessage());
            if (method == null) {
                return QuickReply.builder().reply("无此命令").atSender(true).build();
            }

            int parameterCount = method.getParameterCount();
            Object[] params = new Object[parameterCount];
            Class<?>[] parameterTypes = method.getParameterTypes();
            try {
                for (int i = 0; i < parameterCount; i++) {
                    Class<?> type = parameterTypes[i];
                    Object instance;
                    // 如果命令的形参是msg对象则直接传递参数
                    if (type.isAnnotationPresent(MsgType.class)) {
                        // 参数不是MessageMsg且在群聊使用私聊命令(或在私聊使用群聊命令)
                        if (!type.getName().equalsIgnoreCase(msg.getClass().getName()) && !type.getName().equalsIgnoreCase(MessageMsg.class.getName())) {
                            return QuickReply.builder().reply("该命令不允许在此使用").atSender(true).build();
                        }
                        instance = msg;
                    } else {
                        // 基本数据类型包装类时可使用
                        Constructor<?> constructor = type.getConstructor(String.class);
                        instance = constructor.newInstance(split[i + 1]);
                    }
                    params[i] = instance;
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                log.warn(e.getMessage(), e);
                return QuickReply.builder().reply("参数类型错误, 例子: " + cqContext.getCommandSamples().get(split[0])).atSender(true).build();
            }
            Object instance = ReflectUtil.newInstance(method.getDeclaringClass());
            Object result = ReflectUtil.invoke(instance, method, params);
            if (result != null) {
                return QuickReply.builder().reply(result.toString()).atSender(true).build();
            }
            if (parameterCount > 0 && parameterCount != (split.length - 1)) {
                return QuickReply.builder().reply("参数不足, 请重新输入.例子: " + cqContext.getCommandSamples().get(split[0])).atSender(true).build();
            }
        }

        Map<String, Method> listeners = cqContext.getListenerMethods();
        Method method = listeners.getOrDefault(msg.getClass().getName(), null);
        if (method == null) {
            return null;
        }
        Object instance = ReflectUtil.newInstance(method.getDeclaringClass());
        return ReflectUtil.invoke(instance, method, msg);
    }

    private String getRequestBody(ServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
