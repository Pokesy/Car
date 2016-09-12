package com.hengrtech.carheadline.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.RegisterParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;
import com.hengrtech.carheadline.utils.MD5Encoder;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BasicTitleBarActivity {
    @Inject
    AuthService mInfo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.confirm_pwd_et)
    EditText confirmPwdEt;
    @Bind(R.id.sure_btn)
    Button sureBtn;
    @Bind(R.id.activity_register)
    LinearLayout activityRegister;
    private String userId;
    private String mobleNo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        inject();
        mobleNo = this.getIntent().getStringExtra("phone");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userId = ((CustomApp) getApplication()).getGlobalComponent().appPreferences().getUserId();
        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    @OnClick(R.id.sure_btn)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sure_btn:
                String pwd = pwdEt.getText().toString().trim();
                if(pwd!=null&&pwd.length()>=6){
                    if(pwd.equals(confirmPwdEt.getText().toString().trim())){
                        registerService(mobleNo,pwd);
                    }else{
                        Toast.makeText(this, "密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "密码不能为空或者密码不能少于6位数", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void registerService(String mobileNo,String pwd){
        manageRpcCall(mInfo.register(new RegisterParams(userId, mobileNo, MD5Encoder.encode(pwd))), new UiRpcSubscriber<UserInfo>(this) {
            @Override
            protected void onSuccess(UserInfo userInfo) {
                getComponent().loginSession().login(userInfo);
                Toast.makeText(SetPasswordActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(SetPasswordActivity.this, MainTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            protected void onEnd() {

            }
        });
    }
}
