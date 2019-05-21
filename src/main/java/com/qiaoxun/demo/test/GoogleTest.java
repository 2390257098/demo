package com.qiaoxun.demo.test;

import com.qiaoxun.demo.pojo.MeituanShopInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleTest {
    static MeituanShopInfo info=new MeituanShopInfo();
    private static final Logger logger = LoggerFactory.getLogger(GoogleTest.class);
    /**
     * 打开浏览器，不能向下滑动
     * @param url
     * @return
     */
    public static Document getDocument(String url){
        Document doc = null;
        //可使用的浏览器有：IE浏览器（webdriver.ie.driver）
        //火狐浏览器  (webdriver.gecko.driver)
        //谷歌浏览器 (webdriver.chrome.driver)
        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", "D:\\dev\\tool\\google\\chromedriver.exe");

        // InternetExplorerDriver()   浏览器
        // FirefoxDriver()            火狐浏览器
        //谷歌浏览器
        WebDriver driver = new ChromeDriver();

        driver.get(url);
        //等待一段时间------留给用户充足的时间来登录并且重新定位
        try {
            //((JavascriptExecutor)driver).executeScript("scrollTo(0,10000)");
            Thread.sleep(1000*60*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        doc = Jsoup.parse(driver.getPageSource());

        //关闭浏览器
        driver.close();
        driver.quit();

        return doc;
    }
    /**
     * 解析传过来的doc
     * @param doc
     */
    public static void parse(Document doc){
        if(doc == null){
            logger.info("doc is null, unable to continue! ");
            return ;
        }
        Elements elements=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li");
        System.out.println("数据条数为："+elements.size());

        for (int i=0;i<elements.size();i++){
            //店名
            String title=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._3ce4X4pC6NTjqy4_fKHA8E").text();
            info.setName(title);
            //评分
            String score=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._34MB4leIjAhG3LXl-DN8Ed._19QQt5prXpFQr9QhCVfwC5").text();
            info.setScore(score);
            //销量
            String orderNum=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._1V9_Khfd3oEDl7_xSgGuGL._34MB4leIjAhG3LXl-DN8Ed").text()/*.substring(2)*/;
            info.setOrderNum(orderNum);
            //平均配送时长
            String avgTime=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(1) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1)").text();
            info.setAvgTime(avgTime);
            //人均消费
            String avgCost=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)").text();
            info.setAvgCost(avgCost);
            //配送方式
            String method=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(3) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span._3WMYBNt0cX8jtjuQLqQ-0V").text();
            if (method.equals(""))
                info.setMethod("美团专送");
            //新商家标签
            String isNew=doc.select("> a > div._2q5HWkq__CHgEQLE76bhCF > img").attr("src").trim();
            if (isNew.equals("http://p0.meituan.net/aichequan/eb83cb963e67bc0ea4db4d7aef69d62f2578.png"))
                info.setIsNew(1);
            //地址
            String address=doc.select("#wm-container > div > div > div._2m6ykQWMTX3_dW7Ab-XsY8 > div._1JHn9KhcnvNrar1crXmTIk > div").text();
            info.setAddress(address);
            //距地址的距离
            String distance=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span._34MB4leIjAhG3LXl-DN8Ed._3jD4JjJGIR519uivFOA_ud").text();
            info.setDistance(distance);
            //城市和联系方式无法获取
            logger.info("title: " + title);
            logger.info("orderNum: " + orderNum);
            logger.info("score:  " + score);
            logger.info("--------------\n");
        }
        /*Elements content = doc.select("div.content");

        System.out.println(content.size());
        for (Element element : content) {
            //获取文章标题
            String title = element.select("a.title").text();

            //获取获取帖子网址
            String url = element.select("a.title").attr("href");
            url = "https://www.jianshu.com" + url;

            //获取文章的摘要
            String digest = element.select("p.abstract").text();

            //获取文章作者名称
            String author = element.select("a.nickname").text();

            //获取作者网址
            String authorUrl = element.select("a.nickname").attr("href");
            authorUrl = "https://www.jianshu.com" + authorUrl;

            logger.info("title: " + title);
            logger.info("url: " + url);
            logger.info("digest:  " + digest);
            logger.info("author: " + author);
            logger.info("authorUrl: " + authorUrl);
            logger.info("--------------\n");
        }*/
    }

    public static void main(String[] args) {
        String url="https://h5.waimai.meituan.com/waimai/mindex/home";
        parse(getDocument(url));
    }
}
