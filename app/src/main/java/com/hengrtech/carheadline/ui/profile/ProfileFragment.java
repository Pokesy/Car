package com.hengrtech.carheadline.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.login.LoginActivity;
import com.hengrtech.carheadline.ui.profile.view.SharePopup;
import com.hengrtech.carheadline.utils.imageloader.CircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jiao on 2016/7/18.
 */
public class ProfileFragment extends BasicTitleBarFragment {

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.un_login_container)
    LinearLayout unLoginContainer;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.btn_red_bag)
    LinearLayout btnRedBag;
    @Bind(R.id.qiandao)
    TextView qiandao;
    @Bind(R.id.invite)
    RelativeLayout invite;
    @Bind(R.id.setting)
    RelativeLayout setting;
    @Bind(R.id.all_layout)
    ScrollView allLayout;
    @Bind(R.id.collect_layout)
    RelativeLayout collectLayout;
    private UserInfo mUserInfo;
    private Context mContext;

    @Override
    protected void onCreateViewCompleted(View view) {
        mContext = getActivity();
        ButterKnife.bind(this, view);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.avatar, R.id.invite, R.id.setting, R.id.collect_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                if (!TextUtils.isEmpty(mUserInfo.getMobileNo())) {
                    startActivity(new Intent(getActivity(), ProfileDetailActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.invite:
                SharePopup popup = new SharePopup(getActivity());
                popup.showPopup(allLayout);
                popup.setOnSharePopupListener(new SharePopup.SharePopupOnClickListener() {
                    @Override
                    public void obtainMessage(int flag) {
                        showShortToast(flag + "");
                    }
                });
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.collect_layout:
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserInfo = getComponent().loginSession().getUserInfo();
        if (mUserInfo != null && mUserInfo.getPortrait() != null) {
            Glide.with(mContext).load(mUserInfo.getPortrait()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleTransform(mContext))
                    .placeholder(R.drawable.image_placeholder_error)
                    .error(R.mipmap.photo_no)
                    .crossFade()
                    .into(avatar);
        }
        score.setText(mUserInfo.getScore()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
