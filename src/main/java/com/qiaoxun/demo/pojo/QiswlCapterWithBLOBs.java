package com.qiaoxun.demo.pojo;

import java.io.Serializable;

/**
 * qiswl_capter
 * @author 
 */
public class QiswlCapterWithBLOBs extends QiswlCapter implements Serializable {
    /**
     * 内容
     */
    private String content;

    /**
     * 图片集
     *
     * 对集合里的数据进行拼接形成一个新的字符串
     */
    private String imagelist;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagelist() {
        return imagelist;
    }

    public void setImagelist(String imagelist) {
        this.imagelist = imagelist;
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
        QiswlCapterWithBLOBs other = (QiswlCapterWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getManhuaId() == null ? other.getManhuaId() == null : this.getManhuaId().equals(other.getManhuaId()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getIsvip() == null ? other.getIsvip() == null : this.getIsvip().equals(other.getIsvip()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getView() == null ? other.getView() == null : this.getView().equals(other.getView()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCjid() == null ? other.getCjid() == null : this.getCjid().equals(other.getCjid()))
            && (this.getCjname() == null ? other.getCjname() == null : this.getCjname().equals(other.getCjname()))
            && (this.getSwitch1() == null ? other.getSwitch1() == null : this.getSwitch1().equals(other.getSwitch1()))
            && (this.getCjstatus() == null ? other.getCjstatus() == null : this.getCjstatus().equals(other.getCjstatus()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getImagelist() == null ? other.getImagelist() == null : this.getImagelist().equals(other.getImagelist()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getManhuaId() == null) ? 0 : getManhuaId().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getIsvip() == null) ? 0 : getIsvip().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getView() == null) ? 0 : getView().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCjid() == null) ? 0 : getCjid().hashCode());
        result = prime * result + ((getCjname() == null) ? 0 : getCjname().hashCode());
        result = prime * result + ((getSwitch1() == null) ? 0 : getSwitch1().hashCode());
        result = prime * result + ((getCjstatus() == null) ? 0 : getCjstatus().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getImagelist() == null) ? 0 : getImagelist().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", content=").append(content);
        sb.append(", imagelist=").append(imagelist);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}