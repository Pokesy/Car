/*
 * 文件名: LoginWithPasswordFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/27
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.LoginWithVerifyCodeParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import javax.inject.Inject;

/**
 * 验证码登录界面<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginWithVerifyCodeFragment extends BasicTitleBarFragment {
  private static final long COUNT_DOWN_INTERVAL = 60 * 1000L;

  @Bind(R.id.btn_close)
  ImageView mBtnClose;
  @Bind(R.id.phone_input)
  EditText mPhoneInput;
  @Bind(R.id.verify_code_input)
  EditText mVerifyCodeInput;
  @Bind(R.id.count_down_default)
  TextView mCountDownDefault;
  @Bind(R.id.count_down_view) CountdownView mCountDownView;
  @Bind(R.id.btn_login)
  TextView mBtnLogin;
  @Bind(R.id.btn_login_with_password)
  TextView mBtnLoginWithPassword;
  @Bind(R.id.btn_register)
  TextView mBtnRegister;
  @Bind(R.id.btn_forget_password)
  TextView mBtnForgetPassword;
  @Inject AuthService mAuthService;

  @Override
  protected void onCreateViewCompleted(View view) {
    ButterKnife.bind(this, view);
    initView();
    inject();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((CustomApp) getActivity()
        .getApplication()))
        .serviceModule(new ServiceModule()).build().inject(this);
  }

  private void initView() {
    Link registerLink = new Link(getString(R.string.btn_register)).setTextColor(getResources()
        .getColor(R.color.font_color_yellow)).setUnderlined(false).setOnClickListener(new Link
        .OnClickListener() {
      @Override
      public void onClick(String clickedText) {
        // TODO 跳转到注册界面
      }
    });

    LinkBuilder registerText = LinkBuilder.on(mBtnRegister).setText(
        getString(R.string.btn_register_prefix) + getString(R.string.btn_register)
    ).addLink(registerLink);
    registerText.build();
    stopCountDown();

    mCountDownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
      @Override
      public void onEnd(CountdownView cv) {
        stopCountDown();
      }
    });
  }

  private void startCountDown() {
    mCountDownDefault.setVisibility(View.INVISIBLE);
    mCountDownView.setVisibility(View.VISIBLE);
    mCountDownView.start(COUNT_DOWN_INTERVAL);
  }

  private void stopCountDown() {
    mCountDownDefault.setVisibility(View.VISIBLE);
    mCountDownView.setVisibility(View.GONE);
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_login_with_verify_code;
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @OnClick({R.id.btn_close, R.id.count_down_default, R.id.count_down_view, R.id.btn_login, R.id
      .btn_login_with_password, R.id.btn_register, R.id.btn_forget_password})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_close:
        getActivity().finish();
        break;
      case R.id.count_down_default:
        // TODO 开始倒计时
        if (!checkPhoneInput(mPhoneInput.getText())) {
          showShortToast(R.string.register_phone_invalid);
          return;
        }
        // TODO 调用获取验证码接口
        manageRpcCall(mAuthService.getVerifyCode(mPhoneInput.getText()
            .toString()), new UiRpcSubscriber<String>(getActivity()) {


          @Override
          protected void onSuccess(String s) {
          }

          @Override
          protected void onEnd() {

          }
        });
        startCountDown();
        break;
      case R.id.count_down_view:
        // do nothing
        break;
      case R.id.btn_login:
        // 调用登录接口
        performLogin();
        break;
      case R.id.btn_login_with_password:
        startActivity(LoginActivity.makeIntent(getActivity(), LoginActivity.LOGIN_WAY_PASSWORD));
        getActivity().finish();
        break;
      case R.id.btn_register:
        // TODO 跳转到注册界面
        //startActivity(new Intent(getActivity(), RegisterActivity.class));
        break;
      case R.id.btn_forget_password:
        // TODO 跳转到忘记界面
        break;
    }
  }

  private void performLogin() {
    if (!checkPhoneInput(mPhoneInput.getText())) {
      return;
    }
    if (!checkVerifyCodeInput(mVerifyCodeInput.getText())) {
      return;
    }
    showProgressDialog("");
    // TODO 调用接口
    manageRpcCall(mAuthService.loginWithVerifyCode(new LoginWithVerifyCodeParams(mPhoneInput
        .getText().toString(), mVerifyCodeInput.getText().toString())), new
        UiRpcSubscriber<UserInfo>(getActivity()) {


          @Override
          protected void onSuccess(UserInfo info) {
            getComponent().loginSession().login(info);
            startActivity(new Intent(getActivity(), MainTabActivity.class));
          }

          @Override
          protected void onEnd() {
            closeProgressDialog();
          }

          @Override
          public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
          }
        });
  }

  private boolean checkPhoneInput(Editable charSequence) {
    if (isNull(charSequence)) {
      return false;
    }
    if (charSequence.length() != getResources().getInteger(R.integer.max_phone_length)) {
      return false;
    }
    return true;
  }

  private boolean checkVerifyCodeInput(Editable charSequence) {
    if (isNull(charSequence)) {
      return false;
    }
    if (charSequence.length() != getResources().getInteger(R.integer.max_verify_code_length)) {
      return false;
    }
    return true;
  }

  private boolean isNull(Editable charSequence) {
    return TextUtils.isEmpty(charSequence);
  }
}
