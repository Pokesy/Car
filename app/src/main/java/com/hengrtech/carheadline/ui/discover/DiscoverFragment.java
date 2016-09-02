package com.hengrtech.carheadline.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.discover.adapter.CategoryFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * loonggg
 */
public class DiscoverFragment extends BasicTitleBarFragment implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.dot_container)
    LinearLayout dotContainer;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @Bind(R.id.main_vp_container)
    ViewPager mainVpContainer;
    @Bind(R.id.nsv)
    NestedScrollView nsv;
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.head_layout)
    LinearLayout headLayout;
    @Bind(R.id.car_model_btn)
    TextView carModelBtn;

    @Override
    protected void onCreateViewCompleted(View view) {
        ButterKnife.bind(this, view);
        mContext = getActivity();
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 3 * 2) {
                    collapsingToolbarLayout.setTitle("      发现");
                } else {
                    collapsingToolbarLayout.setTitle(" ");
                }
            }
        });
        Fragment activityFragment = new DiscoverActivityFragment();
        Fragment priceFragment = new DiscoverPriceFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(activityFragment);
        fragments.add(priceFragment);
        CategoryFragmentPagerAdapter vpAdapter = new CategoryFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        mainVpContainer.setAdapter(vpAdapter);
        mainVpContainer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (mainVpContainer));
        carModelBtn.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discover, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.car_model_btn:
                intent.setClass(mContext, CarModelLibActivity.class);
                startActivity(intent);
                break;
        }
    }
}
