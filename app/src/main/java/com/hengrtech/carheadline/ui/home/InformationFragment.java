package com.hengrtech.carheadline.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.model.InfoModel;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarFragment;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.RBaseAdapter;
import com.hengrtech.carheadline.utils.RViewHolder;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by jiao on 2016/7/19.
 */
public class InformationFragment extends BasicTitleBarFragment {
  public static final String TYPE = "type";
  @Inject AppService mInfo;
  @Bind(R.id.profit) TextView profit;
  @Bind(R.id.btn_red_bag) LinearLayout btnRedBag;
  @Bind(R.id.zx_listView) RGridView zxListView;
  @Override protected void onCreateViewCompleted(View view) {
    ButterKnife.bind(this, view);
    inject();
    initview();
    initdata();

  }

  private void initview() {
    btnRedBag.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(getActivity(),TodayActivity.class));
      }
    });
  }

  public void initdata(){
    manageRpcCall(mInfo.getInfoList("1","1", "100"),
        new UiRpcSubscriber<List<InfoModel>>(getActivity()) {
          @Override protected void onSuccess(List<InfoModel> infoModels) {
            zxListView.setAdapter(new ZixunAdapter(getActivity(), infoModels));
          }
          @Override protected void onEnd() {
          }
          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
          }
        });
  }
  @Override public int getLayoutId() {
    return R.layout.activity_main;
  }
  private void inject() {
    DaggerServiceComponent.builder()
        .globalModule(new GlobalModule((CustomApp) getActivity().getApplication()))
        .serviceModule(new ServiceModule())
        .build()
        .inject(this);
  }
  public static InformationFragment newInstance(String param1) {
    InformationFragment fragment = new InformationFragment();
    Bundle args = new Bundle();
    args.putString(TYPE, param1);
    fragment.setArguments(args);
    return fragment;
  }
  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }


  public class ZixunAdapter extends RBaseAdapter<InfoModel> {
    Context context;
    private List<InfoModel> data;

    public ZixunAdapter(Context context) {
      super(context);
    }

    public ZixunAdapter(Context context, List<InfoModel> datas) {
      super(context, datas);
      data = datas;
    }

    @Override protected int getItemLayoutId(int viewType) {
      if (viewType == 1) {
        return R.layout.home_new_list_item;
      } else {
        return R.layout.home_new_list_item_three;
      }
    }

    @Override public int getItemViewType(int position) {
      int imgsize = data.get(position).getCoverArr().size();
      if (imgsize == 1) {
        return 1;
      } else {
        return 2;
      }
    }

    @Override protected void onBindView(RViewHolder holder, int position, final InfoModel bean) {


      holder.tV(R.id.news_title).setText(bean.getTitle());
       holder.tV(R.id.time).setText(bean.getCreateTime());
      holder.tV(R.id.tv_from).setText("来源：" + bean.getSource());
      holder.tV(R.id.view_count).setText(String.valueOf(bean.getPraiseCount()));
      holder.tV(R.id.comment_count).setText(String.valueOf(bean.getCommentsCount()));
      if (bean.getCoverArr()!= null) {
        int imagesize = bean.getCoverArr().size();
        if (imagesize == 1) {
          ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getCoverArr().get(0)).into(holder.imgV(R.id.iv_1));
        } else {
          if (imagesize==3) {
            holder.v(R.id.images).setVisibility(View.VISIBLE);
            holder.v(R.id.iv_1).setVisibility(imagesize > 0 ? View.VISIBLE : View.GONE);
            holder.v(R.id.iv_2).setVisibility(imagesize > 1 ? View.VISIBLE : View.GONE);
            holder.v(R.id.iv_3).setVisibility(imagesize > 2 ? View.VISIBLE : View.GONE);
            int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
            LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            holder.v(R.id.images).setLayoutParams(params);

            for (int i = 0; i < imagesize; i++) {
              String url = bean.getCoverArr().get(i);
              ImageView imageView = null;
              if (i == 0) {
                imageView = holder.imgV(R.id.iv_1);
              } else if (i == 1) {
                imageView = holder.imgV(R.id.iv_2);
              } else if (i == 2) {
                imageView = holder.imgV(R.id.iv_3);
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
                //final int in = i;
                //imageView.setOnClickListener(new View.OnClickListener() {
                //
                //  @Override public void onClick(View v) {
                //    Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                //    Bundle bundle = new Bundle();
                //    bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.imgList);
                //    bundle.putInt("image_index", in);
                //    intent.putExtras(bundle);
                //    startActivity(intent);
                //  }
                //});
              }
            }
          } else {
            holder.v(R.id.images).setVisibility(View.GONE);
          }
        }
      }else {
        holder.v(R.id.images).setVisibility(View.GONE);
      }
    }

    @Override public int getItemCount() {
      return mAllDatas.size();
    }
  }
}
