package com.hengrtech.carheadline.ui.profile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.utils.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class MyLoveCarRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_NORMAL_ITEM = 0;  //普通Item
    private static final int TYPE_FOOTER_ITEM = 1;  //add
    private Context context;


    public List<UserInfo.CarListBean> list;
    private OnItemClickListener mClickListener;
    private List<String> cbList = new ArrayList<>();
    private List<UserInfo.CarListBean> checkeddata = new ArrayList<>();// 选中的数据
    private List<Integer> checkPositionlist;

    public MyLoveCarRvAdapter(Context context, List<UserInfo.CarListBean> list) {
        this.context = context;
        this.list = list;
        checkPositionlist = new ArrayList<>();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //如果viewType是普通item返回普通的布局，否则是底部布局并返回
        if (viewType == TYPE_NORMAL_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                    .item_my_love_car, viewGroup, false);
            NormalItmeViewHolder vh = new NormalItmeViewHolder(view);
            return vh;
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                    .item_my_car_select, viewGroup, false);
            AddViewHolder vh = new AddViewHolder(view);
            return vh;
        }
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof NormalItmeViewHolder) {
            ((NormalItmeViewHolder) viewHolder).titleTv.setText(list.get(position).getSerialName());
            ImageLoader.loadOptimizedHttpImage(context, list.get(position).getPicture()).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(((NormalItmeViewHolder) viewHolder).iv);
            ((NormalItmeViewHolder) viewHolder).cb.setTag(new Integer(position));//设置tag 否则划回来时选中消失
            //checkbox  复用问题
            if (checkPositionlist.size()>0) {
                ((NormalItmeViewHolder) viewHolder).cb.setChecked((checkPositionlist.contains(new Integer(position)) ? true : false));
            } else {
                ((NormalItmeViewHolder) viewHolder).cb.setChecked(false);
            }
            ((NormalItmeViewHolder) viewHolder).cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    UserInfo.CarListBean baseBean = list.get(position);
                    if (b) {
                        if (!checkPositionlist.contains(((NormalItmeViewHolder) viewHolder).cb.getTag())) {
                            checkeddata.add(baseBean);
                            cbList.add(list.get(position).getSerialId() + "");
                            checkPositionlist.add(new Integer(position));
                        }
                    } else {
                        if (checkPositionlist.contains(((NormalItmeViewHolder) viewHolder).cb.getTag())) {
                            checkeddata.remove(baseBean);
                            cbList.remove(list.get(position).getSerialId() + "");
                            checkPositionlist.remove(new Integer(position));
                        }
                    }
                }
            });
        } else if (viewHolder instanceof AddViewHolder) {
            final AddViewHolder addViewHolder = (AddViewHolder) viewHolder;
            if (mClickListener != null) {
                addViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickListener.onItemClick(addViewHolder.itemView, addViewHolder.getLayoutPosition());
                    }
                });
            }
        }
    }

    public List<String> getCbList() {
        return cbList;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FOOTER_ITEM;
        } else {
            return TYPE_NORMAL_ITEM;
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalItmeViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public ImageView iv;
        public CheckBox cb;

        public NormalItmeViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.item_my_car_title);
            iv = (ImageView) view.findViewById(R.id.item_my_car_iv);
            cb = (CheckBox) view.findViewById(R.id.item_my_car_cb);
        }
    }

    /**
     * 添加按钮布局
     */
    public static class AddViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_add_iv;

        public AddViewHolder(View view) {
            super(view);
            item_add_iv = (ImageView) view.findViewById(R.id.item_add_iv);
        }
    }

    public void clearData() {
        cbList.clear();
        checkeddata.clear();
        checkPositionlist.clear();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }


    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }
}