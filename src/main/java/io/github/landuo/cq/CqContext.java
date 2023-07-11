package io.github.landuo.cq;

import cn.hutool.json.JSONUtil;
import io.github.landuo.cq.msg.common.BaseMsg;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author accidia
 */
@Data
public class CqContext {
    private Map<String, Class<?>> msgMap;
    private Map<String, Method> commandMethods;
    private Map<String, String> commandSamples;
    private Map<String, Method> listenerMethods;

    public <T extends BaseMsg> T getMsg(String body) {
        Map param = JSONUtil.toBean(body, Map.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(param.getOrDefault("post_type", "NULL").toString().replace("_sent", "")).append(":")
                .append(param.getOrDefault("message_type", "NULL").toString().toLowerCase()).append(":")
                .append(param.getOrDefault("request_type", "NULL").toString()).append(":")
                .append(param.getOrDefault("notice_type", "NULL").toString()).append(":")
                .append(param.getOrDefault("notice_sub_type", "NULL").toString()).append(":")
                .append(param.getOrDefault("meta_event_type", "NULL").toString());
        if (msgMap.containsKey(stringBuilder.toString())) {
            return (T) JSONUtil.toBean(body, msgMap.get(stringBuilder.toString()));
        } else {
            throw new RuntimeException("未配置策略");
        }
    }

    public Method getCommandMethod(String command) {
        String[] split = command.split("\\s+");
        return commandMethods.getOrDefault(split[0], null);
    }
}
