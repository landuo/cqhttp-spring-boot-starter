package io.github.landuo.cq.autoconfigure;

import io.github.landuo.cq.CqContext;
import io.github.landuo.cq.controller.CQController;
import io.github.landuo.cq.msg.common.BaseMsg;
import io.github.landuo.cq.properties.CqHttpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.*;

/**
 * @author accidia
 */
@AutoConfiguration
@EnableConfigurationProperties(CqHttpProperties.class)
@Slf4j
public class CqAutoConfiguration {

    private final CqHttpProperties configProperties;

    public CqAutoConfiguration(CqHttpProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public CqContext cqContext() {
        return new CqContext();
    }

    @Bean
    @ConditionalOnMissingBean
    public CQController cqController(CqContext cqContext,ApplicationContext applicationContext) {
        CQController cqController = new CQController();
        cqController.setCqContext(cqContext);
        cqController.setApplicationContext(applicationContext);
        return cqController;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(CQController controller, CqContext cqContext) {
        return RouterFunctions.route(RequestPredicates.POST(configProperties.getEndPoint()),
                requestHandler(controller)).filter(filter(cqContext));
    }

    private HandlerFunction<ServerResponse> requestHandler(CQController controller) {
        return request -> {
            Object response = controller.handle();
            return response == null ? ServerResponse.noContent().build() : ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        };
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> filter(CqContext cqContext) {
        return (request, next) -> {
            String body = request.body(String.class);
            BaseMsg originMsg = cqContext.getOriginMessage(body);
            try {
                CqContext.ORIGIN_MSG.set(originMsg);
                return next.handle(request);
            } finally {
                CqContext.ORIGIN_MSG.remove();
            }
        };
    }
}
