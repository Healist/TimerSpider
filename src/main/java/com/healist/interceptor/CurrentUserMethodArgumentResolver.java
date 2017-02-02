package com.healist.interceptor;

import com.healist.annotation.CurrentUser;
import com.healist.config.Constants;
import com.healist.entity.User;
import com.healist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * Created by Healist on 2017/1/31.
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是User并且有CurrentUser注解则支持
        if (methodParameter.getParameterType().isAssignableFrom(User.class) &&
                methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        Long currentUserId = (Long) nativeWebRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            //从数据库中查询并返回
            return userService.findOne(currentUserId);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
