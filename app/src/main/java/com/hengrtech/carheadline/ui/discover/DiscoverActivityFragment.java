package com.hengrtech.carheadline.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.listener.OnItemViewMoveListener;
import com.jtech.listener.OnItemViewSwipeListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DiscoverActivityFragment extends BasicTitleBarFragment implements OnItemClickListener, OnItemLongClickListener, RefreshLayout.OnRefreshListener, OnLoadListener, OnItemViewSwipeListener, OnItemViewMoveListener {
    private Context mContext;
    private DiscoverActivityRvAdapter adapter;
    @Bind(R.id.activity_discover_rv)
    JRecyclerView activityDiscoverRv;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    List<String> datas = new ArrayList<>();

    @Override
    public void onItemClick(RecyclerHolder holder, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
        return false;
    }

    @Override
    public boolean onItemViewMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onItemViewSwipe(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void loadMore() {
        loadData();
    }


    @Override
    protected void onCreateViewCompleted(View view) {
        mContext = getActivity();
        ButterKnife.bind(this, view);
        activityDiscoverRv.setLayoutManager(new LinearLayoutManager(mContext));
        activityDiscoverRv.setOnLoadListener(this);
//        activityDiscoverRv.setOnItemClickListener(this);
        activityDiscoverRv.setLoadMore(true);
        refreshlayout.setOnRefreshListener(this);
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discover_activity;
    }

    @Override
    public void onRefresh() {
        loadData();
    }


    public class DiscoverActivityRvAdapter extends RBaseAdapter<String> {
        private List<String> datas;

        public DiscoverActivityRvAdapter(Context context, List<String> datas) {
            super(context, datas);
            this.datas = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.discover_activity_rv_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, String string) {
            holder.textView(R.id.discover_rv_item_title).setText(string);
            holder.imageView(R.id.discover_rv_item_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext,ActivityDetailsActivity.class));
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 模拟数据请求
     */
    private void loadData() {
        new AsyncTask<Boolean, String, List<String>>() {
            @Override
            protected List<String> doInBackground(Boolean... params) {
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    datas.add("车头条" + i);
                }
                return datas;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                //设置数据
                if (adapter == null) {
                    adapter = new DiscoverActivityRvAdapter(mContext, strings);
                    activityDiscoverRv.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                //标记为请求完成
                refreshlayout.refreshingComplete();
                activityDiscoverRv.setLoadCompleteState();
            }
        }.execute();
    }
}
