package com.hengrtech.carheadline.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.CarModel;
import com.hengrtech.carheadline.net.model.CarSerialModel;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.MyLoveCarParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.discover.adapter.CarModelsAdapter;
import com.hengrtech.carheadline.ui.discover.adapter.PinnedHeaderDecoration;
import com.hengrtech.carheadline.ui.discover.view.DetailParamLocationDialog;
import com.hengrtech.carheadline.ui.discover.view.WaveSideBarView;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class CarModelLibActivity extends BasicTitleBarActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.side_view)
    WaveSideBarView sideView;
    @Bind(R.id.activity_car_model_lib)
    LinearLayout activityCarModelLib;
    @Inject
    AppService mInfo;
    private CarModelsAdapter adapter;

    private List<CarModel.MasterlistBean.MastersBean> masters = new ArrayList<>();
    private int scrollY = 0;
    private boolean isAddDecoration;
    public  int flag = 0;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_model_lib;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        flag = this.getIntent().getIntExtra("flag", 0);
        inject();
        initView();
        initService();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(CarModelLibActivity.this));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY = scrollY + dy;
                if (scrollY < 10) {
                    if (isAddDecoration) {
                        isAddDecoration = false;
                        recyclerView.removeItemDecoration(decoration);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    if (!isAddDecoration) {
                        isAddDecoration = true;
                        recyclerView.addItemDecoration(decoration);
                    }
                }
            }
        });
        recyclerView.addItemDecoration(decoration);
        sideView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);

                if (pos != -1) {
                    recyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) recyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });

    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    private void initService() {
        manageRpcCall(mInfo.getCarMasterList(),
                new UiRpcSubscriber<CarModel>(this) {
                    @Override
                    protected void onSuccess(CarModel infoModels) {
                        sortCarModelsData(infoModels.getMasterlist());
                        adapter = new CarModelsAdapter(CarModelLibActivity.this, masters, infoModels.getHotlist(), mInfo,flag);
                        recyclerView.setAdapter(adapter);
                        sideView.setVisibility(View.VISIBLE);
                        adapter.setOnItemClickListener(new CarModelsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                final DetailParamLocationDialog dialog = new DetailParamLocationDialog(CarModelLibActivity.this, masters.get(position).getMasterId(), mInfo);
                                dialog.show();
                                dialog.setOnCarModelInfoSelectedListener(new DetailParamLocationDialog.OnCarModelInfoSelectedListener() {
                                    @Override
                                    public void onCarModelInfoSelected(CarSerialModel csm) {
                                        if (flag == 0) {
                                            startActivity(new Intent(CarModelLibActivity.this, CarDetailsActivity.class));
                                        } else {
                                            addMyLoveCarService(csm);
                                        }
                                        dialog.cancel();
                                    }
                                });
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
    }

    private void sortCarModelsData(List<CarModel.MasterlistBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CarModel.MasterlistBean.MastersBean mb = new CarModel.MasterlistBean.MastersBean();
            mb.setInitial(list.get(i).getLetter());
            mb.setName("");
            mb.setType(1);
            masters.add(mb);
            List<CarModel.MasterlistBean.MastersBean> mbList = list.get(i).getMasters();
            for (int j = 0; j < mbList.size(); j++) {
                mbList.get(j).setType(0);
                masters.add(mbList.get(j));
            }
        }
        CarModel.MasterlistBean.MastersBean mb = new CarModel.MasterlistBean.MastersBean();
        mb.setType(-1);
        masters.add(0, mb);
    }

    public void addMyLoveCarService(CarSerialModel csm) {
        UserInfo ui = getComponent().loginSession().getUserInfo();
        manageRpcCall(mInfo.addMyLoveCar(ui.getMemberId() + "", ui.getToken(), new MyLoveCarParams(csm.getSerialId())), new UiRpcSubscriber<String>(this) {

            @Override
            protected void onSuccess(String string) {
                mSubscriptions.add(getComponent().loginSession().loadUserInfo());
                Toast.makeText(CarModelLibActivity.this, "添加成功", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onEnd() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}
