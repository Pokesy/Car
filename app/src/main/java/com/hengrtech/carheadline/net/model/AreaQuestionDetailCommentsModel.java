package com.hengrtech.carheadline.net.model;

/**
 * Created by jiao on 2016/7/21.
 */
public class AreaQuestionDetailCommentsModel {

  /**
   * answerId : 39
   * questionId : 1
   * memberId : 36
   * answer : 回答内容：的风格的股份
   * isBest : false
   * createTime : 2016-09-06 15:51:26
   * nickName : 令令令
   * avatar : http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160909/d7ee8ef0.png
   */

  private int answerId;
  private int questionId;
  private int memberId;
  private String answer;
  private boolean isBest;
  private String createTime;
  private String nickName;
  private String avatar;

  public int getAnswerId() {
    return answerId;
  }

  public void setAnswerId(int answerId) {
    this.answerId = answerId;
  }

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

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public boolean isIsBest() {
    return isBest;
  }

  public void setIsBest(boolean isBest) {
    this.isBest = isBest;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
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
}
