package com.healist.service;

import com.healist.entity.MailModel;

/**
 * Created by Healist on 2017/1/28.
 */
public interface EmailService {
    public void emailManage();

    public void sendEmail(MailModel mail);

}
