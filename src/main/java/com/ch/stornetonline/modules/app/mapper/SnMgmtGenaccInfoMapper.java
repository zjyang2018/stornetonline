package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtGenaccInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtGenaccInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtGenaccInfo record);

    int insertSelective(SnMgmtGenaccInfo record);

    SnMgmtGenaccInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtGenaccInfo record);

    int updateByPrimaryKey(SnMgmtGenaccInfo record);

    SnMgmtGenaccInfo selectByHeadlessAddress(String sncHeadlessAddress);
}