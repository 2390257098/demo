package com.qiaoxun.demo.dao;

import com.qiaoxun.demo.pojo.MeituanShopInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MeiTuanShopInfoDao {

    int insertSelective(MeituanShopInfo meituanShopInfo);

}
