package com.hengrtech.carheadline.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.NewsDetailActivity;
import com.hengrtech.carheadline.ui.home.RGridView;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.ImagePagerActivity;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCollectActivity extends BasicTitleBarActivity implements RefreshLayout.OnRefreshListener {
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.my_collect_rv)
    RGridView myCollectRv;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    @Bind(R.id.activity_my_collect)
    LinearLayout activityMyCollect;
    @Inject
    AppService mInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        inject();
        refreshlayout.setOnRefreshListener(this);
        initService();
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    private void initService() {
        UserInfo mUserInfo = getComponent().loginSession().getUserInfo();
        manageRpcCall(mInfo.getCollectList(mUserInfo.getMemberId(), mUserInfo.getToken()), new UiRpcSubscriber<List<InfoModel>>(this) {
            @Override
            protected void onSuccess(List<InfoModel> infoModels) {
                refreshlayout.refreshingComplete();
                myCollectRv.setAdapter(new ZixunAdapter(MyCollectActivity.this, infoModels));
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.fragment_profile_collect);
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
    public void onRefresh() {
        initService();
    }

    public class ZixunAdapter extends RBaseAdapter<InfoModel> {
        Context context;
        private List<InfoModel> data;

        public ZixunAdapter(Context context, List<InfoModel> datas) {
            super(context, datas);
            data = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            if (viewType == 1) {
                return R.layout.home_new_list_item;
            } else {
                return R.layout.home_new_list_item_three;
            }
        }

        @Override
        public int getItemViewType(int position) {
            int imgsize = data.get(position).getCoverArr().size();
            if (imgsize == 1) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final InfoModel bean) {
            holder.v(R.id.ll_zx).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyCollectActivity.this, NewsDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("newId", bean.getNewsId());
                    bundle.putString("content", bean.getContent());
                    bundle.putString("title", bean.getTitle());
                    bundle.putInt("comment_count", bean.getCommentsCount());
                    bundle.putString("view_count", bean.getPraiseCount());
                    bundle.putString("time", DateHelper.getInstance().getRencentTime(bean.getCreateTime()));
                    bundle.putBoolean("isCollected", bean.isCollected());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            holder.tV(R.id.news_title).setText(bean.getTitle());
            holder.tV(R.id.time).setText(DateHelper.getInstance().getRencentTime(bean.getCreateTime()));
            holder.tV(R.id.tv_from).setText(bean.getSource());
            holder.tV(R.id.view_count).setText(String.valueOf(bean.getPraiseCount()));
            holder.tV(R.id.comment_count).setText(String.valueOf(bean.getCommentsCount()));
            if (bean.getCoverArr() != null) {
                int imagesize = bean.getCoverArr().size();
                if (imagesize == 1) {
                    ImageLoader.loadOptimizedHttpImage(MyCollectActivity.this, bean.getCoverArr().get(0))
                            .into(holder.imgV(R.id.iv_1));
                    holder.imgV(R.id.iv_1).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MyCollectActivity.this, ImagePagerActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getCoverArr());
                            bundle.putInt("image_index", 1);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
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
                                    ImageLoader.loadOptimizedHttpImage(MyCollectActivity.this, url)
                                            .placeholder(R.mipmap.ic_launcher)
                                            .error(R.mipmap.ic_launcher)
                                            .into(imageView);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                final int in = i;
                                imageView.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MyCollectActivity.this, ImagePagerActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getCoverArr());
                                        bundle.putInt("image_index", in);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    } else {
                        holder.v(R.id.images).setVisibility(View.GONE);
                    }
                }
            } else {
                holder.v(R.id.images).setVisibility(View.GONE);
            }
        }
    }
}
