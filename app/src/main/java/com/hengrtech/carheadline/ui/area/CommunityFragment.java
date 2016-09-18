package com.hengrtech.carheadline.ui.area;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.home.PraiseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/8/30.
 */
public class CommunityFragment extends BasicTitleBarFragment {
  @Bind(R.id.tablayout) TabLayout mTabLayout;
  @Bind(R.id.im_icon) ImageView imIcon;
  @Bind(R.id.vp_view) ViewPager mViewPager;

  private List<Fragment> fragments;
  private String[] titles;
  private int[] tabIcons = {
      R.drawable.icon_tab_my, R.drawable.icon_tab_shequ
  };

  @Override protected void onCreateViewCompleted(View view) {
    ButterKnife.bind(this, view);

    //mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
    fragments = new ArrayList<>();
    fragments.add(QuestionFragment.newInstance("label1"));
    fragments.add(PraiseFragment.newInstance("label2"));
    mViewPager.setCurrentItem(0);//设置当前显示标签页为第一页
    mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments));
    mTabLayout.setupWithViewPager(mViewPager);
    titles = getResources().getStringArray(R.array.community_tab_title);
    for (int i = 0; i < mTabLayout.getTabCount(); i++) {
      mTabLayout.getTabAt(i).setTag(i);
      mTabLayout.getTabAt(i).setCustomView(getTabView(i));
    }
    mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getTag().toString().equals("0")) {
          imIcon.setImageResource(R.mipmap.shouye_sousuo);
          imIcon.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
              Intent intent = new Intent(getActivity(), SendQuestionActivity.class);

              startActivity(intent);
            }
          });
        }else {
          imIcon.setImageResource(R.mipmap.ic_launcher);
          imIcon.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            }
          });
        }

        }

        @Override public void onTabUnselected (TabLayout.Tab tab){

        }

        @Override public void onTabReselected (TabLayout.Tab tab){

        }
      }

      );
      mViewPager.setOffscreenPageLimit(2);
    }

    @Override public int getLayoutId () {
      return R.layout.community_fragment;
    }

    @Override public void onDestroyView () {
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

  public View getTabView(int position) {
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
    TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
    txt_title.setText(titles[position]);
    ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
    img_title.setImageResource(tabIcons[position]);

    if (position == 0) {
      txt_title.setSelected(true);
      //imIcon.setImageResource(R.mipmap.shouye_sousuo);
    } else {
      //            txt_title.setTextColor(Color.WHITE);
      txt_title.setSelected(false);
      //imIcon.setImageResource(R.mipmap.apply_name_icon);
    }
    return view;
  }
}
