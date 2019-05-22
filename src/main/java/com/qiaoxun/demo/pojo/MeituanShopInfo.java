package com.qiaoxun.demo.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * meituan_shop_info
 * @author 
 */
public class MeituanShopInfo implements Serializable {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 商家名称
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 爬取日期
     */
    private Date crawlerDate;

    /**
     * 单量
     */
    private String orderNum;

    /**
     * 平均配送时长
     */
    private String avgTime;

    /**
     * 人均消费
     */
    private String avgCost;

    /**
     * 配送方式
     */
    private String method;

    /**
     * 联系方式2
     */
    private String phone2;

    /**
     * 新商家标签
     */
    private long isNew;

    /**
     * 品类
     */
    private String type;

    /**
     * 评分
     */
    private String score;
    /**
     * 商家编号
     */
    private String shopNo;
    private static final long serialVersionUID = 1L;

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCrawlerDate() {
        return crawlerDate;
    }

    public void setCrawlerDate(Date crawlerDate) {
        this.crawlerDate = crawlerDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(String avgCost) {
        this.avgCost = avgCost;
    }

    public long getIsNew() {
        return isNew;
    }

    public void setIsNew(long isNew) {
        this.isNew = isNew;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


}