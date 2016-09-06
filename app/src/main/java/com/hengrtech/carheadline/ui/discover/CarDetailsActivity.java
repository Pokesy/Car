package com.hengrtech.carheadline.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.jtech.view.JRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CarDetailsActivity extends BasicTitleBarActivity implements View.OnClickListener{
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.dot_container)
    LinearLayout dotContainer;
    @Bind(R.id.head_layout)
    LinearLayout headLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.car_news_rv)
    JRecyclerView carNewsRv;
    @Bind(R.id.nsv)
    NestedScrollView nsv;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.ask_floor_price_tv)
    TextView askFloorPriceTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        toolbar.setTitle(" ");
        toolbar.setNavigationIcon(R.mipmap.back_normal);
        setSupportActionBar(toolbar);
        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 3 * 2) {
                    collapsingToolbarLayout.setTitle("思域");
                } else {
                    collapsingToolbarLayout.setTitle("");
                }

            }
        });
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("数说新思域：外观受青睐，配置满意度高，真的不错哦" + i);
        }
        carNewsRv.setLayoutManager(new LinearLayoutManager(this));
        carNewsRv.setAdapter(new NewsRvAdapter(this, list));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        askFloorPriceTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ask_floor_price_tv:
                startActivity(new Intent(this,AskFloorPriceActivity.class));
                break;
        }
    }


    public class NewsRvAdapter extends RBaseAdapter<String> {
        private List<String> datas;

        public NewsRvAdapter(Context context, List<String> datas) {
            super(context, datas);
            this.datas = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_car_more_news;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, String string) {
            holder.textView(R.id.item_car_news_item).setText(string);
        }
    }

}
