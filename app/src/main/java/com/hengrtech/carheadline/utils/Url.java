package com.hengrtech.carheadline.utils;

import android.os.Environment;

/**
 * Created by jiao on 2016/8/22.
 */
public class Url {
  public static boolean IMGFLAG = false;
  //图片本地保存位置
  public static String IMGLOCAL = Environment.getExternalStorageDirectory() + "/tox/photos/";
  public static String SAVEIMAGE = Environment.getExternalStorageDirectory() + "/tox/saveImage/";
}
