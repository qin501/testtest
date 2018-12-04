package cn.wsq.entity;
import java.io.Serializable;

public class User implements Serializable {
    private String id;//id主键

    private String username;//用户名

    private String password;//密码

    private String faceicon;//头像

    private String nickname;//昵称

    private java.util.Date createDate;//创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getFaceicon() {
        return faceicon;
    }

    public void setFaceicon(String faceicon) {
        this.faceicon = faceicon;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public java.util.Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }
}