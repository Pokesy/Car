package com.hengrtech.carheadline.net.params;

/**
 * 作者：loonggg on 2016/9/14 09:11
 */

public class SendCommentCarParams {
  public int memberId;
  public int questionId;
  public String answer;

  public SendCommentCarParams(int memberId, int questionId, String answer) {
    this.memberId = memberId;
    this.questionId = questionId;
    this.answer = answer;
  }
}
