/*
 * 文件名: MainTabActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.tab;

import android.os.Bundle;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.area.AreaFragment;
import com.hengrtech.carheadline.ui.basic.tab.BaseTabActivity;
import com.hengrtech.carheadline.ui.discover.DiscoverFragment;
import com.hengrtech.carheadline.ui.home.HomeFragment;
import com.hengrtech.carheadline.ui.profile.ProfileFragment;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * [一句话功述]<BR>
 * [功 详 描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public class MainTabActivity extends BaseTabActivity {
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    @Inject
    AuthService mAuthService;

    @Override
    protected int getLayoutId() {
        return R.layout.main_tab;
    }

    @Override
    protected Class[] getContentClazzes() {
        return new Class[]{
                HomeFragment.class, DiscoverFragment.class, AreaFragment.class, ProfileFragment.class
        };
    }

    @Override
    protected int getTabIconDirection() {
        return super.getTabIconDirection();
    }

    @Override
    protected String[] getTabTitles() {
        return new String[]{"头条", "发现", "社区", "我的"};
    }

    @Override
    protected int[] getTabIcons() {
        return new int[]{
                R.drawable.icon_tab_toutiao, R.drawable.icon_tab_find, R.drawable.icon_tab_shequ, R.drawable.icon_tab_my};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        initUserInfo();
    }

    private void inject() {
        DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
                GlobalModule((CustomApp) getApplication())).build().inject(this);
    }

    private void initUserInfo() {
        if (getComponent().isLogin()) {
            mSubscriptions.add(getComponent().loginSession().loadUserInfo());
        } else {
            manageRpcCall(mAuthService.visitorLogin("1"),
                    new UiRpcSubscriber<UserInfo>(this) {


                        @Override
                        protected void onSuccess(UserInfo info) {
                            getComponent().loginSession().login(info);
                        }

                        @Override
                        protected void onEnd() {

                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getComponent().loginSession().onDestroy();
        mSubscriptions.unsubscribe();
    }
}
