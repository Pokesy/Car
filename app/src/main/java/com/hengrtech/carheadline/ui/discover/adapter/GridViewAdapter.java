package com.hengrtech.carheadline.ui.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.model.CarModel;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;

import java.util.List;

/**
 * 作者：loonggg on 2016/9/1 16:48
 */

public class GridViewAdapter extends BaseAdapter {
    private List<CarModel.HotlistBean> hotlist;
    private Context context;

    public GridViewAdapter(Context context, List<CarModel.HotlistBean> hotlist) {
        this.hotlist = hotlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return hotlist.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.hot_car_model_gv_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.gv_logo_iv);
        TextView tv = (TextView) view.findViewById(R.id.gv_name_tv);
        tv.setText(hotlist.get(i).getName());
        ImageLoader.loadOptimizedHttpImage(context, hotlist.get(i).getLogoUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(iv);
        return view;
    }
}
