package com.hengrtech.carheadline.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;

/**
 * Created by jiao on 2016/7/18.
 */
public class ProfileFragment extends BasicTitleBarFragment {

  @Bind(R.id.avatar) ImageView avatar;
  @Bind(R.id.un_login_container) LinearLayout unLoginContainer;
  @Bind(R.id.score) TextView score;
  @Bind(R.id.message) TextView message;
  @Bind(R.id.btn_red_bag) LinearLayout btnRedBag;
  @Bind(R.id.qiandao) TextView qiandao;
  @Bind(R.id.collect) RelativeLayout collect;
  @Bind(R.id.invite) RelativeLayout invite;
  @Bind(R.id.setting) RelativeLayout setting;

  @Override protected void onCreateViewCompleted(View view) {

  }

  @Override public int getLayoutId() {
    return R.layout.fragment_profile;
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

  @OnClick({ R.id.avatar, R.id.collect, R.id.invite, R.id.setting })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.avatar:
        startActivity(new Intent(getActivity(), ProfileDetailActivity.class));
        break;
      case R.id.collect:
        break;
      case R.id.invite:
        break;
      case R.id.setting:
        startActivity(new Intent(getActivity(), SettingActivity.class));
        break;
    }
  }
}
