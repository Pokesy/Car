package com.hengrtech.carheadline.net.params;

/**
 * 作者：loonggg on 2016/9/18 15:18
 */

public class NewsCollectParams {
    private int userId;
    private int newsId;
    private String token;

    public NewsCollectParams(int userId, int newsId, String token) {
        this.userId = userId;
        this.newsId = newsId;
        this.token = token;
    }
}
