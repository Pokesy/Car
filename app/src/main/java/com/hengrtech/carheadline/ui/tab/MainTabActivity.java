/*
 * 文件名: MainTabActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.tab;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.area.AreaFragment;
import com.hengrtech.carheadline.ui.basic.tab.BaseTabActivity;
import com.hengrtech.carheadline.ui.discover.DiscoverFragment;
import com.hengrtech.carheadline.ui.home.HomeFragment;
import com.hengrtech.carheadline.ui.profile.ProfileFragment;

/**
 * [一句话功述]<BR>
 * [功 详 描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public class MainTabActivity extends BaseTabActivity {

  @Override protected int getLayoutId() {
    return R.layout.main_tab;
  }

  @Override protected Class[] getContentClazzes() {
    return new Class[] {
        HomeFragment.class, DiscoverFragment.class, AreaFragment.class, ProfileFragment.class
    };
  }

  @Override protected String[] getTabTitles() {
    return new String[] { "头条", "发现", "社区", "我的" };
  }

  @Override protected int[] getTabIcons() {
    return new int[] {
        R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher
    };
  }

}
