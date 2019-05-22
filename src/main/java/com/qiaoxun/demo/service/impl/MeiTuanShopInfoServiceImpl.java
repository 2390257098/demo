package com.qiaoxun.demo.service.impl;

import com.qiaoxun.demo.dao.MeiTuanShopInfoDao;
import com.qiaoxun.demo.pojo.MeituanShopInfo;
import com.qiaoxun.demo.service.MeiTuanShopInfoService;
import com.qiaoxun.demo.test.GoogleTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MeiTuanShopInfoServiceImpl implements MeiTuanShopInfoService {

    MeituanShopInfo info=new MeituanShopInfo();
    Logger logger = LoggerFactory.getLogger(GoogleTest.class);

    @Resource
    private MeiTuanShopInfoDao infoDao;

    @Override
    public Document getDocument() {
        String url="https://h5.waimai.meituan.com/waimai/mindex/home";
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
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        doc = Jsoup.parse(driver.getPageSource());

        //关闭浏览器
        driver.close();
        driver.quit();

        return doc;
    }

    @Override
    public void parse(Document doc) {
        if(doc == null){
            logger.info("doc is null, unable to continue! ");
            return ;
        }
        Elements elements=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li");
        logger.info("数据条数为："+elements.size());

        for (int i=0;i<elements.size();i++){
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
            String avgTime=elements.get(i).select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(1) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1)").text();
            info.setAvgTime(avgTime);
            //人均消费
            String avgCost=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)").text();
            info.setAvgCost(avgCost);
            //配送方式
            String method=elements.get(i).select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(3) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span._3WMYBNt0cX8jtjuQLqQ-0V").text();
            if (method.equals(""))
                info.setMethod("美团专送");
            //新商家标签
            String isNew=elements.get(i).select("> a > div._2q5HWkq__CHgEQLE76bhCF > img").attr("src").trim();
            if (isNew.equals("http://p0.meituan.net/aichequan/eb83cb963e67bc0ea4db4d7aef69d62f2578.png"))
                info.setIsNew(1);
            //地址------点击li任意一处实现跳转（但是找不到url），进入商家页面------商家地址在内部(只有部分商家有地址信息)
            //String address=doc.select("#wm-container > div > div > div._2m6ykQWMTX3_dW7Ab-XsY8 > div._1JHn9KhcnvNrar1crXmTIk > div").text();
            //info.setAddress(address);
            //距地址的距离
            /*String distance=doc.select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span._34MB4leIjAhG3LXl-DN8Ed._3jD4JjJGIR519uivFOA_ud").text();
            info.setDistance(distance);*/
            //城市和联系方式无法获取
            //入库
            infoDao.insertSelective(info);
        }
    }
}
