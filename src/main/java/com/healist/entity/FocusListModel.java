package com.healist.entity;

/**
 * Created by Healist on 2017/2/2.
 */
public class FocusListModel {
    private int reportId;

    public FocusListModel(){}

    public FocusListModel(int reportId) {
        this.reportId = reportId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "FocusListModel{" +
                "reportId=" + reportId +
                '}';
    }
}
