/*
 * 文件名: RegisterParams
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/29
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net.params;


import com.hengrtech.carheadline.utils.MD5Encoder;

/**
 * 注册<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/29]
 */
public class RegisterParams {
  public String devId;
  public String password;
  public String mobileNo;
  public String code;
  public Integer userId;
  public String userName;

  public RegisterParams(String devId, String password, String mobileNo, String code, int
      userId, String userName) {

    this.devId = devId;
    this.password = MD5Encoder.encode(password);
    this.mobileNo = mobileNo;
    this.code = code;
    this.userId = userId;
    this.userName = userName;
  }

  public RegisterParams(String devId, String password, String mobileNo, String code, String
      userName) {

    this.devId = devId;
    this.password = MD5Encoder.encode(password);
    this.mobileNo = mobileNo;
    this.code = code;
    this.userName = userName;
  }
}
