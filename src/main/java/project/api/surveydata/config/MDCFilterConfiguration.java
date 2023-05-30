package project.api.surveydata.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MDCFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CustomMDCFilter> filterRegistrationBean()
    {
        final FilterRegistrationBean<CustomMDCFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        final CustomMDCFilter customMDCFilter = new CustomMDCFilter("Correlation_ID", "MDCFilter.UUID");
        filterRegistrationBean.setFilter(customMDCFilter);
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
