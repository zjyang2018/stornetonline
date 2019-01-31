package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtChaInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtChaInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtChaInfo record);

    int insertSelective(SnMgmtChaInfo record);

    SnMgmtChaInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtChaInfo record);

    int updateByPrimaryKey(SnMgmtChaInfo record);
}