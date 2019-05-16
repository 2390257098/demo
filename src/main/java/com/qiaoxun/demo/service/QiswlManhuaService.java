package com.qiaoxun.demo.service;

import com.qiaoxun.demo.pojo.QiswlManhua;

import java.io.IOException;

public interface QiswlManhuaService {
    int insertSelective(QiswlManhua record);

    void parse() throws IOException;

    void update() throws IOException;
}
