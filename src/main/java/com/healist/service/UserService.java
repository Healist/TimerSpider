package com.healist.service;

import com.healist.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Healist on 2017/1/31.
 */
public interface UserService {
    List<JobInfoModel> getJobInfo(int offset, int limit);

    JobInfoModel getJobInfoById(int id);

    User findByUsername(String name);

    User findOne(long userId);

    int register(User user);

    int addEmailTask(EmailTaskModel emailTaskModel);

    List<EmailTaskModel> selectAll();

    List<FocusListModel> getFocusList(int userId);

    int removeEmailTask(long userId, int reportId);
}
