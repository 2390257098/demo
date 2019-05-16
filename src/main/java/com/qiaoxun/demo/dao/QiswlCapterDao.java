package com.qiaoxun.demo.dao;

import com.qiaoxun.demo.pojo.QiswlCapter;
import com.qiaoxun.demo.pojo.QiswlCapterWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QiswlCapterDao {


    int insertSelective(QiswlCapterWithBLOBs record);


}