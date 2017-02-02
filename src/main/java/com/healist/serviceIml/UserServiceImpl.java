package com.healist.serviceIml;

import com.healist.dao.JobDao;
import com.healist.entity.EmailTaskModel;
import com.healist.entity.FocusListModel;
import com.healist.entity.JobInfoModel;
import com.healist.entity.User;
import com.healist.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Healist on 2017/1/31.
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    @Resource(name = "jobDao")
    private JobDao jobDao;

    public List<JobInfoModel> getJobInfo(int offset, int limit) {
        List<JobInfoModel> jobInfoModels = new ArrayList<JobInfoModel>();
        jobInfoModels = jobDao.getJobInfo(offset,limit);
        return jobInfoModels;
    }

    @Override
    public JobInfoModel getJobInfoById(int id) {
        return jobDao.getJobInfoById(id);
    }

    public User findByUsername(String name) {
        return jobDao.findByUsername(name);
    }

    @Override
    public User findOne(long userId) {
        return jobDao.findOne(userId);
    }

    @Override
    public int register(User user) {
        return jobDao.register(user);
    }

    @Override
    public int addEmailTask(EmailTaskModel emailTaskModel) {
        return jobDao.addEmailTask(emailTaskModel);
    }

    @Override
    public List<EmailTaskModel> selectAll() {
        return jobDao.selectAll();
    }

    @Override
    public List<FocusListModel> getFocusList(int userId) {
        return jobDao.getFocusList(userId);
    }

    @Override
    public int removeEmailTask(long userId, int reportId) {
        return jobDao.removeEmailTask(userId, reportId);
    }
}
