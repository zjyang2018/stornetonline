package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserSpace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserSpaceMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserSpace record);

    int insertSelective(SnOlUserSpace record);

    SnOlUserSpace selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnOlUserSpace record);

    int updateByPrimaryKey(SnOlUserSpace record);

    SnOlUserSpace selectByUserId(String userId);
}