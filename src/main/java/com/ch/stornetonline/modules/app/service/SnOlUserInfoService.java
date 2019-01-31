package com.ch.stornetonline.modules.app.service;

import com.ch.stornetonline.common.bean.ResultBean;

public interface SnOlUserInfoService {

    ResultBean register(String mobile, String password) throws Exception;

    ResultBean login(String mobile, String password);

    ResultBean logout(String userId);

    ResultBean mobileVerifyCode(String mobile, String codeType);

    ResultBean modPwd(String mobile, String password);

    ResultBean state(String userId);

    ResultBean updatePwd(String userId, String oldPassword, String password);

    ResultBean info(String userId);

    ResultBean feedback(String userId,String msg);

    ResultBean getFeeEstimation(String userId,String duration, String size);

    ResultBean renterPay(String userId, String fee, String duration, String space);

    ResultBean getBalance(String userId);

    ResultBean getRenterInfo(String userId);

    ResultBean renterRechargeReview(String userId, String allowance);

    ResultBean getPurchaseHistory(String userId,String pageNum,String pageSize, String type);
}
