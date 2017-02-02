package com.healist.controller;

import com.healist.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by Healist on 2017/1/28.
 */
@Controller
@RequestMapping("/email")
public class EmailTaskController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EmailService emailService;

    @RequestMapping("/sendEmailTask")
    public void sendEmailTask() {
        logger.info("————————执行邮件start————————");
        emailService.emailManage();
        logger.info("————————执行邮件end————————");
    }
}
