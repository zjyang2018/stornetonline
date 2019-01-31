package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtChaSn;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtChaSnMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtChaSn record);

    int insertSelective(SnMgmtChaSn record);

    SnMgmtChaSn selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtChaSn record);

    int updateByPrimaryKey(SnMgmtChaSn record);
}