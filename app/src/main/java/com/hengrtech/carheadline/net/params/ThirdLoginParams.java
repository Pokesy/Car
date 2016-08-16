/*
 * 文件名: BibiParams
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/11
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net.params;

/**
 * 获取哔哔数据<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/11]
 */
public class ThirdLoginParams {
  String authId;
  String nickName;

  public ThirdLoginParams(String authId, String nickName) {
    this.authId = authId;
    this.nickName = nickName;
  }
}
