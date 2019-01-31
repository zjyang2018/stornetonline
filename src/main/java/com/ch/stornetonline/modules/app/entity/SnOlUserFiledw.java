package com.ch.stornetonline.modules.app.entity;

import java.util.Date;

public class SnOlUserFiledw {
    private String fileid;

    private String userid;

    private String path;

    private String name;

    private Long size;

    private Long downloadTime;

    private Integer downloadConut;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Integer getDownloadConut() {
        return downloadConut;
    }

    public void setDownloadConut(Integer downloadConut) {
        this.downloadConut = downloadConut;
    }
}