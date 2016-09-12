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

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicFragment;
import com.hengrtech.carheadline.ui.login.LoginActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;

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
                    .guide3, R.mipmap.white_bg};
    private static final String ARG_PAGE_INDEX = "page_index";

    @Bind(R.id.lead_img)
    ImageView mImgLead;
    @Bind(R.id.have_car_family_tv)
    TextView haveCarFamilyTv;
    @Bind(R.id.want_buy_car_tv)
    TextView wantBuyCarTv;
    @Bind(R.id.rand_see_tv)
    TextView randSeeTv;
    @Bind(R.id.full_layout)
    LinearLayout fullLayout;
    @Inject
    AuthService mAuthService;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = getArguments().getInt(ARG_PAGE_INDEX);
        fullLayout.setVisibility(index == LEAD_IMG_RES.length - 1 ? View.VISIBLE : View.GONE);
        mImgLead.setImageResource(LEAD_IMG_RES[index]);
        haveCarFamilyTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        haveCarFamilyTv.getPaint().setAntiAlias(true);//抗锯齿
        wantBuyCarTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        wantBuyCarTv.getPaint().setAntiAlias(true);//抗锯齿
        randSeeTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        randSeeTv.getPaint().setAntiAlias(true);//抗锯齿
        inject();
    }

    public static LeadFragment newInstance(int pageIndex) {
        LeadFragment fragment = new LeadFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_INDEX, pageIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.have_car_family_tv, R.id.want_buy_car_tv, R.id.rand_see_tv})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.have_car_family_tv:
                initService("1");
                break;
            case R.id.want_buy_car_tv:
                initService("2");
                break;
            case R.id.rand_see_tv:
                initService("3");
                break;
        }
    }

    private void inject() {
        DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
                GlobalModule((CustomApp) getActivity().getApplication())).build().inject(this);
    }

    public void initService(final String tag) {
        if (getComponent().isLogin()) {
            mSubscriptions.add(getComponent().loginSession().loadUserInfo());
        } else {
            manageRpcCall(mAuthService.visitorLogin(tag),
                    new UiRpcSubscriber<UserInfo>(mContext) {


                        @Override
                        protected void onSuccess(UserInfo info) {
                            getComponent().appPreferences().setNoGuideView();
                            getComponent().loginSession().login(info);
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                        @Override
                        protected void onEnd() {

                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getComponent().loginSession().onDestroy();
        mSubscriptions.unsubscribe();
    }
}
