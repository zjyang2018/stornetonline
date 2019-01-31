package com.ch.stornetonline.modules.app.entity;

public class SnMgmtGenaccInfo {
    private String id;

    private String curType;

    private Long sncTotalAmount;

    private String sncHeadlessAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCurType() {
        return curType;
    }

    public void setCurType(String curType) {
        this.curType = curType == null ? null : curType.trim();
    }

    public Long getSncTotalAmount() {
        return sncTotalAmount;
    }

    public void setSncTotalAmount(Long sncTotalAmount) {
        this.sncTotalAmount = sncTotalAmount;
    }

    public String getSncHeadlessAddress() {
        return sncHeadlessAddress;
    }

    public void setSncHeadlessAddress(String sncHeadlessAddress) {
        this.sncHeadlessAddress = sncHeadlessAddress == null ? null : sncHeadlessAddress.trim();
    }
}