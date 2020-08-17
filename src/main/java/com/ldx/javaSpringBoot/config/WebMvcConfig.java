package com.ldx.javaSpringBoot.config;

import com.ldx.javaSpringBoot.filter.RequestParamFilter;
import com.ldx.javaSpringBoot.interceptor.RequestViewInterceptor;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
//没有拦截器之前的
//public class WebMvcConfig {
//拦截器注册的
    public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${server.http.port}")
    private int httpPort;
    @Autowired
    private RequestViewInterceptor requestViewInterceptor;

    @Bean
    public Connector connector(){
        Connector connector=new  Connector();
        connector.setPort(httpPort);
        connector.setScheme("http");
        return  connector;
    }
    @Bean
    public ServletWebServerFactory webServerFactory(){
        TomcatServletWebServerFactory tomcatServletWebServerFactory=new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector());
        return tomcatServletWebServerFactory;
    }
@Bean
    public FilterRegistrationBean<RequestParamFilter> register(){
      FilterRegistrationBean<RequestParamFilter> filterRegistrationBean=  new  FilterRegistrationBean<RequestParamFilter>();
      filterRegistrationBean.setFilter(new  RequestParamFilter());
      return filterRegistrationBean;
    }

    //重写addInterceptors,注入requestViewInterceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestViewInterceptor).addPathPatterns();//添加拦截器，匹配拦截的规则
    }
}
