package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnSysProperty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnSysPropertyMapper {
    int deleteByPrimaryKey(String name);

    int insert(SnSysProperty record);

    int insertSelective(SnSysProperty record);

    SnSysProperty selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SnSysProperty record);

    int updateByPrimaryKey(SnSysProperty record);
}