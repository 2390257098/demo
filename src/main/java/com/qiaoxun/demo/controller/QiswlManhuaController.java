package com.qiaoxun.demo.controller;



import com.qiaoxun.demo.service.QiswlCapterService;
import com.qiaoxun.demo.service.QiswlManhuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
public class QiswlManhuaController {


    @Autowired
    private QiswlManhuaService manhuaService;

    @GetMapping("/crawler")
    public String crawler() throws IOException {
        manhuaService.parse();
        return "index";
    }

    @GetMapping("/update")
    public void update() throws IOException {
        manhuaService.update();
    }
    @GetMapping("/a")
    public String aa(){
        return "index.html";
    }
}
