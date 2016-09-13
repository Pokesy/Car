/*
 * 文件名: UserInfo
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/29
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net.model;

import java.util.List;

/**
 * 用户信息<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/29]
 */
public class UserInfo {
    public static final String USER_TYPE_VISITOR = "2";
    public static final String USER_TYPE_THIRD = "1";
    public static final String USER_TYPE_PHONE = "0";

    public static final String CERTIFY_STATUS_CERTIFY_FAILED = "0";
    public static final String CERTIFY_STATUS_CERTIFY_PASS = "1";
    public static final String CERTIFY_STATUS_CERTIFYING = "2";

    /**
     * avart :
     * birthPlace :
     * birthday :
     * certified : 0
     * devId :
     * education :
     * gender :
     * idCardImg :
     * introduce :
     * inviteCode : ao3pka
     * loginNum : 0
     * loginTime : null
     * mobileNo : 13600000002
     * money : 0
     * msgCounts : 0
     * myCity :
     * occupation :
     * password : 111111
     * payPwd :
     * registerDate : null
     * result : 1
     * securityLevel : m
     * state : 1
     * thirdAccount :
     * todayBenefit : 0
     * type : 0
     * userId : 83
     * userLabel :
     * userName :
     * virtualMoney : 0
     */

    private String birthPlace;
    private String birthday;
    private String certified;
    private String devId;
    private String education;
    private String gender;
    private String idCardImg;
    private String introduce;
    private String inviteCode;
    private int loginNum;
    private Object loginTime;
    private String mobileNo;
    private int money;
    private int msgCounts;
    private String myCity;
    private String occupation;
    private String password;
    private String payPwd;
    private Object registerDate;
    private String result;
    private String securityLevel;
    private String state;
    private String thirdAccount;
    private int todayBenefit;
    private int type;
    private String userLabel;
    private int virtualMoney;

    /**
     * memberId : 1
     * realName : 张笑
     * password : e10adc3949ba59abbe56e057f20f883e
     * portrait :
     * nickName : Rock-2015
     * gender : 1
     * mobileNo : 18266455521
     * myCar : 奇瑞E3
     * careCarType : SUV,东风日产
     * signature : 得票得票
     * registerTime : 2016-07-19 09:10:43
     * token : aced34e3492a492597dad022960363d4
     * type : 0
     */

    private int memberId;
    private String realName;
    private String portrait;
    private String nickName;
    private String myCar;
    private String careCarType;
    private String signature;
    private String registerTime;
    private String token;
    private String ageStage;
    private int score;
    /**
     * picture : ss
     * serialId : 3998
     * serialName : sss
     */

    private List<CarListBean> carList;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAgeStage() {
        return ageStage;
    }

    public void setAgeStage(String ageStage) {
        this.ageStage = ageStage;
    }

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


    public String getMyCar() {
        return myCar;
    }

    public void setMyCar(String myCar) {
        this.myCar = myCar;
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


    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCertified() {
        return certified;
    }

    public void setCertified(String certified) {
        this.certified = certified;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public Object getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Object loginTime) {
        this.loginTime = loginTime;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMsgCounts() {
        return msgCounts;
    }

    public void setMsgCounts(int msgCounts) {
        this.msgCounts = msgCounts;
    }

    public String getMyCity() {
        return myCity;
    }

    public void setMyCity(String myCity) {
        this.myCity = myCity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public Object getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Object registerDate) {
        this.registerDate = registerDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public int getTodayBenefit() {
        return todayBenefit;
    }

    public void setTodayBenefit(int todayBenefit) {
        this.todayBenefit = todayBenefit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }


    public int getVirtualMoney() {
        return virtualMoney;
    }

    public void setVirtualMoney(int virtualMoney) {
        this.virtualMoney = virtualMoney;
    }

    public List<CarListBean> getCarList() {
        return carList;
    }

    public void setCarList(List<CarListBean> carList) {
        this.carList = carList;
    }

    public static class CarListBean {
        private String picture;
        private int serialId;
        private String serialName;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getSerialId() {
            return serialId;
        }

        public void setSerialId(int serialId) {
            this.serialId = serialId;
        }

        public String getSerialName() {
            return serialName;
        }

        public void setSerialName(String serialName) {
            this.serialName = serialName;
        }
    }
}
