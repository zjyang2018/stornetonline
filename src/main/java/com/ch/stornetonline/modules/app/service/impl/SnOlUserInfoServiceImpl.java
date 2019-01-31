package com.ch.stornetonline.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.ch.stornetonline.common.bean.ErrorBean;
import com.ch.stornetonline.common.bean.ResultBean;
import com.ch.stornetonline.common.bean.SuccessBean;
import com.ch.stornetonline.common.cache.JedisAPI;
import com.ch.stornetonline.common.utils.MoneyUtils;
import com.ch.stornetonline.common.utils.fileutils.PageUtils;
import com.ch.stornetonline.modules.app.common.Constants;
import com.ch.stornetonline.common.constants.ErrorConstants;
import com.ch.stornetonline.common.constants.JedisNameConstants;
import com.ch.stornetonline.common.utils.SequenceManager;
import com.ch.stornetonline.common.utils.UUIDGenerator;
import com.ch.stornetonline.common.utils.VerificationCodeUtils;
import com.ch.stornetonline.modules.app.common.FileConstants;
import com.ch.stornetonline.modules.app.config.CommonConfig;
import com.ch.stornetonline.modules.app.entity.*;
import com.ch.stornetonline.modules.app.entity.service.UserToken;
import com.ch.stornetonline.modules.app.mapper.*;
import com.ch.stornetonline.modules.app.service.SnOlUserInfoService;
import com.ch.stornetonline.modules.app.sms.AliyunSmsSend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SnOlUserInfoService")
@Transactional
public class SnOlUserInfoServiceImpl extends BaseServiceImpl implements SnOlUserInfoService {
    private static final Logger log = LoggerFactory.getLogger(SnOlUserInfoServiceImpl.class);

    @Resource
    SnOlUserInfoMapper snOlUserInfo;

    @Resource
    SnOlUserWalletMapper snOlUserWalletMapper;

    @Resource
    SnOlUserInfoFeedbackMapper snOlUserInfoFeedbackMapper;

    @Resource
    SnMgmtGenaccRecMapper snMgmtGenaccRecMapper;

    @Resource
    SnMgmtGenaccInfoMapper snMgmtGenaccInfoMapper;

    @Resource
    SnOlUserSpaceMapper snOlUserSpaceMapper;

    @Resource
    SnOlUserSpaceRecMapper snOlUserSpaceRecMapper;

    @Resource
    SnOlUserWalletRecMapper snOlUserWalletRecMapper;

    @Resource
    SnOlUserBuyMapper snOlUserBuyMapper;

    @Override
    public ResultBean register(String mobile, String password) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        SnOlUserInfo user = snOlUserInfo.selectOneByMap(params);
        if (user != null) {
            return new SuccessBean(ErrorConstants.ERRORMSG_REREG_ERROR);
        }
        //TODO AES解密
        String realPassword = password;
        String hashPassword = DigestUtils.sha256Hex(realPassword);
        String userNo = String.valueOf(SequenceManager.nextID(Constants.USER_NO));
        // Mnemonic mn = new Mnemonic("shield salmon sport horse cool hole pool panda embark wrap fancy equip", "asdf");

        //String walletAddr = mn.getNewAddress(0, 0, 0, Integer.parseInt(userNo), 1);

//        SnMgmtChaInfo snMgmtChaInfo = snMgmtChaInfoMapper.selectByPrimaryKey("123214444");
//        String host_addr = snMgmtChaInfo.getIp() + snMgmtChaInfo.getPort();
//
//        SnMgmtChaSn snMgmtChaSn = snMgmtChaSnMapper.selectByPrimaryKey("123214444");
//        String wallet_pwd = snMgmtChaSn.getPassword();
//
//        String walletAddr = Utils.getWalletAddress(host_addr,wallet_pwd); //mn.getNewAddress(0, 0, 0, Integer.parseInt(userNo), 1);

        String headPortrait = "";
        String userId = UUIDGenerator.getUUID();
        long nowTime = System.currentTimeMillis();
        user = new SnOlUserInfo();
        user.setId(userId);
        user.setUserNo(userNo);
        user.setUserName(mobile);
        user.setRealName("");
        user.setMobile(mobile);
        user.setEmail("");
        user.setPassword(hashPassword);
        user.setSalt("");
        user.setHeadPortrait(headPortrait);
        user.setCountryCode("");
        user.setBirthday("");
        user.setAddr("");
        user.setReferrer("");
        user.setCreateBy(userId);
        user.setCreateTime(nowTime);
        user.setUpdateBy("");
        user.setUpdateTime(nowTime);
        user.setRemarks("");
        user.setDelFlag("0");
        int n = snOlUserInfo.insertSelective(user);


        // 新建用户钱包账号
        SnOlUserWallet snOlUserWallet = new SnOlUserWallet();
        snOlUserWallet.setUserid(userId);
        // 分配用户钱包地址
        String addr = CommonConfig.getWalletAddress();
        snOlUserWallet.setAddress(addr);
        int m = snOlUserWalletMapper.insertSelective(snOlUserWallet);

        // 分配用户空间
        SnOlUserSpace snOlUserSpace = new SnOlUserSpace();
        snOlUserSpace.setId(UUIDGenerator.getUUID());
        snOlUserSpace.setUserid(userId);
        snOlUserSpace.setTotalSpace(0l);
        snOlUserSpace.setFreeSpace(0l);
        snOlUserSpace.setCreateTime(System.currentTimeMillis());
        snOlUserSpace.setUpdateTime(System.currentTimeMillis());
        int x = snOlUserSpaceMapper.insertSelective(snOlUserSpace);

        UserToken userToken = new UserToken();
        if (n == 1 && m == 1 && x == 1) {
            String token = UUIDGenerator.getUUID();
            userToken.setUserId(userId);
            userToken.setUserNo(userNo);
            userToken.setUserName(mobile);
            userToken.setHeadPortrait(headPortrait);
            userToken.setToken(token);
            JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_PHONE + mobile, JSON.toJSONString(user));
            JedisAPI.expire(JedisNameConstants.VAL_BIZAPP_USER_PHONE + mobile, Constants.USER_EXPIRE_TIME);
            JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId, JSON.toJSONString(userToken));
            JedisAPI.expire(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId, Constants.TOKEN_EXPIRE_TIME);
            return new SuccessBean(userToken);
        } else {
            return new SuccessBean(ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        }
    }

    @Override
    public ResultBean login(String mobile, String password) {
        SnOlUserInfo user = getUserByMobile(mobile);
        if (user == null) {
            return new ErrorBean(ErrorConstants.ERRORCODE_USER_NOEXIST, ErrorConstants.ERRORMSG_USER_NOEXIST);
        }
        //TODO AES解密
        String realPassword = password;
        if (!(user.getPassword().equals(DigestUtils.sha256Hex(realPassword)))) {
            return new ErrorBean(ErrorConstants.ERRORCODE_PWD_ERROR, ErrorConstants.ERRORMSG_PWD_ERROR);
        }
        String userId = user.getId();
        String userNo = user.getUserNo();
        String userName = user.getUserName();
        String headPortrait = user.getHeadPortrait();
        String token = UUIDGenerator.getUUID();
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setUserNo(userNo);
        userToken.setUserName(userName);
        userToken.setHeadPortrait(headPortrait);
        userToken.setToken(token);
        JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId, JSON.toJSONString(userToken));
        JedisAPI.expire(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId, Constants.TOKEN_EXPIRE_TIME);
        return new SuccessBean(userToken);
    }

    @Override
    public ResultBean logout(String userId) {
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId);
        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean mobileVerifyCode(String mobile, String codeType) {
        String code = VerificationCodeUtils.genRegCode();
        if ("1".equals(codeType)) {
            JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_REG_VERIFYCODE + mobile, code);
            JedisAPI.expire(JedisNameConstants.VAL_BIZAPP_USER_REG_VERIFYCODE + mobile,
                    Constants.VERIFY_CODE_EXPIRE_TIME);
        } else if ("2".equals(codeType)) {
            JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_PWD_VERIFYCODE + mobile, code);
            JedisAPI.expire(JedisNameConstants.VAL_BIZAPP_USER_PWD_VERIFYCODE + mobile,
                    Constants.VERIFY_CODE_EXPIRE_TIME);
        }
        String smsContent = code + Constants.VERIFY_CODE_SMS_CONTENT;
        AliyunSmsSend.send(AliyunSmsSend.CHINA_CODE, AliyunSmsSend.CHINA_CODE + mobile, code);
        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean modPwd(String mobile, String password) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        SnOlUserInfo user = snOlUserInfo.selectOneByMap(params);
        if (user == null) {
            return new SuccessBean(ErrorConstants.ERRORMSG_USER_NOEXIST);
        }
        //TODO AES解密
        String realPassword = password;
        String hashPassword = DigestUtils.sha256Hex(realPassword);
        user.setPassword(hashPassword);
        snOlUserInfo.updateByPrimaryKeySelective(user);
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + user.getId());
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_PHONE + mobile);
        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean state(String userId) {
        return null;
    }

    @Override
    public ResultBean updatePwd(String userId, String oldPassword, String password) {
        SnOlUserInfo user = snOlUserInfo.selectByPrimaryKey(userId);
        if (user == null) {
            return new SuccessBean(ErrorConstants.ERRORMSG_USER_NOEXIST);
        }
        //TODO AES解密
        String realOldPassword = oldPassword;
        String hashOldPassword = DigestUtils.sha256Hex(realOldPassword);
        if (!user.getPassword().equals(hashOldPassword)) {
            return new SuccessBean(ErrorConstants.ERRORMSG_PWD_OLD_ERROR);
        }
        //TODO AES解密
        String realPassword = password;
        String hashPassword = DigestUtils.sha256Hex(realPassword);
        user.setPassword(hashPassword);
        snOlUserInfo.updateByPrimaryKeySelective(user);
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_TOKEN + userId);
        JedisAPI.delete(JedisNameConstants.VAL_BIZAPP_USER_PHONE + user.getMobile());
        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean info(String userId) {
        return null;
    }

    @Override
    public ResultBean feedback(String userId, String msg) {
        long nowTime = System.currentTimeMillis();
        SnOlUserFeedback feedback = new SnOlUserFeedback();
        feedback.setId(UUIDGenerator.getUUID());
        feedback.setUserId(userId);
        feedback.setMsg(msg);
        feedback.setCreateTime(nowTime);
        snOlUserInfoFeedbackMapper.insertSelective(feedback);
        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    public ResultBean renterRechargeSuccess(String userId, String allowance) {
        try {
            //获取用户钱包信息
            SnOlUserWallet userWallet = snOlUserWalletMapper.selectByPrimaryKey(userId);
            String walletAddress = userWallet.getAddress();

            // 更新用户钱包金额
            long balance = userWallet.getAmount();
            long currentBalance = balance + Integer.parseInt(allowance);
            userWallet.setAmount(currentBalance);
            snOlUserWalletMapper.updateByPrimaryKey(userWallet);

            // 从水龙头减掉用户充值金额
            String temp = CommonConfig.getSncHeadlessAddress();
            SnMgmtGenaccInfo snMgmtGenaccInfo = snMgmtGenaccInfoMapper.selectByHeadlessAddress(CommonConfig.getSncHeadlessAddress());
            String headlessAddress = snMgmtGenaccInfo.getSncHeadlessAddress();
            long amount = snMgmtGenaccInfo.getSncTotalAmount();
            long currentAmount = amount - Integer.parseInt(allowance);
            snMgmtGenaccInfo.setSncTotalAmount(currentAmount);
            snMgmtGenaccInfoMapper.updateByPrimaryKey(snMgmtGenaccInfo);

            // 记录水龙头交易信息
            SnMgmtGenaccRec snMgmtGenaccRec = new SnMgmtGenaccRec();
            snMgmtGenaccRec.setId(UUIDGenerator.getUUID());
            snMgmtGenaccRec.setTrType("2");
            snMgmtGenaccRec.setToAddress(walletAddress);
            snMgmtGenaccRec.setAmount(currentBalance);
            snMgmtGenaccRecMapper.insertSelective(snMgmtGenaccRec);

            // 记录用户充值信息
            SnOlUserBuy snOlUserBuy = snOlUserBuyMapper.selectByUserId(userId);
            SnOlUserWalletRec snOlUserWalletRec = new SnOlUserWalletRec();
            snOlUserWalletRec.setId(UUIDGenerator.getUUID());
            snOlUserWalletRec.setTrType("1");
            snOlUserWalletRec.setIsExamine(snOlUserBuy.getIsExamine());
            snOlUserWalletRec.setUserid(userId);
            snOlUserWalletRec.setFromAddress(headlessAddress);
            snOlUserWalletRec.setAmount(currentBalance);
            snOlUserWalletRec.setCreateTime(System.currentTimeMillis());
            snOlUserWalletRecMapper.insertSelective(snOlUserWalletRec);
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean getFeeEstimation(String userId, String duration, String size) {
        BigInteger estimationFee = new BigInteger(duration).multiply(new BigInteger(size));

        try {
            // 查询用户钱包余额信息
            SnOlUserWallet snOlUserWallet = snOlUserWalletMapper.selectByPrimaryKey(userId);
            BigInteger allowance = new BigInteger(snOlUserWallet.getAmount().toString());

            BigInteger balance = allowance.subtract(estimationFee);
            long balances = balance.longValue();
            if (balances < 0) {
                log.error(userId + ": 钱包余额不足");
                return new ErrorBean(ErrorConstants.RRRORCODE_PAY_ERROR, ErrorConstants.RRRORMSG_PAY_ERROR);
            }
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(estimationFee.toString());
    }


    @Override
    public ResultBean renterPay(String userId, String fee, String duration, String space) {
        try {
            // 查询用户钱包余额
            SnOlUserWallet userWallet = snOlUserWalletMapper.selectByPrimaryKey(userId);
            long totalBalance = userWallet.getAmount();

            // 判定当前余额是否够支出
            long fees = Integer.parseInt(fee);
            long result = totalBalance - fees;
            if (result < 0) {
                return new ErrorBean(ErrorConstants.RRRORCODE_PAY_ERROR, ErrorConstants.RRRORMSG_PAY_ERROR);
            }
            userWallet.setAmount(result);
            // 执行扣款操作
            snOlUserWalletMapper.updateByPrimaryKeySelective(userWallet);

            // TODO 支付到哪个地址???

            // 记录空间购买信息
            SnOlUserSpaceRec snOlUserSpaceRec = new SnOlUserSpaceRec();
            snOlUserSpaceRec.setId(UUIDGenerator.getUUID());
            snOlUserSpaceRec.setUserid(userId);
            snOlUserSpaceRec.setDuration(duration);
            long spaces = Integer.parseInt(space);
            long convertSpacesTOGB = spaces * FileConstants.CONVERT_TO_TB;
            snOlUserSpaceRec.setSpace(convertSpacesTOGB);
            snOlUserSpaceRec.setType("1");
            long nowTime = System.currentTimeMillis();
            snOlUserSpaceRec.setCreateTime(nowTime);
            long dueTime = nowTime + Integer.parseInt(duration)*24*60*60*1000;
            snOlUserSpaceRec.setDueTime(dueTime);
            snOlUserSpaceRecMapper.insertSelective(snOlUserSpaceRec);

            // 更新用户空间信息
            SnOlUserSpace snOlUserSpace = snOlUserSpaceMapper.selectByUserId(userId);
            long existSpace = snOlUserSpace.getTotalSpace();
            long currentSpace = existSpace + convertSpacesTOGB;
            long existFreeSpace = snOlUserSpace.getFreeSpace();
            long currentFreeSpace = existFreeSpace + convertSpacesTOGB;

            snOlUserSpace.setTotalSpace(currentSpace);
            snOlUserSpace.setFreeSpace(currentFreeSpace);
            snOlUserSpace.setUpdateTime(nowTime);
            snOlUserSpaceMapper.updateByPrimaryKey(snOlUserSpace);

            // 记录用户消费信息
            SnOlUserWalletRec snOlUserWalletRec = new SnOlUserWalletRec();
            snOlUserWalletRec.setId(UUIDGenerator.getUUID());
            snOlUserWalletRec.setTrType("2");
            snOlUserWalletRec.setIsExamine("");
            snOlUserWalletRec.setUserid(userId);
            //snOlUserWalletRec.setToAddress(headlessAddress);
            snOlUserWalletRec.setAmount(fees);
            snOlUserWalletRec.setCreateTime(System.currentTimeMillis());
            snOlUserWalletRecMapper.insertSelective(snOlUserWalletRec);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(ErrorConstants.ERRORMSG_OK);
    }

    @Override
    public ResultBean getBalance(String userId) {
        long totalBalance = 0;

        try {
            // 查询用户钱包余额
            SnOlUserWallet userWallet = snOlUserWalletMapper.selectByPrimaryKey(userId);
            totalBalance = userWallet.getAmount();
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(totalBalance);
    }

    @Override
    public ResultBean getRenterInfo(String userId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            SnOlUserSpace snOlUserSpace = snOlUserSpaceMapper.selectByUserId(userId);
            resultMap.put("totalSpace", snOlUserSpace.getTotalSpace());
            resultMap.put("freeSpace", snOlUserSpace.getFreeSpace());

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }

        return new SuccessBean(resultMap);
    }

    @Override
    public ResultBean renterRechargeReview(String userId, String allowance) {
        //金额验证
        if(!MoneyUtils.isNumber(allowance)) {
            return new ErrorBean("-1", "输入格式不正确,请输入整数");
        }

        String serialNumber = UUIDGenerator.getUUID();
        try {
            // 向后台发起充值申请
            SnOlUserBuy snOlUserBuy = new SnOlUserBuy();
            snOlUserBuy.setId(serialNumber);
            snOlUserBuy.setUserId(userId);
            snOlUserBuy.setPurchaseCount(new BigDecimal(allowance));
            snOlUserBuy.setPayVoucher("");
            snOlUserBuy.setIsExamine("1");
            snOlUserBuy.setExaminDesc("");
            snOlUserBuy.setCreateBy(CommonConfig.getAddress());
            snOlUserBuy.setCreateTime(System.currentTimeMillis());
            snOlUserBuy.setUpdateTime(System.currentTimeMillis());

            snOlUserBuyMapper.insertSelective(snOlUserBuy);

        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }
        return new SuccessBean(serialNumber);
    }

    @Override
    public ResultBean getPurchaseHistory(String userId, String pageNum,String pageSize,String type) {
        Integer page = Integer.parseInt(pageNum);
        Integer limit = Integer.parseInt(pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("offset", (page - 1) * limit);
        map.put("limit", limit);

        List<SnOlUserWalletRec> fileList = null;
        int total = 0;

        try {
            switch (Integer.parseInt(type)) {
                case 1:
                    map.put("trType",type);
                    fileList = snOlUserWalletRecMapper.queryList(map);
                    total = snOlUserWalletRecMapper.queryTotal(map);
                    break;
                default:
                    map.put("trType","2");
                    fileList = snOlUserWalletRecMapper.queryList(map);
                    total = snOlUserWalletRecMapper.queryTotal(map);
                    break;
            }
        } catch (RuntimeException e) {
            log.error("系统异常," + e.getMessage(), e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_ERROR, ErrorConstants.ERRORMSG_SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("系统异常,请稍后重试", e);
            return new ErrorBean(ErrorConstants.ERRORCODE_SYSTEM_RUNTIME_ERROR_RETRY, ErrorConstants.ERRORMSG_SYSTEM_RUNTIME_ERROR_RETRY);
        }
        PageUtils pageUtil = new PageUtils(fileList, total, limit, page);

        return new SuccessBean(pageUtil);
    }

}
