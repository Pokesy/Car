package com.hengrtech.carheadline.ui.area;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/8/1.
 */
public class QuestionFragment extends BasicTitleBarFragment {
  public static final String TYPE = "type";
  @Bind(R.id.tablayout) TabLayout mTabLayout;
  @Bind(R.id.vp_view) ViewPager mViewPager;
  private List<Fragment> fragments;
  private String[] titles;
  @Override protected void onCreateViewCompleted(View view) {
    mViewPager = (ViewPager) view.findViewById(R.id.vp_view);
    mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
    //mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
    fragments = new ArrayList<>();
    fragments.add(QusetionAllFragment.newInstance("label1"));
    fragments.add(QusetionAllFragment.newInstance("label2"));
    fragments.add(QusetionAllFragment.newInstance("label2"));
    mViewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments));
    mTabLayout.setupWithViewPager(mViewPager);
    titles = getResources().getStringArray(R.array.question_tab_title);
    for (int i = 0; i < mTabLayout.getTabCount(); i++) {
      mTabLayout.getTabAt(i).setText(titles[i]);
    }
    mViewPager.setOffscreenPageLimit(3);
  }
  public static QuestionFragment newInstance(String param1) {
    QuestionFragment fragment = new QuestionFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, param1);
    fragment.setArguments(args);
    return fragment;
  }
  @Override public int getLayoutId() {
    return R.layout.question_fragment;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
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
