package com.ch.stornetonline.common.bean;

import com.ch.stornetonline.common.constants.ErrorConstants;

/**
 * @Description : 成功结果Bean
 * @Author :
 * @Creation Date : 2013-6-21 上午9:09:55
 */
public class SuccessBean extends ResultBean {

    public SuccessBean() {
        this.setSuccess(true);
        this.setErrorCode(ErrorConstants.ERRORCODE_OK);
    }

    public SuccessBean(Object result) {
        this.setSuccess(true);
        this.setErrorCode(ErrorConstants.ERRORCODE_OK);
        this.setResult(result);
    }

//	public SuccessBean() {
//		super();
//	}

    /**
     * 单一结果返回的快速方法
     *
     * @param k
     * @param v
     */
//    public SuccessBean(String k, Object v) {
//        this.setSuccess(true);
//        Map<String, Object> m = new HashMap<String, Object>();
//        m.put(k, v);
//        this.setResult(m);
//    }
}
