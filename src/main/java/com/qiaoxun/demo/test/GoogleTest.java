package com.qiaoxun.demo.test;

import com.qiaoxun.demo.dao.MeiTuanShopInfoDao;
import com.qiaoxun.demo.pojo.MeituanShopInfo;
import com.qiaoxun.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GoogleTest {

    static  Logger logger = LoggerFactory.getLogger(GoogleTest.class);
    static SessionId sessionId=null;
    /**
     * 打开浏览器
     * @return
     */
    public static Document getDocument(String url){
        Document doc = null;
        //可使用的浏览器有：IE浏览器（webdriver.ie.driver）
        //火狐浏览器  (webdriver.gecko.driver)
        //谷歌浏览器 (webdriver.chrome.driver)
        //声明ChromeOptions，主要是给chrome设置参数
        ChromeOptions options = new ChromeOptions();
        //设置user agent为iphone6plus
        options.addArguments("--user-agent=iphone 6 plus");

        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", "D:\\dev\\tool\\google\\chromedriver.exe");

        // InternetExplorerDriver()   浏览器
        // FirefoxDriver()            火狐浏览器
        //谷歌浏览器

        WebDriver driver = new ChromeDriver();
        //设置浏览器窗口大小
        //driver.manage().window().setSize(new Dimension(700, 600));
        driver.manage().window().maximize();//窗口最大化

        driver.get(url);

        //将页面滚动条拖到底部------有时候可能会因为网速的原因无法全部显示
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,45000);");

        //等待一段时间------点击F12(不然登录时候的输入框不显示),切换到手机（触屏）模式，接下来登录并且重新定位------90秒时间足够用了
        try {
            //((JavascriptExecutor)driver).executeScript("scrollTo(0,10000)");
            Thread.sleep(1000*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sessionId=((ChromeDriver) driver).getSessionId();
        doc = Jsoup.parse(driver.getPageSource());

        //关闭浏览器
        //driver.close();
        //driver.quit();

        return doc;
    }
    /**
     * 解析传过来的doc
     * @param doc
     */
    public static void parse(Document doc) throws IOException {
        if(doc == null){
            logger.info("doc is null, unable to continue! ");
            return ;
        }
        System.out.println(doc);
        MeituanShopInfo info=new MeituanShopInfo();
        Elements elements=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li");
        System.out.println("数据条数为："+elements.size());
        Document document1=Jsoup.connect("https://h5.waimai.meituan.com/waimai/mindex/poipicker").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36").get();
        System.out.println(document1);
        //城市
        String city=document1.select("#wm-container > div > div > div > div._3EYkMhSBuKfjb8ikjP1fjq > div > div.WUPT9ZBr7BLmEP6Jeimto._3ArWSLLp_05olq26UbcBFn > font > font").text();
        info.setCity(city);

        SqlSession sqlSession=MyBatisUtil.createSqlSession();
        for (int i=0;i<elements.size();i++){
            //获取店家编号
            String no=elements.get(i).attr("data-watch").trim();
            String detailUrl="https://h5.waimai.meituan.com/waimai/mindex/menu?dpShopId=&mtShopId="+no;
            Document document=Jsoup.connect(detailUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36").get();
            System.out.println(document);
            //地址
            String address=document.select("#scrollArea > div._3_v5L5O7Zac2VFevnq17-E > div > div._347vUMR0jKx6LIMqSnxIZw > div._3rlgdBOov8p5dp4fwJFNQO > div:nth-child(1)").text();
            info.setAddress(address);
            String cityUrl="https://h5.waimai.meituan.com/waimai/mindex/poipicker";
            //店名
            String title=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._3ce4X4pC6NTjqy4_fKHA8E").text();
            info.setName(title);
            //评分
            String score=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._34MB4leIjAhG3LXl-DN8Ed._19QQt5prXpFQr9QhCVfwC5").text();
            info.setScore(score);
            //销量
            String orderNum=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._1V9_Khfd3oEDl7_xSgGuGL._34MB4leIjAhG3LXl-DN8Ed").text()/*.substring(2)*/;
            info.setOrderNum(orderNum);
            //平均配送时长
            String avgTime=elements.get(i).select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(1) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1) > font > font").text();
            //String avgTime=elements.get(i).select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(1) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1)").text();
            info.setAvgTime(avgTime);
            logger.info("配送时长为："+avgTime);
            //人均消费
            String avgCost=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)").text();
            info.setAvgCost(avgCost);
            //配送方式
            String method=elements.get(i).select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(3) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span._3WMYBNt0cX8jtjuQLqQ-0V").text();
            if (method.equals(""))
                info.setMethod("美团专送");
            //新商家标签
            String isNew=elements.get(i).select("> a > div._2q5HWkq__CHgEQLE76bhCF > img").attr("src").trim();
            if (isNew.equals("http://p0.meituan.net/aichequan/eb83cb963e67bc0ea4db4d7aef69d62f2578.png")){
                info.setIsNew(1);
            }

            //城市和联系方式无法获取
            sqlSession.getMapper(MeiTuanShopInfoDao.class).insertSelective(info);
            sqlSession.commit();
            /*logger.info("title: " + title);*/
            /*logger.info("orderNum: " + orderNum);
            logger.info("score:  " + score);
            logger.info("--------------\n");*/
        }
    }

    public static void main(String[] args) throws IOException {
        String url="https://h5.waimai.meituan.com/waimai/mindex/home";
        parse(getDocument(url));
    }
}
