package cn.wsq.entity;
import java.io.Serializable;

public class Friendrequest implements Serializable {
    private String id;//主键id

    private String sendId;//发送者id

    private String acceptId;//接收者id

    private java.util.Date request_data_time;//请求时间

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
    public java.util.Date getRequest_data_time() {
        return request_data_time;
    }

    public void setRequest_data_time(java.util.Date request_data_time) {
        this.request_data_time = request_data_time;
    }
}