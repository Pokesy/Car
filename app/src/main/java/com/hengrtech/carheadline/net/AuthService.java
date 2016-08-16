/*
 * 文件名: AuthService
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/29
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net;

import com.hengrtech.carheadline.net.model.ResponseModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.CheckVerifyCodeParams;
import com.hengrtech.carheadline.net.params.GetUserInfoParams;
import com.hengrtech.carheadline.net.params.GetVerifyCodeParams;
import com.hengrtech.carheadline.net.params.LoginParams;
import com.hengrtech.carheadline.net.params.LoginWithVerifyCodeParams;
import com.hengrtech.carheadline.net.params.PayPswParams;
import com.hengrtech.carheadline.net.params.PayRetPswParams;
import com.hengrtech.carheadline.net.params.RegisterParams;
import com.hengrtech.carheadline.net.params.ThirdLoginParams;
import com.hengrtech.carheadline.net.params.VisitorLoginParams;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 鉴权接口<BR>
 * 包括 登录，验证码获取和验证，注册，获取用户信息
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/29]
 */
public interface AuthService {
  @POST("smsSend.do")
  Observable<Response<ResponseModel<String>>> getVerifyCode(@Body GetVerifyCodeParams params);

  @POST("smsVerify.do")
  Observable<Response<ResponseModel<Void>>> checkVerifyCode(@Body CheckVerifyCodeParams params);

  @POST("reg.do")
  Observable<Response<ResponseModel<UserInfo>>> register(@Body RegisterParams params);

  @POST("updatepaypwd.do")
  Observable<Response<ResponseModel<UserInfo>>> payResetPsw(@Body PayRetPswParams params);

  @POST("setnewpaypwd.do")
  Observable<Response<ResponseModel<UserInfo>>> payPsw(@Body PayPswParams params);

  @POST("applogin.do")
  Observable<Response<ResponseModel<UserInfo>>> loginWithPassword(@Body LoginParams params);

  @POST("smslogin.do")
  Observable<Response<ResponseModel<UserInfo>>> loginWithVerifyCode(@Body
                                                                    LoginWithVerifyCodeParams
                                                                        params);

  @POST("getUserInfoById.do")
  Observable<Response<ResponseModel<UserInfo>>> getUserInfo(@Body GetUserInfoParams params);

  @POST("thirdlogin.do")
  Observable<Response<ResponseModel<UserInfo>>> thirdLogin(@Body
                                                           ThirdLoginParams params);

  @POST("visitorlogin.do")
  Observable<Response<ResponseModel<UserInfo>>> visitorLogin(@Body VisitorLoginParams params);
}
