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

import com.hengrtech.carheadline.net.model.AreaQuestionDetailCommentsModel;
import com.hengrtech.carheadline.net.model.BaseModel;
import com.hengrtech.carheadline.net.model.CarModel;
import com.hengrtech.carheadline.net.model.CarSerialModel;
import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.net.model.NewsDetailCommentsModel;
import com.hengrtech.carheadline.net.model.NewsDetailModel;
import com.hengrtech.carheadline.net.model.QuestionModel;
import com.hengrtech.carheadline.net.model.ResponseModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.MyLoveCarParams;
import com.hengrtech.carheadline.net.params.QuestionParams;
import com.hengrtech.carheadline.net.params.SendCommentCarParams;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public interface AppService {

  @Multipart @POST("file/upload") Observable<Response<ResponseModel<String>>> upload(
      @Part MultipartBody.Part file);

  @GET("newslist/{newslist}/{pageNum}/{pageSize}")
  Observable<Response<ResponseModel<List<InfoModel>>>> getInfoList(
      @Path("newslist") String newslist, @Path("pageNum") String pageNum,
      @Path("pageSize") String pageSize);

  @GET("news/{newsId}/{userId}") Observable<Response<ResponseModel<NewsDetailModel>>> getNewDetail(
      @Path("newsId") int newsId, @Path("userId") String userId);

  @GET("news/comments/{newsId}/{pageNum}/{pageSize}")
  Observable<Response<ResponseModel<List<NewsDetailCommentsModel>>>> getNewComments(
      @Path("newsId") int newsId, @Path("pageNum") String pageNum,
      @Path("pageSize") String pageSize);

  @GET("news/comments/{commentsId}")
  Observable<Response<ResponseModel<List<NewsDetailCommentsModel>>>> getNewDetailComments(
      @Path("commentsId") int newsId);

  @DELETE("news/comments/{commentsId}/{userId}/{token}")
  Observable<Response<ResponseModel<NewsDetailModel>>> getDelcomment(
      @Path("commentsId") int commentsId, @Path("userId") int userId, @Path("token") int token);

  @GET("car/masterlist") Observable<Response<ResponseModel<CarModel>>> getCarMasterList();

  @GET("car/seriallist/{masterId}")
  Observable<Response<ResponseModel<List<CarSerialModel>>>> getCarSerialList(
      @Path("masterId") int masterId);

  @GET("sms/send/{mobileNo}") Observable<Response<BaseModel>> getRegisterVerifyCode(
      @Path("mobileNo") String mobileNo);

  @GET("question/page/{page}")
  Observable<Response<ResponseModel<List<QuestionModel>>>> getAllQuestion(
      @Path("page") String page);

  @GET("question/unsolved/page/{page}")
  Observable<Response<ResponseModel<List<QuestionModel>>>> getUnQuestionList(
      @Path("page") String page);

  @GET("question/page/{page}/{token}")
  Observable<Response<ResponseModel<List<QuestionModel>>>> getMyQuestionfoList(
      @Path("page") String page, @Path("token") String token);

  @GET("sms/verify/{mobileNo}/{code}") Observable<Response<ResponseModel<String>>> verifyCode(
      @Path("mobileNo") String mobileNo, @Path("code") String code);

  @GET("question/{questionId}/answers")
  Observable<Response<ResponseModel<List<AreaQuestionDetailCommentsModel>>>> getAreaComments(
      @Path("questionId") int questionId);

  @POST("question/{token}") Observable<Response<ResponseModel<UserInfo>>> sendQuestion(
      @Path("token") String token, @Body QuestionParams params);

  @POST("member/mycar/{userId}/{token}") Observable<Response<ResponseModel<String>>> addMyLoveCar(
      @Path("userId") String userId, @Path("token") String token, @Body MyLoveCarParams params);

  @POST("question/answer/{token}") Observable<Response<ResponseModel<String>>> sendComments(
    @Path("token") String token, @Body SendCommentCarParams params);

  @DELETE("member/mycar/{userId}/{token}")
  Observable<Response<ResponseModel<String>>> deleteMyLoveCar(@Path("userId") String userId,
      @Path("token") String token, @Query("serialIds") String serialIds);
}
