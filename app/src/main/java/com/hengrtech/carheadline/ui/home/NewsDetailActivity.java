package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.NewsDetailCommentsModel;
import com.hengrtech.carheadline.net.model.NewsDetailMoreModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.NewsCollectParams;
import com.hengrtech.carheadline.net.params.NewsCommentParams;
import com.hengrtech.carheadline.ui.adapter.BaseRecyclerAdapter;
import com.hengrtech.carheadline.ui.adapter.RecyclerViewHolder;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.DisplayUtil;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.ToastHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.view.JRecyclerView;
import com.loonggg.textwrapviewlib.view.TextWrapView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 资讯详情页面
 * Created by Administrator on 2015/7/23 0023.
 */
public class NewsDetailActivity extends BasicTitleBarActivity implements View.OnClickListener {

    protected int activityCloseEnterAnimation;
    protected int activityCloseExitAnimation;
    private static boolean REPLY = false;
    private static boolean REPLYS = false;

    @Bind(R.id.edit_disable_text)
    TextView edit_disable_text;
    @Bind(R.id.supportCount)
    TextView supportCount;
    @Bind(R.id.support_button)
    LinearLayout support_button;
    @Bind(R.id.news_reply)
    ImageView newsReply;
    @Bind(R.id.reply_count)
    TextView replyCount;
    @Bind(R.id.news_fast_reply)
    LinearLayout news_fast_reply;
    @Bind(R.id.reply_editText)
    EditText replyEditText;
    @Bind(R.id.sendButn)
    TextView sendButn;
    @Bind(R.id.reply_box)
    LinearLayout replyBox;
    @Bind(R.id.content)
    TextWrapView content;
    @Bind(R.id.jrecyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.collect_iv)
    ImageView collectIv;
    @Bind(R.id.collect_layout)
    LinearLayout collectLayout;
    @Bind(R.id.more_news_rv)
    JRecyclerView moreNewsRv;
    @Bind(R.id.more_recommend_layout)
    LinearLayout moreRecommendLayout;
    @Bind(R.id.reply_bottom_layout)
    LinearLayout replyBottomLayout;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.news_scroll)
    MyScrollView newsScroll;
    @Bind(R.id.all_layout)
    RelativeLayout allLayout;
    @Bind(R.id.news_zan_iv)
    ImageView newsZanIv;
    @Bind(R.id.news_zan_tv)
    TextView newsZanTv;
    @Bind(R.id.news_zan_layout)
    LinearLayout newsZanLayout;
    private int newsId;
    private Context mContext;
    private BaseRecyclerAdapter commentAdapter;
    private SimpleLoadFooterAdapter adapter1;
    private List<NewsDetailCommentsModel> allComments = new ArrayList<>();

    private String mContent;
    private String mTime;
    private String mTitle;
    private int comment_count;
    private int zan_count;
    private boolean isCollected;

    @Inject
    AppService mInfo;
    private UserInfo mUserInfo;
    private int scrollHeight = 0;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mContext = NewsDetailActivity.this;
        newsId = getIntent().getExtras().getInt("newId");
        comment_count = getIntent().getExtras().getInt("comment_count");
        mContent = getIntent().getExtras().getString("content");
        mTime = getIntent().getExtras().getString("time");
        mTitle = getIntent().getExtras().getString("title");
        time.setText(mTime);
        title.setText(mTitle);
        content.setText("<p>" + mContent.replace("\n", "</p><p>") + "</p>",
                getResources().getColor(R.color.font_color_black), 14);
        replyCount.setText(String.valueOf(comment_count));
        mUserInfo = getComponent().loginSession().getUserInfo();
        inject();
        intView();
        initmoredata();
        initcomment();
        exitAnim();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        scrollHeight = title.getHeight() + time.getHeight() + moreRecommendLayout.getHeight() + content.getHeight();
    }

    @Override
    public boolean initializeTitleBar() {
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setMiddleTitle("详情");
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        return true;
    }

    public void initmoredata() {
        if (mUserInfo.getToken() != null)
            manageRpcCall(
                    mInfo.getNewsMoreRecommendList(newsId, mUserInfo.getMemberId() + ""), new UiRpcSubscriber<NewsDetailMoreModel>(this) {
                        @Override
                        protected void onSuccess(NewsDetailMoreModel newsDetailMoreModel) {
                            zan_count = newsDetailMoreModel.getPraiseCount();
                            isCollected = newsDetailMoreModel.isCollected();
                            if (newsDetailMoreModel.isHavenPraise()) {
                                newsZanIv.setBackgroundResource(R.mipmap.zan_selected);
                            }
                            newsZanTv.setText(newsDetailMoreModel.getPraiseCount()+"");
                            if (isCollected) {
                                collectIv.setImageResource(R.mipmap.shoucang_sl);
                            }
                            if (newsDetailMoreModel.getRelatedReading().size() > 0) {
                                moreRecommendLayout.setVisibility(View.VISIBLE);
                            }
                            supportCount.setText(String.valueOf(newsDetailMoreModel.getPraiseCount()));
                            moreNewsRv.setLayoutManager(new LinearLayoutManager(NewsDetailActivity.this));
                            moreNewsRv.setAdapter(new MoreAdapter(NewsDetailActivity.this, newsDetailMoreModel.getRelatedReading()));
                        }

                        @Override
                        protected void onEnd() {

                        }
                    });
    }

    public void initcomment() {
        manageRpcCall(mInfo.getNewComments(newsId, page + "", "10"),
                new UiRpcSubscriber<List<NewsDetailCommentsModel>>(this) {
                    @Override
                    protected void onSuccess(List<NewsDetailCommentsModel> infoModels) {
                        if (page == 1) {
                            allComments.clear();
                            allComments.addAll(infoModels);
                            setCommentAdapter();
                        } else {
                            allComments.addAll(infoModels);
                            commentAdapter.setMoreStatus(BaseRecyclerAdapter.PULLUP_LOAD_MORE);
                            commentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onEnd() {
                    }

                    @Override
                    public void onApiError(RpcApiError apiError) {
                        super.onApiError(apiError);
                    }
                });


    }

    private void setCommentAdapter() {
        commentAdapter = new BaseRecyclerAdapter<NewsDetailCommentsModel>(this, allComments) {

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.news_reply_item;
            }

            @Override
            public void bindData(RecyclerView.ViewHolder holder, int position, final NewsDetailCommentsModel replyInfo) {
                if (holder instanceof RecyclerViewHolder) {
                    final RecyclerViewHolder mHolder = (RecyclerViewHolder) holder;
                    ImageLoader.loadOptimizedHttpImage(NewsDetailActivity.this,
                            replyInfo.getMember().getPortrait())
                            .
                                    bitmapTransform(new CropCircleTransformation(NewsDetailActivity.this))
                            .into(mHolder.getImageView(R.id.replyer_head));
                    if (replyInfo.getUserId() == mUserInfo.getMemberId()) {
                        mHolder.getTextView(R.id.del_reply).setVisibility(View.VISIBLE);
                    } else {
                        mHolder.getTextView(R.id.del_reply).setVisibility(View.GONE);
                    }
                    mHolder.getTextView(R.id.item_zan_tv).setText(replyInfo.getPraiseCount() + "");
                    mHolder.getTextView(R.id.reply_avatar).setText(replyInfo.getMember().getNickName());
                    mHolder.getTextView(R.id.reply_time).setText(DateHelper.getInstance().getRencentTime(replyInfo.getCommentsTime()));
                    mHolder.getTextView(R.id.reply_content).setText(replyInfo.getContent());
                    if (replyInfo.isHavenPraise()) {
                        mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.zan_selected);
                    } else {
                        mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.news_zan_icon);
                    }
                    mHolder.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(NewsDetailActivity.this,NewsCommentDetailsActivity.class);
                            intent.putExtra("portrait",replyInfo.getMember().getPortrait());
                            intent.putExtra("commentsId",replyInfo.getCommentsId());
                            intent.putExtra("nickName",replyInfo.getMember().getNickName());
                            intent.putExtra("time",replyInfo.getCommentsTime());
                            intent.putExtra("zan_count",replyInfo.getPraiseCount());
                            intent.putExtra("content",replyInfo.getContent());
                            intent.putExtra("isComment",false);
                            startActivity(intent);
                        }
                    });
                    //回复评论按钮
                    mHolder.getTextView(R.id.reply).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NewsDetailActivity.this,NewsCommentDetailsActivity.class);
                            intent.putExtra("portrait",replyInfo.getMember().getPortrait());
                            intent.putExtra("commentsId",replyInfo.getCommentsId());
                            intent.putExtra("nickName",replyInfo.getMember().getNickName());
                            intent.putExtra("time",replyInfo.getCommentsTime());
                            intent.putExtra("zan_count",replyInfo.getPraiseCount());
                            intent.putExtra("content",replyInfo.getContent());
                            intent.putExtra("isComment",true);
                            startActivity(intent);
                        }
                    });
                    mHolder.getView(R.id.item_zan_layout).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            manageRpcCall(mInfo.commentZan(replyInfo.getCommentsId(), mUserInfo.getMemberId()), new UiRpcSubscriber<String>(NewsDetailActivity.this) {
                                @Override
                                protected void onSuccess(String s) {
                                    showShortToast("评论点赞成功");
                                    mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.zan_selected);
                                    mHolder.getTextView(R.id.item_zan_tv).setText(replyInfo.getPraiseCount() + 1 + "");
                                }

                                @Override
                                protected void onEnd() {

                                }
                            });
                        }
                    });

                    mHolder.getTextView(R.id.del_reply).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            manageRpcCall(mInfo.delComment(replyInfo.getCommentsId(), mUserInfo.getMemberId(), mUserInfo.getToken()), new UiRpcSubscriber<String>(NewsDetailActivity.this) {
                                @Override
                                protected void onSuccess(String s) {
                                    showShortToast("评论删除成功");
                                    allComments.remove(replyInfo);
                                    notifyDataSetChanged();
                                }

                                @Override
                                protected void onEnd() {

                                }
                            });
                        }
                    });
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }
        };
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(commentAdapter);
        commentAdapter.setIsShowLoadMore(true);
        commentAdapter.setLoadMoreString("查看更多");
        commentAdapter.setOnLoadMoreListener(new BaseRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                page++;
                initcomment();
                commentAdapter.setMoreStatus(BaseRecyclerAdapter.LOADING_MORE);
            }
        });
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    public void intView() {
        newsReply.setOnClickListener(this);
        sendButn.setOnClickListener(this);
        supportCount.setOnClickListener(this);
        edit_disable_text.setOnClickListener(this);
        collectLayout.setOnClickListener(this);
        newsZanLayout.setOnClickListener(this);

        //发表回复文本框的事件监听器
        replyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                } else {
                }
            }
        });
        newsScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideReplayTextAndImm();
                return false;
            }
        });

    }

    private void hideReplayTextAndImm() {
        //触摸屏幕隐藏回复界面
        replyBox.setVisibility(View.GONE);
        replyBottomLayout.setVisibility(View.VISIBLE);
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(replyEditText.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        replyEditText.clearFocus();
    }

    private void zanNews() {
        manageRpcCall(mInfo.newsZan(newsId, mUserInfo.getToken()), new UiRpcSubscriber<String>(NewsDetailActivity.this) {
            @Override
            protected void onSuccess(String s) {
                showShortToast("点赞成功");
                newsZanIv.setBackgroundResource(R.mipmap.zan_selected);
                newsZanTv.setText(zan_count + 1 + "");
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.collect_layout:
                initColectService();
                break;
            case R.id.news_zan_layout:
                zanNews();
                break;

            case R.id.supportCount:

                break;

            case R.id.news_reply:
                newsScroll.scrollTo(0, DisplayUtil.dip2px(this, scrollHeight));
                break;
            case R.id.edit_disable_text:
                initFlag(true, false);
                if (getComponent().isLogin()) {
                    replyBox.setVisibility(View.VISIBLE);
                    replyBottomLayout.setVisibility(View.GONE);
                    replyEditText.setText("");
                    replyEditText.requestFocus();
                    showInputWindow(replyEditText);
                } else {
                    ToastHelper.showToast("请登录后操作", mContext);
                }
                break;
            case R.id.sendButn:
                String reply = replyEditText.getText().toString();
                if ("".equals(reply)) {
                    Toast.makeText(NewsDetailActivity.this, "回复不能为空哟", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //评论接口
                    commentNews(reply);
                }

                break;
            default:
                break;
        }
    }


    private void commentNews(final String content) {
        manageRpcCall(mInfo.commentNews(mUserInfo.getToken(), new NewsCommentParams(newsId, mUserInfo.getMemberId(), content)), new UiRpcSubscriber<String>(this) {
            @Override
            protected void onSuccess(String s) {
                hideReplayTextAndImm();
                showShortToast("评论成功");
                NewsDetailCommentsModel ncm = new NewsDetailCommentsModel();
                ncm.setCommentsTime(DateHelper.getInstance().getNowDate());
                ncm.setUserId(mUserInfo.getMemberId());
                ncm.setContent(content);
                NewsDetailCommentsModel.MemberEntity me = new NewsDetailCommentsModel.MemberEntity();
                me.setNickName(mUserInfo.getNickName());
                me.setMemberId(mUserInfo.getMemberId());
                me.setPortrait(mUserInfo.getPortrait());
                ncm.setMember(me);
                allComments.add(0, ncm);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    private void initColectService() {
        manageRpcCall(mInfo.collectNews(newsId, mUserInfo.getToken(),
                new NewsCollectParams(newsId, mUserInfo.getToken())),
                new UiRpcSubscriber<String>(this) {
                    @Override
                    protected void onSuccess(String msg) {
                        showShortToast("收藏成功");
                        collectIv.setImageResource(R.mipmap.shoucang_sl);
                    }

                    @Override
                    protected void onEnd() {

                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        //解决Activity退出动画无效的问题
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    /**
     * 解决Activity退出动画无效的问题
     */
    public void exitAnim() {
        TypedArray activityStyle =
                getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
        activityStyle.recycle();
        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{
                android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation
        });
        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
        activityStyle.recycle();
    }

    public void initFlag(boolean reply, boolean replys) {
        REPLY = reply;
        REPLYS = replys;
    }

    public class MoreAdapter extends RBaseAdapter<NewsDetailMoreModel.RelatedReadingBean> {
        Context context;
        private List<NewsDetailMoreModel.RelatedReadingBean> data;

        public MoreAdapter(Context context, List<NewsDetailMoreModel.RelatedReadingBean> datas) {
            super(context, datas);
            data = datas;
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_news_details_more_recommend;
        }

        @Override
        protected void onBindView(RViewHolder holder, final int position,
                                  final NewsDetailMoreModel.RelatedReadingBean replyInfo) {
            holder.tV(R.id.item_title_tv).setText(replyInfo.getTitle());
            holder.tV(R.id.item_title_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }


}
