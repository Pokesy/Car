package com.jtech.listener;

import com.jtech.view.RecyclerHolder;

/**
 * 设置item触摸后可拖动item换位
 */
public interface OnItemTouchToMove {
    void itemTouchToMove(RecyclerHolder holder);
}