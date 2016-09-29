package com.hengrtech.carheadline.net.params;

/**
 * 作者：loonggg on 2016/9/18 15:18
 */

public class NewsCommentParams {
    private int newsId;
    private int userId;
    private String content;

    public NewsCommentParams(int newsId, int userId, String content) {
        this.newsId = newsId;
        this.userId = userId;
        this.content = content;
    }
}
