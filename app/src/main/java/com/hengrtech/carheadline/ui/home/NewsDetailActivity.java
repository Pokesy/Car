package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.NewsDetailCommentsModel;
import com.hengrtech.carheadline.net.model.NewsDetailModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.NewsCollectParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.ToastHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.loonggg.textwrapviewlib.view.TextWrapView;
import java.util.List;
import javax.inject.Inject;
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

  @Bind(R.id.news_scroll) ScrollView newsScroll;
  @Bind(R.id.edit_disable_text) TextView edit_disable_text;
  @Bind(R.id.supportCount) TextView supportCount;
  @Bind(R.id.support_button) LinearLayout support_button;
  @Bind(R.id.news_reply) ImageView newsReply;
  @Bind(R.id.reply_count) TextView replyCount;
  @Bind(R.id.news_fast_reply) LinearLayout news_fast_reply;
  @Bind(R.id.reply_bottom_layout) LinearLayout replyBottomLayout;
  @Bind(R.id.reply_editText) EditText replyEditText;
  @Bind(R.id.sendButn) TextView sendButn;
  @Bind(R.id.reply_box) LinearLayout replyBox;
  @Bind(R.id.content) TextWrapView content;
  @Bind(R.id.jrecyclerview) RGridView mRecyclerView;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.time) TextView time;
  @Bind(R.id.collect_iv) ImageView collectIv;
  @Bind(R.id.recycler_view) RGridView recyclerView;
  @Bind(R.id.collect_layout) LinearLayout collectLayout;
  private int newsId;
  private Context mContext;
  private CommentAcapter adapter;
  private SimpleLoadFooterAdapter adapter1;

  private String mContent;
  private String mTime;
  private String mTitle;
  private int comment_count;
  private String view_count;
  private boolean isCollected;

  @Inject AppService mInfo;

  @Override public int getLayoutId() {
    return R.layout.activity_news_detail;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mContext = NewsDetailActivity.this;
    newsId = getIntent().getExtras().getInt("newId");
    comment_count = getIntent().getExtras().getInt("comment_count");
    view_count = getIntent().getExtras().getString("view_count");
    mContent = getIntent().getExtras().getString("content");
    mTime = getIntent().getExtras().getString("time");
    mTitle = getIntent().getExtras().getString("title");
    isCollected = getIntent().getExtras().getBoolean("isCollected");
    time.setText(mTime);
    title.setText(mTitle);
    content.setText("<p>" + mContent.replace("\n", "</P><p>") + "</p>",
        getResources().getColor(R.color.font_color_black), 14);
    supportCount.setText(String.valueOf(comment_count));
    replyCount.setText(String.valueOf(view_count));
    inject();
    initmoredata();
    initcomment();
    intView();
    exitAnim();
  }

  @Override public boolean initializeTitleBar() {
    setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    setMiddleTitle("详情");
    setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
    return true;
  }

  public void initmoredata() {
    manageRpcCall(
        mInfo.getNewDetail(newsId, String.valueOf(getComponent().loginSession().getMemberId())),
        new UiRpcSubscriber<NewsDetailModel>(this) {
          @Override protected void onSuccess(NewsDetailModel infoModels) {
            recyclerView.setAdapter(new MoreAdapter(NewsDetailActivity.this, infoModels.getRelatedReading()));
          }

          @Override protected void onEnd() {
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
          }
        });
  }

  public void initcomment() {
    manageRpcCall(mInfo.getNewComments(newsId, "1", "100"),
        new UiRpcSubscriber<List<NewsDetailCommentsModel>>(this) {
          @Override protected void onSuccess(List<NewsDetailCommentsModel> infoModels) {
            mRecyclerView.setAdapter(new CommentAcapter(NewsDetailActivity.this, infoModels));
          }

          @Override protected void onEnd() {
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
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

    //发表回复文本框的事件监听器
    replyEditText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
          sendButn.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendButn.setTextColor(Color.WHITE);
        } else {
          sendButn.setBackgroundResource(R.drawable.border);
          sendButn.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }

      @Override public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
          sendButn.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendButn.setTextColor(Color.WHITE);
        } else {
          sendButn.setBackgroundResource(R.drawable.border);
          sendButn.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }
    });
    newsScroll.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        //触摸屏幕隐藏回复界面
        replyBox.setVisibility(View.GONE);
        InputMethodManager imm =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
          imm.hideSoftInputFromWindow(replyEditText.getWindowToken(),
              InputMethodManager.HIDE_NOT_ALWAYS);
        }
        replyEditText.clearFocus();
        return false;
      }
    });

    if (isCollected) {
      collectIv.setImageResource(R.mipmap.shoucang_sl);
    }
  }

  @Override public void onClick(View v) {
    int viewId = v.getId();
    switch (viewId) {
      case R.id.collect_layout:
        initColectService();
        break;

      case R.id.supportCount:

        break;

      case R.id.news_reply://回复按钮
        initFlag(true, false);
        if (!getComponent().isLogin()) {
          replyBox.setVisibility(View.VISIBLE);
          replyEditText.setText("");
          //获取焦点显示键盘
          showInputWindow(replyEditText);
        } else {
          ToastHelper.showToast("请登录后操作", mContext);
        }
        break;
      case R.id.edit_disable_text://回复按钮
        initFlag(true, false);
        if (getComponent().isLogin()) {
          replyBox.setVisibility(View.VISIBLE);
          replyEditText.setText("");
          //获取焦点显示键盘
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

        }

        break;
      default:
        break;
    }
  }

  private void initColectService() {
    UserInfo mUserInfo = getComponent().loginSession().getUserInfo();
    manageRpcCall(mInfo.collectNews(mUserInfo.getMemberId(), newsId, mUserInfo.getToken(),
        new NewsCollectParams(mUserInfo.getMemberId(), newsId, mUserInfo.getToken())),
        new UiRpcSubscriber<String>(this) {
          @Override protected void onSuccess(String msg) {
            showShortToast("收藏成功");
            collectIv.setImageResource(R.mipmap.shoucang_sl);
          }

          @Override protected void onEnd() {

          }
        });
  }

  @Override public void finish() {
    super.finish();
    //解决Activity退出动画无效的问题
    overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
  }

  /**
   * 解决Activity退出动画无效的问题
   */
  public void exitAnim() {
    TypedArray activityStyle =
        getTheme().obtainStyledAttributes(new int[] { android.R.attr.windowAnimationStyle });
    int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
    activityStyle.recycle();
    activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {
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

  public class MoreAdapter extends RBaseAdapter<NewsDetailModel.RelatedReadingEntity> {
    Context context;
    private List<NewsDetailModel.RelatedReadingEntity> data;

    public MoreAdapter(Context context, List<NewsDetailModel.RelatedReadingEntity> datas) {
      super(context, datas);

      data = datas;
    }

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.more_news;
    }

    @Override protected void onBindView(RViewHolder holder, final int position,
        final NewsDetailModel.RelatedReadingEntity replyInfo) {
      holder.tV(R.id.more_news).setText(replyInfo.getTitle());
      holder.tV(R.id.more_news).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(NewsDetailActivity.this, NewsDetailActivity.class);
          Bundle bundle = new Bundle();
          bundle.putInt("newId", replyInfo.getNewsId());
          bundle.putString("content", replyInfo.getContent());
          bundle.putString("title", replyInfo.getTitle());
          bundle.putInt("comment_count", replyInfo.getCommentsCount());
          bundle.putString("view_count", String.valueOf(replyInfo.getPraiseCount()));
          bundle.putString("time", DateHelper.getInstance().getRencentTime(replyInfo.getCreateTime()));
          intent.putExtras(bundle);
          startActivity(intent);
        }
      });
    }
  }

  public class CommentAcapter extends RBaseAdapter<NewsDetailCommentsModel> {
    Context context;
    private List<NewsDetailCommentsModel> data;

    public CommentAcapter(Context context, List<NewsDetailCommentsModel> datas) {
      super(context, datas);

      data = datas;
    }

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.news_reply_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, NewsDetailCommentsModel replyInfo) {

      ImageLoader.loadOptimizedHttpImage(NewsDetailActivity.this,
          replyInfo.getMember().getPortrait())
          .
              bitmapTransform(new CropCircleTransformation(NewsDetailActivity.this))
          .into(holder.imageView(R.id.replyer_head));
      holder.v(R.id.replyer_head).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          // TODO: 2016/8/25  点击头像跳转信息

        }
      });
      holder.tV(R.id.reply_avatar).setText(replyInfo.getMember().getNickName());
      holder.tV(R.id.reply_time).setText(replyInfo.getCommentsTime());
      holder.tV(R.id.reply_content).setText(replyInfo.getContent());
      //回复评论按钮
      holder.v(R.id.reply).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (getComponent().isLogin()) {
            replyBox.setVisibility(View.VISIBLE);
            //获取焦点显示键盘
            showInputWindow(replyEditText);
            replyEditText.setText("回复@" + "：");
            //把光标自动放末尾
            replyEditText.setSelection(replyEditText.getText().length());
          } else {
            ToastHelper.showToast("请登录后操作", mContext);
          }
        }
      });
      holder.v(R.id.del_reply).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (getComponent().isLogin()) {
            // TODO: 2016/8/25 删除评论接口
          } else {
            ToastHelper.showToast("请登录后操作", mContext);
          }
        }
      });
      //控件赋值

    }
    //private void del(){
    //  manageRpcCall(mInfo.getNewDetail(newsId), new UiRpcSubscriber<NewsDetailModel>(this) {
    //    @Override protected void onSuccess(NewsDetailModel infoModels) {
    //      supportCount.setText(infoModels.getCommentsCount());
    //      replyCount.setText(infoModels.getPraiseCount());
    //      CharSequence text = Html.fromHtml(infoModels.getContent(), new Html.ImageGetter() {
    //        public Drawable getDrawable(String source) {
    //          //根据图片资源ID获取图片
    //          Log.d("source", source);
    //          if (source.equals("strawberry")) {
    //            Drawable draw = getResources().getDrawable(R.mipmap.ic_launcher);
    //            draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
    //            return draw;
    //          }
    //          return null;
    //        }
    //      }, null);
    //      content.setText(text);
    //      initcomment();
    //    }
    //
    //    @Override protected void onEnd() {
    //    }
    //
    //    @Override public void onApiError(RpcApiError apiError) {
    //      super.onApiError(apiError);
    //    }
    //  });
    //}
  }
}
