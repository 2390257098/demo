package com.qiaoxun.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;

public class DownloadImg {

    /*
    不带cookie下载图片
    漫画封面图片
     */
    public static String download1(String url,int cartoonId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        //savaImage(img, "D:\\javaCrawJWXT"+"/bookimages/"+cartoonId, "/image.jpg");
        return "/bookimages/"+cartoonId+"/image/"+cartoonId+"image.jpg";
    }
    /*
    不带cookie下载图片
    横着的封面图片
     */
    public static String download2(String url,int cartoonId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        //savaImage(img, "D:\\javaCrawJWXT"+"/bookimages/"+cartoonId, "/cover.jpg");
        return "/bookimages/"+cartoonId+"/cover.jpg";
    }
    /*
    不带cookie下载图片
    章节内部图片
     */
    public static String download3(String url,int cartoonId,int chapterId,int imgId) throws IOException {
        Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
        byte[] img = response.bodyAsBytes();
        //savaImage(img, "D:\\javaCrawJWXT"+"/bookimages/"+cartoonId+"/chapter"+chapterId, "/"+imgId+".jpg");
        return "/bookimages/"+cartoonId+"/chapter"+chapterId+"/"+imgId+".jpg";
    }
    /*
    保存图片
     */
    public static void savaImage(byte[] img,String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        try {
            //创建目录和图片

            file=new File(filePath);
            if(!dir.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(img);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }



    }


}
