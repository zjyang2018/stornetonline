package com.ch.stornetonline.common.bean;

/**
 * @Description : 结果bean父类
 * @Author :
 * @Creation Date : 2013-6-21 上午9:01:45
 */
public class ResultBean {

    private boolean success;

    private Object result;

    private String errorCode;

    public ResultBean() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }

    public String toJsonString() {
        String tf = (success == true) ? "true" : "false";
        return "{\"success\":" + tf + ",\"errorCode\":\"" + this.getErrorCode() + "\",\"result\":\"" + this.getResult().toString() + "\"}";
        //{"success":false,"result":"签名体校验不对","errorCode":"100006"}

    }
}
