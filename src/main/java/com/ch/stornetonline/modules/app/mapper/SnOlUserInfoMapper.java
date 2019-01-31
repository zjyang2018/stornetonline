package com.ch.stornetonline.modules.app.mapper;


import com.ch.stornetonline.modules.app.entity.SnOlUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SnOlUserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserInfo record);

    int insertSelective(SnOlUserInfo record);

    SnOlUserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnOlUserInfo record);

    int updateByPrimaryKey(SnOlUserInfo record);

    SnOlUserInfo selectOneByMap(Map<String, Object> params);

    SnOlUserInfo selectByMobile(String mobile);

    Map<String, Object> selectMapByPrimaryKey(String id);

    Map<String,Object> selectMapByMobile(String mobile);
}