package com.hengrtech.carheadline.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hengrtech.carheadline.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gengguanglong on 2016/4/20.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_ITEM = 0;  //普通Item View
    private int TYPE_FOOTER = 1;  //底部FootView
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    //默认为0
    private int load_more_status = 0;
    public static final int LOADING_MORE = 1;
    public List<T> mData;
    public Context mContext;
    public LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    private boolean isLoadMore = false;
    private int size;
    private String loadMoreString = "上拉加载更多";
    private OnLoadMoreListener onLoadMoreListener;

    public BaseRecyclerAdapter(Context context, List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }

    public void setIsShowLoadMore(boolean flag) {
        isLoadMore = flag;
    }

    public void setLoadMoreString(String lms) {
        loadMoreString = lms;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            final RecyclerViewHolder holder = new RecyclerViewHolder(mContext, mInflater.inflate
                    (getItemLayoutId(viewType), parent, false));
            if (mClickListener != null) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                    }
                });
            }

            if (mLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLongClickListener.onItemLongClick(holder.itemView, holder
                                .getLayoutPosition());

                        return true;
                    }
                });
            }
            return holder;
        } else if (isLoadMore && viewType == TYPE_FOOTER) {
            View foot_view = mInflater.inflate(R.layout.recyclerview_footer_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadMore) {
            if (holder instanceof FootViewHolder) {
                FootViewHolder footViewHolder = (FootViewHolder) holder;
                footViewHolder.footer_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onLoadMoreListener.loadMore();
                    }
                });
                switch (load_more_status) {
                    case PULLUP_LOAD_MORE:
                        footViewHolder.foot_view_item_tv.setVisibility(View.VISIBLE);
                        footViewHolder.foot_view_item_tv.setText(loadMoreString);
                        footViewHolder.pb.setVisibility(View.GONE);
                        break;
                    case LOADING_MORE:
                        footViewHolder.foot_view_item_tv.setVisibility(View.GONE);
                        footViewHolder.pb.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                bindData(holder, position, mData.get(position));
            }
        } else {
            bindData(holder, position, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        size = mData.size();
        return size;
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    abstract public int getItemLayoutId(int viewType);

    abstract public void bindData(RecyclerView.ViewHolder holder, int position, T item);

    public interface OnItemClickListener {
        public void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(View itemView, int pos);
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (isLoadMore) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        public TextView foot_view_item_tv;
        public ProgressBar pb;
        public LinearLayout footer_layout;

        public FootViewHolder(View view) {
            super(view);
            pb = (ProgressBar) view.findViewById(R.id.progress_view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.tv_content);
            footer_layout = (LinearLayout) view.findViewById(R.id.footer_layout);
        }
    }

    public void setMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
