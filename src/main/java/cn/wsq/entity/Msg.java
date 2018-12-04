package cn.wsq.entity;
import java.io.Serializable;

public class Msg implements Serializable {
    private String id;//主键id

    private String sendId;//发送者id

    private String acceptId;//接收者id

    private String msg;//消息内容

    private Integer signFlag;//是否签收，0是未签收，1是签收

    private java.util.Date createTime;//发送的时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }
    public String getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Integer getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }
    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
}