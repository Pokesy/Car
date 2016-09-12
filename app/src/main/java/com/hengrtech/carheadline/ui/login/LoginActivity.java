/*
 * 文件名: LogInWithPassword
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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

/**
 * 登录界面<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/27]
 */
public class LoginActivity extends BasicTitleBarActivity {
    public static final int LOGIN_WAY_PASSWORD = 0;
    public static final int LOGIN_WAY_VERIFY_CODE = 1;
    public static final int LOGIN_WAY_THIRD_WEB_CHAT = 2;
    public static final int LOGIN_WAY_THIRD_QQ = 3;
    public static final int LOGIN_WAY_THIRD_SINA = 4;

    private static final String BUNDLE_KEY_LOG_IN_TYPE = "login_type";
    private static final String FRAGMENT_TAG = "login_fragment_";
    //第一次用户打开软件是选择的是有车一族，还是打算买车，随便看看

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int loginType = getIntent().getIntExtra(BUNDLE_KEY_LOG_IN_TYPE, LOGIN_WAY_PASSWORD);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment;
        //switch (loginType) {
        //  case LOGIN_WAY_PASSWORD:
        //    fragment = new LoginWithPasswordFragment();
        //    break;
        //  case LOGIN_WAY_VERIFY_CODE:
        //    fragment = new LoginWithVerifyCodeFragment();
        //    break;
        //  default:
        //    fragment = new LoginWithPasswordFragment();
        //    break;
        //}
        fragment = new LoginWithPasswordFragment();
        transaction.add(R.id.fragment_container, fragment, FRAGMENT_TAG + loginType);
        transaction.commitAllowingStateLoss();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    public static Intent makeIntent(Context context, int logInType) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(BUNDLE_KEY_LOG_IN_TYPE, logInType);
        return intent;
    }

    @Override
    protected void onLogin() {
        super.onLogin();
        finish();
    }
}
