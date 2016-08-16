/*
 * 文件名: HomeActivity
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/15
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.boot;

import android.content.Intent;
import android.os.Bundle;
import com.hengrtech.carheadline.ui.basic.BasicActivity;
import com.hengrtech.carheadline.ui.login.LoginActivity;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;

/**
 * 主页<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/15]
 */
public class BootActivity extends BasicActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dispatchForwardUi();
  }


  private void dispatchForwardUi() {
    Intent intent;
    if (getComponent().appPreferences().showGuideView()) {
      intent = new Intent(this, LeadActivity.class);
    } else if (getComponent().isLogin()) {
      intent = new Intent(this, MainTabActivity.class);
    } else {
      intent = new Intent(this, LoginActivity.class);
    }
    startActivity(intent);
    finish();
  }

}
