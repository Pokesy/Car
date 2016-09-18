package com.hengrtech.carheadline.utils;

import android.os.Environment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/8/22.
 */
public class Url {
  public static boolean IMGFLAG = false;
  //图片本地保存位置
  public static String IMGLOCAL = Environment.getExternalStorageDirectory() + "/tox/photos/";
  public static String SAVEIMAGE = Environment.getExternalStorageDirectory() + "/tox/saveImage/";
  //临时保存上传压缩后的图片
  public static String UPLOADTEMPORARYPATH = Environment.getExternalStorageDirectory() + "/tox/cache/";
  public static String CHECKUPDATE = "/public/checkUpdate";
  public static List<String> getDeleteFilesPath() {
    List<String> list = new ArrayList<String>();
    list.add(Environment.getExternalStorageDirectory() + "/tox/cache");
    list.add(Environment.getExternalStorageDirectory() + "/tox/photos");
    return list;
  }
  public static String UPLOADIMGURL = "http://192.168.15.8:8088/carheadline/api/file/upload";
}
