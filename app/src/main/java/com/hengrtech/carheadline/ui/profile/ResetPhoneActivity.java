package com.hengrtech.carheadline.ui.profile;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by jiao on 2016/5/27.
 */
public class ResetPhoneActivity extends BasicTitleBarActivity {



  @Bind(R.id.country_code) TextView countryCode;
  @Bind(R.id.btn_clear) ImageView btnClear;
  @Bind(R.id.phone_input) EditText phoneInput;
  @Bind(R.id.num1) TextView num1;
  @Bind(R.id.et_num_input) EditText etNumInput;
  @Bind(R.id.tv_recycle) TextView tvRecycle;
  @Bind(R.id.tv_activation) TextView tvActivation;
  private TimeCount time;
  @Inject AuthService mAuthService;
  private Subscription mSubscription;

  @Override public boolean initializeTitleBar() {
    setMiddleTitle(R.string.activity_settings_account);
    setLeftTitleButton(R.mipmap.icon_title_bar_back, new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    return true;
  }

  private void inject() {
    DaggerServiceComponent.builder()
        .serviceModule(new ServiceModule())
        .globalModule(new GlobalModule((CustomApp) getApplication()))
        .build()
        .inject(this);
  }

  @Override public int getLayoutId() {
    return R.layout.activity_reset_phone;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
    time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    inject();
  }

  @OnClick({ R.id.btn_clear, R.id.tv_recycle, R.id.tv_activation }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_clear:
        phoneInput.setText("");

        break;
      case R.id.tv_recycle:
        time.start();
        if (!checkInput(phoneInput.getText(),
            getResources().getInteger(R.integer.max_phone_length))) {
          showShortToast(R.string.register_phone_invalid);
          return;
        }
        // TODO 调用获取验证码接口
        manageRpcCall(
            mAuthService.getVerifyCode(phoneInput.getText().toString()),
            new UiRpcSubscriber<String>(this) {

              @Override protected void onSuccess(String s) {
              }

              @Override protected void onEnd() {

              }
            });
        break;
      case R.id.tv_activation:
//        // TODO 调用校验验证码接口，这个按钮是否应该叫下一步？？？？！！
//        manageRpcCall(mAuthService.checkVerifyCode(
//            new CheckVerifyCodeParams(phoneInput.getText().toString(),
//                etNumInput.getText().toString())), new UiRpcSubscriber<Void>(this) {
//          @Override protected void onSuccess(Void s) {
//            mSubscription = getComponent().loginSession().userInfoChangeBuilder().setMobileNo(phoneInput.getText().toString()).update();
//            finish();
//          }
//
//          @Override protected void onEnd() {
//
//          }
//
//          @Override public void onApiError(RpcApiError apiError) {
//            super.onApiError(apiError);
//          }
//        });
        break;
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (null!=mSubscription&&mSubscription.isUnsubscribed()){
      mSubscription.unsubscribe();
    }
  }

  private boolean checkInput(Editable editable, int lengthLimit) {
    return !TextUtils.isEmpty(editable) && editable.length() == lengthLimit;
  }

  /* 定义一个倒计时的内部类 */
  class TimeCount extends CountDownTimer {
    public TimeCount(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
    }

    @Override public void onFinish() {//计时完毕时触发
      tvRecycle.setText("重新验证");
      tvRecycle.setClickable(true);
    }

    @Override public void onTick(long millisUntilFinished) {//计时过程显示
      tvRecycle.setClickable(false);
      tvRecycle.setText(millisUntilFinished / 1000 + "秒");
    }
  }
}
