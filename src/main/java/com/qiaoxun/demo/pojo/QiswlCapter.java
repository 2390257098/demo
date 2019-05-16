package com.qiaoxun.demo.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * qiswl_capter
 * @author 
 */
public class QiswlCapter implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 章节标题
     */
    private String title;//----ok

    /**
     * 章节封面图
     */
    private String image;//不知道是那张图

    /**
     * 创建时间
     */
    private Date createTime;//获取不到

    /**
     * 更新时间
     */
    private Date updateTime;//获取不到

    /**
     * 所属漫画:id:title
     */
    private Integer manhuaId;//----ok

    /**
     * 排序
     */
    private Integer sort;//----ok

    /**
     * 性别:0=免费,1=VIP
     */
    private String isvip;

    /**
     * 售价
     */
    private Integer score;

    /**
     * 阅读量
     */
    private Integer view;

    /**
     * 类型:2=小说,1=漫画
     */
    private Byte type;

    /**
     * 采集标识
     */
    private String cjid;

    /**
     * 采集渠道
     */
    private String cjname;

    /**
     * 上架状态:0=下架,1=正常
     */
    private Boolean switch1;

    /**
     * 采集状态
     */
    private Byte cjstatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getManhuaId() {
        return manhuaId;
    }

    public void setManhuaId(Integer manhuaId) {
        this.manhuaId = manhuaId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCjid() {
        return cjid;
    }

    public void setCjid(String cjid) {
        this.cjid = cjid;
    }

    public String getCjname() {
        return cjname;
    }

    public void setCjname(String cjname) {
        this.cjname = cjname;
    }

    public void setSwitch1(Boolean switch1) {
        this.switch1 = switch1;
    }

    public Boolean getSwitch1() {
        return switch1;
    }

    public Byte getCjstatus() {
        return cjstatus;
    }

    public void setCjstatus(Byte cjstatus) {
        this.cjstatus = cjstatus;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", image=").append(image);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", manhuaId=").append(manhuaId);
        sb.append(", sort=").append(sort);
        sb.append(", isvip=").append(isvip);
        sb.append(", score=").append(score);
        sb.append(", view=").append(view);
        sb.append(", type=").append(type);
        sb.append(", cjid=").append(cjid);
        sb.append(", cjname=").append(cjname);
        sb.append(", switch1=").append(switch1);
        sb.append(", cjstatus=").append(cjstatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}