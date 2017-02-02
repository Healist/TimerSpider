package com.healist.timer;

import com.healist.entity.EmailTaskModel;
import com.healist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Healist on 2017/2/2.
 */
@Component
public class EmailTaskExecuter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender sender;

    @Autowired
    private UserService userService;

    //上午7点到晚上22点，没一个小时查询发送一次
    @Scheduled(cron = "0 0/59 7,22 * * ?")
    public void emailExecutorRun() {
        //log
        logger.info("正在执行email检查发送任务...");

        SimpleMailMessage mail = new SimpleMailMessage();

        //获取当前时间
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);

        //获取数据库的任务，拿报告的时间与当前时间做对比，小于一个小时就发送邮件
        List<EmailTaskModel> emailTaskModels = userService.selectAll();
        for (EmailTaskModel emailModel : emailTaskModels) {
            Date date = emailModel.getExecuteDay();
            int execute_year = date.getYear() + 1900;
            int execute_month = date.getMonth()+1;
            int execute_day = date.getDate();
            int execute_hour = date.getHours();

            if(year == execute_year && month == execute_month && day == execute_day) {
                if(execute_hour - hour > 0 && execute_hour - hour <= 1) {
                    //满足要求，发送邮件
                    try {
                        mail.setTo(emailModel.getUserEmail());// 接受者
                        mail.setFrom("794622537@qq.com");// 发送者,
                        mail.setSubject("宣讲会通知");// 主题
                        mail.setText("您好，你关注的宣讲会将在一个小时后举行，请提前做好准备。");// 邮件内容
                        sender.send(mail);
                        //执行过后删除这个任务
                        int flag = userService.removeEmailTask(emailModel.getUserId(), emailModel.getReportId());
                        if(flag <= 0) {
                            //通过日志记录失败的记录
                            logger.info("清除执行完成的EmailTask任务失败（userId:" + emailModel.getUserId() + "," + emailModel.getReportId() + "）");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
