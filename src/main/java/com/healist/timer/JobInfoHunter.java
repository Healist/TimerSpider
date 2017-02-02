package com.healist.timer;

import com.healist.dao.JobDao;
import com.healist.entity.JobInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Healist on 2017/1/29.
 */
@Component
public class JobInfoHunter implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setTimeOut(30000).setSleepTime(3000);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public synchronized void process(Page page) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        JobDao jobDao = (JobDao) ctx.getBean("jobDao");
        if(page.getUrl().regex("http://gdjy\\.hfut\\.edu\\.cn/JobIn/JobMeetingInside\\.jsp\\?uid=[0-9]+").match()){

            String title = page.getHtml()
                    .xpath("//table/tbody/tr/td[2]/table/tbody/tr[2]/td[@align='center']/table/tbody/tr[2]/td[2]/text()").get();
            String date = page.getHtml()
                    .xpath("//table/tbody/tr/td[2]/table/tbody/tr[2]/td[@align='center']/table/tbody/tr[5]/td[2]/tidyText()").get();
            String where = page.getHtml()
                    .xpath("//table/tbody/tr/td[2]/table/tbody/tr[2]/td[@align='center']/table/tbody/tr[6]/td[2]/text()").get();
            Date down_date = new Date();

            if(title == null) {
                page.setSkip(true);
            }

            JobInfoModel jobInfo = new JobInfoModel(title, date, where, page.getUrl().toString(), down_date);
            int saveFlag = jobDao.saveJobInfo(jobInfo);
            if(saveFlag > 0) {
                logger.info("成功插入数据了:" + page.getUrl());
                System.out.println("成功插入数据了");
            }
        } else {
            List<String> links = page.getHtml().xpath("//table/tbody/tr[9]/td[@colspan='8']").links().all();
            for (String link:links) {
                System.out.println(link);
                int check = jobDao.query(link);
                System.out.println(check);
                System.out.println("__________________");
                if(check > 0) {
                    logger.info("已经爬取过该url：" + link);
                    System.out.println("已经爬取过该url：" + link);
                } else {
                    page.addTargetRequest(link);
                }
            }
        }
    }

    public Site getSite() {
        return site;
    }

    @Scheduled(cron = "0 0 9,14,23 * * ?")
    public void timerRun() {
        //log
        logger.info("正在执行爬取报告会信息的爬虫任务...");

        Spider.create(new JobInfoHunter())
                .addUrl("http://gdjy.hfut.edu.cn/").thread(1).run();
    }

}
