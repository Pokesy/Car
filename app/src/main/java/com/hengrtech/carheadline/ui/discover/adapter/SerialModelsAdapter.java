package com.hengrtech.carheadline.ui.discover.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.model.CarSerialModel;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;

import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;


public class SerialModelsAdapter extends BaseTurboAdapter<CarSerialModel, BaseViewHolder> {
    private Context context;
    private OnItemClickListener listener;

    public SerialModelsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public SerialModelsAdapter(Context context, List<CarSerialModel> data) {
        super(context, data);
        this.context = context;
    }


    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new SerialHolder(inflateItemView(R.layout.car_serial_rv_item, parent));
    }


    @Override
    protected void convert(final BaseViewHolder holder, CarSerialModel item) {
        if (holder instanceof SerialHolder) {
            ((SerialHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClick(((SerialHolder) holder).itemView, ((SerialHolder) holder).getLayoutPosition());
                }
            });
            ((SerialHolder) holder).brand_name_tv.setText(item.getSerialName());
            ((SerialHolder) holder).price_status_tv.setText(item.getDealerPrice());
            ImageLoader.loadOptimizedHttpImage(context, item.getPicture())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(((SerialHolder) holder).car_brand_logo);
        }
    }


    class SerialHolder extends BaseViewHolder {

        TextView brand_name_tv;
        ImageView car_brand_logo;
        TextView price_status_tv;

        public SerialHolder(View view) {
            super(view);
            brand_name_tv = findViewById(R.id.brand_name_tv);
            car_brand_logo = findViewById(R.id.brand_logo_iv);
            price_status_tv = findViewById(R.id.price_status_tv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
