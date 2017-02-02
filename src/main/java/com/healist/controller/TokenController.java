package com.healist.controller;

import com.healist.annotation.Authorization;
import com.healist.annotation.CurrentUser;
import com.healist.entity.ResultModel;
import com.healist.entity.ResultStatus;
import com.healist.entity.TokenModel;
import com.healist.entity.User;
import com.healist.service.TokenManager;
import com.healist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by Healist on 2017/1/31.
 */
@Controller
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultModel> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");

        User user = userService.findByUsername(username);
        if (user == null ||  //未注册
                !user.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
