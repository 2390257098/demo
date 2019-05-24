package com.qiaoxun.demo.test;

import com.qiaoxun.demo.dao.MeiTuanShopInfoDao;
import com.qiaoxun.demo.pojo.MeituanShopInfo;
import com.qiaoxun.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MeiTuanCrawler {

    static  Logger logger = LoggerFactory.getLogger(MeiTuanCrawler.class);
    static MeituanShopInfo info=new MeituanShopInfo();
    static SqlSession sqlSession=MyBatisUtil.createSqlSession();
    static WebDriverWait wait = null;
    static final int i=0;


    static WebDriver driver =null;
    /**
     * 打开浏览器
     * @return
     */
    public static void getDocument() throws IOException, InterruptedException {
        Document doc = null;
        //用代理ip
        //System.getProperties().setProperty("http.proxyHost", "123.172.68.180");
        //System.getProperties().setProperty("http.proxyPort", "");
        ChromeOptions options = new ChromeOptions();
        // 设置代理ip
        String ip = "112.87.70.171:9999";
        options.addArguments("--proxy-server=http://" + ip);

        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", "D:\\dev\\tool\\google\\chromedriver.exe");
        //System.getProperties().setProperty("http.proxyHost", "123.172.68.180");
        //System.getProperties().setProperty("http.proxyPort", "53281");
        //换个版本试试
        //System.setProperty("w","D:\\dev\\tool\\Chrome\\Chrome.74.0.3729.157.x32\\\\chromedriver.exe");
        //创建一个浏览器------这行代码的位置是固定的，不能放在前面
        driver=new ChromeDriver(options);
        wait=new WebDriverWait(driver, 10, 1);
        //加载页面（登陆页面）
        String loginUrl="http://h5.waimai.meituan.com/login?force=true&back_url=http%3A%2F%2Fh5.waimai.meituan.com%2Fwaimai%2Fmindex%2Fmine";
        driver.get(loginUrl);
        //设置浏览器窗口大小
        //driver.manage().window().setSize(new Dimension(700, 600));
        driver.manage().window().maximize();//窗口最大化
        //定位对象时给10s 的时间, 如果10s 内还定位不到则抛出异常
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //等待一段时间------点击F12(不然登录时候的输入框不显示),切换到手机（触屏）模式,点击首页，重新定位(网络信号强的时候不用手动刷新)
        Thread.sleep(1000*60*2);

        //城市不获取了，直接赋值
        info.setCity("杭州");
        String type=driver.findElement(By.className("_2qDABxIhG58DjS3SnGcdQ4")).getText();
        Thread.sleep(500);
        String types[]=type.split("\n");
        for (int i=0;i<10;i++){
            if (i<5){
                driver.findElement(By.xpath("//*[@id=\"wm-container\"]/div/div/div[2]/div[1]/a["+(i+1)+"]")).click();
            }else {
                driver.findElement(By.xpath("//*[@id=\"wm-container\"]/div/div/div[2]/div[2]/a["+(i+1)+"]")).click();
            }
            //等待页面加载完全------爬的慢一点，对待对方服务器友好一点
            Thread.sleep(1000*20);
            info.setType(types[i]);
            logger.info("我点击了"+types[i]+"！！！");
            //下载页面
            doc = Jsoup.parse(driver.getPageSource());
            //解析页面------获取目标数据
            parse(doc);

            //将页面滚动条拖到底部    但不会实现加载更多------这个问题有待解决
            //((JavascriptExecutor)driver).executeScript("window.scrollTo(0,45000);");

        }


        //关闭浏览器
        driver.close();
        driver.quit();

    }
    /**
     * 解析传过来的doc
     * @param doc
     */
    public static void parse(Document doc) throws InterruptedException {
        if(doc == null){
            logger.info("doc is null, unable to continue! ");
            return ;
        }
        Elements elements=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li");
        logger.info("数据条数为："+elements.size());
        for ( int i=0;i<elements.size();i++){
            String name=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._3ce4X4pC6NTjqy4_fKHA8E").text();
            info.setName(name);
            String no=elements.get(i).attr("data-watch").trim();
            info.setShopNo(no);
            String score=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._34MB4leIjAhG3LXl-DN8Ed._19QQt5prXpFQr9QhCVfwC5").text();
            info.setScore(score);
            String orderNum=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._1V9_Khfd3oEDl7_xSgGuGL._34MB4leIjAhG3LXl-DN8Ed").text();
            if (!orderNum.equals("")){
                orderNum=orderNum.substring(2);
            }
            info.setOrderNum(orderNum);
            String avgTime=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1)").text();
            info.setAvgTime(avgTime);
            String avgCost=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)").text();
            if (!avgCost.equals("")){
                avgCost=avgCost.substring(2);
            }
            info.setAvgCost(avgCost);
            Thread.sleep(1000);

            //driver.findElement(By.linkText(name)).click();
            //进入商家详情页------拿地址
            driver.findElement(By.xpath("//*[@id=\"wm-container\"]/div/div/div[4]/div/ul/li["+(i+1)+"]/a")).click();
            Thread.sleep(1000);

            //点击商家------此处有坑，定位问题
            driver.findElement(By.xpath("//*[@id=\"scrollArea\"]/div[3]/nav/div[3]")).click();
            Thread.sleep(1000);
            String address=driver.findElement(By.className("_1DIKnLUnCkmE6RVWwwhY-e")).getText();
            info.setAddress(address);
            //退回到商家列表页面
            driver.navigate().back();
            Thread.sleep(2000);
            sqlSession.getMapper(MeiTuanShopInfoDao.class).insertSelective(info);
            sqlSession.commit();
        }
        driver.navigate().back();
        Thread.sleep(10000);
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        getDocument();

    }
}
