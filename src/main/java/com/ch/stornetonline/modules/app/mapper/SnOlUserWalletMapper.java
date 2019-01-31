package com.ch.stornetonline.modules.app.mapper;

import com.ch.stornetonline.modules.app.entity.SnOlUserWallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SnOlUserWalletMapper {
    int deleteByPrimaryKey(String userid);

    int insert(SnOlUserWallet record);

    int insertSelective(SnOlUserWallet record);

    SnOlUserWallet selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(SnOlUserWallet record);

    int updateByPrimaryKey(SnOlUserWallet record);
}