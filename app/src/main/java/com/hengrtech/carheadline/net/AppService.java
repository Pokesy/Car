/*
 * 文件名: AppService
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

import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.net.model.ResponseModel;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public interface AppService {

  @Multipart
  @POST("upload.do")
  Observable<Response<ResponseModel<String>>> upload(@Part MultipartBody.Part file);
  @GET("newslist/{newslist}/{pageNum}/{pageSize}")
  Observable<Response<ResponseModel<List<InfoModel>>>> getInfoList(@Path("newslist") String newslist,@Path("pageNum") String pageNum,@Path("pageSize") String pageSize);
}
