package com.healist.test;

import com.healist.dao.JobDao;
import com.healist.entity.EmailTaskModel;
import com.healist.entity.JobInfoModel;
import com.healist.entity.User;
import com.healist.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by Healist on 2017/1/30.
 */

public class TestDataBase {
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        JobDao jobDao = (JobDao) ctx.getBean("jobDao");
        //Date date = new Date();
//        JobInfoModel jobInfoModel = new JobInfoModel("科大国盾量子技术股份有限公司", "东风报告厅",
//                "2016/11/06  10:00", "http://gdjy.hfut.edu.cn/JobIn/JobMeetingInside.jsp?uid=9195", date);
        //int saveFlag = jobDao.saveJobInfo(jobInfoModel);
        //int saveFlag = jobDao.query("http://gdjy.hfut.edu.cn/JobIn/JobMeetingInside.jsp?uid=91");
//        System.out.println("saveFlag:" + saveFlag);
//        User user = jobDao.findByUsername("admin");
//        User user = jobDao.findOne(1);
//        System.out.println(user);
//        User user = new User("Healist", "123456", "1234567@qq.com", "male");
//        int saveFlag = jobDao.register(user);
//        System.out.println(saveFlag);
//        List list = jobDao.getFocusList(2);
//        System.out.println(list);
        //Date date1 = new Date(2016-1900,11, 9,9, 0, 0);
//        EmailTaskModel emailTaskModel = new EmailTaskModel(2, 77, date1, "794622537@qq.com");
//        int flag = jobDao.addEmailTask(emailTaskModel);
//        System.out.println(flag);
        List<EmailTaskModel> emailTaskModels = jobDao.selectAll();
        for (EmailTaskModel emailModel : emailTaskModels) {
            Date date = emailModel.getExecuteDay();
            int execute_year = date.getYear() + 1900;
            int execute_month = date.getMonth()+1;
            int execute_day = date.getDate();
            int execute_hour = date.getHours();

            System.out.println(execute_year + "_" + execute_month + "_" + execute_day + "_" + execute_hour);
        }
    }

}
