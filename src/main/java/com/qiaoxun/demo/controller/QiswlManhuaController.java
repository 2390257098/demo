package com.qiaoxun.demo.controller;




import com.qiaoxun.demo.service.QiswlManhuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.annotation.Resource;
import java.io.IOException;


@Controller
public class QiswlManhuaController {


    @Resource
    private QiswlManhuaService manhuaService;

    @GetMapping("/crawler")
    public String crawler() throws IOException, InterruptedException {
        manhuaService.parse();
        return "/crawler";
    }

    @GetMapping("/update")
    public String update() throws IOException, InterruptedException {
        manhuaService.update();
        return "/update";
    }

}
