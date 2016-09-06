package com.hengrtech.carheadline.ui.discover.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.model.CarModel;
import com.hengrtech.carheadline.ui.discover.view.DetailParamLocationDialog;
import com.hengrtech.carheadline.ui.discover.view.ScrollGridView;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;

import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;

import static com.hengrtech.carheadline.R.id.sgv;

public class CarModelsAdapter extends BaseTurboAdapter<CarModel.MasterlistBean.MastersBean, BaseViewHolder> {
    private Context context;
    private List<CarModel.HotlistBean> hotlistBeanList;
    private OnItemClickListener listener;
    private AppService mInfo;

    public CarModelsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public CarModelsAdapter(Context context, List<CarModel.MasterlistBean.MastersBean> data, List<CarModel.HotlistBean> hotlistBeanList, AppService mInfo) {
        super(context, data);
        this.context = context;
        this.hotlistBeanList = hotlistBeanList;
        this.mInfo = mInfo;
    }

    @Override
    protected int getDefItemViewType(int position) {
        CarModel.MasterlistBean.MastersBean city = getItem(position);
        return city.getType();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new CityHolder(inflateItemView(R.layout.item_car_model, parent));
        } else if (viewType == 1) {
            return new PinnedHolder(inflateItemView(R.layout.item_pinned_header, parent));
        } else {
            return new GridViewHolder(inflateItemView(R.layout.hot_car_brand_layout, parent));
        }
    }


    @Override
    protected void convert(final BaseViewHolder holder, final CarModel.MasterlistBean.MastersBean item) {
        if (holder instanceof CityHolder) {
            ((CityHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(((CityHolder) holder).itemView, ((CityHolder) holder).getLayoutPosition());
                }
            });
            ((CityHolder) holder).car_model_name.setText(item.getName());
            ImageLoader.loadOptimizedHttpImage(context, item.getLogoUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(((CityHolder) holder).car_model_logo);
        } else if (holder instanceof PinnedHolder) {
            ((PinnedHolder) holder).city_tip.setText(item.getInitial());
        } else if (holder instanceof GridViewHolder) {
            ((GridViewHolder) holder).gv.setAdapter(new GridViewAdapter(context, hotlistBeanList));
            ((GridViewHolder) holder).gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CarModel.HotlistBean hb = (CarModel.HotlistBean) adapterView.getItemAtPosition(i);
                    DetailParamLocationDialog dialog = new DetailParamLocationDialog(context, hb.getMasterId(), mInfo);
                    dialog.show();
                }
            });
        }
    }

    public int getLetterPosition(String letter) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getType() == 1 && getData().get(i).getInitial().equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    class CityHolder extends BaseViewHolder {

        TextView car_model_name;
        ImageView car_model_logo;

        public CityHolder(View view) {
            super(view);
            car_model_name = findViewById(R.id.car_model_name);
            car_model_logo = findViewById(R.id.car_model_logo_iv);
        }
    }


    class PinnedHolder extends BaseViewHolder {

        TextView city_tip;

        public PinnedHolder(View view) {
            super(view);
            city_tip = findViewById(R.id.city_tip);
        }
    }

    class GridViewHolder extends BaseViewHolder {

        ScrollGridView gv;

        public GridViewHolder(View view) {
            super(view);
            gv = findViewById(sgv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
