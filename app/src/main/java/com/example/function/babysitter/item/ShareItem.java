package com.example.function.babysitter.item;

import android.graphics.drawable.Drawable;

/**
 * Created by KongFanyang on 2015/11/21.
 */
public class ShareItem {
    private Drawable sharePhoto;
    private String shareContent;

    public Drawable getSharePhoto() {
        return sharePhoto;
    }

    public void setSharePhoto(Drawable sharePhoto) {
        this.sharePhoto = sharePhoto;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }
}
