/*
 * 文件名: AddTagsActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/30
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;

/**
 * 添加标签<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/30]
 */
public class AddTagsActivity extends BasicTitleBarActivity {
  @Bind(R.id.btn_add)
  TextView mBtnAdd;
  @Bind(R.id.tags_layout) FlowLayout mTagsLayout;
  @Bind(R.id.tag_input)
  EditText mTagInput;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  private List<String> mUserLabelList = new ArrayList<>();

  @Override
  public boolean initializeTitleBar() {
    setMiddleTitle(R.string.activity_add_tags_title);
    setLeftTitleButton(R.mipmap.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    return true;
  }

  private void initView() {
    showUserLabel();
  }

  @Override
  public int getLayoutId() {
    return R.layout.activity_add_tags;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
    inject();
    initView();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((CustomApp) getApplication())).serviceModule(new ServiceModule()).build()
        .inject(this);
  }

  @OnClick(R.id.btn_add)
  public void addTag() {
    if (TextUtils.isEmpty(mTagInput.getText())) {
      return;
    }
    addTag(mTagInput.getText().toString());
    mUserLabelList.add(mTagInput.getText().toString());
    updateUserLabel();
  }

  private void showUserLabel() {
    String userLabel = getComponent().loginSession().getUserInfo().getUserLabel();
    if (TextUtils.isEmpty(userLabel)) {
      return;
    }
    String[] labels = userLabel.split(",");
    mUserLabelList.addAll(Arrays.asList(labels));
    for (String label : labels) {
      addTag(label);
    }
  }

  private void addTag(final String label) {
    TextView labelView = (TextView) LayoutInflater.from(this).inflate(R.layout.tag_item_big, mTagsLayout, false);
    labelView.setBackgroundResource(R.drawable.bg_btn_yellow_round_corner);
    labelView.setText(label);
    labelView.setTextColor(getResources().getColor(R.color.font_color_primary));
    labelView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTagsLayout.removeView(v);
        mUserLabelList.remove(label);
        updateUserLabel();
      }
    });
    mTagsLayout.addView(labelView);
  }

  private void updateUserLabel() {
    StringBuilder labelBuilder = new StringBuilder();
    for (int i = 0; i < mUserLabelList.size(); i++) {
      String label = mUserLabelList.get(i);
      labelBuilder.append(label);
      if (i != mUserLabelList.size() - 1) {
        labelBuilder.append(",");
      }
    }
    mSubscriptions.add(getComponent().loginSession().userInfoChangeBuilder().setUserLabel(labelBuilder.toString()).update("",""));
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mSubscriptions.unsubscribe();
  }
}
