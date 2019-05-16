package com.qiaoxun.demo.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * qiswl_manhua
 * @author 
 */
public class QiswlManhua implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;//------ok

    /**
     * 所属栏目:id:name
     */
    private Integer lanmuId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 排序
     */
    private String sort;

    /**
     * 上架状态:0=下架,1=正常
     */
    private Byte status;//------ok

    /**
     * 浏览量
     */
    private Integer view;//------ok

    /**
     * 封面图片
     */
    private String image;//------ok

    /**
     * 类型(扩展)
     */
    private String type;

    /**
     * 作者
     */
    private String auther;//------ok

    /**
     * 描述
     */
    private String desc;//------ok

    /**
     * 订阅量
     */
    private Integer mark;//------ok

    /**
     * 题材:id:name
     */
    private Integer ticaiId;

    /**
     * 读者群:id:name
     */
    private Integer duzhequnId;

    /**
     * 地域:id:name
     */
    private Integer diyuId;

    /**
     * 进度:0=连载,1=完结
     */
    private Byte mhstatus;//------ok

    /**
     * 推荐:0=OFF,1=ON
     */
    private Boolean tjswitch;

    /**
     * 收费状态:0=免费,1=收费
     */
    private Boolean isfree;//------ok

    /**
     * 采集标识
     */
    private String cjid;

    /**
     * 采集状态:0=NO,1=YES
     */
    private Byte cjstatus;

    /**
     * 采集渠道
     */
    private String cjname;

    /**
     * 标签
     */
    private String keyword;//------ok

    /**
     * 最新章节
     */
    private String lastChapterTitle;//------ok

    /**
     * 被搜索次数
     */
    private Integer searchnums;

    /**
     * 最新章节用于检测
     */
    private String lastChapter;//------ok

    /**
     * 精品:0=OFF,1=ON
     */
    private Boolean isjingpin;//默认

    /**
     * 限免:0=OFF,1=ON
     */
    private Boolean xianmian;//默认

    /**
     * 横着的封面图
     */
    private String cover;//------ok

    /**
     * 热门:0=OFF,1=ON
     */
    private Boolean ishot;//默认

    /**
     * 独家:0=OFF,1=ON
     */
    private Boolean issole;//默认

    /**
     * 新作:0=OFF,1=ON
     */
    private Boolean isnew;//默认

    /**
     * 18+:0=OFF,1=ON
     */
    private Boolean h;//不懂

    /**
     * VIP专属:0=OFF,1=ON
     */
    private Boolean vipcanread;//默认

    /**
     * 评分
     */
    private String pingfen;

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

    public Integer getLanmuId() {
        return lanmuId;
    }

    public void setLanmuId(Integer lanmuId) {
        this.lanmuId = lanmuId;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getTicaiId() {
        return ticaiId;
    }

    public void setTicaiId(Integer ticaiId) {
        this.ticaiId = ticaiId;
    }

    public Integer getDuzhequnId() {
        return duzhequnId;
    }

    public void setDuzhequnId(Integer duzhequnId) {
        this.duzhequnId = duzhequnId;
    }

    public Integer getDiyuId() {
        return diyuId;
    }

    public void setDiyuId(Integer diyuId) {
        this.diyuId = diyuId;
    }

    public Byte getMhstatus() {
        return mhstatus;
    }

    public void setMhstatus(Byte mhstatus) {
        this.mhstatus = mhstatus;
    }

    public Boolean getTjswitch() {
        return tjswitch;
    }

    public void setTjswitch(Boolean tjswitch) {
        this.tjswitch = tjswitch;
    }

    public Boolean getIsfree() {
        return isfree;
    }

    public void setIsfree(Boolean isfree) {
        this.isfree = isfree;
    }

    public String getCjid() {
        return cjid;
    }

    public void setCjid(String cjid) {
        this.cjid = cjid;
    }

    public Byte getCjstatus() {
        return cjstatus;
    }

    public void setCjstatus(Byte cjstatus) {
        this.cjstatus = cjstatus;
    }

    public String getCjname() {
        return cjname;
    }

    public void setCjname(String cjname) {
        this.cjname = cjname;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLastChapterTitle() {
        return lastChapterTitle;
    }

    public void setLastChapterTitle(String lastChapterTitle) {
        this.lastChapterTitle = lastChapterTitle;
    }

    public Integer getSearchnums() {
        return searchnums;
    }

    public void setSearchnums(Integer searchnums) {
        this.searchnums = searchnums;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public Boolean getIsjingpin() {
        return isjingpin;
    }

    public void setIsjingpin(Boolean isjingpin) {
        this.isjingpin = isjingpin;
    }

    public Boolean getXianmian() {
        return xianmian;
    }

    public void setXianmian(Boolean xianmian) {
        this.xianmian = xianmian;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Boolean getIshot() {
        return ishot;
    }

    public void setIshot(Boolean ishot) {
        this.ishot = ishot;
    }

    public Boolean getIssole() {
        return issole;
    }

    public void setIssole(Boolean issole) {
        this.issole = issole;
    }

    public Boolean getIsnew() {
        return isnew;
    }

    public void setIsnew(Boolean isnew) {
        this.isnew = isnew;
    }

    public Boolean getH() {
        return h;
    }

    public void setH(Boolean h) {
        this.h = h;
    }

    public Boolean getVipcanread() {
        return vipcanread;
    }

    public void setVipcanread(Boolean vipcanread) {
        this.vipcanread = vipcanread;
    }

    public String getPingfen() {
        return pingfen;
    }

    public void setPingfen(String pingfen) {
        this.pingfen = pingfen;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        QiswlManhua other = (QiswlManhua) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getLanmuId() == null ? other.getLanmuId() == null : this.getLanmuId().equals(other.getLanmuId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getView() == null ? other.getView() == null : this.getView().equals(other.getView()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getAuther() == null ? other.getAuther() == null : this.getAuther().equals(other.getAuther()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getMark() == null ? other.getMark() == null : this.getMark().equals(other.getMark()))
            && (this.getTicaiId() == null ? other.getTicaiId() == null : this.getTicaiId().equals(other.getTicaiId()))
            && (this.getDuzhequnId() == null ? other.getDuzhequnId() == null : this.getDuzhequnId().equals(other.getDuzhequnId()))
            && (this.getDiyuId() == null ? other.getDiyuId() == null : this.getDiyuId().equals(other.getDiyuId()))
            && (this.getMhstatus() == null ? other.getMhstatus() == null : this.getMhstatus().equals(other.getMhstatus()))
            && (this.getTjswitch() == null ? other.getTjswitch() == null : this.getTjswitch().equals(other.getTjswitch()))
            && (this.getIsfree() == null ? other.getIsfree() == null : this.getIsfree().equals(other.getIsfree()))
            && (this.getCjid() == null ? other.getCjid() == null : this.getCjid().equals(other.getCjid()))
            && (this.getCjstatus() == null ? other.getCjstatus() == null : this.getCjstatus().equals(other.getCjstatus()))
            && (this.getCjname() == null ? other.getCjname() == null : this.getCjname().equals(other.getCjname()))
            && (this.getKeyword() == null ? other.getKeyword() == null : this.getKeyword().equals(other.getKeyword()))
            && (this.getLastChapterTitle() == null ? other.getLastChapterTitle() == null : this.getLastChapterTitle().equals(other.getLastChapterTitle()))
            && (this.getSearchnums() == null ? other.getSearchnums() == null : this.getSearchnums().equals(other.getSearchnums()))
            && (this.getLastChapter() == null ? other.getLastChapter() == null : this.getLastChapter().equals(other.getLastChapter()))
            && (this.getIsjingpin() == null ? other.getIsjingpin() == null : this.getIsjingpin().equals(other.getIsjingpin()))
            && (this.getXianmian() == null ? other.getXianmian() == null : this.getXianmian().equals(other.getXianmian()))
            && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
            && (this.getIshot() == null ? other.getIshot() == null : this.getIshot().equals(other.getIshot()))
            && (this.getIssole() == null ? other.getIssole() == null : this.getIssole().equals(other.getIssole()))
            && (this.getIsnew() == null ? other.getIsnew() == null : this.getIsnew().equals(other.getIsnew()))
            && (this.getH() == null ? other.getH() == null : this.getH().equals(other.getH()))
            && (this.getVipcanread() == null ? other.getVipcanread() == null : this.getVipcanread().equals(other.getVipcanread()))
            && (this.getPingfen() == null ? other.getPingfen() == null : this.getPingfen().equals(other.getPingfen()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getLanmuId() == null) ? 0 : getLanmuId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getView() == null) ? 0 : getView().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAuther() == null) ? 0 : getAuther().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getMark() == null) ? 0 : getMark().hashCode());
        result = prime * result + ((getTicaiId() == null) ? 0 : getTicaiId().hashCode());
        result = prime * result + ((getDuzhequnId() == null) ? 0 : getDuzhequnId().hashCode());
        result = prime * result + ((getDiyuId() == null) ? 0 : getDiyuId().hashCode());
        result = prime * result + ((getMhstatus() == null) ? 0 : getMhstatus().hashCode());
        result = prime * result + ((getTjswitch() == null) ? 0 : getTjswitch().hashCode());
        result = prime * result + ((getIsfree() == null) ? 0 : getIsfree().hashCode());
        result = prime * result + ((getCjid() == null) ? 0 : getCjid().hashCode());
        result = prime * result + ((getCjstatus() == null) ? 0 : getCjstatus().hashCode());
        result = prime * result + ((getCjname() == null) ? 0 : getCjname().hashCode());
        result = prime * result + ((getKeyword() == null) ? 0 : getKeyword().hashCode());
        result = prime * result + ((getLastChapterTitle() == null) ? 0 : getLastChapterTitle().hashCode());
        result = prime * result + ((getSearchnums() == null) ? 0 : getSearchnums().hashCode());
        result = prime * result + ((getLastChapter() == null) ? 0 : getLastChapter().hashCode());
        result = prime * result + ((getIsjingpin() == null) ? 0 : getIsjingpin().hashCode());
        result = prime * result + ((getXianmian() == null) ? 0 : getXianmian().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getIshot() == null) ? 0 : getIshot().hashCode());
        result = prime * result + ((getIssole() == null) ? 0 : getIssole().hashCode());
        result = prime * result + ((getIsnew() == null) ? 0 : getIsnew().hashCode());
        result = prime * result + ((getH() == null) ? 0 : getH().hashCode());
        result = prime * result + ((getVipcanread() == null) ? 0 : getVipcanread().hashCode());
        result = prime * result + ((getPingfen() == null) ? 0 : getPingfen().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", lanmuId=").append(lanmuId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", view=").append(view);
        sb.append(", image=").append(image);
        sb.append(", type=").append(type);
        sb.append(", auther=").append(auther);
        sb.append(", desc=").append(desc);
        sb.append(", mark=").append(mark);
        sb.append(", ticaiId=").append(ticaiId);
        sb.append(", duzhequnId=").append(duzhequnId);
        sb.append(", diyuId=").append(diyuId);
        sb.append(", mhstatus=").append(mhstatus);
        sb.append(", tjswitch=").append(tjswitch);
        sb.append(", isfree=").append(isfree);
        sb.append(", cjid=").append(cjid);
        sb.append(", cjstatus=").append(cjstatus);
        sb.append(", cjname=").append(cjname);
        sb.append(", keyword=").append(keyword);
        sb.append(", lastChapterTitle=").append(lastChapterTitle);
        sb.append(", searchnums=").append(searchnums);
        sb.append(", lastChapter=").append(lastChapter);
        sb.append(", isjingpin=").append(isjingpin);
        sb.append(", xianmian=").append(xianmian);
        sb.append(", cover=").append(cover);
        sb.append(", ishot=").append(ishot);
        sb.append(", issole=").append(issole);
        sb.append(", isnew=").append(isnew);
        sb.append(", h=").append(h);
        sb.append(", vipcanread=").append(vipcanread);
        sb.append(", pingfen=").append(pingfen);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}