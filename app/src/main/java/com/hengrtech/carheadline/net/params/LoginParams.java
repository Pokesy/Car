/*
 * 文件名: LoginParams
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/3
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net.params;

import com.hengrtech.carheadline.utils.MD5Encoder;

/**
 * 登录参数<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/3]
 */
public class LoginParams {
  public String mobileNo;
  public String password;

  public LoginParams(String mobileNo, String password) {
    this.mobileNo = mobileNo;
    this.password = MD5Encoder.encode(password);
  }
}
