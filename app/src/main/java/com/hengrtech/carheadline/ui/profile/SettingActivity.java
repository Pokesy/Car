package com.hengrtech.carheadline.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiao on 2016/8/29.
 */
public class SettingActivity extends BasicTitleBarActivity {
    @Bind(R.id.check_update_layout)
    RelativeLayout checkUpdateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.check_update_layout
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_update_layout:
                startActivity(new Intent(this, CheckVersionUpdateActivity.class));
                break;
        }
    }

    @Override
    public boolean initializeTitleBar() {
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setMiddleTitle(R.string.fragment_profile_setting);
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.setting_profile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
