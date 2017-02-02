package com.healist.entity;

import java.util.Date;

/**
 * Created by Healist on 2017/2/1.
 */
public class EmailTaskModel {
    private int id;
    private long userId;
    private int reportId;
    private Date executeDay;
    private String userEmail;

    public EmailTaskModel(){}

    public EmailTaskModel(long userId, int reportId, Date executeDay, String userEmail) {
        this.userId = userId;
        this.reportId = reportId;
        this.executeDay = executeDay;
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getExecuteDay() {
        return executeDay;
    }

    public void setExecuteDay(Date executeDay) {
        this.executeDay = executeDay;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "EmailTaskModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", reportId=" + reportId +
                ", executeDay=" + executeDay +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
