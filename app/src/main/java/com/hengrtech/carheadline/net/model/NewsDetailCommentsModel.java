package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * Created by jiao on 2016/7/21.
 */
public class NewsDetailCommentsModel {

  /**
   * memberId : 1
   * realName : 李
   * password : e10adc3949ba59abbe56e057f20f883e
   * portrait : /uploads/default/2a6ea70a.png
   * nickName : 爱笑的眼
   * gender : 1
   * ageStage : 90后
   * mobileNo : 18253553110
   * careCarType : SUV,东风日产
   * signature : 爱在一起
   * registerTime : 2016-07-19 09:10:43
   * token : 5751b80443dd4150b23ba837f8225de5
   * type : 0
   */

  private MemberEntity member;
  /**
   * member : {"memberId":1,"realName":"李","password":"e10adc3949ba59abbe56e057f20f883e","portrait":"/uploads/default/2a6ea70a.png","nickName":"爱笑的眼","gender":1,"ageStage":"90后","mobileNo":"18253553110","careCarType":"SUV,东风日产","signature":"爱在一起","registerTime":"2016-07-19 09:10:43","token":"5751b80443dd4150b23ba837f8225de5","type":0}
   * commentsId : 40
   * newsId : 2
   * userId : 1
   * content : Dsds
   * commentsTime : 2016-07-21 15:18:43
   * praiseCount : 1
   * praiseUserList : [2]
   * havenPraise : false
   * commentsCount : 1
   */

  private int commentsId;
  private int newsId;
  private int userId;
  private String content;
  private String commentsTime;
  private int praiseCount;
  private boolean havenPraise;
  private int commentsCount;
  private List<Integer> praiseUserList;

  public MemberEntity getMember() {
    return member;
  }

  public void setMember(MemberEntity member) {
    this.member = member;
  }

  public int getCommentsId() {
    return commentsId;
  }

  public void setCommentsId(int commentsId) {
    this.commentsId = commentsId;
  }

  public int getNewsId() {
    return newsId;
  }

  public void setNewsId(int newsId) {
    this.newsId = newsId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getCommentsTime() {
    return commentsTime;
  }

  public void setCommentsTime(String commentsTime) {
    this.commentsTime = commentsTime;
  }

  public int getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(int praiseCount) {
    this.praiseCount = praiseCount;
  }

  public boolean isHavenPraise() {
    return havenPraise;
  }

  public void setHavenPraise(boolean havenPraise) {
    this.havenPraise = havenPraise;
  }

  public int getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(int commentsCount) {
    this.commentsCount = commentsCount;
  }

  public List<Integer> getPraiseUserList() {
    return praiseUserList;
  }

  public void setPraiseUserList(List<Integer> praiseUserList) {
    this.praiseUserList = praiseUserList;
  }

  public static class MemberEntity {
    private int memberId;
    private String realName;
    private String password;
    private String portrait;
    private String nickName;
    private int gender;
    private String ageStage;
    private String mobileNo;
    private String careCarType;
    private String signature;
    private String registerTime;
    private String token;
    private int type;

    public int getMemberId() {
      return memberId;
    }

    public void setMemberId(int memberId) {
      this.memberId = memberId;
    }

    public String getRealName() {
      return realName;
    }

    public void setRealName(String realName) {
      this.realName = realName;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getPortrait() {
      return portrait;
    }

    public void setPortrait(String portrait) {
      this.portrait = portrait;
    }

    public String getNickName() {
      return nickName;
    }

    public void setNickName(String nickName) {
      this.nickName = nickName;
    }

    public int getGender() {
      return gender;
    }

    public void setGender(int gender) {
      this.gender = gender;
    }

    public String getAgeStage() {
      return ageStage;
    }

    public void setAgeStage(String ageStage) {
      this.ageStage = ageStage;
    }

    public String getMobileNo() {
      return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
      this.mobileNo = mobileNo;
    }

    public String getCareCarType() {
      return careCarType;
    }

    public void setCareCarType(String careCarType) {
      this.careCarType = careCarType;
    }

    public String getSignature() {
      return signature;
    }

    public void setSignature(String signature) {
      this.signature = signature;
    }

    public String getRegisterTime() {
      return registerTime;
    }

    public void setRegisterTime(String registerTime) {
      this.registerTime = registerTime;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }
  }

}
