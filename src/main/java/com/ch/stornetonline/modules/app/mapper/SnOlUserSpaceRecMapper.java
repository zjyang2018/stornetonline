package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserSpaceRec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserSpaceRecMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserSpaceRec record);

    int insertSelective(SnOlUserSpaceRec record);

    SnOlUserSpaceRec selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnOlUserSpaceRec record);

    int updateByPrimaryKey(SnOlUserSpaceRec record);
}