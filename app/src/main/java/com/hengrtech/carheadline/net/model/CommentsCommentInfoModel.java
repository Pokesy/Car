package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/29 14:45
 */

public class CommentsCommentInfoModel {
    /**
     * memberId : 1
     * realName : L
     * portrait : http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160912/c81c4221.png
     * nickName : 爱笑的眼
     * gender : 1
     * ageStage : 90后
     * mobileNo : 18253553110
     * careCarType : SUV,东风日产
     * signature : 爱在一起
     * registerTime : 2016-07-19 09:10:43
     * token : dac9cf465f194cafa6b9c95fa30b478c
     * type : 0
     * score : 722
     */

    private MemberBean member;
    /**
     * member : {"memberId":1,"realName":"L","portrait":"http://headline.img-cn-beijing.aliyuncs.com/upload/default/20160912/c81c4221.png","nickName":"爱笑的眼","gender":1,"ageStage":"90后","mobileNo":"18253553110","careCarType":"SUV,东风日产","signature":"爱在一起","registerTime":"2016-07-19 09:10:43","token":"dac9cf465f194cafa6b9c95fa30b478c","type":0,"score":722}
     * commentsId : 12
     * cmtId : 1
     * userId : 1
     * content : 12324123
     * commentsTime : 2016-07-19 16:05:15
     * praiseCount : 0
     * praiseUserList : []
     * havenPraise : false
     * commentsCount : 0
     */

    private int commentsId;
    private int cmtId;
    private int userId;
    private String content;
    private String commentsTime;
    private int praiseCount;
    private boolean havenPraise;
    private int commentsCount;
    private List<?> praiseUserList;

    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public int getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(int commentsId) {
        this.commentsId = commentsId;
    }

    public int getCmtId() {
        return cmtId;
    }

    public void setCmtId(int cmtId) {
        this.cmtId = cmtId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentsTime() {
        return commentsTime;
    }

    public void setCommentsTime(String commentsTime) {
        this.commentsTime = commentsTime;
    }

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

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<?> getPraiseUserList() {
        return praiseUserList;
    }

    public void setPraiseUserList(List<?> praiseUserList) {
        this.praiseUserList = praiseUserList;
    }

    public static class MemberBean {
        private int memberId;
        private String realName;
        private String portrait;
        private String nickName;
        private int gender;
        private String ageStage;
        private String mobileNo;
        private String careCarType;
        private String signature;
        private String registerTime;
        private String token;
        private int type;
        private int score;

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAgeStage() {
            return ageStage;
        }

        public void setAgeStage(String ageStage) {
            this.ageStage = ageStage;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getCareCarType() {
            return careCarType;
        }

        public void setCareCarType(String careCarType) {
            this.careCarType = careCarType;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
