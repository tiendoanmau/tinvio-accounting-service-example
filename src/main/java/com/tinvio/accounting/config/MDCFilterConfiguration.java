package com.tinvio.accounting.config;

import com.tinvio.accounting.filter.MDCFilter;;
import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MDCFilterConfiguration {

    public static final String DEFAULT_MDC_TOKEN_KEY = "correlationId";


    @Bean
    public FilterRegistrationBean<MDCFilter> filterRegistrationBean() {
        final FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();
        final MDCFilter mdcFilter = new MDCFilter();
        registrationBean.setFilter(mdcFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
