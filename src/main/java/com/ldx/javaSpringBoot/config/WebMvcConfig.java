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
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
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
    @Autowired
    private ResourceConfigBean resourceConfigBean;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");//得到操作系统的名字；Windows7
        if (osName.toLowerCase().startsWith("win")) {//toLowerCase小写，startsWith头部,//判断系统类类别
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX +//FILE_URL_PREFIX代表的是本地路径
                            resourceConfigBean.getLocationPathForWindows());
        } else {
            registry.addResourceHandler(resourceConfigBean.getRelativePathPattern())
                    .addResourceLocations(ResourceUtils.FILE_URL_PREFIX +
                            resourceConfigBean.getLocationPathForLinux());
        }
    }

    //1欢迎页面  2可以将页面当做静态页面，放到static
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //addViewController根目录
        //由于做了shiro，所以也要对这个页面进行配置
        registry.addViewController("/").setViewName("wellcome");
    }
}
