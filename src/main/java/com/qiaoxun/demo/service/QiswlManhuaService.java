package com.qiaoxun.demo.service;

import com.qiaoxun.demo.pojo.QiswlManhua;

import java.io.IOException;

public interface QiswlManhuaService {

    void parse() throws IOException;

    void update() throws IOException;
}
