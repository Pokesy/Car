package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/28 10:28
 */

public class NewsDetailMoreModel {
    private int praiseCount;
    private boolean havenPraise;
    private boolean collected;

    private List<RelatedReadingBean> relatedReading;

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

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public List<RelatedReadingBean> getRelatedReading() {
        return relatedReading;
    }

    public void setRelatedReading(List<RelatedReadingBean> relatedReading) {
        this.relatedReading = relatedReading;
    }

    public static class RelatedReadingBean {
        private int newsId;
        private String title;
        private String source;
        private String link;
        private int authorId;
        private int type;
        private String content;
        private boolean havenPraise;
        private boolean collected;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isHavenPraise() {
            return havenPraise;
        }

        public void setHavenPraise(boolean havenPraise) {
            this.havenPraise = havenPraise;
        }

        public boolean isCollected() {
            return collected;
        }

        public void setCollected(boolean collected) {
            this.collected = collected;
        }
    }
}
