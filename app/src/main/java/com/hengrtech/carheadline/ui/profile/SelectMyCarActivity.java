package com.hengrtech.carheadline.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.discover.CarModelLibActivity;
import com.hengrtech.carheadline.ui.profile.adapter.MarginDecoration;
import com.hengrtech.carheadline.ui.profile.adapter.MyLoveCarRvAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class SelectMyCarActivity extends BasicTitleBarActivity {
    @Bind(R.id.my_car_rv)
    RecyclerView myCarRv;
    @Bind(R.id.activity_select_my_car)
    RelativeLayout activitySelectMyCar;
    private MyLoveCarRvAdapter adapter;
    private List<UserInfo.CarListBean> carListBeenInfos = new ArrayList<>();
    private String carSerialIds;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    @Inject
    AppService mInfo;
    private AlertDialog deleteCarDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_my_car;
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.activity_my_love_car_title);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setRightTextButton(R.string.delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<String> list = adapter.getCbList();
                if (list.size() > 0) {
                    deleteCarDialog = new AlertDialog.Builder(SelectMyCarActivity.this).setMessage("是否删除" + list.size() + "已选定的爱车？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteCarDialog.cancel();
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int p) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("[");
                            for (int i = 0; i < list.size(); i++) {
                                sb.append(list.get(i));
                                if (i != (list.size() - 1))
                                    sb.append(",");
                            }
                            sb.append("]");
                            carSerialIds = sb.toString();
                            deleteMyCarService();
                            deleteCarDialog.cancel();
                        }
                    }).create();
                    deleteCarDialog.show();
                } else {
                    showShortToast("请选中你要删除的爱车");
                }
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        inject();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (carListBeenInfos != null)
            carListBeenInfos.clear();
        List<UserInfo.CarListBean> list = getComponent().loginSession().getUserInfo().getCarList();
        if (list != null && list.size() > 0) {
            carListBeenInfos.addAll(list);
        }
        carListBeenInfos.add(0, new UserInfo.CarListBean());
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        myCarRv.setHasFixedSize(true);
        myCarRv.setLayoutManager(new GridLayoutManager(this, 3));
        myCarRv.addItemDecoration(new MarginDecoration(this));
        List<UserInfo.CarListBean> list = getComponent().loginSession().getUserInfo().getCarList();
        carListBeenInfos.clear();
        if (list != null && list.size() > 0) {
            carListBeenInfos.addAll(list);
        }
        carListBeenInfos.add(0, new UserInfo.CarListBean());
        adapter = new MyLoveCarRvAdapter(this, carListBeenInfos);
        myCarRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyLoveCarRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (pos == 0) {
                    Intent intent = new Intent(SelectMyCarActivity.this, CarModelLibActivity.class);
                    intent.putExtra("flag", 1);
                    startActivity(intent);
                }
            }
        });

    }

    private void deleteMyCarService() {
        UserInfo ui = getComponent().loginSession().getUserInfo();
        manageRpcCall(mInfo.deleteMyLoveCar(ui.getMemberId() + "", ui.getToken(), carSerialIds), new UiRpcSubscriber<String>(this) {

                    @Override
                    protected void onSuccess(String string) {
                        mSubscriptions.add(getComponent().loginSession().loadUserInfo());
                        Toast.makeText(SelectMyCarActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                        List<String> list = adapter.getCbList();
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = 0; j < carListBeenInfos.size(); j++) {
                                if (list.get(i).equals(carListBeenInfos.get(j).getSerialId() + "")) {
                                    carListBeenInfos.remove(j);
                                }
                            }
                        }
                        adapter.clearData();
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    protected void onEnd() {

                    }
                }

        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
