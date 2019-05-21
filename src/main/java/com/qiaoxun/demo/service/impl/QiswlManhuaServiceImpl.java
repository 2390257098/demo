package com.qiaoxun.demo.service.impl;

import com.qiaoxun.demo.dao.QiswlManhuaDao;
import com.qiaoxun.demo.pojo.QiswlCapterWithBLOBs;
import com.qiaoxun.demo.pojo.QiswlManhua;
import com.qiaoxun.demo.service.QiswlManhuaService;
import com.qiaoxun.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
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
    Map<String, String> cookies = null;
    Document document=null;
    List<String> imgUrlSqls=new ArrayList<>();
    QiswlManhua qiswlManhua=new QiswlManhua();
    QiswlCapterWithBLOBs bloBs=new QiswlCapterWithBLOBs();
    public static int cartoons=0;
    String imgUrlSql;


    @Autowired
    private  QiswlManhuaDao manhuaDao;
    @Autowired
    private  QiswlChapterServiceImpl chapterService;

    CommonMethodsImpl methods = new CommonMethodsImpl();

    String init="http://www.yymh8.com";

    Date date=new Date();

    /**
     * 爬取数据  下载图片  数据入库
     */
    @Override
    public void parse() throws IOException {
        cookies=methods.login();
        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).timeout(1000*60*2).post();
        //选择器定位
        Elements elements = document.select("#html_box").select(".item");

        cartoons=elements.size();

        //创建时间和更新时间

        qiswlManhua.setCreateTime(date);
        qiswlManhua.setUpdateTime(date);

        //获取内部元素（目标数据）
        //i表示漫画的顺序
        for (int i=1;i<=elements.size();i++) {
            System.out.println("这是第"+i+"个漫画");
            //获取封面图片地址------选择器换过了，之前不是这个
            String image=elements.get(i-1).select("a > div.bg > img").attr("src").trim();

            //下载封面图片------保存封面图片路径
            qiswlManhua.setImage(methods.download1(image,i));
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
            String newCover=methods.download2(cover,i);
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
                System.out.println("开始下载图片了！！！");
                //j代表第q个章节里的第j张图片
                for (int j=1;j<=elements2.size();j++){
                    System.out.println("正在下载第"+j+"张图片ing...");
                    //获取章节内部每张图的url
                    String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                    //下载每个章节里的图片
                    String imgUrlSql=methods.download3(imgUrl,i,q,j);
                    //获取每张图片的路径，存进集合，用完后清空
                    imgUrlSqls.add(imgUrlSql);
                }
                bloBs.setTitle("第"+q+"话");
                bloBs.setCjid(i+"");
                bloBs.setUpdateTime(date);
                bloBs.setCreateTime(date);
                bloBs.setImagelist(imgUrlSqls.toString());
                bloBs.setManhuaId(qiswlManhua.getId());
                bloBs.setSort(q);
                chapterService.insertSelective(bloBs);

                //清空集合
                imgUrlSqls.clear();
            }
        }
    }

    /**
     * 数据更新
     */
    @Override
    public void update() throws IOException {
        cookies=methods.login();
        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).post();
        Elements elements = document.select("#html_box").select(".item");
        //结婚接收漫画名称
        List<String> titles=manhuaDao.selectTitles() ;
        cartoons=elements.size();
        for (int i=1;i<=cartoons;i++) {
            String url = "http://www.yymh8.com"+elements.get(i-1).select("a").attr("href").trim();
            document = Jsoup.connect(url).cookies(cookies).get();
            String title=document.select("body > div.cover-box > div.container > div.title").text();
            //判断是否有新漫画
            //false
            if (titles.toString().substring(1,titles.toString().length()-1).contains(title)){
                String status=document.select("#chapters > div.ch > div").text().substring(0,2);
                //判断状态
                if (status.equals("已完结")){
                    //如果true------修改漫画状态
                    manhuaDao.updateStatusByTitle(title);
                }else {
                    String lastChapterTitle=document.select("#chapters > div.ch > div > span").text();
                    System.out.println(lastChapterTitle+"-------------");
                    String lastChapter="第"+lastChapterTitle.substring(3);
                    String last=manhuaDao.selectLastChapterByTitle(title);
                    if(last.equals(lastChapter)){
                        System.out.println("当前已是最新章节，请耐心等待");
                    }else {
                        System.out.println("正在尽力补全，请耐心等待");
                        String chapterUrl=elements.get(i-1).select("#html_box > div:nth-child(1) > a").attr("href").trim();

                        //更新漫画表里的最新章节字段
                        manhuaDao.updateLastChapter(title,lastChapter);

                        int htmlLastChapter=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
                        for (int sqlLastChapter=Integer.parseInt(last.substring(1,last.length()-1));sqlLastChapter<htmlLastChapter;sqlLastChapter++){
                            document = Jsoup.connect(chapterUrl.substring(0,chapterUrl.length()-1)+sqlLastChapter).cookies(cookies).get();
                            Elements elements2=document.select(".read-article").select(".item");
                            System.out.println("第"+(sqlLastChapter+1)+"话里边有"+elements2.size()+"张图片");
                            System.out.println("开始下载图片了！！！");
                            //j代表第q个章节里的第j张图片
                            for (int j=1;j<=elements2.size();j++){
                                System.out.println("正在下载第"+j+"张图片ing...");
                                //获取章节内部每张图的url
                                String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                                //下载每个章节里的图片
                                imgUrlSql=methods.download3(imgUrl,i,sqlLastChapter+1,j);
                                //加进集合------入库后清空集合
                                imgUrlSqls.add(imgUrlSql);
                            }
                            bloBs.setImagelist(imgUrlSqls.toString().substring(1,imgUrlSqls.toString().length()-1));
                            bloBs.setManhuaId(qiswlManhua.getId());
                            bloBs.setCreateTime(new Date());
                            bloBs.setUpdateTime(new Date());
                            bloBs.setCjid(i+"");
                            bloBs.setSort(sqlLastChapter+1);
                            bloBs.setTitle("第"+(sqlLastChapter+1)+"话");
                            chapterService.insertSelective(bloBs);
                            imgUrlSqls.clear();
                        }
                    }
                }
            }else {
                //以下代码是重复代码------不加注释了
                System.out.println("这是第"+i+"个漫画");
                String image=elements.get(i-1).select("a > div.cover > img").attr("src").trim();
                qiswlManhua.setImage(methods.download1(image,i));
                document = Jsoup.connect(url).cookies(cookies).get();
                qiswlManhua.setTitle(title);
                String cover=document.select("body > div.cover-box > div.bg > img").attr("src").trim();
                String newCover=methods.download2(cover,i);
                qiswlManhua.setCover(newCover);
                String desc=document.select("#book-info > article > div.body").text();
                qiswlManhua.setDesc(desc.substring(0,desc.length()-10));
                String author=document.select("#book-info > article > div.author").text();
                qiswlManhua.setAuther(author.substring(3));
                String keyWord=document.select("body > div.cover-box > div.container > div.tags").text();
                qiswlManhua.setKeyword(keyWord);
                String view=document.select("#book-info > div > div:nth-child(1)").text();
                qiswlManhua.setView(Integer.parseInt(view.substring(4)));
                String mark=document.select("#book-info > div > div:nth-child(3)").text();
                qiswlManhua.setMark(Integer.parseInt(mark.substring(0, mark.indexOf("人"))));
                String lastChapterTitle=document.select("#chapters > div.ch > div > span").text();
                String chapterUrl=document.select("#html_box > div:nth-child(1) > a").attr("href").trim();
                String chapter=init+chapterUrl.substring(0,chapterUrl.length()-1);
                int chapters=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
                qiswlManhua.setLastChapter("第"+chapters+"话");
                qiswlManhua.setLastChapterTitle("第"+chapters+"话");
                manhuaDao.insertSelective(qiswlManhua);
                for (int q=1;q<=chapters;q++){
                    String newUrl=chapter+q;
                    document = Jsoup.connect(newUrl).cookies(cookies).timeout(1000*60*2).get();
                    Elements elements2=document.select(".read-article").select(".item");
                    System.out.println("第"+q+"话里边有"+elements2.size()+"张图片");
                    System.out.println("开始下载图片了！！！");
                    for (int j=1;j<=elements2.size();j++){
                        System.out.println("正在下载第"+j+"张图片ing...");
                        String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                        String imgUrlSql=methods.download3(imgUrl,i,q,j);
                        imgUrlSqls.add(imgUrlSql);
                    }
                    bloBs.setTitle("第"+q+"话");
                    bloBs.setCjid(i+"");
                    bloBs.setUpdateTime(date);
                    bloBs.setCreateTime(date);
                    bloBs.setImagelist(imgUrlSqls.toString());
                    bloBs.setManhuaId(qiswlManhua.getId());
                    bloBs.setSort(q);
                    chapterService.insertSelective(bloBs);
                    imgUrlSqls.clear();
                }
            }
        }
    }
}
