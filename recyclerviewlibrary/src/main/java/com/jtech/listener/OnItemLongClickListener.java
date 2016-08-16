package com.jtech.listener;

import android.view.View;

import com.jtech.view.RecyclerHolder;

/**
 * item长点击事件回调
 * Created by wuxubaiyang on 2016/3/7.
 */
public interface OnItemLongClickListener {
    boolean onItemLongClick(RecyclerHolder holder, View view, int position);
}