package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserWalletRec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SnOlUserWalletRecMapper {
    int deleteByPrimaryKey(String id);

    int insert(SnOlUserWalletRec record);

    int insertSelective(SnOlUserWalletRec record);

    SnOlUserWalletRec selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SnOlUserWalletRec record);

    int updateByPrimaryKey(SnOlUserWalletRec record);

    List<SnOlUserWalletRec> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);
}