package com.hengrtech.carheadline.ui.area;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
import com.hengrtech.carheadline.net.model.QuestionModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.adapter.RecyclerAdapter;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class QusetionAllFragment extends BasicTitleBarFragment
        implements RefreshLayout.OnRefreshListener,
        OnLoadListener {
    public static final String TYPE = "type";
    @Bind(R.id.jrecyclerview)
    JRecyclerView mRecyclerView;
    @Bind(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    @Inject
    AppService mInfo;
    private QuestionAdapter adapter;
    private SimpleLoadFooterAdapter adapter1;
    private List<QuestionModel> allList = new ArrayList<>();
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
        adapter = new QuestionAdapter(getActivity());
        adapter1 = new SimpleLoadFooterAdapter(getActivity());
        mRecyclerView.setAdapter(adapter, adapter1);
        mRecyclerView.setLoadMore(true);
        //设置事件
        mRecyclerView.setOnLoadListener(this);
        refreshlayout.setOnRefreshListener(this);
        //主动发起下拉刷新
        refreshlayout.startRefreshing();
    }

    public void initdata() {
        manageRpcCall(mInfo.getAllQuestion(page + ""),
                new UiRpcSubscriber<List<QuestionModel>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<QuestionModel> infoModels) {
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

    public static QusetionAllFragment newInstance(String param1) {
        QusetionAllFragment fragment = new QusetionAllFragment();
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

    public class QuestionAdapter extends RecyclerAdapter<QuestionModel> {

        /**
         * 主构造
         *
         * @param activity Activity对象
         */
        public QuestionAdapter(Activity activity) {
            super(activity);
        }

        @Override
        public List<QuestionModel> getRealDatas() {
            return super.getRealDatas();
        }

        @Override
        public int getItemViewType(int position) {
            if (getRealDatas().get(position).getStatus() == 0) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return inflater.inflate(R.layout.area_question_two_list_item, parent, false);
            } else {
                return inflater.inflate(R.layout.area_question_list_item, parent, false);
            }
        }

        @Override
        public void convert(RecyclerHolder holder, final QuestionModel bean, int position) {
            holder.setText(R.id.content, bean.getQuestion());
            holder.setText(R.id.time, DateHelper.getInstance().getRencentTime(bean.getCreateTime()));
            holder.setText(R.id.nick_name, bean.getNickName());
            holder.getView(R.id.ll_zx).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AreaDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("logo", bean.getAvatar());
                    bundle.putString("nickName", bean.getNickName());
                    bundle.putString("time", bean.getCreateTime());
                    bundle.putInt("questionId", bean.getQuestionId());
                    bundle.putString("question", bean.getQuestion());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getAvatar())
                    .
                            bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(holder.getImageView(R.id.head));
            if (bean.getImgList() != null) {
                int imagesize = bean.getImgList().size();

                if (imagesize > 0) {
                    holder.getView(R.id.images).setVisibility(View.VISIBLE);
                    holder.getImageView(R.id.iv_1)
                            .setVisibility(imagesize > 0 ? View.VISIBLE : View.INVISIBLE);
                    holder.getImageView(R.id.iv_2)
                            .setVisibility(imagesize > 1 ? View.VISIBLE : View.INVISIBLE);
                    holder.getImageView(R.id.iv_3)
                            .setVisibility(imagesize > 2 ? View.VISIBLE : View.INVISIBLE);
                    int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
                    LinearLayout.LayoutParams params =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    holder.getView(R.id.images).setLayoutParams(params);

                    for (int i = 0; i < imagesize; i++) {
                        String url = bean.getImgList().get(i);
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
//                            final int in = i;
//                            imageView.setOnClickListener(new View.OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getImgList());
//                                    bundle.putInt("image_index", in);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

                            if (bean.getStatus() == 0) {
                                holder.getImageView(R.id.my_want_answer_iv).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                            } else {
                                if(bean.getAnswer()!=null) {
                                    ImageSpan span = new ImageSpan(getContext(), R.mipmap.adoption_icon);
                                    SpannableString spanStr = new SpannableString("   " + bean.getAnswer().getAnswer());
                                    spanStr.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                    holder.setText(R.id.adoption_tv, spanStr);
                                }
                            }
                        }
                    }
                } else {
                    holder.getView(R.id.images).setVisibility(View.GONE);
                }
            } else {
                holder.getView(R.id.images).setVisibility(View.GONE);
            }
        }
    }
}
