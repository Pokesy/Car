/*
 * 文件名: ProfileDetailActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/14
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.manager.UserInfoChangedEvent;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;

/**
 * 个人详情页面<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/14]
 */
public class ProfileDetailActivity extends BasicTitleBarActivity {

    private static final int REQUEST_CODE_NICK_NAME = 3;
    private static final int REQUEST_CODE_NICK_INTRODUCTION = 4;
    private static final int REQUEST_CODE_REAL_NAME = 5;


    @Bind(R.id.phone_value)
    TextView mPhoneValueView;
    @Bind(R.id.nick_name_value)
    TextView mNickNameValueView;
    @Bind(R.id.introduction_value)
    TextView mIntroductionValueView;
    @Bind(R.id.gender_value)
    TextView mGenderValueView;
    @Bind(R.id.age_value)
    TextView mAgeValueView;
    @Bind(R.id.user_avatar_display)
    SimpleDraweeView userAvatarDisplay;
    @Bind(R.id.real_name_value)
    TextView realNameValue;
    @Bind(R.id.my_car_setting)
    RelativeLayout myCarSetting;
    @Bind(R.id.guanzhu_car_model_value)
    TextView guanzhuCarModelValue;

    private AvatarChoosePresenter mAvatarChoosePresenter;


    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    private AlertDialog mGenderChooseDialog;
    private AlertDialog mAgeChooseDialog;
    private AlertDialog mResidenceChooseDialog;
    private AlertDialog mProfessionalChooseDialog;
    private AlertDialog mEducationChooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        bindData();
        mAvatarChoosePresenter = new AvatarChoosePresenter(this);
    }

    private UserInfo getUserInfo() {
        return getComponent().loginSession().getUserInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile_detail;
    }

    @OnClick({
            R.id.avatar_setting, R.id.phone_setting, R.id.nick_name_setting, R.id.introduction_setting,
            R.id.gender_setting, R.id.age_setting, R.id.real_name_setting, R.id.my_car_setting
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar_setting:
                mAvatarChoosePresenter.showChooseAvatarDialog();
                break;
            case R.id.phone_setting:
                startActivity(new Intent(this, ResetPhoneActivity.class));
                break;
            case R.id.nick_name_setting:
                startActivityForResult(new Intent(this, NickNameActivity.class), REQUEST_CODE_NICK_NAME);
                break;
            case R.id.real_name_setting:
                startActivityForResult(new Intent(this, RealNameActivity.class), REQUEST_CODE_REAL_NAME);
                break;
            case R.id.introduction_setting:
                startActivityForResult(new Intent(this, IntroductionActivity.class),
                        REQUEST_CODE_NICK_INTRODUCTION);
                break;
            case R.id.gender_setting:
                showGenderChooseDialog();
                break;
            case R.id.age_setting:
                showAgeChooseDialog();
                break;
            case R.id.my_car_setting:
                startActivity(new Intent(this, SelectMyCarActivity.class));
                break;
        }
    }

    private void showGenderChooseDialog() {
        if (null == mGenderChooseDialog) {
            final String[] genderItems = getResources().getStringArray(R.array.gender_items);
            mGenderChooseDialog = new AlertDialog.Builder(this).setItems(genderItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSubscriptions.add(getComponent().loginSession()
                                    .userInfoChangeBuilder()
                                    .setGender(genderItems[which].equals("男") ? "1" : "0")
                                    .update("gender", genderItems[which].equals("男") ? "1" : "0"));
                        }
                    }).create();
        }
        if (mGenderChooseDialog.isShowing()) {
            return;
        }
        mGenderChooseDialog.show();
    }

    private void showAgeChooseDialog() {
        if (null == mAgeChooseDialog) {
            final String[] ageItems = getResources().getStringArray(R.array.age_items);
            mAgeChooseDialog = new AlertDialog.Builder(this).setItems(ageItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSubscriptions.add(getComponent().loginSession()
                                    .userInfoChangeBuilder()
                                    .setAgeStage(ageItems[which])
                                    .update("agestage", ageItems[which]));
                        }
                    }).create();
        }
        if (mAgeChooseDialog.isShowing()) {
            return;
        }
        mAgeChooseDialog.show();
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.activity_profile_detail_title);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_NICK_NAME:
                mSubscriptions.add(getComponent().loginSession()
                        .userInfoChangeBuilder()
                        .setNickName(data.getStringExtra(NickNameActivity.RESULT_KEY_NICK_NAME))
                        .update("nickname", data.getStringExtra(NickNameActivity.RESULT_KEY_NICK_NAME)));
                return;
            case REQUEST_CODE_REAL_NAME:
                mSubscriptions.add(getComponent().loginSession()
                        .userInfoChangeBuilder()
                        .setRealName(data.getStringExtra(RealNameActivity.RESULT_KEY_REAL_NAME))
                        .update("realname", data.getStringExtra(RealNameActivity.RESULT_KEY_REAL_NAME)));
                return;
            case REQUEST_CODE_NICK_INTRODUCTION:
                mSubscriptions.add(getComponent().loginSession()
                        .userInfoChangeBuilder()
                        .setIntroduce(data.getStringExtra(IntroductionActivity.RESULT_KEY_INTRODUCTION))
                        .update("sig", data.getStringExtra(IntroductionActivity.RESULT_KEY_INTRODUCTION)));
                return;
        }
        mAvatarChoosePresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe
    public void onUserInfoChangedEvent(UserInfoChangedEvent event) {
        bindData();
    }

    private void bindData() {
//        ImageLoader.loadOptimizedHttpImage(this, getUserInfo().getAvart()).placeholder(R.mipmap
//            .src_avatar_default_drawer).into(userAvatarDisplay);
        if (!TextUtils.isEmpty(getUserInfo().getPortrait()))
            userAvatarDisplay.setImageURI(Uri.parse(getUserInfo().getPortrait()));
        mPhoneValueView.setText(TextUtils.isEmpty(getUserInfo().getMobileNo()) ? "去设置" : getUserInfo().getMobileNo());
        mNickNameValueView.setText(
                TextUtils.isEmpty(getUserInfo().getNickName()) ? "去设置"
                        : getUserInfo().getNickName());
        realNameValue.setText(TextUtils.isEmpty(getUserInfo().getRealName()) ? "去设置"
                : getUserInfo().getRealName());
        mIntroductionValueView.setText(TextUtils.isEmpty(getUserInfo().getSignature()) ? getString(
                R.string.activity_introduction_hint) : getUserInfo().getSignature());
        mGenderValueView.setText(TextUtils.isEmpty(getUserInfo().getGender()) ? "去设置" : (getUserInfo().getGender().equals("0") ? "女" : "男"));
        mAgeValueView.setText(TextUtils.isEmpty(getUserInfo().getAgeStage()) ? "去设置" : getUserInfo().getAgeStage());
        guanzhuCarModelValue.setText(TextUtils.isEmpty(getUserInfo().getCareCarType()) ? " " : getUserInfo().getCareCarType());

    }


//    private void showUserLabel() {
//        tagsContainer.removeAllViews();
//        addTag(getString(R.string.activity_profile_detail_add_tag),
//                R.drawable.bg_btn_gray_round_corner);
//        tagsContainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ProfileDetailActivity.this, AddTagsActivity.class));
//            }
//        });
//        String userLabel = getComponent().loginSession().getUserInfo().getUserLabel();
//        if (TextUtils.isEmpty(userLabel)) {
//            return;
//        }
//        String[] labels = userLabel.split(",");
//        for (String label : labels) {
//            addTag(label, R.drawable.bg_btn_yellow_round_corner);
//        }
//    }

//    private void addTag(String label, int backGroundResource) {
//        TextView labelView =
//                (TextView) LayoutInflater.from(this).inflate(R.layout.tag_item_big, tagsContainer, false);
//        labelView.setBackgroundResource(backGroundResource);
//        labelView.setText(label);
//        labelView.setTextColor(getResources().getColor(R.color.font_color_primary));
//        tagsContainer.addView(labelView);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mAvatarChoosePresenter.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
