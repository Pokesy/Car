package com.hengrtech.carheadline.ui.area;

import android.content.Context;
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
import com.hengrtech.carheadline.net.model.AreaQuestionDetailCommentsModel;
import com.hengrtech.carheadline.net.params.SendCommentCarParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.RGridView;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.CircleImageView;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.ToastHelper;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 社区详情页面
 * Created by Administrator on 2015/7/23 0023.
 */
public class AreaDetailActivity extends BasicTitleBarActivity implements View.OnClickListener {

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
  @Bind(R.id.content) TextView content;
  @Bind(R.id.jrecyclerview) RGridView mRecyclerView;
  @Bind(R.id.head) ImageView head;
  @Bind(R.id.nick_name) TextView mNickName;
  @Bind(R.id.time) TextView mTime;
  @Bind(R.id.zan) TextView zan;
  @Bind(R.id.buzan) TextView buzan;
  private int newsId;
  private Context mContext;
  private CommentAcapter adapter;
  private SimpleLoadFooterAdapter adapter1;

  private String logo;
  private String time;
  private String nickName;
  private String question;
  private int questionId;
  CircleImageView replyerHead;
  TextView replyAvatar, replyTime, replyContent, replyBtn, delReBtn;
  private List<AreaQuestionDetailCommentsModel> mInfoModels;

  @Inject AppService mInfo;

  @Override public int getLayoutId() {
    return R.layout.activity_area_detail;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mContext = AreaDetailActivity.this;
    logo = getIntent().getExtras().getString("logo");
    time = getIntent().getExtras().getString("time");
    nickName = getIntent().getExtras().getString("nickName");
    questionId = getIntent().getExtras().getInt("questionId");
    question = getIntent().getExtras().getString("question");
    ImageLoader.loadOptimizedHttpImage(this, logo).
        bitmapTransform(new CropCircleTransformation(this)).into(head);
    mTime.setText(DateHelper.getInstance().getRencentTime(time));
    mNickName.setText(nickName);
    content.setText(question);
    inject();
    intView();
    initcomment();
    exitAnim();
  }

  @Override public boolean initializeTitleBar() {
    setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    setMiddleTitle(R.string.news_detail_title);
    return true;
  }

  public void initcomment() {
    manageRpcCall(mInfo.getAreaComments(questionId),
        new UiRpcSubscriber<List<AreaQuestionDetailCommentsModel>>(this) {
          @Override protected void onSuccess(List<AreaQuestionDetailCommentsModel> infoModels) {
            mInfoModels = infoModels;
            adapter = new CommentAcapter(AreaDetailActivity.this, mInfoModels);
            mRecyclerView.setAdapter(adapter);
          }

          @Override protected void onEnd() {
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
          }
        });
  }

  public void sendComment(final String string) {
    manageRpcCall(mInfo.sendComments(getComponent().loginSession().getUserInfo().getToken(),
        new SendCommentCarParams(getComponent().loginSession().getUserInfo().getMemberId(),
            questionId, string)), new UiRpcSubscriber<String>(this) {

      @Override protected void onSuccess(String info) {
        //设置数据
        if (adapter == null) {
          initcomment();
        } else {
          AreaQuestionDetailCommentsModel aqdc = new AreaQuestionDetailCommentsModel();
          aqdc.setAnswer(string);
          aqdc.setAvatar(getComponent().loginSession().getUserInfo().getPortrait());
          aqdc.setNickName(getComponent().loginSession().getUserInfo().getNickName());
          mInfoModels.add(aqdc);
          adapter.notifyDataSetChanged();
        }
      }

      @Override protected void onEnd() {
        closeProgressDialog();
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
    //获取资讯信息
    //newsApi.getNewsInfo(newsListInfo.getId());
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
  }

  @Override public void onClick(View v) {
    int viewId = v.getId();
    switch (viewId) {

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
          Toast.makeText(AreaDetailActivity.this, "回复不能为空哟", Toast.LENGTH_SHORT).show();
          return;
        } else {
          //评论接口
          sendComment(reply);
          replyBox.setVisibility(View.GONE);
        }

        break;
      default:
        break;
    }
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

  public class CommentAcapter extends RBaseAdapter<AreaQuestionDetailCommentsModel> {

    private List<AreaQuestionDetailCommentsModel> data;

    public CommentAcapter(Context context, List<AreaQuestionDetailCommentsModel> datas) {
      super(context, datas);

      data = datas;
    }

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.question_reply_item;
    }

    @Override protected void onBindView(RViewHolder holder, int position,
        AreaQuestionDetailCommentsModel replyInfo) {

      ImageLoader.loadOptimizedHttpImage(AreaDetailActivity.this, replyInfo.getAvatar())
          .
              bitmapTransform(new CropCircleTransformation(AreaDetailActivity.this))
          .into(holder.imageView(R.id.replyer_head));
      holder.v(R.id.replyer_head).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          // TODO: 2016/8/25  点击头像跳转信息

        }
      });
      holder.tV(R.id.reply_avatar).setText(replyInfo.getNickName());
      holder.tV(R.id.reply_time)
          .setText(DateHelper.getInstance().getRencentTime(replyInfo.getCreateTime()));
      holder.tV(R.id.reply_content).setText(replyInfo.getAnswer());


    }
  }
}
