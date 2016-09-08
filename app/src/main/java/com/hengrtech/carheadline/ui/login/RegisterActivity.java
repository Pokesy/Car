package com.hengrtech.carheadline.ui.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.model.BaseModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Subscriber;

public class RegisterActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.verify_btn)
    Button verifyBtn;
    @Bind(R.id.activity_register)
    LinearLayout activityRegister;
    @Inject
    AppService mInfo;
    @Bind(R.id.phone_number_et)
    EditText phoneNumberEt;
    @Bind(R.id.get_verify_code_tv)
    TextView getVerifyCodeTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        inject();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    @OnClick(R.id.get_verify_code_tv)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_verify_code_tv:
                getVerifyCodeService(phoneNumberEt.getText().toString());
                break;
        }
    }

    private void getVerifyCodeService(String phoneNumber) {
        manageRpcCall(mInfo.getRegisterVerifyCode(phoneNumber), new Subscriber<Response<BaseModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Response<BaseModel> baseModelResponse) {
                Toast.makeText(RegisterActivity.this,baseModelResponse.body().getResult(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
