package com.ldx.javaSpringBoot.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//设置为主键,并没有有拦截功能，所以需要去注册
@Component
public class RequestViewInterceptor implements HandlerInterceptor {

    private final static Logger LOGGER= LoggerFactory.getLogger(RequestViewInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("++++++++preHandle++++++++");
        return HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("++++++++postHandle++++++++");
        if (modelAndView==null || modelAndView.getViewName().startsWith("redirect")){
            return;
        }

        //获得路径,path是登录的路径,执行下面的操作会出现空指针异常，所以才有上面的判断
        String path=request.getServletPath();// /test/index2
        String template=(String) modelAndView.getModelMap().get("template");//??
        if (StringUtils.isBlank(template)){
            //由于返回的页面无/,而接口却有
            if (path.startsWith("/")){
                path=path.substring(1);//??
            }
            modelAndView.getModelMap().addAttribute("template",path.toLowerCase());
        }
       HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.debug("++++++++afterCompletion++++++++");
       HandlerInterceptor.super.preHandle(request,response,handler);
    }
}
