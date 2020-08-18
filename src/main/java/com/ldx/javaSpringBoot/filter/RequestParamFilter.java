package com.ldx.javaSpringBoot.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
/**
 * 过滤器
 * 步骤，得到前端数据，建立过滤器，将过滤器注册到容器中（WebMvcConfig（FilterRegistrationBean）），查看配置是否允许（live-》div）,得到控制器的request,
 * 使用包装类，重写getparam等方法，判断值是否存在，然后进行替换，然后返回。
 * */

//过滤器注解
//urlPatterns = "/**"过滤所有
@WebFilter(filterName = "requestParamFilter",urlPatterns = "/**")
@Order(1)//过滤器的优先级，越小越优先，多个过滤器使用
//过滤器实现servlet的Filter
public class RequestParamFilter implements Filter {
//日志
    private  final  static Logger LOGGER= LoggerFactory.getLogger(RequestParamFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("=========== init request param filter");
    }

    @Override
    public void destroy() {
        LOGGER.debug("=========== destroy request param filter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug("=========== doFilter request param filter");
        //字符串的请求参数是ServletRequest，但是控制器的是HttpServletRequest，所以需要强转
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        //查看getParameterMap的类型是Map<String,String[]>
//       Map<String,String[]> paramMap=httpServletRequest.getParameterMap();
//       paramMap.put("param",new String[]{"***"});//只到这里的话会报错，因为这个是被锁住，所以需要其他方法来操作

        //提供了包装类值的替换
        //重写了getParameter方法,外部就可以调用这个方法,name就是test中的getParameter，还要将全局配置文件INFO换成DEBUG
        HttpServletRequestWrapper wrapper=new HttpServletRequestWrapper(httpServletRequest) {
            @Override
            public String getParameter(String name) {
                String value = httpServletRequest.getParameter(name);//这里是从test中传来的数据进行判断
                if (StringUtils.isNoneBlank(value)) {
                    return value.replaceAll("fuck", "***");//替换
                }
                return super.getParameter(name);//只得到value2的值
            }
            //得到所有的parame的值
            @Override
            public String[] getParameterValues(String name) {
               String[] values=  httpServletRequest.getParameterValues(name);
               if (values!=null && values.length>0){
                   for (int i=0;i<values.length;i++){
                       values[i]=values[i].replaceAll("fuck","***");
                   }
                   return  values;
               }
                return super.getParameterValues(name);
            }
        };
        //需要通过filterChain将servletRequest，servletResponse交出去,由于处理的是重写的wrapper所以丢的是wrapper
        filterChain.doFilter(wrapper,servletResponse);
    }
}
