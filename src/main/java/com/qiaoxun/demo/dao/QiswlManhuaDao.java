package com.qiaoxun.demo.dao;

import com.qiaoxun.demo.pojo.QiswlManhua;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QiswlManhuaDao {


    int insertSelective(QiswlManhua record);

    String selectLastChapterByTitle(String title);

    void updateLastChapter(String title,String lastChapter);

}