package com.hengrtech.carheadline.ui.area;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.MyScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TucaoDetailsActivity extends BasicTitleBarActivity implements View.OnClickListener {
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.reply_editText)
    EditText replyEditText;
    @Bind(R.id.sendButn)
    TextView sendButn;
    @Bind(R.id.reply_box)
    LinearLayout replyBox;
    @Bind(R.id.edit_disable_text)
    TextView editDisableText;
    @Bind(R.id.collect_iv)
    ImageView collectIv;
    @Bind(R.id.collect_layout)
    LinearLayout collectLayout;
    @Bind(R.id.supportCount)
    TextView supportCount;
    @Bind(R.id.support_button)
    LinearLayout supportButton;
    @Bind(R.id.reply_bottom_layout)
    LinearLayout replyBottomLayout;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.head)
    ImageView head;
    @Bind(R.id.nick_name)
    TextView nickName;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.zan_iv)
    ImageView zanIv;
    @Bind(R.id.zan_tv)
    TextView zanTv;
    @Bind(R.id.zan_layout)
    LinearLayout zanLayout;
    @Bind(R.id.no_zan_iv)
    ImageView noZanIv;
    @Bind(R.id.no_zan_tv)
    TextView noZanTv;
    @Bind(R.id.no_zan_layout)
    LinearLayout noZanLayout;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.jrecyclerview)
    RecyclerView jrecyclerview;
    @Bind(R.id.news_scroll)
    MyScrollView newsScroll;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tucao_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public boolean initializeTitleBar() {
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setMiddleTitle(R.string.news_detail_title);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
