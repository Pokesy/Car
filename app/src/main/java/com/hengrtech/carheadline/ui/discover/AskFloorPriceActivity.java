package com.hengrtech.carheadline.ui.discover;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AskFloorPriceActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_floor_price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
