package com.hengrtech.carheadline.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import com.jtech.adapter.RecyclerAdapter;
import com.jtech.listener.OnItemClickListener;
import com.jtech.listener.OnItemLongClickListener;
import com.jtech.listener.OnItemViewMoveListener;
import com.jtech.listener.OnItemViewSwipeListener;
import com.jtech.listener.OnLoadListener;
import com.jtech.view.JRecyclerView;
import com.jtech.view.RecyclerHolder;
import com.jtech.view.RefreshLayout;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by jiao on 2016/8/1.
 */
public class TodayActivity extends BasicTitleBarActivity implements OnItemClickListener,
    OnItemLongClickListener, RefreshLayout.OnRefreshListener, OnLoadListener,
    OnItemViewSwipeListener, OnItemViewMoveListener {
  @Bind(R.id.jrecyclerview) JRecyclerView mRecyclerView;
  @Bind(R.id.refreshlayout) RefreshLayout refreshlayout;
  @Inject AppService mInfo;
  private TodayAdapter adapter;
  private SimpleLoadFooterAdapter adapter1;
  @Override public int getLayoutId() {
    return R.layout.activity_today;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    inject();
    init();
    initdata();
  }

  private void initdata() {
    manageRpcCall(mInfo.getInfoList("1","1", "100"),
        new UiRpcSubscriber<List<InfoModel>>(this) {
          @Override protected void onSuccess(List<InfoModel> infoModels) {
            adapter.setDatas(infoModels);
            //标记为请求完成
            adapter.notifyDataSetChanged();
            //adapter1.hide();
            refreshlayout.refreshingComplete();
            mRecyclerView.setLoadCompleteState();
          }

          @Override protected void onEnd() {
            refreshlayout.refreshingComplete();
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
            refreshlayout.refreshingComplete();
          }
        });
  }

  private void inject() {
    DaggerServiceComponent.builder()
        .globalModule(new GlobalModule((CustomApp) this.getApplication()))
        .serviceModule(new ServiceModule())
        .build()
        .inject(this);
  }

  private void init() {
    //设置layoutmanager
    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

    adapter = new TodayAdapter(this);
    adapter1=new SimpleLoadFooterAdapter(this);
    mRecyclerView.setAdapter(adapter,adapter1);
    //设置适配器
    //开启滑动到底部加载更多功能
    mRecyclerView.setLoadMore(true);
    ////开启滑动删除(默认状态，可以手动设置)
    //mRecyclerView.setSwipeStart(true, this);
    ////开启长点击拖动换位(默认状态，可以手动设置)
    //mRecyclerView.setMoveUpDown(true, this);
    //设置事件
    mRecyclerView.setOnLoadListener(this);
    refreshlayout.setOnRefreshListener(this);
    mRecyclerView.setOnItemClickListener(this);
    //mRecyclerView.setOnItemLongClickListener(this);
    //主动发起下拉刷新
    refreshlayout.startRefreshing();
  }

  @Override public boolean initializeTitleBar() {
    setLeftTitleButton(R.mipmap.ic_launcher, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    setMiddleTitle(R.string.subject);
    return true;
  }

  @Override public void onItemClick(RecyclerHolder holder, View view, int position) {

  }

  @Override public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
    return false;
  }

  @Override
  public boolean onItemViewMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target) {
    return false;
  }

  @Override public void onItemViewSwipe(RecyclerView.ViewHolder viewHolder, int direction) {

  }

  @Override public void loadMore() {
    initdata();
    //refreshlayout.setRefreshing(false);
  }

  @Override public void onRefresh() {
    initdata();
  }

  public class TodayAdapter extends RecyclerAdapter<InfoModel> {
    public TodayAdapter(Activity activity) {
      super(activity);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
      return inflater.inflate(R.layout.today_list_item, parent, false);
    }

    @Override
    public void convert(RecyclerHolder holder, InfoModel item, int position) {
      holder.setText(R.id.td_content, item.getTitle());

      ImageLoader.loadOptimizedHttpImage(TodayActivity.this, item.getCoverArr().get(0))
          .placeholder(R.mipmap.ic_launcher)
          .error(R.mipmap.ic_launcher)
          .into( holder.getImageView(R.id.bg_img));

    }
  }
}
