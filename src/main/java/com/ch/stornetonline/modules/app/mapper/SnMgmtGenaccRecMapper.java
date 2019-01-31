package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnMgmtGenaccRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnMgmtGenaccRecMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnMgmtGenaccRec record);

    int insertSelective(SnMgmtGenaccRec record);

    SnMgmtGenaccRec selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnMgmtGenaccRec record);

    int updateByPrimaryKey(SnMgmtGenaccRec record);
}