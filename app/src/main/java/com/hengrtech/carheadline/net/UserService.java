/*
 * 文件名: UserService
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net;

import com.hengrtech.carheadline.net.model.ResponseModel;
import com.hengrtech.carheadline.net.params.UpdateUserInfo;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 用户信息<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public interface UserService {

  @PUT("member/{userId}/{token}")
  Observable<Response<ResponseModel<UpdateUserInfo>>> updateUser(@Body UpdateUserInfo info, @Path("userId") int userId,@Path("token") String token);

}
