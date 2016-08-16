package com.jtech.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.view.RecyclerHolder;
import com.jtech.listener.OnItemTouchToMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * JRecyclerView适配器
 *
 * @param <T>
 * @author wuxubaiyang
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {
    /**
     * Activity对象
     */
    private Activity activity;
    /**
     * 数据集合
     */
    private List<T> realDatas;
    /**
     * 起始页码
     */
    private int startPage = 1;
    /**
     * 单页数据量
     */
    private int displayNumber = 20;
    /**
     * 当前页码
     */
    private int currentPage = startPage;
    /**
     * item触摸后可拖动监听
     */
    private OnItemTouchToMove onItemTouchToMove;

    /**
     * 主构造
     *
     * @param activity Activity对象
     */
    public RecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    /**
     * 得到Activity对象
     *
     * @return
     */
    public Activity getActivity() {
        return activity;
    }

    public void setOnItemTouchToMove(OnItemTouchToMove onItemTouchToMove) {
        this.onItemTouchToMove = onItemTouchToMove;
    }

    /**
     * 添加一个视图的监听为触摸可拖动换位
     *
     * @param holder 视图持有类
     * @param viewId 视图的id
     */
    public void addItemTouchToMove(RecyclerHolder holder, int viewId) {
        addItemTouchToMove(holder, holder.getView(viewId));
    }

    /**
     * 添加一个视图的监听为触摸可拖动换位
     *
     * @param holder 视图持有类
     * @param view   视图对象
     */
    public void addItemTouchToMove(final RecyclerHolder holder, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (null != onItemTouchToMove) {
                    onItemTouchToMove.itemTouchToMove(holder);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 获取item对象
     *
     * @param position 位置
     */
    public T getItem(int position) {
        if (null != realDatas && realDatas.size() > position) {
            return realDatas.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (null != realDatas) {
            return realDatas.size();
        }
        return 0;
    }

    /**
     * 根据标记获取页码（刷新或者加载更多）
     *
     * @param loadMore 是否为加载更多
     * @return 页码
     */
    public int getPage(boolean loadMore) {
        if (loadMore) {
            return nextPage();
        } else {
            return startPage;
        }
    }

    /**
     * 根据标记设置页码
     *
     * @param loadMore 是否为加载更多
     * @return 页码
     */
    public int setpage(boolean loadMore) {
        if (loadMore) {
            return pagePlus();
        } else {
            return resetPage();
        }
    }

    /**
     * 获取起始页码
     *
     * @return 起始页码
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * 得到下一页的页码
     *
     * @return 下一页页码
     */
    public int nextPage() {
        return currentPage + 1;
    }

    /**
     * 页码增加
     *
     * @return 增加后的页码
     */
    public int pagePlus() {
        return currentPage++;
    }

    /**
     * 将页码重置
     *
     * @return 重置后的页码
     */
    public int resetPage() {
        this.currentPage = startPage;
        return currentPage;
    }

    public int getDisplayNumber() {
        return displayNumber;
    }

    /**
     * 设置起始页码
     *
     * @param startPage 起始页码
     */
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    /**
     * 设置单页数据量
     *
     * @param displayNumber 单页数据量
     */
    public void setDisplayNumber(int displayNumber) {
        this.displayNumber = displayNumber;
    }

    /**
     * 获取真实数据
     *
     * @return
     */
    public List<T> getRealDatas() {
        return realDatas;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View root = createView(inflater, parent, viewType);
        return new RecyclerHolder(root);
    }

    /**
     * 创建item的根视图
     *
     * @param inflater LayoutInflater
     * @param parent   父视图
     * @param viewType 视图类型
     * @return item根视图
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        convert(holder, getItem(position), position);
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public abstract void convert(RecyclerHolder holder, T item, int position);

    /**
     * 设置数据
     *
     * @param datas 数据
     */
    public void setDatas(Collection<T> datas) {
        setDatas(datas, false);
    }

    /**
     * 设置数据
     *
     * @param datas    数据
     * @param loadMore 是否为加载更多
     * @return 适配器对象
     */
    public void setDatas(Collection<T> datas, boolean loadMore) {
        if (loadMore) {
            if (null != datas && null != realDatas) {
                realDatas.addAll(datas);
            }
        } else {
            realDatas = new ArrayList<T>(datas);
        }
        setpage(loadMore);
        notifyDataSetChanged();
    }

    /**
     * 添加数据集合
     *
     * @param datas 数据
     */
    public void addDatas(Collection<T> datas) {
        addDatas(datas.size(), datas);
    }

    /**
     * 添加数据集合
     *
     * @param index 位置
     * @param datas 数据
     */
    public void addDatas(int index, Collection<T> datas) {
        if (null != datas && null != realDatas) {
            realDatas.addAll(index, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加单条数据
     *
     * @param data 数据
     * @return 适配器对象
     */
    public void addData(T data) {
        if (null != data && null != realDatas) {
            addData(realDatas.size(), data);
        }
    }

    /**
     * 添加数据到指定位置
     *
     * @param index 指定位置
     * @param data  数据
     * @return 适配器对象
     */
    public void addData(int index, T data) {
        if (null != data && null != realDatas) {
            realDatas.add(index, data);
            notifyItemInserted(index);
        }
    }

    /**
     * 移除数据
     *
     * @param position 数据位置
     * @return 适配器对象
     */
    public void removeData(int position) {
        if (null != realDatas && realDatas.size() > position) {
            realDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 移动数据
     *
     * @param fromPosition 原始位置
     * @param toPosition   目标位置
     */
    public void moveData(int fromPosition, int toPosition) {
        Collections.swap(realDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 更新某一项数据
     *
     * @param position 下标
     * @param data     数据对象
     */
    public void updataItem(int position, T data) {
        if (null != data && null != realDatas && realDatas.size() > position) {
            realDatas.set(position, data);
            notifyItemChanged(position);
        }
    }
}