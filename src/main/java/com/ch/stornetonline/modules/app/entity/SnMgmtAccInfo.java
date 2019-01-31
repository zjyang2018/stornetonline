package com.ch.stornetonline.modules.app.entity;

public class SnMgmtAccInfo {
    private String id;

    private String sncWalletid;

    private Long sncTotalAmount;

    private String sncRevaddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSncWalletid() {
        return sncWalletid;
    }

    public void setSncWalletid(String sncWalletid) {
        this.sncWalletid = sncWalletid == null ? null : sncWalletid.trim();
    }

    public Long getSncTotalAmount() {
        return sncTotalAmount;
    }

    public void setSncTotalAmount(Long sncTotalAmount) {
        this.sncTotalAmount = sncTotalAmount;
    }

    public String getSncRevaddress() {
        return sncRevaddress;
    }

    public void setSncRevaddress(String sncRevaddress) {
        this.sncRevaddress = sncRevaddress == null ? null : sncRevaddress.trim();
    }
}