package com.hengrtech.carheadline.ui.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.R;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/5 16:13
 */

public class LikeGridViewAdapter extends BaseAdapter {
    private List<String> hotlist;
    private Context context;

    public LikeGridViewAdapter(Context context, List<String> hotlist) {
        this.hotlist = hotlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return hotlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_car_like_gv, null);
        ImageView iv = (ImageView) view.findViewById(R.id.gv_logo_iv);
        TextView tv = (TextView) view.findViewById(R.id.gv_name_tv);
        return view;
    }
}