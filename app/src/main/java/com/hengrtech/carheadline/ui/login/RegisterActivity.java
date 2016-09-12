package com.hengrtech.carheadline.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.Utils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.verify_btn)
    Button verifyBtn;
    @Bind(R.id.activity_register)
    LinearLayout activityRegister;
    @Inject
    AuthService mInfo;
    @Bind(R.id.phone_number_et)
    EditText phoneNumberEt;
    @Bind(R.id.get_verify_code_btn)
    TextView getVerifyCodeBtn;
    @Bind(R.id.verify_code_et)
    EditText verifyCodeEt;

    private TimeCount time;
    private String phoneNumber;

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
        time = new TimeCount(60000, 1000);//构造
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    @OnClick({R.id.get_verify_code_btn, R.id.verify_btn})
    public void onClick(View view) {
        phoneNumber = phoneNumberEt.getText().toString();
        switch (view.getId()) {
            case R.id.get_verify_code_btn:
                if (Utils.verifyPhoneNumber(phoneNumber)) {
                    getVerifyCodeService(phoneNumber);
                    time.start();
                } else {
                    Toast.makeText(this, "手机号码不正确或者为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.verify_btn:
                String code = verifyCodeEt.getText().toString();
                if (code != null && code.length() > 0) {
                    verifyCodeService(phoneNumber, code);
                } else {
                    Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getVerifyCodeService(String phoneNumber) {
        manageRpcCall(mInfo.getVerifyCode(phoneNumber), new UiRpcSubscriber<String>(this) {
            @Override
            protected void onSuccess(String s) {
                Toast.makeText(RegisterActivity.this, "发送成功", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            getVerifyCodeBtn.setText("重新验证");
            getVerifyCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            getVerifyCodeBtn.setClickable(false);
            getVerifyCodeBtn.setText(millisUntilFinished / 1000 + "秒");
        }
    }


    private void verifyCodeService(String phone, String code) {
        manageRpcCall(mInfo.checkVerifyCode(phone, code), new UiRpcSubscriber<String>(this) {
            @Override
            protected void onSuccess(String s) {
                Intent intent =new Intent(RegisterActivity.this, SetPasswordActivity.class);
                intent.putExtra("phone",phoneNumber);
                startActivity(intent);
            }

            @Override
            protected void onEnd() {

            }
        });
    }
}
