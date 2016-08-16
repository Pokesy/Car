/*
 * 文件名: MD5Encoder
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/3
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.utils;

import android.support.annotation.NonNull;
import com.hengrtech.carheadline.log.Logger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/3]
 */
public class MD5Encoder {
  private static final String TAG = "MD5Encoder";
  private static String key = "qHsGce9IMTx305jygNotQ6mzWdn8C1pfibYVUk27";
  public static String encode(@NonNull String text) {
    try {

      MessageDigest md5 = MessageDigest.getInstance("MD5");
      md5.update(encryptToSHA(text).getBytes("UTF-8"));
      byte[] encryption = md5.digest();

      StringBuffer strBuf = new StringBuffer();
      for (int i = 0; i < encryption.length; i++) {
        if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
          strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
        } else {
          strBuf.append(Integer.toHexString(0xff & encryption[i]));
        }
      }
      Logger.d(TAG, "password ===> " + strBuf.toString());

      return strBuf.toString();
    } catch (NoSuchAlgorithmException e) {
      Logger.d(TAG, "", e);
      return "";
    } catch (UnsupportedEncodingException e) {
      Logger.d(TAG, "", e);
      return "";
    }
  }

  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs = hs + "0" + stmp;
      } else {
        hs = hs + stmp;
      }
    }
    return hs;
  }
  //SHA1 加密实例
  public static String encryptToSHA(String info) {
    byte[] digesta = null;
    try {
      // 得到一个SHA-1的消息摘要
      MessageDigest alga = MessageDigest.getInstance("SHA-1");
      // 添加要进行计算摘要的信息
      alga.update(info.getBytes());
      // 得到该摘要
      digesta = alga.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // 将摘要转为字符串
    String rs = byte2hex(digesta);
    return rs + key;
  }
}
