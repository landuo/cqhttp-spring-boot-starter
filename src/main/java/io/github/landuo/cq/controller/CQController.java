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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.MethodInvoker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author accidia
 */
@Data
@Slf4j
@RestController
public class CQController implements ApplicationContextAware {
    @Autowired
    private CqContext cqContext;
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @PostMapping("${cqhttp.end-point}")
    public QuickReply test() {
        BaseMsg msg = CqContext.ORIGIN_MSG.get();
        if (msg instanceof MessageMsg messageMsg) {
            String[] split = messageMsg.getMessage().split("\\s+");
            Method method = cqContext.getCommandMethod();
            Command command = cqContext.getCommand();
            // 不允许群聊且消息是群聊的时候
            if (!command.allowGroupMsg() && messageMsg instanceof GroupMessageMsg) {
                throw new CQException("指令: " + command.value() + " 不允许群聊");
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
                            throw new CQException("指令: " + command.value() + " 不允许在此使用");
                        }
                        param = msg;
                    } else {
                        if (parameterCount > (split.length - 1)) {
                            throw new CQException("参数不足, 请重新输入.例子: " + command.sample());
                        }
                        // 基本数据类型包装类时可使用
                        Constructor<?> constructor = type.getConstructor(String.class);
                        param = constructor.newInstance(split[i + 1]);
                    }
                    params[i] = param;
                }
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                if (e instanceof InvocationTargetException exception) {
                    throw new CQException(exception.getCause().getMessage(), exception.getCause());
                } else {
                    throw new CQException(e.getMessage());
                }

            }
            return invoke(ctx.getBean(method.getDeclaringClass()), method.getName(), params);
        }

        Method listenerMethod = cqContext.getListenerMethod();
        if (listenerMethod == null) {
            return null;
        }
        return invoke(ctx.getBean(listenerMethod.getDeclaringClass()), listenerMethod.getName(), msg);
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
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause().getMessage(), e.getCause());
        }
    }

}
