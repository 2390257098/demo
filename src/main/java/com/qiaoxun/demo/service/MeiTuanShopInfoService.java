package com.qiaoxun.demo.service;


import org.jsoup.nodes.Document;

public interface MeiTuanShopInfoService {

    Document getDocument();

    void parse(Document doc);
}
