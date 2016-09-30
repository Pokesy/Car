package com.hengrtech.carheadline.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.adapter.RecyclerAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiao on 2016/8/1.
 */
public class MediaFragment extends BasicTitleBarFragment
        implements OnItemClickListener, RefreshLayout.OnRefreshListener,
        OnLoadListener {
    public static final String TYPE = "type";
    @Bind(R.id.jrecyclerview)
    JRecyclerView mRecyclerView;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    @Inject
    AppService mInfo;
    private ZixunAdapter adapter;
    private SimpleLoadFooterAdapter adapter1;
    private List<InfoModel> allList = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreateViewCompleted(View view) {
        ButterKnife.bind(this, view);
        inject();
        init();
        initdata();
    }

    public void init() {

        //设置layoutmanager
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        adapter = new ZixunAdapter(getActivity());
        adapter1 = new SimpleLoadFooterAdapter(getActivity());
        mRecyclerView.setAdapter(adapter, adapter1);
        //设置适配器
        //开启滑动到底部加载更多功能
        mRecyclerView.setLoadMore(true);
        //开启滑动删除(默认状态，可以手动设置)
        //mRecyclerView.setSwipeStart(true, this);
        //开启长点击拖动换位(默认状态，可以手动设置)
        //mRecyclerView.setMoveUpDown(true, this);
        //设置事件
        mRecyclerView.setOnLoadListener(this);
        refreshlayout.setOnRefreshListener(this);
        mRecyclerView.setOnItemClickListener(this);
        //mRecyclerView.setOnItemLongClickListener(this);
        //主动发起下拉刷新
        refreshlayout.startRefreshing();
    }

    public void initdata() {
        manageRpcCall(mInfo.getInfoList("1", page + "", "10"),
                new UiRpcSubscriber<List<InfoModel>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<InfoModel> infoModels) {
                        refreshlayout.refreshingComplete();
                        mRecyclerView.setLoadCompleteState();
                        if (page == 1) {
                            allList.clear();
                            allList.addAll(infoModels);
                            adapter.setDatas(allList);
                            adapter.notifyDataSetChanged();
                        } else {
                            allList.addAll(infoModels);
                            adapter.setDatas(allList);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onEnd() {
                        refreshlayout.refreshingComplete();
                        mRecyclerView.setLoadCompleteState();
                    }

                    @Override
                    public void onApiError(RpcApiError apiError) {
                        super.onApiError(apiError);
                        refreshlayout.refreshingComplete();
                        mRecyclerView.setLoadCompleteState();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_work;
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getActivity().getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    public static MediaFragment newInstance(String param1) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * item点击事件
     */
    @Override
    public void onItemClick(RecyclerHolder holder, View view, int position) {
        InfoModel bean = allList.get(position);
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("newId", bean.getNewsId());
        bundle.putString("content", bean.getContent());
        bundle.putString("title", bean.getTitle());
        bundle.putInt("comment_count", bean.getCommentsCount());
        bundle.putInt("view_count", bean.getViewCount());
        bundle.putString("time", DateHelper.getInstance().getRencentTime(bean.getPublishTime()));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 加载更多的回调
     */
    @Override
    public void loadMore() {
        page++;
        initdata();
    }

    /**
     * 下拉刷新的回调
     */
    @Override
    public void onRefresh() {
        page = 1;
        initdata();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public class ZixunAdapter extends RecyclerAdapter<InfoModel> {
        Context context;
        private List<InfoModel> data;

        /**
         * 主构造
         *
         * @param activity Activity对象
         */
        public ZixunAdapter(Activity activity) {
            super(activity);
        }

        @Override
        public int getItemViewType(int position) {

            int imgsize = getRealDatas().get(position).getCoverArr().size();
            if (imgsize == 1) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return inflater.inflate(R.layout.home_new_list_item, parent, false);
            } else {
                return inflater.inflate(R.layout.home_new_list_item_three, parent, false);
            }
        }

        @Override
        public void convert(RecyclerHolder holder, InfoModel bean, int position) {
            holder.setText(R.id.news_title, bean.getTitle());
            holder.setText(R.id.time, DateHelper.getInstance().getRencentTime(bean.getPublishTime()));
            holder.setText(R.id.tv_from, bean.getSource());
            holder.setText(R.id.view_count, String.valueOf(bean.getViewCount()));
            holder.setText(R.id.comment_count, String.valueOf(bean.getCommentsCount()));
            if (bean.getCoverArr() != null) {
                int imagesize = bean.getCoverArr().size();
                if (imagesize == 1) {
                    ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getCoverArr().get(0))
                            .into(holder.getImageView(R.id.iv_1));
                } else {
                    if (imagesize == 3) {
                        holder.getView(R.id.images).setVisibility(View.VISIBLE);
                        holder.getImageView(R.id.iv_1).setVisibility(imagesize > 0 ? View.VISIBLE : View.GONE);
                        holder.getImageView(R.id.iv_2).setVisibility(imagesize > 1 ? View.VISIBLE : View.GONE);
                        holder.getImageView(R.id.iv_3).setVisibility(imagesize > 2 ? View.VISIBLE : View.GONE);
                        int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
                        LinearLayout.LayoutParams params =
                                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                        holder.getView(R.id.images).setLayoutParams(params);

                        for (int i = 0; i < imagesize; i++) {
                            String url = bean.getCoverArr().get(i);
                            ImageView imageView = null;
                            if (i == 0) {
                                imageView = holder.getImageView(R.id.iv_1);
                            } else if (i == 1) {
                                imageView = holder.getImageView(R.id.iv_2);
                            } else if (i == 2) {
                                imageView = holder.getImageView(R.id.iv_3);
                            }

                            if (imageView != null) {
                                try {
                                    ImageLoader.loadOptimizedHttpImage(getActivity(), url)
                                            .placeholder(R.mipmap.ic_launcher)
                                            .error(R.mipmap.ic_launcher)
                                            .into(imageView);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //final int in = i;
                                //imageView.setOnClickListener(new View.OnClickListener() {
                                //
                                //  @Override public void onClick(View v) {
                                //    Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                                //    Bundle bundle = new Bundle();
                                //    bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.imgList);
                                //    bundle.putInt("image_index", in);
                                //    intent.putExtras(bundle);
                                //    startActivity(intent);
                                //  }
                                //});
                            }
                        }
                    } else {
                        holder.getView(R.id.images).setVisibility(View.GONE);
                    }
                }
            } else {
                holder.getView(R.id.images).setVisibility(View.GONE);
            }
        }
    }
}
