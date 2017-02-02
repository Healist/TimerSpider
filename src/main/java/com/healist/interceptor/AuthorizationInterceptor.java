package com.healist.interceptor;

import com.healist.annotation.Authorization;
import com.healist.config.Constants;
import com.healist.entity.TokenModel;
import com.healist.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by Healist on 2017/1/31.
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = httpServletRequest.getHeader(Constants.AUTHORIZATION);
        //验证token
        TokenModel model = tokenManager.getToken(authorization);
        if (tokenManager.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
            httpServletRequest.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
