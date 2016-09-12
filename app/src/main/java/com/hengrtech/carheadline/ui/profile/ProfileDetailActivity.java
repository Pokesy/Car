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

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.manager.UserInfoChangedEvent;
import com.hengrtech.carheadline.net.constant.NetConstant;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.squareup.otto.Subscribe;
import java.util.Calendar;
import org.apmem.tools.layouts.FlowLayout;
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

  @Bind(R.id.tags_container) FlowLayout tagsContainer;

  @Bind(R.id.phone_value) TextView mPhoneValueView;
  @Bind(R.id.nick_name_value) TextView mNickNameValueView;
  @Bind(R.id.introduction_value) TextView mIntroductionValueView;
  @Bind(R.id.gender_value) TextView mGenderValueView;
  @Bind(R.id.age_value) TextView mAgeValueView;
  @Bind(R.id.user_avatar_display) SimpleDraweeView userAvatarDisplay;

  private AvatarChoosePresenter mAvatarChoosePresenter;

  private UserInfo mUserInfo;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  private AlertDialog mGenderChooseDialog;
  private DatePickerDialog mBirthdayChooseDialog;
  private AlertDialog mBirthplaceChooseDialog;
  private AlertDialog mResidenceChooseDialog;
  private AlertDialog mProfessionalChooseDialog;
  private AlertDialog mEducationChooseDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    bindData();
    mAvatarChoosePresenter = new AvatarChoosePresenter(this);
  }

  private UserInfo getUserInfo() {
    return getComponent().loginSession().getUserInfo();
  }

  @Override public int getLayoutId() {
    return R.layout.activity_profile_detail;
  }

  @OnClick({
      R.id.avatar_setting, R.id.phone_setting, R.id.nick_name_setting, R.id.introduction_setting,
      R.id.gender_setting, R.id.age_setting
  }) public void onClick(View view) {
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
      case R.id.introduction_setting:
        startActivityForResult(new Intent(this, IntroductionActivity.class),
            REQUEST_CODE_NICK_INTRODUCTION);
        break;
      case R.id.gender_setting:
        showGenderChooseDialog();
        break;
      case R.id.age_setting:
        showBirthChooseDialog();
        break;
    }
  }

  private void showGenderChooseDialog() {
    if (null == mGenderChooseDialog) {
      final String[] genderItems = getResources().getStringArray(R.array.gender_items);
      mGenderChooseDialog = new AlertDialog.Builder(this).setItems(genderItems,
          new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
              mSubscriptions.add(getComponent().loginSession()
                  .userInfoChangeBuilder()
                  .setGender(genderItems[which])
                  .update());
            }
          }).create();
    }
    if (mGenderChooseDialog.isShowing()) {
      return;
    }
    mGenderChooseDialog.show();
  }

  @Override public boolean initializeTitleBar() {
    setMiddleTitle(R.string.activity_profile_detail_title);
    setLeftTitleButton(R.mipmap.icon_title_bar_back, new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    return true;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_CODE_NICK_NAME:
        mSubscriptions.add(getComponent().loginSession()
            .userInfoChangeBuilder()
            .setUserName(data.getStringExtra(NickNameActivity.RESULT_KEY_NICK_NAME))
            .update());
        return;
      case REQUEST_CODE_NICK_INTRODUCTION:
        mSubscriptions.add(getComponent().loginSession()
            .userInfoChangeBuilder()
            .setIntroduce(data.getStringExtra(IntroductionActivity.RESULT_KEY_INTRODUCTION))
            .update());
        return;
    }
    mAvatarChoosePresenter.onActivityResult(requestCode, resultCode, data);
  }

  @Subscribe public void onUserInfoChangedEvent(UserInfoChangedEvent event) {
    bindData();
  }

  private void bindData() {
    //ImageLoader.loadOptimizedHttpImage(this, mUserInfo.getAvart()).placeholder(R.mipmap
    //    .src_avatar_default_drawer).into(userAvatarDisplay);
    userAvatarDisplay.setImageURI(Uri.parse(NetConstant.BASE_URL_LOCATION+getUserInfo().getPortrait()));
    mPhoneValueView.setText(getUserInfo().getMobileNo());
    mNickNameValueView.setText(
        TextUtils.isEmpty(getUserInfo().getRealName()) ? getUserInfo().getMobileNo()
            : getUserInfo().getRealName());
    mIntroductionValueView.setText(TextUtils.isEmpty(getUserInfo().getSignature()) ? getString(
        R.string.activity_introduction_hint) : getUserInfo().getSignature());
    mGenderValueView.setText(getUserInfo().getGender());
    //mAgeValueView.setText(computeAge(getUserInfo().getCareCarType()));

    showUserLabel();
  }

  private String computeAge(String birthday) {
    if (TextUtils.isEmpty(birthday)) {
      return "";
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());

    Calendar calendar1 = Calendar.getInstance();
    String[] date = birthday.split("-");
    calendar1.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));

    return String.valueOf(calendar.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR));
  }

  private void showBirthChooseDialog() {
    if (null == mBirthdayChooseDialog) {
      String birthday = getUserInfo().getBirthday();
      String[] birthDate = !TextUtils.isEmpty(birthday) ? birthday.split("-") : null;
      Calendar today = Calendar.getInstance();
      int year = today.get(Calendar.YEAR);
      int month = today.get(Calendar.MONTH);
      int day = today.get(Calendar.DAY_OF_MONTH);
      if (null != birthDate) {
        year = birthDate.length > 0 && !TextUtils.isEmpty(birthDate[0]) ? Integer.parseInt(
            birthDate[0]) : year;
        month = birthDate.length > 1 && !TextUtils.isEmpty(birthDate[1]) ? Integer.parseInt(
            birthDate[1]) : month;
        day = birthDate.length > 2 && !TextUtils.isEmpty(birthDate[2]) ? Integer.parseInt(
            birthDate[2]) : day;
      }

      mBirthdayChooseDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
          mSubscriptions.add(getComponent().loginSession()
              .userInfoChangeBuilder()
              .setBirthday(year + "-" + monthOfYear + "-" + dayOfMonth)
              .update());
        }
      }, year, month, day);
      mBirthdayChooseDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override public void onCancel(DialogInterface dialog) {
          mBirthdayChooseDialog = null;
        }
      });
      mBirthdayChooseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override public void onDismiss(DialogInterface dialog) {
          mBirthdayChooseDialog = null;
        }
      });
      mBirthdayChooseDialog.getDatePicker().setMaxDate(today.getTimeInMillis());
      //Calendar maxDay = Calendar.getInstance();
      //maxDay.set(Calendar.YEAR, today.get(Calendar.YEAR) - 100);
      //maxDay.set(Calendar.MONTH, today.get(Calendar.MONTH));
      //maxDay.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
      //mBirthdayChooseDialog.getDatePicker().setMinDate(maxDay.getTimeInMillis());
    }
    if (mBirthdayChooseDialog.isShowing()) {
      return;
    }
    mBirthdayChooseDialog.show();
  }

  private void showUserLabel() {
    tagsContainer.removeAllViews();
    addTag(getString(R.string.activity_profile_detail_add_tag),
        R.drawable.bg_btn_gray_round_corner);
    tagsContainer.getChildAt(0).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(ProfileDetailActivity.this, AddTagsActivity.class));
      }
    });
    String userLabel = getComponent().loginSession().getUserInfo().getUserLabel();
    if (TextUtils.isEmpty(userLabel)) {
      return;
    }
    String[] labels = userLabel.split(",");
    for (String label : labels) {
      addTag(label, R.drawable.bg_btn_yellow_round_corner);
    }
  }

  private void addTag(String label, int backGroundResource) {
    TextView labelView =
        (TextView) LayoutInflater.from(this).inflate(R.layout.tag_item_big, tagsContainer, false);
    labelView.setBackgroundResource(backGroundResource);
    labelView.setText(label);
    labelView.setTextColor(getResources().getColor(R.color.font_color_primary));
    tagsContainer.addView(labelView);
  }


  @Override protected void onDestroy() {
    super.onDestroy();
    mAvatarChoosePresenter.onDestroy();
    mSubscriptions.unsubscribe();
  }
}
