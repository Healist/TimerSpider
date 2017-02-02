package com.healist.controller;

import com.healist.entity.*;
import com.healist.service.TokenManager;
import com.healist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Healist on 2017/1/31.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/jobInfo",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<JobInfoModel> getJobInfo(@RequestParam int offset, @RequestParam int limit) {
        return userService.getJobInfo(offset, limit);
    }

    @RequestMapping(value = "/userInfo",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public  ResponseEntity<ResultModel> getUserInfo(@RequestParam String username, @RequestParam String token){
        User user = userService.findByUsername(username);
        if(user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        } else {
            TokenModel tokenModel = new TokenModel(user.getId(), token);
            boolean result = tokenManager.checkToken(tokenModel);
            if(result) { // 验证token
                userService.findByUsername(username);
                return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
            } else {
                //token不符合要求
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
            }
        }
    }


    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ResultModel> register(@RequestParam String username, @RequestParam String password,
                                                @RequestParam String email, @RequestParam String sex) {
        User user = new User(username, password, email, sex);
        //检查用户名是否已经存在
        User checkUser = userService.findByUsername(username);
        if(checkUser == null) {
            int saveFlag = userService.register(user);
            System.out.println(saveFlag);
            if(saveFlag > 0) {
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/focus",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ResultModel> focus(@RequestParam String username, @RequestParam String token, @RequestParam int reportId) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        } else {
            TokenModel tokenModel = new TokenModel(user.getId(), token);
            boolean result = tokenManager.checkToken(tokenModel);
            if(result) { // 验证token
                //查询对应report
                JobInfoModel jobInfoModel = userService.getJobInfoById(reportId);
                if(jobInfoModel==null) {
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
                } else {
                    //根据report的执行时间来进一步加工
                    String report_date = jobInfoModel.getDates();
                    report_date = report_date.replaceAll("：", ":");
                    report_date = report_date.replaceAll("  ", "　");
                    report_date = report_date.replaceAll("  ", " ");
                    System.out.println(report_date);
                    Date date = analyse(report_date);
                    //插入任务到任务执行表
                    EmailTaskModel emailTaskModel = new EmailTaskModel(user.getId(), reportId, date, user.getEmail());
                    int flag = userService.addEmailTask(emailTaskModel);
                    if(flag > 0) {
                        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                //token不符合要求
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/cancelfocus",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ResultModel> cancelfocus(@RequestParam String username, @RequestParam String token, @RequestParam int reportId) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        } else {
            TokenModel tokenModel = new TokenModel(user.getId(), token);
            boolean result = tokenManager.checkToken(tokenModel);
            if(result) { // 验证token
                //查询对应report
                JobInfoModel jobInfoModel = userService.getJobInfoById(reportId);
                if(jobInfoModel==null) {
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
                } else {
                    //删除对应EmailTask任务
                    int flag = userService.removeEmailTask(user.getId(), reportId);
                    if(flag > 0) {
                        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                //token不符合要求
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/focuslist",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ResultModel> getFocusList(@RequestParam String username, @RequestParam String token) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
        } else {
            TokenModel tokenModel = new TokenModel(user.getId(), token);
            boolean result = tokenManager.checkToken(tokenModel);
            if(result) { // 验证token
                //获取focusList
                int userId = (int) user.getId();
                List<FocusListModel> list = userService.getFocusList(userId);
                return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);
            } else {
                //token不符合要求
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.BAD_REQUEST);
            }
        }
    }

    private Date analyse(String execute_day) {
        String year = execute_day.split("　")[0].split("\\/")[0];
        String month = execute_day.split("　")[0].split("\\/")[1];
        String day = execute_day.split("　")[0].split("\\/")[2];
        String hour = execute_day.split("　")[1].split(":")[0];
        Date date = new Date(Integer.valueOf(year)-1900, Integer.valueOf(month)-1, Integer.valueOf(day), Integer.valueOf(hour),0, 0);
        return date;
    }

}
