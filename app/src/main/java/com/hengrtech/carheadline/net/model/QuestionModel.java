package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * Created by jiao on 2016/9/9.
 */
public class QuestionModel {

  /**
   * questionId : 127
   * memberId : 36
   * question :
   * images : http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160909/ac155ffd.png,
   * reward : 10
   * createTime : 2016-09-09 10:07:11
   * status : 0
   * imgList : ["http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160909/ac155ffd.png"]
   * nickName : 令令令
   * avatar : http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160905/523310ea.png
   */

  private int questionId;
  private int memberId;
  private String question;
  private String images;
  private int reward;
  private String createTime;
  private int status;
  private String nickName;
  private String avatar;
  private List<String> imgList;

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public int getReward() {
    return reward;
  }

  public void setReward(int reward) {
    this.reward = reward;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public List<String> getImgList() {
    return imgList;
  }

  public void setImgList(List<String> imgList) {
    this.imgList = imgList;
  }
}
