package com.hengrtech.carheadline.ui.discover.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.CarSerialModel;
import com.hengrtech.carheadline.ui.discover.CarDetailsActivity;
import com.hengrtech.carheadline.ui.discover.CarModelLibActivity;
import com.hengrtech.carheadline.ui.discover.adapter.SerialModelsAdapter;

import java.util.List;

public class DetailParamLocationDialog extends Dialog {

    private View customView;
    private Context context;
    private TextView detailParamLocationDialogCancelTv;
    private RecyclerView serialRv;
    private OnParamLoationSelectedListener listener;
    private LinearLayout detailParamLocationDialogCancelLayout;

    private List<CarSerialModel> datas;
    private int masterId;
    private SerialModelsAdapter adapter;

    public DetailParamLocationDialog(final Context context, int masterId, AppService mInfos) {
        super(context, R.style.FullScreenDialog);
        this.context = context;
        this.masterId = masterId;
        customView = LayoutInflater.from(context).inflate(R.layout.car_serial_slide_dialog_layout, null);
        setContentView(customView);
        detailParamLocationDialogCancelTv = (TextView) customView.findViewById(R.id.detailParamLocationDialogCancelTv);
        serialRv = (RecyclerView) customView.findViewById(R.id.sub_car_model_rv);
        serialRv.setLayoutManager(new LinearLayoutManager(context));
        detailParamLocationDialogCancelLayout = (LinearLayout) customView
                .findViewById(R.id.detailParamLocationDialogCancelLayout);

        detailParamLocationDialogCancelTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DetailParamLocationDialog.this.cancel();
            }
        });
        detailParamLocationDialogCancelLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DetailParamLocationDialog.this.cancel();
            }
        });
        ((CarModelLibActivity) context).manageRpcCall(mInfos.getCarSerialList(masterId), new UiRpcSubscriber<List<CarSerialModel>>(context) {
            @Override
            protected void onSuccess(List<CarSerialModel> infoModels) {
                adapter = new SerialModelsAdapter(context, infoModels);
                serialRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new SerialModelsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, CarDetailsActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            protected void onEnd() {
            }

            @Override
            public void onApiError(RpcApiError apiError) {
                super.onApiError(apiError);
            }
        });


//		adapter = new LocationLvAdapter();
//		detailParamLoactionDialogLv.setAdapter(adapter);
//		detailParamLoactionDialogLv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				if (listener != null) {
//					listener.onParamLoationSelected(Integer.parseInt(paramLoactions.get(position).get("location")));
//				}
//			}
//		});

        getWindow().setWindowAnimations(R.style.AnimationDialog);
    }

//	private class LocationLvAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return paramLoactions.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return paramLoactions.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder;
//			if (convertView == null) {
//				holder = new ViewHolder();
//				convertView = LayoutInflater.from(context).inflate(R.layout.detail_param_location_list_item_layout,
//						null);
//				holder.textView = (TextView) convertView.findViewById(R.id.detailParamLoactionItemTv);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//			holder.textView.setText(paramLoactions.get(position).get("paramTeam"));
//			return convertView;
//		}
//
//		private class ViewHolder {
//			public TextView textView;
//		}
//	}

    public void setOnParamLoationSelectedListener(OnParamLoationSelectedListener l) {
        listener = l;
    }

    public interface OnParamLoationSelectedListener {
        public void onParamLoationSelected(int location);
    }

}
