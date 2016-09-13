package com.hengrtech.carheadline.ui.profile;

import android.os.Bundle;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

/**
 * Created by jiao on 2016/8/29.
 */
public class SettingActivity extends BasicTitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
