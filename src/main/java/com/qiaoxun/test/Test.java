package com.qiaoxun.test;


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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
    private static Map<String, String> cookies = null;
    private static Document document=null;
    private static QiswlManhua qiswlManhua=new QiswlManhua();
    private static List<String> imgUrlSqls=new ArrayList<>();
    private static QiswlCapterWithBLOBs bloBs=new QiswlCapterWithBLOBs();
    public static int cartoons=0;

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
        document = Jsoup.connect(initUrl).cookies(cookies).timeout(1000*60*2).post();
        String init="http://www.yymh8.com";
        //选择器定位
        Elements elements = document.select("#html_box").select(".item");
        cartoons=elements.size();
        //入库操作
        SqlSession sqlSession=MyBatisUtil.createSqlSession();
        qiswlManhua.setCreateTime(new Date());
        qiswlManhua.setUpdateTime(new Date());

        //获取内部元素（目标数据）

        //i表示漫画的顺序
        for (int i=1;i<=elements.size();i++) {
            System.out.println("这是第"+i+"个漫画");
            //获取封面图片地址
            String image=elements.get(i-1).select("a > div.cover > img").attr("src").trim();
            //下载封面图片
            DownloadImg.download1(image,i);
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

            //获取第一章节的url,然后更换这个url的最后一个参数，一直到这个漫画的最新章节数
            String chapterUrl=document.select("#html_box > div:nth-child(1) > a").attr("href").trim();
            //截取该漫画最新一期编号
            String chapter=init+chapterUrl.substring(0,chapterUrl.length()-1);
            //对编号类型转换
            int chapters=Integer.parseInt(lastChapterTitle.substring(3,lastChapterTitle.length()-1));
            qiswlManhua.setLastChapter("第"+chapters+"话");
            qiswlManhua.setLastChapterTitle("第"+chapters+"话");
            System.out.println(qiswlManhua);
            qiswlManhua.setId(i);
            qiswlManhua.setImage(image);
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
                    String imgUrlSql=DownloadImg.download3(imgUrl,i,q,j);
                    //获取每张图片的路径，存进集合，用完后清空
                    imgUrlSqls.add(imgUrlSql);
                }
                bloBs.setTitle("第"+q+"话");
                bloBs.setCjid(i+"");
                bloBs.setUpdateTime(new Date());
                bloBs.setCreateTime(new Date());
                bloBs.setImagelist(imgUrlSqls .toString());
                bloBs.setManhuaId(i);
                bloBs.setSort(q);
                sqlSession.getMapper(QiswlCapterDao.class).insertSelective(bloBs);
                sqlSession.commit();
                //清空集合
                imgUrlSqls.clear();
            }
            //System.out.println(cover+"\n"+title+"\n"+desc.substring(0,desc.length()-10)+"\n"+author.substring(3)+"\n"+keyWord+"\n"+view.substring(4)+"\n"+mark.substring(0, mark.indexOf("人"))+"\n"+lastChapterTitle.substring(3,lastChapterTitle.length()-1));
        }
    }

    public static void main(String[] args) throws IOException {

        // 先模拟登录获取到cookie和sessionid并存到全局变量cookies中
        login();
        //从document中解析出目标数据
        parse();

    }



}
