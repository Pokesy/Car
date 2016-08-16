package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * Created by jiao on 2016/7/21.
 */
public class InfoModel {

  public String getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(String praiseCount) {
    this.praiseCount = praiseCount;
  }

  /**
   * newsId : 15
   * source : 搜狐汽车
   * coverArr : ["http://i3.itc.cn/20160413/36fe_3e9a0919_e4b2_0ba3_19f2_5ffe6221abae_4.jpg"]
   * commentsCount : 0
   * link : http://auto.sohu.com/20160415/n444241298.shtml
   * title : 华晨自主品牌渐式微 合资业务成救命稻草
   * author :
   * createTime : 2198-05-05 10:10:19
   * praiseCount:0
   * status : 1
   * content : 　　华晨汽车旗下A股上市公司金杯汽车日前发布的2015年报显示，公司利润总额2.58亿元，同比增长551.7%。这表明金杯汽车2015年终
   * 于结束了连续亏损，实现扭亏为盈。但从年报中能够看出，金杯汽车的扭亏为盈来源于与宝马相配套的零部件业务，自主整车业务依然下滑明显，销量和利润呈现双 双下滑的态势。
   * 　　不仅是金杯汽车，华晨旗下的几个自主品牌的整车业务业绩堪忧，宝马的合资业务成为支撑华晨汽车的唯一支柱，而这种严重偏科的情况也引发了人们对华晨汽车未来发展的担忧。
   * 　　合资业务成唯一救命稻草
   * 　　对华晨汽车而言，与宝马相关的业务已经成为支撑其发展获得利润的最重要来源。
   * 　　金杯汽车的2015年年报显示，金杯汽车利润之所有能够扭亏为盈，并不是来自于整车业务的贡献，而是来自于与宝马配套的零配件公司。例如，金杯
   * 汽车控股子公司金杯江森公司和上海延峰江森公司共同出资收购了沈阳施尔奇公司，为获得华晨宝马1系、3系车型新业务提供支撑。同时，金杯江森公司和上海延 锋江森公司2015年8月与Johnson
   * Controls Solingen Beteiligungs GmbH签署了《股权转让协议》，分别受让后者持有的沈阳施尔奇汽车50%的股权，而施尔奇的主要客户就是宝马。
   * 　　除了金杯汽车之外，华晨在香港上市的新晨动力、华晨中国两家上市公司的业绩也都紧靠宝马的业务。根据其业绩报告，2015年华晨中国利润同比下滑35.32%，主要原因是华晨宝马利润下滑超过30%。
   * 　　而与此相对，华晨的自主品牌整车业务却难阻下滑趋势。2015年，金杯汽车整车销量不到5万辆，同比下降41.15%，金杯整车业务依然处于持续亏损状态。
   * 　　自主整车业务日渐势微
   * 　　根据中国汽车工业协会的最新统计数据，2016年1季度，汽车产销保持稳定增长，产销增幅比上年同期均呈小幅提升。其中，中国品牌乘用车共销售
   * 255.3万辆，同比增长11.1%，占乘用车销售总量的45%，比上年同期提高1.8个百分点。中国品牌的商用车产销几乎都超过了85万辆，销售同比增 长了1.2%。
   * 　　但在这样的大环境下也没能改变华晨汽车自主品牌整车业务衰退的局势，华晨旗下3个自主整车品牌全部沦陷。
   * 　　根据金杯汽车日前发布的2016年3月份产销数据快报显示，金杯汽车3月份销售载货汽车3532辆，较去年同期下降41.30%。而1月至3月，金杯汽车累计销售载货汽车5803辆，较去年同期下降65.16%。
   * 　　与此同时，一直被给予众望的中华品牌也表现欠佳。在全国乘用车联席会发布的2016年前两个月的轿车销量排行榜中，中华品牌连前100名都没有
   * 进入。而其SUV产品V5，是今年前两个月SUV销售排行榜中唯一进入前100名的车型，但前两个销量只有2000辆出头，同比下降超过70%。显然，中
   * 华品牌的这种衰退已成为一个持续趋势。2014年，中华汽车国内销量仅为14万辆左右，同比下降超过30%。
   * 　　而华晨汽车下大力气专门打造的高端自主品牌华颂的表现则更是惨淡。2015年3月，华颂7正式上市，作为华晨汽车的战略车型，2015年华颂7
   * 全年销售不足1万辆，甚至前九个月的累计销量不足800辆。进入2016年，更是有媒体曝出，1月份华颂7的销量仅为60多辆。
   * 　　严重“偏科”存发展隐忧
   * 　　合资业务与自主业务的这种明显强弱对比的“偏科”现象，为华晨汽车未来的发展增加了问题和风险。
   * 　　有业内人士表示，依托强势合资伙伴，华晨汽车获得了巨大的发展机会，但其自主品牌体系的弱势表现，让这种发展模式存在隐忧。虽然华晨汽车通过资本市场等一系列操作和措施来对自主品牌进行投入，但成效不佳。
   * 　　分析造成这种局面的原因，一些专家表示，华晨汽车业绩不景气已经持续很长一段时间，作为一个典型的多品牌战略集团，华晨汽车自主品牌的步伐显得
   * 有些缓慢，产品、品牌和体系的竞争力这些年来没有明显提升，一直没有形成一个强有力的支柱型产品和品牌价值，这也使得华晨汽车自主品牌产品的竞争力日益薄 弱。
   * 　　对此，华晨汽车也开始对产品战略进行调整，以挽救日渐下滑的趋势。日前，华晨中华公布规划，将主打V系列SUV和H系列轿车，其他产品逐渐退
   * 出。在自主品牌轿车销量不断下滑、SUV销量份额加大的背景下，这一战略调整被业内人士视为 “断臂自救”的措施。但同时，这一改变是否能够阻止华晨自主整车业务的颓势，业内还没有十分明确的信心。
   * 　　正如专家所说，华晨未来的发展必须要有一个主要目标，经营好主次业务，必须要有自己过硬的自主产品和品牌价值，在合资业务不断推进的过程中逐步找准自己的节奏。
   */

  private int newsId;
  private String source;
  private int commentsCount;
  private String link;
  private String title;
  private String author;
  private String createTime;
  private int status;
  private String content;
  private List<String> coverArr;
  private String praiseCount;

  public int getNewsId() {
    return newsId;
  }

  public void setNewsId(int newsId) {
    this.newsId = newsId;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public int getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(int commentsCount) {
    this.commentsCount = commentsCount;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getCoverArr() {
    return coverArr;
  }

  public void setCoverArr(List<String> coverArr) {
    this.coverArr = coverArr;
  }
}
