package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserFeedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserInfoFeedbackMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserFeedback record);

    int insertSelective(SnOlUserFeedback record);

    SnOlUserFeedback selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnOlUserFeedback record);

    int updateByPrimaryKey(SnOlUserFeedback record);
}