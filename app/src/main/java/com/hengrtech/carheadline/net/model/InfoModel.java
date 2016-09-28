package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * Created by jiao on 2016/7/21.
 */
public class InfoModel {
    /**
     * newsId : 1
     * title : 锁定中国市场 探访特斯拉金港体验中心
     * source : 搜狐汽车
     * link : http://auto.sohu.com/20160414/n444192126.shtml
     * authorId : 0
     * type : 0
     * publishTime : 2016-09-23 17:14:39
     * content : 　　本月19日，特斯拉金港体验中心将正式营业，这是继侨福芳草地、恒通商务园、亦庄、华贸店之后，北京第5家特斯拉体验中心，也是特斯拉在中国的第17家体验中心。金港体验中心具有展示及体验功能，可向用户提供包括试驾、订车、充电等服务。
     * <img style="border: 0px currentColor; border-image: none;" title="走进特斯拉金港体验中心" src="http://m4.auto.itc.cn/car/600/83/84/Img4528483_600.jpg" alt="走进特斯拉金港体验中心" />
     * 　　对于此次金港体验中心重新复活，特斯拉工作人员表示，首先，这里的外部环境，以及周边的其它品牌4S店，和特斯拉的调性相符，另外一方面，金港曾是特斯拉进入中国的第一个落脚点，这次选择让金港“复活”更多的是一种“情怀”。
     * <img style="border: 0px currentColor; border-image: none;" title="走进特斯拉金港体验中心" src="http://m3.auto.itc.cn/car/600/82/84/Img4528482_600.jpg" alt="走进特斯拉金港体验中心" />
     * 　　目前，特斯拉采用“官网电商+实体体验中心”的直营模式，为用户实现购买过程中的完全透明、自主提供了更加便利的条件。在特斯拉体验中心，用户不仅能够充分领略特斯拉所带来的豪华智能的生活方式，更可以通过车身构造展示、互动触摸屏和亲身试驾体验等方式自主、透明的了解纯电动智能汽车。
     * 　　据特斯拉中国销售负责人王昊介绍，今年特斯拉还将在全国建立10家新店以满足日益增长的需求，同时他强调直销模式是特斯拉的特点，特斯拉不会采取授权的模式让其他经销商参与销售等业务。对于国产化问题，负责人表示特斯拉将在3年内完成，至于选址问题，目前还没有确切消息。
     * <img style="border: 0px currentColor; border-image: none;" title="走进特斯拉金港体验中心" src="http://m2.auto.itc.cn/car/600/81/84/Img4528481_600.jpg" alt="走进特斯拉金港体验中心" />
     * <img style="border: 0px currentColor; border-image: none;" title="走进特斯拉金港体验中心" src="http://m1.auto.itc.cn/car/600/80/84/Img4528480_600.jpg" alt="走进特斯拉金港体验中心" />
     * <img style="border: 0px currentColor; border-image: none;" title="走进特斯拉金港体验中心" src="http://m4.auto.itc.cn/car/600/79/84/Img4528479_600.jpg" alt="走进特斯拉金港体验中心" />
     * 　　总结：目前，在北京购买纯电动车特斯拉，可享受斯免摇号、不限行等优惠政策。伴随着政府部门对新能源汽车大力支持，以及全新产品Model X以及Model 3的引入，相信特拉将会在中国市场大有作为。据外媒统计，去年中国市场电动乘用车销量达到207,382辆，首次超越美国，占全球总销量的37.7%。未来中国将取代美国成为全球最大的零排放汽车市场。毫无疑问，中国市场对特斯拉来说显得格外重要。
     * coverArr : ["http://i3.itc.cn/20160413/36fe_3e9a0919_e4b2_0ba3_19f2_5ffe6221abae_4.jpg","http://i3.itc.cn/20160413/36fe_3e9a0919_e4b2_0ba3_19f2_5ffe6221abae_4.jpg","http://i3.itc.cn/20160413/36fe_3e9a0919_e4b2_0ba3_19f2_5ffe6221abae_4.jpg"]
     * commentsCount : 18
     * viewCount : 103
     * havenPraise : false
     * collected : false
     */

    private int newsId;
    private String title;
    private String source;
    private String link;
    private int authorId;
    private int type;
    private String publishTime;
    private String content;
    private int commentsCount;
    private int viewCount;
    private boolean havenPraise;
    private boolean collected;
    private List<String> coverArr;

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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

    public List<String> getCoverArr() {
        return coverArr;
    }

    public void setCoverArr(List<String> coverArr) {
        this.coverArr = coverArr;
    }
}
