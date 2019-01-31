package com.ch.stornetonline.modules.app.entity;

public class SnMgmtChaSn {
    private String id;

    private String password;

    private Long currAmount;

    private String recvAddress;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Long getCurrAmount() {
        return currAmount;
    }

    public void setCurrAmount(Long currAmount) {
        this.currAmount = currAmount;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress == null ? null : recvAddress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}