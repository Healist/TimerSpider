package com.healist.dao;

import com.healist.entity.EmailTaskModel;
import com.healist.entity.FocusListModel;
import com.healist.entity.JobInfoModel;
import com.healist.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Healist on 2017/1/29.
 */
public interface JobDao {

    int saveJobInfo(JobInfoModel jobInfoModel);

    int query(String url);

    List<JobInfoModel> getJobInfo(@Param("offset") int offset, @Param("limit") int limit);

    User findByUsername(String name);

    User findOne(long id);

    int register(User user);

    JobInfoModel getJobInfoById(int id);

    int addEmailTask(EmailTaskModel emailTaskModel);

    List<EmailTaskModel> selectAll();

    List<FocusListModel> getFocusList(int userId);

    int removeEmailTask(@Param("userId") long userId, @Param("reportId") int reportId);

}
