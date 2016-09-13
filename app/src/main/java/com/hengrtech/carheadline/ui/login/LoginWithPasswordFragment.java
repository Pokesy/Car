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
import android.widget.TextView;

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
import com.hengrtech.carheadline.utils.Utils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码登录界面<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginWithPasswordFragment extends BasicTitleBarFragment {
    private static final int LENGTH_PHONE_NUMBER = 11;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Inject
    AuthService mAuthService;
    @Bind(R.id.phone_input)
    EditText phoneInput;
    @Bind(R.id.password_input)
    EditText passwordInput;
    @Bind(R.id.btn_login)
    TextView btnLogin;
    @Bind(R.id.forget_pwd_tv)
    TextView forgetPwdTv;
    @Bind(R.id.fast_register_tv)
    TextView fastRegisterTv;
    @Bind(R.id.go_see_tv)
    TextView goSeeTv;

    @Override
    protected void onCreateViewCompleted(View view) {
        ButterKnife.bind(this, view);
        inject();
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

    @OnClick({R.id.go_see_tv, R.id.btn_login, R.id.fast_register_tv, R
            .id.forget_pwd_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_see_tv:
                startActivity(new Intent(getActivity(), MainTabActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_login:
                performLogin();
                break;

            case R.id.fast_register_tv:
                // 跳转到注册界面
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.forget_pwd_tv:
                // TODO 跳转到忘记密码界面
                break;
        }
    }

    private void performLogin() {
        if (!Utils.verifyPhoneNumber(phoneInput.getText().toString().trim())) {
            showShortToast(R.string.login_input_phone_error);
            return;
        }
        if (!checkPasswordInput(passwordInput.getText())) {
            showShortToast(R.string.login_input_password_error);
            return;
        }
        showProgressDialog("", false);
        manageRpcCall(mAuthService.loginWithPassword(new LoginParams(phoneInput.getText().toString()
                , passwordInput.getText().toString())), new UiRpcSubscriber<UserInfo>(getActivity()) {
            @Override
            protected void onSuccess(UserInfo userInfo) {
                getComponent().loginSession().login(userInfo);
                Intent intent = new Intent(getActivity(), MainTabActivity.class);
                startActivity(intent);
                getActivity().finish();
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
