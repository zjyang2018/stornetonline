package com.ch.stornetonline.modules.app.controller;

import com.ch.stornetonline.common.bean.SuccessBean;
import com.ch.stornetonline.common.cache.JedisAPI;
import com.ch.stornetonline.common.constants.JedisNameConstants;
import com.ch.stornetonline.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ch.stornetonline.common.bean.ResultBean;
import com.ch.stornetonline.common.constants.ErrorConstants;
import com.ch.stornetonline.modules.app.service.SnOlUserInfoService;

import java.util.Map;

@RestController
@RequestMapping("/app/user")
public class SnOlUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(SnOlUserController.class);

    @Autowired
    private SnOlUserInfoService SnOlUserInfoService;

    @PostMapping("/reg")
    public ResultBean register(@RequestBody Map<String, Object> params) throws Exception {
        String mobile = getParam("mobile", params);
        String password = getParam("password", params);
        String verifyCode = getParam("verifyCode", params);
        String jcode = JedisAPI.get(JedisNameConstants.VAL_BIZAPP_USER_REG_VERIFYCODE + mobile);
        if (StringUtils.isNull(jcode)) {
            return new SuccessBean(ErrorConstants.ERRORMSG_VERIFYCODE_ERROR);
        }
        if (!jcode.equals(verifyCode)) {
            return new SuccessBean(ErrorConstants.ERRORMSG_VERIFYCODE_ERROR);
        }
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_REG_VERIFYCODE + mobile);
        return SnOlUserInfoService.register(mobile, password);
    }

    @PostMapping("/login")
    public ResultBean login(@RequestBody Map<String, Object> params) {
        String mobile = getParam("mobile", params);
        String password = getParam("password", params);
        return SnOlUserInfoService.login(mobile, password);
    }

    @GetMapping("/logout/{userId}")
    public ResultBean logout(@PathVariable("userId") String userId) {
        return SnOlUserInfoService.logout(userId);
    }

    @PostMapping("/mobilecode")
    public ResultBean mobileVerifyCode(@RequestBody Map<String, Object> params) {
        String mobile = getParam("mobile", params);
        String codeType = getParam("codeType", params);
        return SnOlUserInfoService.mobileVerifyCode(mobile, codeType);
    }

    @PostMapping("/modpwd")
    public ResultBean modPwd(@RequestBody Map<String, Object> params) {
        String mobile = getParam("mobile", params);
        String password = getParam("password", params);
        String verifyCode = getParam("verifyCode", params);
        String jcode = JedisAPI.get(JedisNameConstants.VAL_BIZAPP_USER_PWD_VERIFYCODE + mobile);
        if (StringUtils.isNull(jcode)) {
            return new SuccessBean(ErrorConstants.ERRORMSG_VERIFYCODE_ERROR);
        }
        if (!jcode.equals(verifyCode)) {
            return new SuccessBean(ErrorConstants.ERRORMSG_VERIFYCODE_ERROR);
        }
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_PWD_VERIFYCODE + mobile);
        return SnOlUserInfoService.modPwd(mobile, password);
    }

    @PostMapping("/state")
    public ResultBean state(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        return SnOlUserInfoService.state(userId);
    }

    @PostMapping("/updatepwd")
    public ResultBean updatePwd(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String oldPassword = getParam("oldPassword", params);
        String password = getParam("password", params);
        return SnOlUserInfoService.updatePwd(userId, oldPassword, password);
    }

    @PostMapping("/info")
    public ResultBean info(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        return SnOlUserInfoService.info(userId);
    }

    @PostMapping("/feedback")
    public ResultBean feedback(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String msg = getParam("msg", params);
        return SnOlUserInfoService.feedback(userId, msg);
    }

    /**
     * 用户充值申请
     *
     * @param params
     * @return
     */
    @PostMapping("/rechargereview")
    public ResultBean renterRechargeReview(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String allowance = getParam("allowance", params);
        return SnOlUserInfoService.renterRechargeReview(userId, allowance);
    }

    /**
     * 费用预估
     *
     * @param params
     * @return
     */
    @PostMapping("/feeEstimation")
    public ResultBean getFeeEstimation(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String duration = getParam("duration", params);
        String size = getParam("size", params);
        return SnOlUserInfoService.getFeeEstimation(userId, duration, size);
    }

    /**
     * 支付
     *
     * @param params
     * @return
     */
    @PostMapping("/pay")
    public ResultBean renterPay(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String fee = getParam("fee", params);
        String duration = getParam("duration", params);
        String space = getParam("space", params);
        return SnOlUserInfoService.renterPay(userId, fee, duration, space);
    }


    /**
     * 获取用户钱包余额
     *
     * @param params
     * @return
     */
    @PostMapping("/balance")
    public ResultBean getBalance(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        return SnOlUserInfoService.getBalance(userId);
    }


    /**
     * 获取用户租用信息
     *
     * @param params
     * @return
     */
    @PostMapping("/renterInfo")
    public ResultBean getRenterInfo(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        return SnOlUserInfoService.getRenterInfo(userId);
    }

    /**
     * 获取用户流水账记录
     *
     * @param params
     * @return
     */
    @PostMapping("/purchaseHistory")
    public ResultBean getPurchaseHistory(@RequestBody Map<String, Object> params) {
        String userId = getParam("userId", params);
        String pageNum = getParam("pageNum", params);
        String pageSize = getParam("pageSize", params);
        String type = getParam("type", params);

        return SnOlUserInfoService.getPurchaseHistory(userId,pageNum,pageSize,type);
    }
}
