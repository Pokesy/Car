package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/30 11:01
 */

public class TucaoInfoModel {

    /**
     * complaintId : 58
     * memberId : 36
     * content : Yyyyyyyyyyy
     * images :
     * createTime : 2016-09-27 08:10:37
     * imgList : []
     * nickName : 孙悟空他哥
     * avatar : http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160909/d7ee8ef0.png
     * replyCount : 0
     * supportCount : 0
     * againstCount : 0
     */

    private int complaintId;
    private int memberId;
    private String content;
    private String images;
    private String createTime;
    private String nickName;
    private String avatar;
    private int replyCount;
    private int supportCount;
    private int againstCount;
    private List<?> imgList;

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int supportCount) {
        this.supportCount = supportCount;
    }

    public int getAgainstCount() {
        return againstCount;
    }

    public void setAgainstCount(int againstCount) {
        this.againstCount = againstCount;
    }

    public List<?> getImgList() {
        return imgList;
    }

    public void setImgList(List<?> imgList) {
        this.imgList = imgList;
    }
}
