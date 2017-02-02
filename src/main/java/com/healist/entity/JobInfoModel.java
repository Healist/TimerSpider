package com.healist.entity;

import java.util.Date;

/**
 * Created by Healist on 2017/1/29.
 */
public class JobInfoModel {
    private int id;
    private String title;
    private String dates;
    private String places;
    private String url;
    private Date down_date;

    public JobInfoModel(){}

    public JobInfoModel(String title, String dates, String places, String url, Date down_date) {
        this.title = title;
        this.dates = dates;
        this.places = places;
        this.url = url;
        this.down_date = down_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDown_date() {
        return down_date;
    }

    public void setDown_date(Date down_date) {
        this.down_date = down_date;
    }

    @Override
    public String toString() {
        return "JobInfoModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dates='" + dates + '\'' +
                ", places='" + places + '\'' +
                ", url='" + url + '\'' +
                ", down_date=" + down_date +
                '}';
    }
}
