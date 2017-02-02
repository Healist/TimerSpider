package com.healist.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by Healist on 2017/1/29.
 */
public class TestEmail {

    @Test
    public void testExecute() {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
//        JavaMailSender sender = (JavaMailSender) ctx.getBean("javaMailSender");
//        System.out.println("测试发送邮件.....");
//        SimpleMailMessage mail = new SimpleMailMessage();
//        try {
//            mail.setTo("382808591@qq.com");// 接受者
//            mail.setFrom("794622537@qq.com");// 发送者,
//            mail.setSubject("测试主题");// 主题
//            mail.setText("发送邮件内容测试");// 邮件内容
//            sender.send(mail);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}


