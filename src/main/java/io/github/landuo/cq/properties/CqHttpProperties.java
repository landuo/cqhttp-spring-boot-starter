package io.github.landuo.cq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author accidia
 */
@ConfigurationProperties(prefix = "cqhttp")
@Data
public class CqHttpProperties {
    private String ip;
    private Integer port;
    private String endPoint = "/cq";

}
