package com.qiaoxun.demo.test;


import com.qiaoxun.demo.dao.QiswlCapterDao;
import com.qiaoxun.demo.dao.QiswlManhuaDao;
import com.qiaoxun.demo.pojo.QiswlCapterWithBLOBs;
import com.qiaoxun.demo.pojo.QiswlManhua;
import com.qiaoxun.util.DownloadImg;
import com.qiaoxun.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class Test {
    private static Map<String, String> cookies = null;
    private static Document document=null;
    private static List<String> imgUrlSqls=new ArrayList<>();
    private static QiswlManhua qiswlManhua=new QiswlManhua();
    private static QiswlCapterWithBLOBs bloBs=new QiswlCapterWithBLOBs();
    private static String imgUrlSql;
    public static int cartoons=0;
    public static Date date=new Date();

    /**
     * 模拟登录获取cookie和sessionid
     */
    public static void login() throws IOException {
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
        cookies = res.cookies();
    }

    /**
     * 获取信息  下载图片  数据入库
     */
    public static void parse() throws IOException {

        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).post();
        String init="http://www.yymh8.com";
        //选择器定位
        Elements elements = document.select("#html_box").select(".item");
        cartoons=elements.size();
        SqlSession sqlSession=MyBatisUtil.createSqlSession();
        //创建时间和更新时间

        qiswlManhua.setCreateTime(date);
        qiswlManhua.setUpdateTime(date);

        //获取内部元素（目标数据）

        //i表示漫画的顺序
        for (int i=1;i<=cartoons;i++) {
            System.out.println("这是第"+i+"个漫画");
            //获取封面图片地址
            String image=elements.get(i-1).select("a > div.cover > img").attr("src").trim();
            //下载封面图片
            String newImage=DownloadImg.download1(image,i);
            //获取漫画url
            String url = init+elements.get(i-1).select("a").attr("href").trim();
            //带着漫画url再次发请求，进入漫画内部页
            document = Jsoup.connect(url).cookies(cookies).timeout(1000*60*2).get();
            //开始获取目标数据

            //名称
            String title=document.select("body > div.cover-box > div.container > div.title").text();
            qiswlManhua.setTitle(title);
            //横着的封面图地址
            String cover=document.select("body > div.cover-box > div.bg > img").attr("src").trim();
            //下载横着的封面图
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
            System.out.println(lastChapterTitle);
            //获取第一章节的url,然后更换这个url的最后一个参数，一直到这个漫画的最新章节数
            String chapterUrl=document.select("#html_box > div:nth-child(1) > a").attr("href").trim();
            //截取该漫画最新一期编号
            String chapter=init+chapterUrl.substring(0,chapterUrl.length()-1);
            //对编号类型转换
            int chapters=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
            qiswlManhua.setLastChapter("第"+chapters+"话");
            qiswlManhua.setLastChapterTitle("第"+chapters+"话");
            qiswlManhua.setImage(newImage);
            //System.out.println(qiswlManhua);
            qiswlManhua.setCjid(i+"");
            qiswlManhua.setId(i);
            //获取到的数据入库
            sqlSession.getMapper(QiswlManhuaDao.class).insertSelective(qiswlManhua);
            sqlSession.commit();
            System.out.println("目前更新到第"+chapters+"话");
            //拿到新的url,再次发起请求
            //q代表第i个漫画里的第q个章节
            for (int q=1;q<=chapters;q++){
                String newUrl=chapter+q;
                document = Jsoup.connect(newUrl).cookies(cookies).timeout(1000*60*2).get();
                //System.out.println(newUrl);
                //拿到图片地址，进行下载
                Elements elements2=document.select(".read-article").select(".item");
                System.out.println("第"+q+"话里边有"+elements2.size()+"张图片");
                //j代表第q个章节里的第j张图片
                for (int j=1;j<=elements2.size();j++){
                    System.out.println("正在下载第"+j+"张图片ing...");
                    //获取章节内部每张图的url
                    String imgUrl=elements2.get(j-1).select("img").attr("src").trim();
                    //下载每个章节里的图片
                    imgUrlSql=DownloadImg.download3(imgUrl,i,q,j);

                    //加进集合------入库后清空集合
                    imgUrlSqls.add(imgUrlSql);
                }
                bloBs.setImagelist(imgUrlSqls.toString().substring(1,imgUrlSqls.toString().length()-1));
                bloBs.setManhuaId(i);
                bloBs.setCreateTime(date);
                bloBs.setUpdateTime(date);
                bloBs.setCjid(i+"");
                bloBs.setSort(q);
                bloBs.setTitle("第"+q+"话");
                sqlSession.getMapper(QiswlCapterDao.class).insertSelective(bloBs);
                sqlSession.commit();
                imgUrlSqls.clear();
            }
            //System.out.println(cover+"\n"+title+"\n"+desc.substring(0,desc.length()-10)+"\n"+author.substring(3)+"\n"+keyWord+"\n"+view.substring(4)+"\n"+mark.substring(0, mark.indexOf("人"))+"\n"+lastChapterTitle.substring(3,lastChapterTitle.length()-1));
        }
    }

    public static void update() throws IOException {
        SqlSession sqlSession=MyBatisUtil.createSqlSession();
        String initUrl = "http://www.yymh8.com/index.php?m=&c=Mh&a=book_cate&p_reload=1&reload_time=1557799064903";
        // 直接获取DOM树，带着cookies去获取
        document = Jsoup.connect(initUrl).cookies(cookies).timeout(1000*60*2).post();
        Elements elements = document.select("#html_box").select(".item");
        cartoons=elements.size();
        for (int i=1;i<=cartoons;i++) {
            String url = "http://www.yymh8.com"+elements.get(i-1).select("a").attr("href").trim();
            document = Jsoup.connect(url).cookies(cookies).get();
            String title=document.select("body > div.cover-box > div.container > div.title").text();
            String lastChapterTitle=document.select("#chapters > div.ch > div > span").text();

            String lastChapter="第"+lastChapterTitle.substring(3);
            String last=sqlSession.getMapper(QiswlManhuaDao.class).selectLastChapterByTitle(title);
            System.out.println(last);
            if(last.equals(lastChapter)){
                System.out.println("当前已是最新章节，请耐心等待");
            }else {
                System.out.println("正在尽力补全，请耐心等待");
                String chapterUrl=elements.get(i-1).select("#html_box > div:nth-child(1) > a").attr("href").trim();
                //数据库内信息
                //int sql=Integer.parseInt(last.substring(1,last.length()-1));
                //更新数据库
                sqlSession.getMapper(QiswlManhuaDao.class).updateLastChapter(title,lastChapter);
                sqlSession.commit();
                int html=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
                for (int sql=Integer.parseInt(last.substring(1,last.length()-1));sql<html;sql++){
                    document = Jsoup.connect(chapterUrl.substring(0,chapterUrl.length()-1)+sql).cookies(cookies).timeout(1000*60*2).get();
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
                    bloBs.setCreateTime(date);
                    bloBs.setUpdateTime(date);
                    bloBs.setCjid(i+"");
                    bloBs.setSort(sql+1);
                    bloBs.setTitle("第"+(sql+1)+"话");
                    sqlSession.getMapper(QiswlCapterDao.class).insertSelective(bloBs);
                    sqlSession.commit();
                    imgUrlSqls.clear();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {

        login();
        Scanner input=new Scanner(System.in);
        int num = -1;
        do {
            System.out.println("欢迎使用小蜘蛛，能帮您做些什么呢？");
            System.out.println("1.初始化");
            System.out.println("2.更新");
            System.out.println("请选择：");
            int choose=input.nextInt();
            switch (choose){
                case 1:
                    System.out.println("等待时间可能会有点长，正在努力......");
                    parse();
                    break;
                case 2:
                    System.out.println("等待时间可能会有点长，正在努力......");
                    update();
                    break;
            }
        }while (num == 0);
    }
}
