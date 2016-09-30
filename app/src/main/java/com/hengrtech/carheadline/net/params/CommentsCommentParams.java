package com.hengrtech.carheadline.net.params;

/**
 * 作者：loonggg on 2016/9/18 15:18
 */

public class CommentsCommentParams {
    private int cmtId;
    private int userId;
    private String content;

    public CommentsCommentParams(int cmtId, int userId, String content) {
        this.cmtId = cmtId;
        this.userId = userId;
        this.content = content;
    }
}
