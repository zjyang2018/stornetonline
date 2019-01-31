package com.ch.stornetonline.modules.app.entity;

import java.math.BigDecimal;

public class SnOlUserBuy {
    private String id;

    private String userId;

    private BigDecimal purchaseCount;

    private String payVoucher;

    private String isExamine;

    private String examinDesc;

    private Long examinTime;

    private String createBy;

    private Long createTime;

    private String updateBy;

    private Long updateTime;

    private String remarks;

    private String delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(BigDecimal purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getPayVoucher() {
        return payVoucher;
    }

    public void setPayVoucher(String payVoucher) {
        this.payVoucher = payVoucher == null ? null : payVoucher.trim();
    }

    public String getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(String isExamine) {
        this.isExamine = isExamine == null ? null : isExamine.trim();
    }

    public String getExaminDesc() {
        return examinDesc;
    }

    public void setExaminDesc(String examinDesc) {
        this.examinDesc = examinDesc == null ? null : examinDesc.trim();
    }

    public Long getExaminTime() {
        return examinTime;
    }

    public void setExaminTime(Long examinTime) {
        this.examinTime = examinTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}