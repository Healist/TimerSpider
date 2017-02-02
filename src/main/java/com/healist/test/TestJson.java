package com.healist.test;

import com.healist.entity.JobInfoModel;
import com.healist.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Healist on 2017/1/31.
 */

public class TestJson {

    @Test
    public void testJson() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        UserService userService = (UserService) ctx.getBean("UserService");
        List<JobInfoModel> jobInfoModels;
        jobInfoModels = userService.getJobInfo(0, 10);
        System.out.println(jobInfoModels);
    }
}
