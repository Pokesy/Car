package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
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
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.DisplayUtil;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiao on 2016/7/19.
 */
public class InformationFragment extends BasicTitleBarFragment implements RefreshLayout.OnRefreshListener,
        OnLoadListener {
    public static final String TYPE = "type";
    @Inject
    AppService mInfo;
    @Bind(R.id.zx_listView)
    JRecyclerView zxListView;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    private int page = 1;
    private List<InfoModel> allInfoModels = new ArrayList<>();
    private ZixunAdapter adapter;
    private boolean isStopAd = false;
    private boolean isFirst = true;
    private List<String> banner = new ArrayList<>();

    @Override
    protected void onCreateViewCompleted(View view) {
        ButterKnife.bind(this, view);
        inject();
        banner.add("1");
        banner.add("2");
        initView();
        initdata();
        refreshlayout.startRefreshing();
    }

    private void initView() {
        zxListView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        zxListView.setLoadMore(true);
        zxListView.setOnLoadListener(this);
        refreshlayout.setOnRefreshListener(this);
    }


    public void initdata() {
        manageRpcCall(mInfo.getInfoList("1", page + "", "10"),
                new UiRpcSubscriber<List<InfoModel>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<InfoModel> infoModels) {
                        refreshlayout.refreshingComplete();
                        zxListView.setLoadCompleteState();
                        if (page == 1) {
                            allInfoModels.clear();
                            allInfoModels.addAll(infoModels);
                            allInfoModels.add(0, new InfoModel());
                            adapter = new ZixunAdapter(getActivity(), allInfoModels);
                            zxListView.setAdapter(adapter);
                        } else {
                            allInfoModels.addAll(infoModels);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onEnd() {
                    }

                    @Override
                    public void onApiError(RpcApiError apiError) {
                        super.onApiError(apiError);
                        refreshlayout.refreshingComplete();
                        zxListView.setLoadCompleteState();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getActivity().getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    public static InformationFragment newInstance(String param1) {
        InformationFragment fragment = new InformationFragment();
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

    @Override
    public void loadMore() {
        page++;
        initdata();
    }

    @Override
    public void onRefresh() {
        if (!isFirst) {
            isStopAd = true;
        }
        page = 1;
        initdata();
        isFirst = false;
    }


    public class ZixunAdapter extends RBaseAdapter<InfoModel> {
        private Thread adThread;
        private List<InfoModel> data;
        private int currentItem;

        public ZixunAdapter(Context context) {
            super(context);
        }

        public ZixunAdapter(Context context, List<InfoModel> datas) {
            super(context, datas);
            data = datas;
        }


        @Override
        protected int getItemLayoutId(int viewType) {
            if (viewType == 1) {
                return R.layout.home_new_list_item;
            } else if (viewType == 2) {
                return R.layout.home_new_list_item_three;
            } else {
                return R.layout.news_head_item;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 3;
            } else {
                int imgsize = data.get(position).getCoverArr().size();
                if (imgsize == 1) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final InfoModel bean) {
            //position代表的是头布局
            if (position != 0) {
                holder.v(R.id.ll_zx).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });
                holder.tV(R.id.news_title).setText(bean.getTitle());
                holder.tV(R.id.time).setText(DateHelper.getInstance().getRencentTime(bean.getPublishTime()));
                holder.tV(R.id.tv_from).setText(bean.getSource());
                holder.tV(R.id.view_count).setText(String.valueOf(bean.getViewCount()));
                holder.tV(R.id.comment_count).setText(String.valueOf(bean.getCommentsCount()));
                if (bean.getCoverArr() != null) {
                    int imagesize = bean.getCoverArr().size();
                    if (imagesize == 1) {
                        ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getCoverArr().get(0))
                                .into(holder.imgV(R.id.iv_1));
                    } else {
                        if (imagesize == 3) {
                            holder.v(R.id.images).setVisibility(View.VISIBLE);
                            holder.v(R.id.iv_1).setVisibility(imagesize > 0 ? View.VISIBLE : View.GONE);
                            holder.v(R.id.iv_2).setVisibility(imagesize > 1 ? View.VISIBLE : View.GONE);
                            holder.v(R.id.iv_3).setVisibility(imagesize > 2 ? View.VISIBLE : View.GONE);
                            int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
                            LinearLayout.LayoutParams params =
                                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                            holder.v(R.id.images).setLayoutParams(params);

                            for (int i = 0; i < imagesize; i++) {
                                String url = bean.getCoverArr().get(i);
                                ImageView imageView = null;
                                if (i == 0) {
                                    imageView = holder.imgV(R.id.iv_1);
                                } else if (i == 1) {
                                    imageView = holder.imgV(R.id.iv_2);
                                } else if (i == 2) {
                                    imageView = holder.imgV(R.id.iv_3);
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
                                }
                            }
                        } else {
                            holder.v(R.id.images).setVisibility(View.GONE);
                        }
                    }
                } else {
                    holder.v(R.id.images).setVisibility(View.GONE);
                }
            } else {
                holder.v(R.id.btn_red_bag).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), ZhuanTiActivity.class));
                    }
                });
                holder.v(R.id.today_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), TodayActivity.class));
                    }
                });

                holder.v(R.id.self_media_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), SelfMediaActivity.class));
                    }
                });
                if (isStopAd) {
                    holder.v(R.id.lunbo_layout).setVisibility(View.GONE);
                } else {
                    holder.v(R.id.lunbo_layout).setVisibility(View.VISIBLE);
                }
                final LinearLayout dotContainer = (LinearLayout) holder.view(R.id.dot_container);
                dotContainer.removeAllViews();
                //动态添加小圆点
                for (int i = 0; i < banner.size(); i++) {
                    View v = new View(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            DisplayUtil.dip2px(getContext(), 5),
                            DisplayUtil.dip2px(getContext(), 5));
                    lp.setMargins(DisplayUtil.dip2px(getContext(), 6), 0,
                            DisplayUtil.dip2px(getContext(), 6), 0);
                    v.setLayoutParams(lp);

                    if (i == 0) {
                        v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dot_focused));
                    } else {
                        v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dot_normal));

                    }
                    dotContainer.addView(v, i);
                }

                AdViewPagerAdapter adViewPagerAdapter = new AdViewPagerAdapter(getActivity(), banner);
                final ViewPager adViewpager = (ViewPager) holder.view(R.id.ad_viewpager);
                adViewpager.setAdapter(adViewPagerAdapter);
                if (adThread == null) {
                    adThread = new Thread() {
                        public void run() {
                            while (!isStopAd) {
                                try {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentItem = (currentItem + 1) % banner.size();
                                            //设置当前页面
                                            adViewpager.setCurrentItem(currentItem, true);
                                            //修改指示点
                                            int childCount = dotContainer.getChildCount();
                                            for (int i = 0; i < childCount; i++) {
                                                View each = dotContainer.getChildAt(i);
                                                each.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dot_normal));
                                            }
                                            if (dotContainer.getChildCount() > 0) {
                                                View child = dotContainer.getChildAt(currentItem);
                                                if (child != null) {
                                                    child.setBackgroundDrawable(getResources().getDrawable(R.mipmap.dot_focused));
                                                }
                                            }
                                        }
                                    });
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    adThread.start();
                }
            }
        }


        @Override
        public int getItemCount() {
            return mAllDatas.size();
        }
    }

    private class AdViewPagerAdapter extends PagerAdapter {
        private Context context;
        private List<String> banners;

        public AdViewPagerAdapter(Context context, List<String> banners) {
            this.context = context;
            this.banners = banners;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getContext());
            iv.setAdjustViewBounds(true);
            iv.setMaxHeight(Integer.MAX_VALUE);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (position == 0) {
                iv.setImageResource(R.mipmap.ad_demo);
            } else {
                iv.setImageResource(R.mipmap.ad_demo);
            }
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return banners.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
