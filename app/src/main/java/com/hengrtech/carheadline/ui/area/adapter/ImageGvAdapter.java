package com.hengrtech.carheadline.ui.area.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.utils.BitmapUtiles;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/26 15:40
 */

public class ImageGvAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ImageGvAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_sendimage_gv, null);
        ImageView iv = (ImageView) view.findViewById(R.id.item_iv);
        iv.setImageBitmap(BitmapUtiles.loadBitmap(list.get(i),2));
        return view;
    }
}
