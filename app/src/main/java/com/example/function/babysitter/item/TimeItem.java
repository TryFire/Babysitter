package com.example.function.babysitter.item;

import android.graphics.drawable.Drawable;

/**
 * Created by KongFanyang on 2015/11/20.
 */
public class TimeItem {
    private Drawable timeLinePhoto;
    private String timeLineTitle;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getTimeLinePhoto() {
        return timeLinePhoto;
    }

    public void setTimeLinePhoto(Drawable timeLinePhoto) {
        this.timeLinePhoto = timeLinePhoto;
    }

    public String getTimeLineTitle() {
        return timeLineTitle;
    }

    public void setTimeLineTitle(String timeLineTitle) {
        this.timeLineTitle = timeLineTitle;
    }
}
