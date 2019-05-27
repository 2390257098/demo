package com.qiaoxun.demo.dao;

import com.qiaoxun.demo.pojo.MeituanShopInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MeiTuanShopInfoDao {

    int insertSelective(MeituanShopInfo meituanShopInfo);

    List<String > selectShopNo();

    void updateAddressByNo(MeituanShopInfo meituanShopInfo);
}
