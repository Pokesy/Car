package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.CommentsCommentInfoModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.CommentsCommentParams;
import com.hengrtech.carheadline.ui.adapter.BaseRecyclerAdapter;
import com.hengrtech.carheadline.ui.adapter.RecyclerViewHolder;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class NewsCommentDetailsActivity extends BasicTitleBarActivity {
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.edit_disable_text)
    TextView editDisableText;
    @Bind(R.id.supportCount)
    TextView supportCount;
    @Bind(R.id.support_button)
    LinearLayout supportButton;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.activity_news_comment_details)
    RelativeLayout activityNewsCommentDetails;
    @Inject
    AppService mInfo;
    @Bind(R.id.news_zan_iv)
    ImageView newsZanIv;
    @Bind(R.id.comments_rv)
    RecyclerView commentsRv;
    @Bind(R.id.reply_editText)
    EditText replyEditText;
    @Bind(R.id.sendButn)
    TextView sendButn;
    @Bind(R.id.reply_box)
    LinearLayout replyBox;
    @Bind(R.id.reply_bottom_layout)
    LinearLayout replyBottomLayout;
    private UserInfo mUserInfo;
    private int commentsId, zan_count;
    private String portrait, nickName, time, content;
    private List<CommentsCommentInfoModel> allComments = new ArrayList<>();
    private BaseRecyclerAdapter commentAdapter;
    private boolean isComment = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_comment_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        commentsId = this.getIntent().getIntExtra("commentsId", 0);
        portrait = this.getIntent().getStringExtra("portrait");
        nickName = this.getIntent().getStringExtra("nickName");
        time = this.getIntent().getStringExtra("time");
        zan_count = this.getIntent().getIntExtra("zan_count", 0);
        content = this.getIntent().getStringExtra("content");
        supportCount.setText(zan_count + "");
        isComment = this.getIntent().getBooleanExtra("isComment",false);
        mUserInfo = getComponent().loginSession().getUserInfo();
        if(isComment){
            replyBox.setVisibility(View.VISIBLE);
            replyBottomLayout.setVisibility(View.GONE);
            replyEditText.setText("");
            replyEditText.requestFocus();
            showInputWindow(replyEditText);
        }
        inject();
        getComments();
        commentsRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideReplayTextAndImm();
                return false;
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

    @OnClick({
            R.id.support_button, R.id.edit_disable_text, R.id.sendButn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.support_button:
                manageRpcCall(mInfo.commentZan(commentsId, mUserInfo.getMemberId()), new UiRpcSubscriber<String>(NewsCommentDetailsActivity.this) {
                    @Override
                    protected void onSuccess(String s) {
                        showShortToast("评论点赞成功");
                        newsZanIv.setBackgroundResource(R.mipmap.zan_selected);
                        int zan = zan_count + 1;
                        supportCount.setText(zan + "");
                        allComments.get(0).setPraiseCount(zan);
                        allComments.get(0).setHavenPraise(true);
                        commentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onEnd() {

                    }
                });
                break;
            case R.id.edit_disable_text:
                if (getComponent().isLogin()) {
                    replyBox.setVisibility(View.VISIBLE);
                    replyBottomLayout.setVisibility(View.GONE);
                    replyEditText.setText("");
                    replyEditText.requestFocus();
                    showInputWindow(replyEditText);
                } else {
                    showShortToast("请登录后操作");
                }
                break;
            case R.id.sendButn:
                String reply = replyEditText.getText().toString();
                if ("".equals(reply)) {
                    Toast.makeText(NewsCommentDetailsActivity.this, "回复不能为空哟", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //评论接口
                    commentComments(reply);
                }

                break;
        }
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

    private void commentComments(final String content) {
        manageRpcCall(mInfo.commentComments(mUserInfo.getToken(), new CommentsCommentParams(commentsId, mUserInfo.getMemberId(), content)), new UiRpcSubscriber<String>(this) {
            @Override
            protected void onSuccess(String s) {
                hideReplayTextAndImm();
                showShortToast("评论成功");
                CommentsCommentInfoModel ccim = new CommentsCommentInfoModel();
                ccim.setCommentsTime(DateHelper.getInstance().getNowDate());
                ccim.setUserId(mUserInfo.getMemberId());
                ccim.setContent(content);
                CommentsCommentInfoModel.MemberBean mm = new CommentsCommentInfoModel.MemberBean();
                mm.setNickName(mUserInfo.getNickName());
                mm.setMemberId(mUserInfo.getMemberId());
                mm.setPortrait(mUserInfo.getPortrait());
                ccim.setMember(mm);
                allComments.add(1, ccim);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    private void getComments() {
        manageRpcCall(mInfo.getCommentsCommentList(commentsId), new UiRpcSubscriber<List<CommentsCommentInfoModel>>(this) {
            @Override
            protected void onSuccess(List<CommentsCommentInfoModel> infos) {
                CommentsCommentInfoModel ccim = new CommentsCommentInfoModel();
                ccim.setCommentsTime(time);
                ccim.setCommentsId(commentsId);
                CommentsCommentInfoModel.MemberBean mb = new CommentsCommentInfoModel.MemberBean();
                mb.setNickName(nickName);
                mb.setPortrait(portrait);
                ccim.setMember(mb);
                ccim.setPraiseCount(zan_count);
                ccim.setContent(content);
                infos.add(0, ccim);
                allComments.addAll(infos);
                setCommentAdapter(allComments);
            }

            @Override
            protected void onEnd() {

            }
        });
    }


    @Override
    public boolean initializeTitleBar() {
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setMiddleTitle("评论详情");
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        return true;
    }

    private void setCommentAdapter(final List<CommentsCommentInfoModel> infos) {
        commentAdapter = new BaseRecyclerAdapter<CommentsCommentInfoModel>(this, infos) {

            @Override
            public int getItemLayoutId(int viewType) {
                if (viewType == 2) {
                    return R.layout.item_more_comment_rv_header;
                } else {
                    return R.layout.item_more_comment_rv;
                }
            }

            @Override
            public void bindData(RecyclerView.ViewHolder holder, int position, final CommentsCommentInfoModel replyInfo) {
                if (holder instanceof RecyclerViewHolder) {
                    final RecyclerViewHolder mHolder = (RecyclerViewHolder) holder;
                    if (position != 0) {
                        ImageLoader.loadOptimizedHttpImage(NewsCommentDetailsActivity.this,
                                replyInfo.getMember().getPortrait())
                                .
                                        bitmapTransform(new CropCircleTransformation(NewsCommentDetailsActivity.this))
                                .into(mHolder.getImageView(R.id.replyer_head));
                        mHolder.getTextView(R.id.item_zan_tv).setText(replyInfo.getPraiseCount() + "");
                        mHolder.getTextView(R.id.reply_name_tv).setText(replyInfo.getMember().getNickName());
                        mHolder.getTextView(R.id.reply_time).setText(DateHelper.getInstance().getRencentTime(replyInfo.getCommentsTime()));
                        mHolder.getTextView(R.id.reply_content).setText(replyInfo.getContent());
                        if (replyInfo.getUserId() == mUserInfo.getMemberId()) {
                            mHolder.getTextView(R.id.del_reply).setVisibility(View.VISIBLE);
                        } else {
                            mHolder.getTextView(R.id.del_reply).setVisibility(View.GONE);
                        }
                        if (replyInfo.isHavenPraise()) {
                            mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.zan_selected);
                        } else {
                            mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.news_zan_icon);
                        }
                        mHolder.getView(R.id.item_zan_layout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                manageRpcCall(mInfo.commentZan(replyInfo.getCommentsId(), mUserInfo.getMemberId()), new UiRpcSubscriber<String>(NewsCommentDetailsActivity.this) {
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
                                manageRpcCall(mInfo.delComment(replyInfo.getCommentsId(), mUserInfo.getMemberId(), mUserInfo.getToken()), new UiRpcSubscriber<String>(NewsCommentDetailsActivity.this) {
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

                    } else {
                        ImageLoader.loadOptimizedHttpImage(NewsCommentDetailsActivity.this,
                                replyInfo.getMember().getPortrait())
                                .
                                        bitmapTransform(new CropCircleTransformation(NewsCommentDetailsActivity.this))
                                .into(mHolder.getImageView(R.id.replyer_head));
                        mHolder.getTextView(R.id.item_zan_tv).setText(replyInfo.getPraiseCount() + "");
                        mHolder.getTextView(R.id.reply_name_tv).setText(replyInfo.getMember().getNickName());
                        mHolder.getTextView(R.id.reply_time).setText(DateHelper.getInstance().getRencentTime(replyInfo.getCommentsTime()));
                        mHolder.getTextView(R.id.reply_content).setText(replyInfo.getContent());
                        if (replyInfo.isHavenPraise()) {
                            mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.zan_selected);
                        } else {
                            mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.news_zan_icon);
                        }
                        mHolder.getView(R.id.item_zan_layout).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                manageRpcCall(mInfo.commentZan(replyInfo.getCommentsId(), mUserInfo.getMemberId()), new UiRpcSubscriber<String>(NewsCommentDetailsActivity.this) {
                                    @Override
                                    protected void onSuccess(String s) {
                                        showShortToast("评论点赞成功");
                                        mHolder.getImageView(R.id.item_zan_iv).setBackgroundResource(R.mipmap.zan_selected);
                                        mHolder.getTextView(R.id.item_zan_tv).setText(replyInfo.getPraiseCount() + 1 + "");
                                        newsZanIv.setBackgroundResource(R.mipmap.zan_selected);
                                        supportCount.setText(zan_count++ + "");
                                    }

                                    @Override
                                    protected void onEnd() {

                                    }
                                });
                            }
                        });
                    }
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return super.onCreateViewHolder(parent, viewType);
            }
        };
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        commentsRv.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter.setHeaderItem(true);
        commentsRv.setAdapter(commentAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
