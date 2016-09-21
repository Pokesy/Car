package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
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

public class SelfMediaActivity extends BasicTitleBarActivity implements OnItemClickListener, OnItemLongClickListener, RefreshLayout.OnRefreshListener,
        OnLoadListener, OnItemViewSwipeListener, OnItemViewMoveListener {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jrecyclerview;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_self_media;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置layoutmanager
        jrecyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("3");
        list.add("4");
        list.add("3");
        list.add("4");
        list.add("3");
        list.add("4");
        list.add("3");
        list.add("4");
        jrecyclerview.setAdapter(new SelfMediaRvAdapter(this, list));
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.media);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

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

    }

    @Override
    public void onRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private class SelfMediaRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ONE_ITEM = 0;
        private static final int TYPE_TWO_ITEM = 1;
        private static final int TYPE_THREE_ITME = 2;
        private static final int TYPE_FOUR_ITME = 3;
        private Context context;


        public List<String> list;
        private OnItemClickListener mClickListener;
        private List<String> cbList = new ArrayList<>();
        private List<UserInfo.CarListBean> checkeddata = new ArrayList<>();// 选中的数据
        private List<Integer> checkPositionlist;

        public SelfMediaRvAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
            checkPositionlist = new ArrayList<>();
        }

        //创建新View，被LayoutManager所调用
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            //如果viewType是普通item返回普通的布局，否则是底部布局并返回
            if (viewType == TYPE_ONE_ITEM) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .item_self_media_rv_one, viewGroup, false);
                OneItemViewHolder vh = new OneItemViewHolder(view);
                return vh;
            } else if (viewType == TYPE_TWO_ITEM) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .item_self_media_rv_two, viewGroup, false);
                TwoItemViewHolder vh = new TwoItemViewHolder(view);
                return vh;
            } else if (viewType == TYPE_THREE_ITME) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .home_new_list_item, viewGroup, false);
                ThreeItemViewHolder vh = new ThreeItemViewHolder(view);
                return vh;
            } else {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .home_new_list_item_three, viewGroup, false);
                ThreeItemViewHolder vh = new ThreeItemViewHolder(view);
                return vh;
            }
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            if (viewHolder instanceof OneItemViewHolder) {

            } else if (viewHolder instanceof TwoItemViewHolder) {

            }
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public int getItemViewType(int position) {
            if (list.get(position).equals("1")) {
                return TYPE_ONE_ITEM;
            } else if (list.get(position).equals("2")) {
                return TYPE_TWO_ITEM;
            } else if (list.get(position).equals("3")) {
                return TYPE_THREE_ITME;
            } else {
                return TYPE_FOUR_ITME;
            }
        }

        public class OneItemViewHolder extends RecyclerView.ViewHolder {

            public OneItemViewHolder(View view) {
                super(view);
            }
        }

        public class TwoItemViewHolder extends RecyclerView.ViewHolder {

            public TwoItemViewHolder(View view) {
                super(view);
            }
        }

        public class ThreeItemViewHolder extends RecyclerView.ViewHolder {

            public ThreeItemViewHolder(View view) {
                super(view);
            }
        }


    }
}
