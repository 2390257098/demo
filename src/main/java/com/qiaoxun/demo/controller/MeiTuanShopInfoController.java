package com.qiaoxun.demo.controller;

import com.qiaoxun.demo.service.MeiTuanShopInfoService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Controller
public class MeiTuanShopInfoController {
    @Resource
    private MeiTuanShopInfoService infoService;


    @GetMapping("/meituan")
    public String crawler(){
        Document doc=infoService.getDocument();
        infoService.parse(doc);
        return "/index";
    }
}
