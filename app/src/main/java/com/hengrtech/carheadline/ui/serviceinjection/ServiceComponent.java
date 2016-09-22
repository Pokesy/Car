/*
 * 文件名: AppServiceComponent
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.serviceinjection;

import com.hengrtech.carheadline.manager.LoginSession;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.AuthService;
import com.hengrtech.carheadline.net.RpcCallManager;
import com.hengrtech.carheadline.net.UserService;
import com.hengrtech.carheadline.ui.area.AreaDetailActivity;
import com.hengrtech.carheadline.ui.area.ComplaintFragment;
import com.hengrtech.carheadline.ui.area.QuestionFragment;
import com.hengrtech.carheadline.ui.area.QusetionAllFragment;
import com.hengrtech.carheadline.ui.area.SendQuestionActivity;
import com.hengrtech.carheadline.ui.area.TucaoFragment;
import com.hengrtech.carheadline.ui.boot.LeadFragment;
import com.hengrtech.carheadline.ui.discover.CarModelLibActivity;
import com.hengrtech.carheadline.ui.home.InformationFragment;
import com.hengrtech.carheadline.ui.home.MediaFragment;
import com.hengrtech.carheadline.ui.home.NewsDetailActivity;
import com.hengrtech.carheadline.ui.home.NewsFragment;
import com.hengrtech.carheadline.ui.home.PraiseFragment;
import com.hengrtech.carheadline.ui.home.TodayActivity;
import com.hengrtech.carheadline.ui.home.WorkFragment;
import com.hengrtech.carheadline.ui.home.ZhuanTiActivity;
import com.hengrtech.carheadline.ui.login.LoginWithPasswordFragment;
import com.hengrtech.carheadline.ui.login.LoginWithVerifyCodeFragment;
import com.hengrtech.carheadline.ui.login.RegisterActivity;
import com.hengrtech.carheadline.ui.login.SetPasswordActivity;
import com.hengrtech.carheadline.ui.profile.AddTagsActivity;
import com.hengrtech.carheadline.ui.profile.AvatarChoosePresenter;
import com.hengrtech.carheadline.ui.profile.MyCollectActivity;
import com.hengrtech.carheadline.ui.profile.ResetPhoneActivity;
import com.hengrtech.carheadline.ui.profile.SelectMyCarActivity;
import com.hengrtech.carheadline.ui.tab.MainTabActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * 服务器接口 Component<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
@Singleton @Component(modules = ServiceModule.class) public interface ServiceComponent {
  RpcCallManager rpcCallManager();

  AppService appService();

  UserService userService();

  AuthService authService();

  void inject(InformationFragment fragment);

  void inject(PraiseFragment fragment);

  void inject(WorkFragment fragment);

  void inject(MediaFragment fragment);

  void inject(TodayActivity activity);

  void inject(AddTagsActivity activity);

  void inject(ResetPhoneActivity activity);

  void inject(AvatarChoosePresenter presenter);

  void inject(LoginSession loginSession);

  void inject(LoginWithVerifyCodeFragment fragment);

  void inject(LoginWithPasswordFragment fragment);

  void inject(NewsDetailActivity activity);

  void inject(ZhuanTiActivity activity);

  void inject(ComplaintFragment fragment);

  void inject(QuestionFragment fragment);

  void inject(CarModelLibActivity activity);

  void inject(RegisterActivity activity);

  void inject(QusetionAllFragment fragment);

  void inject(SetPasswordActivity activity);

  void inject(MainTabActivity activity);

  void inject(LeadFragment fragment);

  void inject(AreaDetailActivity activity);

  void inject(SendQuestionActivity activity);

  void inject(SelectMyCarActivity activity);

  void inject(TucaoFragment fragment);

  void inject(NewsFragment fragment);

  void inject(MyCollectActivity activity);
}
