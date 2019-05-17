package com.qiaoxun.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        /*String cmd = "D:\\dev\\tool\\google\\chrome.exe http://localhost:8080/index";
        Runtime run = Runtime.getRuntime();
        try{
            run.exec(cmd);
            System.out.println("启动浏览器打开项目成功");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }*/

    }

}
