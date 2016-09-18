package com.hengrtech.carheadline.ui.profile;

import android.os.Bundle;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

public class MyCollectActivity extends BasicTitleBarActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.fragment_profile_collect);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }
}
