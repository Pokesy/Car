package com.hengrtech.carheadline.ui.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewCarOnlineActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.new_car_online_rv)
    JRecyclerView newCarOnlineRv;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    @Bind(R.id.activity_new_car_online)
    LinearLayout activityNewCarOnline;

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_car_online;
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
        newCarOnlineRv.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        newCarOnlineRv.setAdapter(new NewCarOnlineRvAdapter(this, list));
    }

    public class NewCarOnlineRvAdapter extends RBaseAdapter<String> {
        private List<String> datas;

        public NewCarOnlineRvAdapter(Context context, List<String> datas) {
            super(context, datas);
            this.datas = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_new_car_online_rv;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, String string) {
            holder.imageView(R.id.item_sort_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
