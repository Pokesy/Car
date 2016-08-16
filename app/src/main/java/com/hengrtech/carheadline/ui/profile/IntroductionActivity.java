/*
 * 文件名: IntroductionActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/31
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import rx.subscriptions.CompositeSubscription;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/31]
 */
public class IntroductionActivity extends BasicTitleBarActivity {
  public static final String RESULT_KEY_INTRODUCTION = "result_key_introduction";
  @Bind(R.id.input)
  EditText mInputView;
  @Bind(R.id.text_number)
  TextView mNumberView;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  @Override
  public int getLayoutId() {
    return R.layout.activity_introduction;
  }

  @Override
  public boolean initializeTitleBar() {
    setLeftTitleButton(R.mipmap.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    setMiddleTitle(R.string.activity_introduction_title);
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    initView();
  }

  @Override
  public void onBackPressed() {
    Intent data = new Intent();
    data.putExtra(RESULT_KEY_INTRODUCTION, mInputView.getText().toString());
    setResult(RESULT_OK, data);
    super.onBackPressed();
  }

  private void initView() {
    //final int maxLength = getResources().getInteger(R.integer.introduction_max_length);
    //mSubscriptions.add(RxTextView.afterTextChangeEvents(mInputView).subscribe(new Action1<TextViewAfterTextChangeEvent>() {
    //  @Override
    //  public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
    //    int leftCount = maxLength - textViewAfterTextChangeEvent.editable().length();
    //    mNumberView.setText(String.valueOf(leftCount));
    //  }
    //}));
    mInputView.setText(getComponent().loginSession().getUserInfo().getIntroduce());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mSubscriptions.unsubscribe();
  }
}
