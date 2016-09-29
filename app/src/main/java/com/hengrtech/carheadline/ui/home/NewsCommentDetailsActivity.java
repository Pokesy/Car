package com.hengrtech.carheadline.ui.home;

import android.os.Bundle;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

public class NewsCommentDetailsActivity extends BasicTitleBarActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_comment_details;
    }

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
        setMiddleTitle("评论详情");
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        return true;
    }
}
