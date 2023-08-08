package io.github.landuo.cq.controller;

import io.github.landuo.cq.CQException;
import io.github.landuo.cq.CqContext;
import io.github.landuo.cq.annotations.Command;
import io.github.landuo.cq.annotations.MsgType;
import io.github.landuo.cq.msg.QuickReply;
import io.github.landuo.cq.msg.common.BaseMsg;
import io.github.landuo.cq.msg.common.MessageMsg;
import io.github.landuo.cq.msg.message.GroupMessageMsg;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.MethodInvoker;

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
    private ApplicationContext applicationContext;

    public Object handle() {
        BaseMsg msg = CqContext.ORIGIN_MSG.get();
        if (msg instanceof MessageMsg messageMsg) {
            String[] split = messageMsg.getMessage().split("\\s+");
            Method method = cqContext.getCommandMethod(messageMsg.getMessage());
            if (method == null) {
                return null;
//                return QuickReply.builder().reply("无此命令").atSender(true).build();
            }
            Command command = method.getAnnotation(Command.class);
            // 不允许群聊且消息是群聊的时候
            if (!command.allowGroupMsg() && messageMsg instanceof GroupMessageMsg) {
                return QuickReply.builder().reply("该命令不允许群聊").atSender(true).build();
            }
            int parameterCount = method.getParameterCount();
            Object[] params = new Object[parameterCount];
            Class<?>[] parameterTypes = method.getParameterTypes();
            try {
                for (int i = 0; i < parameterCount; i++) {
                    Class<?> type = parameterTypes[i];
                    Object param;
                    // 如果命令的形参是msg对象则直接传递参数
                    if (type.isAnnotationPresent(MsgType.class)) {
                        // 参数不是MessageMsg且在群聊使用私聊命令(或在私聊使用群聊命令)
                        if (!type.getName().equalsIgnoreCase(msg.getClass().getName()) && !type.getName().equalsIgnoreCase(MessageMsg.class.getName())) {
                            return QuickReply.builder().reply("该命令不允许在此使用").atSender(true).build();
                        }
                        param = msg;
                    } else {
                        if (parameterCount > (split.length - 1)) {
                            return QuickReply.builder().reply("参数不足, 请重新输入.例子: " + cqContext.getCommandDesc().get(split[0])).atSender(true).build();
                        }
                        // 基本数据类型包装类时可使用
                        Constructor<?> constructor = type.getConstructor(String.class);
                        param = constructor.newInstance(split[i + 1]);
                    }
                    params[i] = param;
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                return QuickReply.builder().reply("系统报错: " + e.getCause().getMessage()).atSender(true).build();
            }
            return invoke(applicationContext.getBean(method.getDeclaringClass()), method.getName(), params);
        }

        Map<String, Method> listeners = cqContext.getListenerMethods();
        Method method = listeners.getOrDefault(msg.getClass().getName(), null);
        if (method == null) {
            return null;
        }
        return invoke(applicationContext.getBean(method.getDeclaringClass()), method.getName(), msg);
    }

    private QuickReply invoke(Object targetObject, String targetMethod, Object... param) {
        MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(targetObject);
        invoker.setTargetMethod(targetMethod);
        invoker.setArguments(param);
        try {
            invoker.prepare();
            Object result = invoker.invoke();
            return QuickReply.builder().reply(result != null ? result.toString() : null).atSender(true).build();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            return QuickReply.builder().reply("系统报错: " + e.getCause().getMessage()).atSender(true).build();
        }
    }
}
