package com.hengrtech.carheadline.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.discover.adapter.LikeGridViewAdapter;
import com.hengrtech.carheadline.ui.discover.view.ScrollGridView;
import com.hengrtech.carheadline.ui.home.MyScrollView;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.jtech.view.JRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityDetailsActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nsv)
    MyScrollView nsv;
    @Bind(R.id.car_news_rv)
    JRecyclerView carNewsRv;
    @Bind(R.id.like_gv)
    ScrollGridView likeGv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_activity_details;
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

        carNewsRv.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        carNewsRv.setAdapter(new DiscoverActivityRvAdapter(this, list));
        likeGv.setAdapter(new LikeGridViewAdapter(this, null));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            nsv.scrollTo(0, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_discover, menu);
        return true;
    }

    public class DiscoverActivityRvAdapter extends RBaseAdapter<String> {
        private List<String> datas;

        public DiscoverActivityRvAdapter(Context context, List<String> datas) {
            super(context, datas);
            this.datas = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.activity_detail_rv_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, String string) {
            holder.imageView(R.id.discover_rv_item_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, ActivityDetailsActivity.class));
                }
            });
        }
    }
}
