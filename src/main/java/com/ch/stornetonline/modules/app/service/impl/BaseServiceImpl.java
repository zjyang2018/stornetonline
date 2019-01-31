package com.ch.stornetonline.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ch.stornetonline.common.cache.JedisAPI;
import com.ch.stornetonline.common.constants.JedisNameConstants;
import com.ch.stornetonline.common.utils.StringUtils;
import com.ch.stornetonline.modules.app.entity.SnOlUserInfo;
import com.ch.stornetonline.modules.app.mapper.SnOlUserInfoMapper;
import com.ch.stornetonline.modules.app.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService {
    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Resource
    SnOlUserInfoMapper snOlUserInfoMapper;

    @Override
    public SnOlUserInfo getUserByMobile(String mobile) {
        SnOlUserInfo snOlUserInfo = new SnOlUserInfo();
        String strUser = JedisAPI.get(JedisNameConstants.VAL_BIZAPP_USER_PHONE + mobile);
        if (!StringUtils.isNull(strUser)) {
            snOlUserInfo = JSONObject.parseObject(strUser, SnOlUserInfo.class);
        } else {
            snOlUserInfo = snOlUserInfoMapper.selectByMobile(mobile);
            if (snOlUserInfo != null) {
                JedisAPI.set(JedisNameConstants.VAL_BIZAPP_USER_PHONE + mobile, JSON.toJSONString(snOlUserInfo));
            }
        }
        return snOlUserInfo;
    }

    @Override
    public SnOlUserInfo getUserById(String userId) {
        return snOlUserInfoMapper.selectByPrimaryKey(userId);
    }

    @Override
    public String getUserNoById(String userId) {
        return getUserById(userId).getUserNo();
    }

}
