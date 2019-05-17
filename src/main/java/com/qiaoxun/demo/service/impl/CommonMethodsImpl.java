package com.qiaoxun.demo.service.impl;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

public class CommonMethodsImpl{


    public String download1(String url, int cartoonId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        savaImage(img, "D:\\cartoonApp"+"/bookimages/"+cartoonId, "/image.jpg");
        return "/bookimages/"+cartoonId+"/image.jpg";
    }


    public String download2(String url, int cartoonId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        savaImage(img, "D:\\cartoonApp"+"/bookimages/"+cartoonId, "/cover.jpg");
        return "/bookimages/"+cartoonId+"/cover.jpg";
    }


    public String download3(String url, int cartoonId, int chapterId, int imgId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        savaImage(img, "D:\\cartoonApp"+"/bookimages/"+cartoonId+"/chapter"+chapterId, "/"+imgId+".jpg");
        return "/bookimages/"+cartoonId+"/chapter"+chapterId+"/"+imgId+".jpg";
    }


    public void savaImage(byte[] img, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        try {
            //创建目录和图片

            file = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(img);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 模拟登录获取cookie和sessionid
     */
    public  Map<String, String> login() throws IOException {
        String urlLogin = "http://www.yymh8.com/index.php?m=&c=MhPublic&a=binding";
        Connection connect = Jsoup.connect(urlLogin);
        // 伪造请求头
        connect.header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Encoding",
                "gzip, deflate");
        connect.header("Accept-Language", "zh-CN,zh;q=0.8").header("Connection", "keep-alive");
        connect.header("Content-Length", "68").header("Content-Type",
                "application/x-www-form-urlencoded; charset=UTF-8");
        connect.header("Host", "www.yymh8.com").header("Referer", "http://www.yymh8.com/index.php?m=&c=MhPublic&a=binding&fr=aHR0cDovL3d3dy55eW1oOC5jb20vaW5kZXgucGhwP209JmM9TWgmYT1ib29rX2NhdGU%3D");
        connect.header("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest");

        // 携带登陆信息
        // mobile:pkran1986
        //pass:7788120K
        //fr:index.php?m=&c=Mh&a=my
        connect.data("mobile", "pkran1986").data("pass", "7788120K").data("fr", "index.php?m=&c=Mh&a=my");

        //请求url获取响应信息
        Connection.Response res = connect.ignoreContentType(true).method(Connection.Method.POST).execute();// 执行请求
        // 获取返回的cookie
        System.out.println(res.cookies());
        return res.cookies();
    }

}
