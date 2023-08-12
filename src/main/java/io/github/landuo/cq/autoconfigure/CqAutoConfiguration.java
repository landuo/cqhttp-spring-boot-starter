package io.github.landuo.cq.autoconfigure;

import io.github.landuo.cq.CQRequestFilter;
import io.github.landuo.cq.CqContext;
import io.github.landuo.cq.properties.CqHttpProperties;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

/**
 * @author accidia
 */
@AutoConfiguration
@EnableConfigurationProperties(CqHttpProperties.class)
@ComponentScan({"io.github.landuo.cq.controller"})
@Slf4j
public class CqAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CqContext cqContext() {
        return new CqContext();
    }

    @Bean
    public FilterRegistrationBean<Filter> cqFilterRegistrationBean(CqHttpProperties configProperties) {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CQRequestFilter());
        registrationBean.setUrlPatterns(Collections.singleton(configProperties.getEndPoint()));
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
