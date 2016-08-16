/*
 * 文件名: LeadFragment
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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicFragment;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;

/**
 * <BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/20]
 */
public class LeadFragment extends BasicFragment {
  static final int[] LEAD_IMG_RES = new int[]{
      R.mipmap.guide1, R.mipmap.guide2,
      R.mipmap
      .guide3};
  private static final String ARG_PAGE_INDEX = "page_index";

  @Bind(R.id.btn_enter)
  View mBtnEnter;
  @Bind(R.id.lead_img)
  ImageView mImgLead;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
  Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_lead, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int index = getArguments().getInt(ARG_PAGE_INDEX);
    mBtnEnter.setVisibility(index == LEAD_IMG_RES.length - 1 ? View.VISIBLE : View.GONE);
    mImgLead.setImageResource(LEAD_IMG_RES[index]);
  }

  public static LeadFragment newInstance(int pageIndex) {
    LeadFragment fragment = new LeadFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE_INDEX, pageIndex);
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.btn_enter)
  void onClick() {
    // TODO 进入对应界面, ???调用逻辑接口去获得userId？？？
    // TODO 调用接口存储UID
    getComponent().appPreferences().saveUserId("1");
    getComponent().appPreferences().setNoGuideView();

    startActivity(new Intent(getActivity(), MainTabActivity.class));
    getActivity().finish();
  }

}
