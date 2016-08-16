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
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.LoginParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import javax.inject.Inject;

/**
 * 密码登录界面<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginWithPasswordFragment extends BasicTitleBarFragment {
  private static final int LENGTH_PHONE_NUMBER = 11;
  private static final int MIN_PASSWORD_LENGTH = 6;

  @Bind(R.id.btn_close)
  ImageView mBtnClose;
  @Bind(R.id.phone_input)
  EditText mPhoneInput;
  @Bind(R.id.password_input)
  EditText mPasswordInput;
  @Bind(R.id.btn_login)
  TextView mBtnLogin;
  @Bind(R.id.btn_login_with_verify_code)
  TextView mBtnLoginWithVerifyCode;
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

  private void initView() {
    Link registerLink = new Link(getString(R.string.btn_register)).setTextColor(getResources()
        .getColor(R.color.font_color_yellow)).setUnderlined(false).setOnClickListener(new Link
        .OnClickListener() {
      @Override
      public void onClick(String clickedText) {
        //startActivity(new Intent(getActivity(), RegisterActivity.class));
      }
    });
    LinkBuilder registerText = LinkBuilder.on(mBtnRegister).setText(
        getString(R.string.btn_register_prefix) + getString(R.string.btn_register)
    ).addLink(registerLink);
    registerText.build();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((CustomApp) getActivity()
        .getApplication())).serviceModule(new ServiceModule()).build().inject(this);
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_login_with_password;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @OnClick({R.id.btn_close, R.id.btn_login, R.id.btn_login_with_verify_code, R.id.btn_register, R
      .id.btn_forget_password})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_close:
        startActivity(new Intent(getActivity(), MainTabActivity.class));
        getActivity().finish();
        break;
      case R.id.btn_login:
        performLogin();
        break;
      case R.id.btn_login_with_verify_code:
        startActivity(LoginActivity.makeIntent(getActivity(), LoginActivity.LOGIN_WAY_VERIFY_CODE));
        getActivity().finish();
        break;
      case R.id.btn_register:
        // 跳转到注册界面
        //startActivity(new Intent(getActivity(), RegisterActivity.class));
        break;
      case R.id.btn_forget_password:
        // TODO 跳转到忘记密码界面
        break;
    }
  }

  private void performLogin() {
    if (!checkPhoneInput(mPhoneInput.getText())) {
      showShortToast(R.string.login_input_phone_error);
      return;
    }
    if (!checkPasswordInput(mPasswordInput.getText())) {
      showShortToast(R.string.login_input_password_error);
      return;
    }
    showProgressDialog("", false);
    manageRpcCall(mAuthService.loginWithPassword(new LoginParams(mPhoneInput.getText().toString()
        , mPasswordInput.getText().toString())), new UiRpcSubscriber<UserInfo>(getActivity()) {


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
        if (!TextUtils.isEmpty(apiError.getMessage())) {
          showShortToast(apiError.getMessage());
        } else {
          showShortToast(R.string.login_error);
        }
      }
    });
  }

  private boolean checkPhoneInput(Editable charSequence) {
    if (isNull(charSequence)) {
      return false;
    }
    if (charSequence.length() != LENGTH_PHONE_NUMBER) {
      return false;
    }
    return true;
  }

  private boolean checkPasswordInput(Editable charSequence) {
    if (isNull(charSequence)) {
      return false;
    }
    if (charSequence.length() < MIN_PASSWORD_LENGTH) {
      return false;
    }
    return true;
  }

  private boolean isNull(Editable charSequence) {
    return TextUtils.isEmpty(charSequence);
  }
}
