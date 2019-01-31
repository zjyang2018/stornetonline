package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserFiledw;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserFiledwMapper {
    int deleteByPrimaryKey(String fileid);

    int insert(SnOlUserFiledw record);

    int insertSelective(SnOlUserFiledw record);

    SnOlUserFiledw selectByPrimaryKey(String fileid);

    int updateByPrimaryKeySelective(SnOlUserFiledw record);

    int updateByPrimaryKey(SnOlUserFiledw record);
}