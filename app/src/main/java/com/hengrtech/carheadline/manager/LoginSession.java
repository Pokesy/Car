/*
 * 文件名: LogInSession
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/27
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.manager;

import android.text.TextUtils;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.UserService;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.UpdateUserInfo;
import com.hengrtech.carheadline.ui.login.LoginEvent;
import com.hengrtech.carheadline.ui.login.LogoutEvent;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.JsonConverter;
import com.hengrtech.carheadline.utils.preference.CustomAppPreferences;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Session<BR>
 * 处理登录登出相关
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginSession {

    private CustomApp mContext;
    private UserInfo mUserInfo;
    @Inject
    AuthService mAuthService;
    @Inject
    UserService mUserService;

    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    public LoginSession(CustomApp app) {
        mContext = app;
        DaggerServiceComponent.builder().globalModule(new GlobalModule(mContext)).serviceModule(new
                ServiceModule()).build().inject(this);
        switchUserInfo();
    }

    public int getMemberId() {
        return mUserInfo.getMemberId();
    }

    private void switchUserInfo() {
        String userJson = mContext.getGlobalComponent().appPreferences().getString
                (CustomAppPreferences.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userJson)) {
            mUserInfo = JsonConverter.jsonToObject(UserInfo.class, userJson);
            return;
        }
        mUserInfo = new UserInfo();
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void login(UserInfo userInfo) {
        saveUserInfo(userInfo);
        onLoginStatusChanged();
        mContext.getGlobalComponent().getGlobalBus().post(new LoginEvent());
    }

    public Subscription loadUserInfo() {
        Subscription getUserInfoSubscription = mAuthService.getUserInfo(mUserInfo.getMemberId
                (), mUserInfo.getToken()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new UiRpcSubscriber<UserInfo>
                        (mContext) {
                    @Override
                    protected void onSuccess(UserInfo info) {
                        saveUserInfo(info);
                        switchUserInfo();
                        mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent());
                    }

                    @Override
                    protected void onEnd() {

                    }
                });
        mSubscriptions.add(getUserInfoSubscription);
        return getUserInfoSubscription;
    }

    private Subscription updateUserInfo(String module, String key) {
        UpdateUserInfo uuInfo = new UpdateUserInfo();
        uuInfo.setUserId(mUserInfo.getMemberId());
        uuInfo.setContent(key);
        uuInfo.setModule(module);
        uuInfo.setToken(mUserInfo.getToken());
        Subscription updateUserInfoSubscription = mUserService.updateUser(uuInfo, mUserInfo.getMemberId(), mUserInfo.getToken()).subscribeOn
                (Schedulers
                        .newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new UiRpcSubscriber<UpdateUserInfo>(mContext) {

            @Override
            protected void onSuccess(UpdateUserInfo ui) {
                saveUserInfo(mUserInfo);
                switchUserInfo();
                mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent());
            }

            @Override
            public void onApiError(RpcApiError apiError) {
                super.onApiError(apiError);
                mContext.getGlobalComponent().getGlobalBus().post(new UserInfoChangedEvent(false));
            }

            @Override
            protected void onEnd() {

            }

        });
        mSubscriptions.add(updateUserInfoSubscription);
        return updateUserInfoSubscription;
    }

    private void saveUserInfo(UserInfo userInfo) {
        mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_INFO,
                JsonConverter
                        .objectToJson(userInfo));
        mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_ID, userInfo
                .getMemberId());
        mContext.getGlobalComponent().appPreferences().put(CustomAppPreferences.KEY_USER_TYPE,
                userInfo.getType());
    }

    public void logout() {
        mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_INFO);
        mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_ID);
        mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_USER_TYPE);
        mContext.getGlobalComponent().appPreferences().remove(CustomAppPreferences.KEY_COOKIE_SESSION_ID);
        mContext.getGlobalComponent().getGlobalBus().post(new LogoutEvent());
        onLoginStatusChanged();
    }

    public void onLoginStatusChanged() {
        switchUserInfo();
    }

    public void onDestroy() {
        //mSubscriptions.unsubscribe();
    }

    public UserInfoChangeBuilder userInfoChangeBuilder() {
        return new UserInfoChangeBuilder();
    }

    public class UserInfoChangeBuilder {

        public Subscription update(String module, String key) {
            return updateUserInfo(module, key);
        }

        public UserInfoChangeBuilder setPortrait(String avart) {
            mUserInfo.setPortrait(avart);
            return this;
        }

        public UserInfoChangeBuilder setBirthPlace(String birthPlace) {
            mUserInfo.setBirthPlace(birthPlace);
            return this;
        }

        public UserInfoChangeBuilder setAgeStage(String ageStage) {
            mUserInfo.setAgeStage(ageStage);
            return this;
        }

        public UserInfoChangeBuilder setCertified(String certified) {
            mUserInfo.setCertified(certified);
            return this;
        }

        public UserInfoChangeBuilder setDevId(String devId) {
            mUserInfo.setDevId(devId);
            return this;
        }

        public UserInfoChangeBuilder setEducation(String education) {
            mUserInfo.setEducation(education);
            return this;
        }

        public UserInfoChangeBuilder setGender(String gender) {
            mUserInfo.setGender(gender);
            return this;
        }

        public UserInfoChangeBuilder setIdCardImg(String idCardImg) {
            mUserInfo.setIdCardImg(idCardImg);
            return this;
        }

        public UserInfoChangeBuilder setIntroduce(String introduce) {
            mUserInfo.setSignature(introduce);
            return this;
        }

        public UserInfoChangeBuilder setInviteCode(String inviteCode) {
            mUserInfo.setInviteCode(inviteCode);
            return this;
        }

        public UserInfoChangeBuilder setLoginNum(int loginNum) {
            mUserInfo.setLoginNum(loginNum);
            return this;
        }

        public UserInfoChangeBuilder setLoginTime(Object loginTime) {
            mUserInfo.setLoginTime(loginTime);
            return this;
        }

        public UserInfoChangeBuilder setMobileNo(String mobileNo) {
            mUserInfo.setMobileNo(mobileNo);
            return this;
        }

        public UserInfoChangeBuilder setMoney(int money) {
            mUserInfo.setMoney(money);
            return this;
        }

        public UserInfoChangeBuilder setMsgCounts(int msgCounts) {
            mUserInfo.setMsgCounts(msgCounts);
            return this;
        }

        public UserInfoChangeBuilder setMyCity(String myCity) {
            mUserInfo.setMyCity(myCity);
            return this;
        }

        public UserInfoChangeBuilder setOccupation(String occupation) {
            mUserInfo.setOccupation(occupation);
            return this;
        }

        public UserInfoChangeBuilder setPassword(String password) {
            mUserInfo.setPassword(password);
            return this;
        }

        public UserInfoChangeBuilder setPayPwd(String payPwd) {
            mUserInfo.setPayPwd(payPwd);
            return this;
        }

        public UserInfoChangeBuilder setRegisterDate(Object registerDate) {
            mUserInfo.setRegisterDate(registerDate);
            return this;
        }

        public UserInfoChangeBuilder setResult(String result) {
            mUserInfo.setResult(result);
            return this;
        }

        public UserInfoChangeBuilder setSecurityLevel(String securityLevel) {
            mUserInfo.setSecurityLevel(securityLevel);
            return this;
        }

        public UserInfoChangeBuilder setState(String state) {
            mUserInfo.setState(state);
            return this;
        }

        public UserInfoChangeBuilder setThirdAccount(String thirdAccount) {
            mUserInfo.setThirdAccount(thirdAccount);
            return this;
        }

        public UserInfoChangeBuilder setTodayBenefit(int todayBenefit) {
            mUserInfo.setTodayBenefit(todayBenefit);
            return this;
        }

        public UserInfoChangeBuilder setType(int type) {
            mUserInfo.setType(type);
            return this;
        }

        public UserInfoChangeBuilder setMemberId(int memberId) {
            mUserInfo.setMemberId(memberId);
            return this;
        }

        public UserInfoChangeBuilder setUserLabel(String userLabel) {
            mUserInfo.setUserLabel(userLabel);
            return this;
        }

        public UserInfoChangeBuilder setRealName(String realName) {
            mUserInfo.setRealName(realName);
            return this;
        }


        public UserInfoChangeBuilder setNickName(String nickName) {
            mUserInfo.setNickName(nickName);
            return this;
        }

        public UserInfoChangeBuilder setVirtualMoney(int virtualMoney) {
            mUserInfo.setVirtualMoney(virtualMoney);
            return this;
        }
    }
}
