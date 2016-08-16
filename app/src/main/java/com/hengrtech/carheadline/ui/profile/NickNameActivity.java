/*
 * 文件名: NickNameActivity
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/30]
 */
public class NickNameActivity extends BasicTitleBarActivity {
  public static final String RESULT_KEY_NICK_NAME = "result_nick_name";

  @Bind(R.id.nick_name_input) AppCompatEditText mNickNameInputView;

  @Override
  public int getLayoutId() {
    return R.layout.activity_nick_name;
  }

  @Override
  public boolean initializeTitleBar() {
    setMiddleTitle(R.string.activity_nick_name_title);
    setLeftTitleButton(R.mipmap.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
    mNickNameInputView.setText(getComponent().loginSession().getUserInfo().getUserName());
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(RESULT_KEY_NICK_NAME, mNickNameInputView.getText().toString());
    setResult(RESULT_OK, intent);
    super.onBackPressed();
  }
}
