package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnSysBaseid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnSysBaseidMapper {
    int deleteByPrimaryKey(Integer idType);

    int insert(SnSysBaseid record);

    int insertSelective(SnSysBaseid record);

    SnSysBaseid selectByPrimaryKey(Integer idType);

    int updateByPrimaryKeySelective(SnSysBaseid record);

    int updateByPrimaryKey(SnSysBaseid record);
}