package com.ch.stornetonline.common.bean;

/**
 * @Description : 错误结果bean
 * @Author :
 * @Creation Date : 2013-6-21 上午9:00:51
 */
public class ErrorBean extends ResultBean {

	// public ErrorBean() {
	// this.setSuccess(false);
	// }

	public ErrorBean(String errorCode, Object result) {
		this.setSuccess(false);
		this.setResult(result);
		this.setErrorCode(errorCode);
	}

	
}
