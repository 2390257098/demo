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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleTest {

    static  Logger logger = LoggerFactory.getLogger(GoogleTest.class);
    static MeituanShopInfo info=new MeituanShopInfo();
    static SqlSession sqlSession=MyBatisUtil.createSqlSession();

    static WebDriver driver =null;
    /**
     * 打开浏览器
     * @return
     */
    public static void getDocument() throws IOException, InterruptedException {
        Document doc = null;
        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", "D:\\dev\\tool\\google\\chromedriver.exe");
        //创建一个浏览器------这行代码的位置是固定的，不能放在前面
        driver=new ChromeDriver();
        //加载页面（登陆页面）
        String loginUrl="http://h5.waimai.meituan.com/login?force=true&back_url=http%3A%2F%2Fh5.waimai.meituan.com%2Fwaimai%2Fmindex%2Fmine";
        driver.get(loginUrl);
        //设置浏览器窗口大小
        //driver.manage().window().setSize(new Dimension(700, 600));
        driver.manage().window().maximize();//窗口最大化
        /*WebElement input = driver.findElement(By.id("kw"));
        input.sendKeys(Keys.F12);*/
        //等待一段时间------点击F12(不然登录时候的输入框不显示),切换到手机（触屏）模式,带年纪首页，重新定位
        Thread.sleep(1000*50);
        //点击首页
        //driver.findElement(By.className("vhHLSLSvFKDyfNbVNtOGV")).click();
        //logger.info("我点击了首页了！！！");
        //城市不获取了，直接赋值
        info.setCity("杭州");
        //执行浏览器后退操作
        //driver.navigate().back();
        Thread.sleep(1000);
        String [] types={"美食","美团超市","生鲜果蔬","美团专送","下午茶","汉堡披萨","小吃馆","家常菜","鲜花蛋糕","品牌连锁"};
        String [] classNames={"efeR5uyg2vbdcC1mfhm1B","","","","","","","","",""};
        List<WebElement> list=driver.findElements(By.cssSelector("#wm-container > div > div > div._2qDABxIhG58DjS3SnGcdQ4 > div:nth-child(1) > a:nth-child(2) > div.lYqlChWY4rNp3-JYyHmhG > div > div > img"));
        List<String> list1=Collections.singletonList(driver.findElement(By.className("lazyimage-img")).getAttribute("src").trim());
        System.out.println("集合长度为"+list.size());
        System.out.println(list);
        for (int i=0;i<classNames.length;i++){
            driver.findElement(By.className(classNames[i])).click();
            info.setType(types[i]);
            logger.info("我点击了"+types[i]+"！！！");
            //将页面滚动条拖到底部    实现加载更多------but有时候可能会因为网速的原因无法全部显示
            ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,45000);");
            doc = Jsoup.parse(driver.getPageSource());
            parse(doc);
        }
        //logger.info(driver.findElement(By.className("_3ce4X4pC6NTjqy4_fKHA8E")).getCssValue("_3ce4X4pC6NTjqy4_fKHA8E"));

        //关闭浏览器
        //driver.close();
        //driver.quit();

    }
    /**
     * 解析传过来的doc
     * @param doc
     */
    public static void parse(Document doc) {
        if(doc == null){
            logger.info("doc is null, unable to continue! ");
            return ;
        }
        List<WebElement> elementList=driver.findElements(By.cssSelector("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li"));
        logger.info("方法一得到的数据条数为："+elementList.size());
        Elements elements=doc.select("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li");
        logger.info("方法二得到的数据条数为："+elements.size());
        for (int i=0;i<elements.size();i++){
            String name=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._3ce4X4pC6NTjqy4_fKHA8E").text();
            info.setName(name);
            String no=elements.get(i).attr("data-watch").trim();
            info.setShopNo(no);
            String score=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._34MB4leIjAhG3LXl-DN8Ed._19QQt5prXpFQr9QhCVfwC5").text();
            info.setScore(score);
            String orderNum=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._1V9_Khfd3oEDl7_xSgGuGL._34MB4leIjAhG3LXl-DN8Ed").text().substring(2);
            info.setOrderNum(orderNum);
            String avgTime=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div._20HnNmr2Skt7lyGyrsg5R7 > span:nth-child(1)").text();
            info.setAvgTime(avgTime);
            String avgCost=elements.get(i).select("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)").text();
            info.setAvgCost(avgCost);
            logger.info(avgCost);
            driver.findElement(By.cssSelector(".FcKg0z7ZA3tlZo-vSxopN")).click();
            doc = Jsoup.parse(driver.getPageSource());
            String address=doc.select("#scrollArea > div._3_v5L5O7Zac2VFevnq17-E > div > div._347vUMR0jKx6LIMqSnxIZw > div._3rlgdBOov8p5dp4fwJFNQO > div:nth-child(1) > div._3TRRkFr5YLsBe5i4G3Y0Yc._3UpJpE2r3K6PlnsEUkPva8 > p").text();
            info.setAddress(address);
            driver.navigate().back();
            sqlSession.getMapper(MeiTuanShopInfoDao.class).insertSelective(info);
            sqlSession.commit();
        }
        /*for (int i=0;i<elementList.size();i++){
            //获取店家编号
            String no=elementList.get(i).getAttribute("data-watch").trim();
            info.setShopNo(no);
            logger.info("商家编号："+no);
            //店名
            String title=elementList.get(i).getCssValue("a > div._2L_M6YlchG3yfnSSg7L6mL > div._3ce4X4pC6NTjqy4_fKHA8E");
            info.setName(title);
            //评分
            String score=elementList.get(i).getCssValue("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._34MB4leIjAhG3LXl-DN8Ed._19QQt5prXpFQr9QhCVfwC5");
            info.setScore(score);
            //销量
            String orderNum=elementList.get(i).getCssValue("a > div._2L_M6YlchG3yfnSSg7L6mL > div._21yyPg0MxIEFMCg8RnTeUo > div:nth-child(1) > span._1V9_Khfd3oEDl7_xSgGuGL._34MB4leIjAhG3LXl-DN8Ed")*//*.substring(2)*//*;
            info.setOrderNum(orderNum);
            //平均配送时长
            String avgTime=elementList.get(i).getCssValue("._20HnNmr2Skt7lyGyrsg5R7._34MB4leIjAhG3LXl-DN8Ed");
            info.setAvgTime(avgTime);
            //人均消费
            String avgCost=elementList.get(i).getCssValue("a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span:nth-child(3)");
            info.setAvgCost(avgCost);
            logger.info("人均消费："+avgCost);
            //配送方式
            String method=elementList.get(i).getCssValue("#wm-container > div > div > div._3uTnjGaICQR0QQJ0b-Nk8k._2yCMxFCzKcIRQuCBS1XZ_k > div > ul > li:nth-child(3) > a > div._2L_M6YlchG3yfnSSg7L6mL > div._1wRyOmTit2wxvwxcfx4tf1 > span._3WMYBNt0cX8jtjuQLqQ-0V");
            if (method.equals(""))
                info.setMethod("美团专送");
            //新商家标签
            *//*String isNew=
            if (!elementList.get(i).findElement(By.cssSelector("a > div._2q5HWkq__CHgEQLE76bhCF > img")).getAttribute("src").trim().isEmpty());
            if (isNew.equals("http://p0.meituan.net/aichequan/eb83cb963e67bc0ea4db4d7aef69d62f2578.png")){
                info.setIsNew(1);
            }*//*
            //点击进入商家内部，获取地址
            *//*driver.findElement(By.className("FcKg0z7ZA3tlZo-vSxopN")).click();
            doc = Jsoup.parse(driver.getPageSource());
            String address=doc.select("#scrollArea > div._3_v5L5O7Zac2VFevnq17-E > div > div._347vUMR0jKx6LIMqSnxIZw > div._3rlgdBOov8p5dp4fwJFNQO > div:nth-child(1) > div._3TRRkFr5YLsBe5i4G3Y0Yc._3UpJpE2r3K6PlnsEUkPva8 > p").text();
            info.setAddress(address);
            //后退到商家列表页面
            driver.navigate().back();*//*
            //联系方式无法获取
            sqlSession.getMapper(MeiTuanShopInfoDao.class).insertSelective(info);
            sqlSession.commit();
        }*/
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        getDocument();

    }
}
