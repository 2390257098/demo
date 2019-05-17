package com.qiaoxun.demo.controller;




import com.qiaoxun.demo.service.QiswlManhuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class QiswlManhuaController {


    @Autowired
    private QiswlManhuaService manhuaService;

    @GetMapping("/crawler")
    public void crawler() throws IOException {
        manhuaService.parse();
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
