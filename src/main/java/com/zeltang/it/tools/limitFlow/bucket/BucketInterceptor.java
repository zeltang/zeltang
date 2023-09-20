package com.zeltang.it.tools.limitFlow.bucket;

import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 令牌桶拦截器
 */
public class BucketInterceptor implements HandlerInterceptor {
    //预处理回调方法，在接口调用之前使用true代表放行false代表不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        BucketAnnotation methodAnnotation = method.getAnnotation(BucketAnnotation.class);
        if (methodAnnotation != null) {
            //在名为：bucket的令牌桶里取令牌取到即放行未取到即抛出异常
            if(BucketUtil.buckets.get("bucket").getToken()) {
                return true;
            } else {
                //抛出自定义异常
                throw new BucketException("不好意思，您被限流了");
            }
        } else {
            return true;
        }
    }

    //接口调用之后，返回之前使用
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception{

    }

    //整个请求完成后，在视图渲染前使用
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,Exception e) throws Exception{

    }
}
