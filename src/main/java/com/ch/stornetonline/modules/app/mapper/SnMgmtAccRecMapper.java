package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtAccRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtAccRecMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtAccRec record);

    int insertSelective(SnMgmtAccRec record);

    SnMgmtAccRec selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtAccRec record);

    int updateByPrimaryKey(SnMgmtAccRec record);
}