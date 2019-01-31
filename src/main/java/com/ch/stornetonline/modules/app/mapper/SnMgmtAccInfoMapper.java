package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtAccInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtAccInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtAccInfo record);

    int insertSelective(SnMgmtAccInfo record);

    SnMgmtAccInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtAccInfo record);

    int updateByPrimaryKey(SnMgmtAccInfo record);
}