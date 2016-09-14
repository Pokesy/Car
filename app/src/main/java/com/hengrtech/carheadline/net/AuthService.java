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
import com.hengrtech.carheadline.net.params.LoginParams;
import com.hengrtech.carheadline.net.params.PayPswParams;
import com.hengrtech.carheadline.net.params.PayRetPswParams;
import com.hengrtech.carheadline.net.params.RegisterParams;
import com.hengrtech.carheadline.net.params.ThirdLoginParams;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 鉴权接口<BR>
 * 包括 登录，验证码获取和验证，注册，获取用户信息
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/29]
 */
public interface AuthService {
    //@GET("sms/send") Observable<Response<ResponseModel<String>>> getVerifyCode(
    //    @Body GetVerifyCodeParams params);

    @GET("sms/send/{mobileNo}")
    Observable<Response<ResponseModel<String>>> getVerifyCode(
            @Path("mobileNo") String mobileNo);

    @GET("member/visitor")
    Observable<Response<ResponseModel<UserInfo>>> loginvictor();

    @GET("sms/verify/{mobileNo}/{code}")
    Observable<Response<ResponseModel<String>>> loginWithVerifyCode(@Path("mobileNo") String mobileNo,
                                                                    @Path("code") String code);

    @GET("sms/verify/{mobileNo}/{code}")
    Observable<Response<ResponseModel<String>>> checkVerifyCode(@Path("mobileNo") String mobileNo,
                                                                @Path("code") String code);
    //@GET("sms/verify") Observable<Response<ResponseModel<Void>>> checkVerifyCode(
    //    @Body CheckVerifyCodeParams params);

    @PUT("member/register")
    Observable<Response<ResponseModel<UserInfo>>> register(
            @Body RegisterParams params);

    @POST("updatepaypwd.do")
    Observable<Response<ResponseModel<UserInfo>>> payResetPsw(
            @Body PayRetPswParams params);

    @POST("setnewpaypwd.do")
    Observable<Response<ResponseModel<UserInfo>>> payPsw(
            @Body PayPswParams params);

    @PUT("member/login")
    Observable<Response<ResponseModel<UserInfo>>> loginWithPassword(
            @Body LoginParams params);


    @GET("member/{userId}/{token}")
    Observable<Response<ResponseModel<UserInfo>>> getUserInfo(@Path("userId") int userId, @Path("token") String token);

    @POST("thirdlogin.do")
    Observable<Response<ResponseModel<UserInfo>>> thirdLogin(
            @Body ThirdLoginParams params);

    @GET("member/visitor/{tag}")
    Observable<Response<ResponseModel<UserInfo>>> visitorLogin(@Path("tag") String tag);


}
