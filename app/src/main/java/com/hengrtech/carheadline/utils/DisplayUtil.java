package com.hengrtech.carheadline.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtil {


    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = getDisplay(context);
        display.getMetrics(dm);
        return dm;
    }

    private static Display getDisplay(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getMetrics(context);
        if (dm != null) {
            return dm.widthPixels;
        }
        return 0;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getMetrics(context);
        if (dm != null) {
            return dm.heightPixels;
        }
        return 0;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
