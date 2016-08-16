package com.hengrtech.carheadline.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/7/18.
 */
public class HomeFragment extends BasicTitleBarFragment {

  private TabLayout mTabLayout;
  private ViewPager mViewPager;
  private int currIndex;//当前页卡编号
  private int bmpW;//横线图片宽度
  private int offset;//图片移动的偏移量
  private ImageView image;
  private LayoutInflater mInflater;
  private List<String> mTitleList = new ArrayList<>();//页卡标题集合
  private View view1, view2, view3, view4, view5;//页卡视图
  private List<View> mViewList = new ArrayList<>();//页卡视图集合
  private List<Fragment> fragments;

  @Override protected void onCreateViewCompleted(View view) {
    mViewPager = (ViewPager) view.findViewById(R.id.vp_view);
    mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
    //mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
    fragments = new ArrayList<>();
    fragments.add(InformationFragment.newInstance("label1"));
    fragments.add(PraiseFragment.newInstance("label2"));
    fragments.add(WorkFragment.newInstance("label3"));
    fragments.add(MediaFragment.newInstance("label4"));
    mViewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments));
    mTabLayout.setupWithViewPager(mViewPager);
    String[] titles = getResources().getStringArray(R.array.home_tab_title);
    for (int i = 0; i < mTabLayout.getTabCount(); i++) {
      mTabLayout.getTabAt(i).setText(titles[i]);
    }
    mViewPager.setOffscreenPageLimit(4);
  }

  @Override public int getLayoutId() {
    return R.layout.home_activity;
  }

  //ViewPager适配器
  class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
    }

    @Override public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override public int getCount() {
      return fragments.size();
    }
  }
}