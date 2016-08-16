package com.hengrtech.carheadline.net;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.log.Logger;
import com.hengrtech.carheadline.net.constant.NetConstant;
import com.hengrtech.carheadline.net.model.ResponseModel;
import retrofit2.Response;
import rx.Subscriber;

/**
 * 简化的，供UI调用网络接口使用的RpcSubscriber，统一处理HttpError提示
 */
public abstract class UiRpcSubscriber<T> extends Subscriber<Response<ResponseModel<T>>> {
  private static final String TAG = "SERVER_ERROR";
  HttpErrorUiNotifier httpErrorUiNotifier;
  SessionNotifier sessionNotifier;
  private Context mContext;

  public UiRpcSubscriber(Context context) {
    mContext = context;
    httpErrorUiNotifier =
        ((CustomApp) context.getApplicationContext()).getGlobalComponent().httpErrorUiNotifier();
    //sessionNotifier = ((CustomApp) context.getApplicationContext()).getGlobalComponent()
    //    .sessionNotifier();
  }

  @Override
  public final void onCompleted() {
    onEnd();
  }

  @Override
  public final void onError(Throwable e) {
    onHttpError(new RpcHttpError(NetConstant.HttpCodeConstant.UNKNOWN_ERROR, ""));
    Logger.e(TAG, e, e.getMessage());
    onCompleted();
  }


  @Override
  public final void onNext(Response<ResponseModel<T>> responseModelResponse) {
    if (null == responseModelResponse || null == responseModelResponse.body()) {
      onHttpError(new RpcHttpError(NetConstant.HttpCodeConstant.UNKNOWN_ERROR, ""));
      return;
    }

    if (responseModelResponse.body().getResult() == NetConstant.HttpCodeConstant.SESSION_EXPIRED) {
      onSessionExpired();
      return;
    }

    if (responseModelResponse.code() == NetConstant.HttpCodeConstant.SUCCESS
        && responseModelResponse.body().getResult() == 0) {
      //// 存储token
      //if (!TextUtils.isEmpty(responseModelResponse.body().getToken())) {
      //  ((CustomApp) mContext.getApplicationContext()).getGlobalComponent().appPreferences().put
      //      (CustomAppPreferences.KEY_COOKIE_SESSION_ID,
      //          responseModelResponse.body().getToken());
      //}
      onSuccess(responseModelResponse.body().getData());
      return;
    }
    if (responseModelResponse.code() == NetConstant.HttpCodeConstant.HTTP_ERROR_NOT_FOUND) {
      onHttpError(new RpcHttpError(responseModelResponse.code(), responseModelResponse.message()));
      return;
    }
    onApiError(new RpcApiError(responseModelResponse.body().getResult(), responseModelResponse
        .body().getMessage()));
    onCompleted();
  }

  public void onApiError(RpcApiError apiError) {
    if (!TextUtils.isEmpty(apiError.getMessage())) {
      Toast.makeText(mContext, apiError.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  private void onSessionExpired() {
    sessionNotifier.notifySessionExpired();
    onCompleted();
  }

  public void onHttpError(RpcHttpError httpError) {
    httpErrorUiNotifier.notifyUi(httpError);
  }

  protected abstract void onSuccess(T t);

  protected abstract void onEnd();

}
