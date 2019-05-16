package com.qiaoxun.demo.service.impl;

import com.qiaoxun.demo.dao.QiswlCapterDao;
import com.qiaoxun.demo.dao.QiswlManhuaDao;
import com.qiaoxun.demo.pojo.QiswlCapterWithBLOBs;
import com.qiaoxun.demo.pojo.QiswlManhua;
import com.qiaoxun.demo.service.QiswlManhuaService;
import com.qiaoxun.util.DownloadImg;
import com.qiaoxun.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QiswlManhuaServiceImpl implements QiswlManhuaService {
    private static Map<String, String> cookies = null;
    private static Document document=null;
    private static List<String> imgUrlSqls=new ArrayList<>();
    private static QiswlManhua qiswlManhua=new QiswlManhua();
    private static QiswlCapterWithBLOBs bloBs=new QiswlCapterWithBLOBs();
    public static int cartoons=0;
    private static String imgUrlSql;


    @Autowired
    private static QiswlManhuaDao manhuaDao;
    @Autowired
    private static QiswlChapterServiceImpl chapterService;
    @Autowired
    private static CommonMethodsImpl methods;

    @Override
    public int insertSelective(QiswlManhua record) {
        return manhuaDao.insertSelective(record);
    }


    /**
     * 爬取数据  下载图片  数据入库
     */
    @Override
    public void parse() throws IOException {
        System.out.println(methods.login()+"----------------");
        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).post();
        String init="http://www.yymh8.com";
        //选择器定位
        Elements elements = document.select("#html_box").select(".item");
        cartoons=elements.size();

        //创建时间和更新时间
        Date date=new Date();
        qiswlManhua.setCreateTime(date);
        qiswlManhua.setUpdateTime(date);
        //入库操作
        SqlSession sqlSession=MyBatisUtil.createSqlSession();
        //获取内部元素（目标数据）

        //i表示漫画的顺序
        for (int i=1;i<=elements.size();i++) {
            System.out.println("这是第"+i+"个漫画");
            //获取封面图片地址
            String image=elements.get(i-1).select("a > div.cover > img").attr("src").trim();
            //下载封面图片------保存封面图片路径
            qiswlManhua.setImage(DownloadImg.download1(image,i));
            //获取漫画url
            String url = init+elements.get(i-1).select("a").attr("href").trim();
            //带着漫画url再次发请求，进入漫画内部页
            document = Jsoup.connect(url).cookies(cookies).get();
            //开始获取目标数据

            //名称
            String title=document.select("body > div.cover-box > div.container > div.title").text();
            qiswlManhua.setTitle(title);
            //横着的封面图地址
            String cover=document.select("body > div.cover-box > div.bg > img").attr("src").trim();
            //下载横着的封面图------并保存地址
            String newCover=DownloadImg.download2(cover,i);
            qiswlManhua.setCover(newCover);
            //描述
            String desc=document.select("#book-info > article > div.body").text();
            qiswlManhua.setDesc(desc.substring(0,desc.length()-10));
            //作者
            String author=document.select("#book-info > article > div.author").text();
            qiswlManhua.setAuther(author.substring(3));
            //标签
            String keyWord=document.select("body > div.cover-box > div.container > div.tags").text();
            qiswlManhua.setKeyword(keyWord);
            //浏览量
            String view=document.select("#book-info > div > div:nth-child(1)").text();
            qiswlManhua.setView(Integer.parseInt(view.substring(4)));
            //订阅量
            String mark=document.select("#book-info > div > div:nth-child(3)").text();
            qiswlManhua.setMark(Integer.parseInt(mark.substring(0, mark.indexOf("人"))));
            //最新章节
            String lastChapterTitle=document.select("#chapters > div.ch > div > span").text();

            //获取第一章节的url,然后更换这个url的最后一个参数，一直到这个漫画的最新章节数
            String chapterUrl=document.select("#html_box > div:nth-child(1) > a").attr("href").trim();
            //截取该漫画最新一期编号
            String chapter=init+chapterUrl.substring(0,chapterUrl.length()-1);
            //对编号进行类型转换
            int chapters=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
            qiswlManhua.setLastChapter("第"+chapters+"话");
            qiswlManhua.setLastChapterTitle("第"+chapters+"话");
            qiswlManhua.setId(i);
            //获取到的数据入库
            manhuaDao.insertSelective(qiswlManhua);
            //拿到新的url,再次发起请求

            //q代表第i个漫画里的第q个章节
            for (int q=1;q<=chapters;q++){
                String newUrl=chapter+q;
                document = Jsoup.connect(newUrl).cookies(cookies).timeout(1000*60*2).get();
                //拿到图片地址，进行下载
                Elements elements2=document.select(".read-article").select(".item");
                System.out.println("第"+q+"话里边有"+elements2.size()+"张图片");
                //j代表第q个章节里的第j张图片
                for (int j=1;j<=elements2.size();j++){
                    System.out.println("正在下载第"+j+"张图片ing...");
                    //获取章节内部每张图的url
                    String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                    //下载每个章节里的图片
                    String imgUrlSql=DownloadImg.download3(imgUrl,i,q,j);
                    //获取每张图片的路径，存进集合，用完后清空
                    imgUrlSqls.add(imgUrlSql);
                }
                bloBs.setTitle("第"+q+"话");
                bloBs.setCjid(i+"");
                bloBs.setUpdateTime(date);
                bloBs.setCreateTime(date);
                bloBs.setImagelist(imgUrlSqls.toString());
                bloBs.setManhuaId(i);
                bloBs.setSort(q);
                chapterService.insertSelective(bloBs);

                //清空集合
                imgUrlSqls.clear();
            }
        }
    }

    @Override
    public void update() throws IOException {
        methods.login();
        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).post();
        Elements elements = document.select("#html_box").select(".item");
        cartoons=elements.size();
        for (int i=1;i<=cartoons;i++) {
            String url = "http://www.yymh8.com"+elements.get(i-1).select("a").attr("href").trim();
            document = Jsoup.connect(url).cookies(cookies).get();
            String title=document.select("body > div.cover-box > div.container > div.title").text();
            String lastChapterTitle=document.select("#chapters > div.ch > div > span").text();
            System.out.println(lastChapterTitle+"-------------");
            String lastChapter="第"+lastChapterTitle.substring(3);
            String last=manhuaDao.selectLastChapterByTitle(title);
            System.out.println(last);
            if(last.equals(lastChapter)){
                System.out.println("当前已是最新章节，请耐心等待");
            }else {
                System.out.println("正在尽力补全，请耐心等待");
                String chapterUrl=elements.get(i-1).select("#html_box > div:nth-child(1) > a").attr("href").trim();
                //数据库内信息
                //int sql=Integer.parseInt(last.substring(1,last.length()-1));
                //更新数据库

                manhuaDao.updateLastChapter(title,lastChapter);

                int html=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
                for (int sql=Integer.parseInt(last.substring(1,last.length()-1));sql<html;sql++){
                    document = Jsoup.connect(chapterUrl.substring(0,chapterUrl.length()-1)+sql).cookies(cookies).get();
                    Elements elements2=document.select(".read-article").select(".item");
                    System.out.println("第"+(sql+1)+"话里边有"+elements2.size()+"张图片");
                    //j代表第q个章节里的第j张图片
                    for (int j=1;j<=elements2.size();j++){
                        System.out.println("正在下载第"+j+"张图片ing...");
                        //获取章节内部每张图的url
                        String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                        //下载每个章节里的图片
                        imgUrlSql=DownloadImg.download3(imgUrl,i,sql+1,j);
                        //加进集合------入库后清空集合
                        imgUrlSqls.add(imgUrlSql);
                    }
                    bloBs.setImagelist(imgUrlSqls.toString().substring(1,imgUrlSqls.toString().length()-1));
                    bloBs.setManhuaId(i);
                    bloBs.setCreateTime(new Date());
                    bloBs.setUpdateTime(new Date());
                    bloBs.setCjid(i+"");
                    bloBs.setSort(sql+1);
                    bloBs.setTitle("第"+(sql+1)+"话");
                    chapterService.insertSelective(bloBs);
                    imgUrlSqls.clear();
                }
            }
        }
    }
}
