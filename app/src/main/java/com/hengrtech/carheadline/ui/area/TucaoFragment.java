package com.hengrtech.carheadline.ui.area;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.QuestionModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.home.adapter.SimpleLoadFooterAdapter;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.DateHelper;
import com.hengrtech.carheadline.utils.ImagePagerActivity;
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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by jiao on 2016/8/1.
 */
public class TucaoFragment extends BasicTitleBarFragment
    implements OnItemClickListener, OnItemLongClickListener, RefreshLayout.OnRefreshListener,
    OnLoadListener, OnItemViewSwipeListener, OnItemViewMoveListener {
  public static final String TYPE = "type";
  @Bind(R.id.jrecyclerview) JRecyclerView mRecyclerView;
  @Bind(R.id.refreshlayout) RefreshLayout refreshlayout;
  @Inject AppService mInfo;
  private QuestionAdapter adapter;
  private SimpleLoadFooterAdapter adapter1;

  @Override protected void onCreateViewCompleted(View view) {
    ButterKnife.bind(this, view);
    inject();
    init();
    initdata();
  }

  public void init() {

    //设置layoutmanager
    mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

    adapter = new QuestionAdapter(getActivity());
    adapter1 = new SimpleLoadFooterAdapter(getActivity());
    mRecyclerView.setAdapter(adapter, adapter1);
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

  public void initdata() {
    manageRpcCall(mInfo.getAllQuestion("1"),
        new UiRpcSubscriber<List<QuestionModel>>(getActivity()) {
          @Override protected void onSuccess(List<QuestionModel> infoModels) {
            adapter.setDatas(infoModels);
            //标记为请求完成
            adapter.notifyDataSetChanged();
            refreshlayout.refreshingComplete();
            mRecyclerView.setLoadCompleteState();
          }

          @Override protected void onEnd() {
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
          }
        });
  }

  @Override public int getLayoutId() {
    return R.layout.activity_work;
  }

  private void inject() {
    DaggerServiceComponent.builder()
        .globalModule(new GlobalModule((CustomApp) getActivity().getApplication()))
        .serviceModule(new ServiceModule())
        .build()
        .inject(this);
  }

  public static TucaoFragment newInstance(String param1) {
    TucaoFragment fragment = new TucaoFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  /**
   * item点击事件
   */
  @Override public void onItemClick(RecyclerHolder holder, View view, int position) {
    Intent intent = new Intent(getActivity(), AreaDetailActivity.class);
    intent.putExtra("", "");
    intent.putExtra("", "");
    intent.putExtra("", "");
    Toast.makeText(getActivity(), "第" + position + "行点击事件", Toast.LENGTH_SHORT).show();
  }

  /**
   * item长点击事件
   */
  @Override public boolean onItemLongClick(RecyclerHolder holder, View view, int position) {
    Toast.makeText(getActivity(), "第" + position + "行长点击事件", Toast.LENGTH_SHORT).show();
    return false;//因为这里return false 所以长点击拖动才有效，演示功能用，所以会触发两次震动
  }

  /**
   * item长点击拖动换位事件
   */
  @Override public boolean onItemViewMove(RecyclerView recyclerView,
      RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    //adapter.moveData(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    return false;
  }

  /**
   * item滑动删除事件
   */
  @Override public void onItemViewSwipe(RecyclerView.ViewHolder viewHolder, int direction) {
    //adapter.removeData(viewHolder.getAdapterPosition());
  }

  /**
   * 加载更多的回调
   */
  @Override public void loadMore() {
    initdata();
  }

  /**
   * 下拉刷新的回调
   */
  @Override public void onRefresh() {
    initdata();
  }

  public class QuestionAdapter extends RecyclerAdapter<QuestionModel> {

    /**
     * 主构造
     *
     * @param activity Activity对象
     */
    public QuestionAdapter(Activity activity) {
      super(activity);
    }

    @Override public View createView(LayoutInflater inflater, ViewGroup parent, int viewType) {
      return inflater.inflate(R.layout.area_tucao_list_item, parent, false);
    }

    @Override public void convert(RecyclerHolder holder, final QuestionModel bean, int position) {
      holder.setText(R.id.content, bean.getQuestion());
      holder.setText(R.id.time, DateHelper.getInstance().getRencentTime(bean.getCreateTime()));
      holder.setText(R.id.nick_name, bean.getNickName());
      holder.getView(R.id.ll_zx).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(getActivity(), AreaDetailActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("logo", bean.getAvatar());
          bundle.putString("nickName", bean.getNickName());
          bundle.putString("time", bean.getCreateTime());
          bundle.putInt("questionId", bean.getQuestionId());
          bundle.putString("question", bean.getQuestion());
          intent.putExtras(bundle);
          startActivity(intent);
        }
      });
      ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getAvatar())
          .
              bitmapTransform(new CropCircleTransformation(getActivity()))
          .into(holder.getImageView(R.id.head));
      if (bean.getImgList() != null) {
        int imagesize = bean.getImgList().size();

        if (imagesize > 0) {
          holder.getView(R.id.images).setVisibility(View.VISIBLE);
          holder.getImageView(R.id.iv_1)
              .setVisibility(imagesize > 0 ? View.VISIBLE : View.INVISIBLE);
          holder.getImageView(R.id.iv_2)
              .setVisibility(imagesize > 1 ? View.VISIBLE : View.INVISIBLE);
          holder.getImageView(R.id.iv_3)
              .setVisibility(imagesize > 2 ? View.VISIBLE : View.INVISIBLE);
          int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
          LinearLayout.LayoutParams params =
              new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
          holder.getView(R.id.images).setLayoutParams(params);

          for (int i = 0; i < imagesize; i++) {
            String url = bean.getImgList().get(i);
            ImageView imageView = null;
            if (i == 0) {
              imageView = holder.getImageView(R.id.iv_1);
            } else if (i == 1) {
              imageView = holder.getImageView(R.id.iv_2);
            } else if (i == 2) {
              imageView = holder.getImageView(R.id.iv_3);
            }

            if (imageView != null) {
              try {
                ImageLoader.loadOptimizedHttpImage(getActivity(), url)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
              } catch (Exception e) {
                e.printStackTrace();
              }
              final int in = i;
              imageView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                  Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getImgList());
                  bundle.putInt("image_index", in);
                  intent.putExtras(bundle);
                  startActivity(intent);
                }
              });
            }
          }
        } else {
          holder.getView(R.id.images).setVisibility(View.GONE);
        }
      } else {
        holder.getView(R.id.images).setVisibility(View.GONE);
      }
    }
  }
}