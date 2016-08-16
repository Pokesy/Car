/*
 * 文件名: LeadActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/20
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.boot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicActivity;
import github.chenupt.springindicator.SpringIndicator;
import me.relex.circleindicator.CircleIndicator;

/**
 * 引导页<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/20]
 */
public class LeadActivity extends BasicActivity {

  @Bind(R.id.lead_pager) ViewPager mLeadPager;
  @Bind(R.id.indicator) CircleIndicator mIndicator;
  @Bind(R.id.spring_indicator) SpringIndicator mSpringIndicator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lead);
    ButterKnife.bind(this);
    initViews();
  }

  private void initViews() {
    LeadPagerAdapter pagerAdapter = new LeadPagerAdapter(getSupportFragmentManager());
    mLeadPager.setAdapter(pagerAdapter);
    mIndicator.setViewPager(mLeadPager);
    mSpringIndicator.setViewPager(mLeadPager);
  }

  private class LeadPagerAdapter extends FragmentPagerAdapter {

    public LeadPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return LeadFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return LeadFragment.LEAD_IMG_RES.length;
    }
  }
}
