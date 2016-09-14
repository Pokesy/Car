package com.hengrtech.carheadline.ui.discover.view;

import android.app.Dialog;
import android.content.Context;
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
import com.hengrtech.carheadline.ui.discover.CarModelLibActivity;
import com.hengrtech.carheadline.ui.discover.adapter.SerialModelsAdapter;

import java.util.List;

public class DetailParamLocationDialog extends Dialog {

    private View customView;
    private Context context;
    private TextView detailParamLocationDialogCancelTv;
    private RecyclerView serialRv;
    private OnCarModelInfoSelectedListener listener;
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
            protected void onSuccess(final List<CarSerialModel> infoModels) {
                adapter = new SerialModelsAdapter(context, infoModels);
                serialRv.setAdapter(adapter);
                adapter.setOnItemClickListener(new SerialModelsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listener.onCarModelInfoSelected(infoModels.get(position));
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


        getWindow().setWindowAnimations(R.style.AnimationDialog);
    }

    public void setOnCarModelInfoSelectedListener(OnCarModelInfoSelectedListener l) {
        listener = l;
    }

    public interface OnCarModelInfoSelectedListener {
        public void onCarModelInfoSelected(CarSerialModel csm);
    }

}
