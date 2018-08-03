package com.whu.zengbin.bingocodenews.event;

import android.text.TextUtils;

import com.whu.zengbin.bingocodenews.common.ConstraintUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zengbin on 2018/2/15.
 */
@Entity
public class NewsInfo {

    @Id
    private String id;
    private String createdAt;
    private String desc;
    private String images;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private Boolean used;
    private String who;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt.substring(0,10);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public NewsInfo() {

    }

    public NewsInfo(JSONObject jsonObject) {
        if (TextUtils.isEmpty(jsonObject.optString(ConstraintUtil.ID))) {
            setId(jsonObject.optString(ConstraintUtil.GANHUOID));
        } else {
            setId(jsonObject.optString(ConstraintUtil.ID));
        }
        setCreatedAt(jsonObject.optString(ConstraintUtil.CREATEAT));
        setDesc(jsonObject.optString(ConstraintUtil.DESC));
        JSONArray imagesArray = jsonObject.optJSONArray(ConstraintUtil.IMAGES);
        if (imagesArray != null && imagesArray.length() > 0) {
            try {
                setImages(jsonObject.optJSONArray(ConstraintUtil.IMAGES).getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setPublishedAt(jsonObject.optString(ConstraintUtil.PUBLISHAT));
        setSource(jsonObject.optString(ConstraintUtil.SOURCE));
        setType(jsonObject.optString(ConstraintUtil.TYPE));
        setUrl(jsonObject.optString(ConstraintUtil.URL));
        setUsed(jsonObject.optBoolean(ConstraintUtil.USED));
        setWho(jsonObject.optString(ConstraintUtil.WHO));

    }

    @Generated(hash = 159269385)
    public NewsInfo(String id, String createdAt, String desc, String images,
            String publishedAt, String source, String type, String url, Boolean used,
            String who) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.images = images;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }
}
