package com.hengrtech.carheadline.ui.profile.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.hengrtech.carheadline.R;


public class SharePopup implements OnClickListener {
    private ImageButton wxFriendBtn, weiboShareBtn, qqShareBtn, qZoneShareBtn;
    private ImageButton shareCancleBtn;
    public PopupWindow mPopupWindow;
    private SharePopupOnClickListener sharePopupListener;
    private LinearLayout ll_share_to_community;

    public PopupWindow getmPopupWindow() {
        return mPopupWindow;
    }

    private Context mContext;

    @SuppressWarnings("deprecation")
    public SharePopup(Context context) {
        mContext = context;
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setContentView(initViews());

        mPopupWindow.getContentView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.setFocusable(false);
                mPopupWindow.dismiss();
                return true;
            }
        });

    }

    public void setShareToCommunityEnabled() {
        ll_share_to_community.setVisibility(View.VISIBLE);
    }

    public void setShareToCommunityDisabled() {
        ll_share_to_community.setVisibility(View.GONE);
    }

    public View initViews() {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.share_pop_window, null);
        ll_share_to_community = (LinearLayout) view
                .findViewById(R.id.ll_share_to_community);
        wxFriendBtn = (ImageButton) view
                .findViewById(R.id.share_weixin_btn);
        weiboShareBtn = (ImageButton) view
                .findViewById(R.id.share_to_weibo);
        qqShareBtn = (ImageButton) view.findViewById(R.id.share_qq_btn);
        qZoneShareBtn = (ImageButton) view
                .findViewById(R.id.share_to_qq_zone_btn);
        shareCancleBtn = (ImageButton) view.findViewById(R.id.share_pop_cancle_btn);

        wxFriendBtn.setOnClickListener(this);
        qqShareBtn.setOnClickListener(this);
        qZoneShareBtn.setOnClickListener(this);
        weiboShareBtn.setOnClickListener(this);
        shareCancleBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_qq_btn:
                sharePopupListener.obtainMessage(0);
                break;
            case R.id.share_weixin_btn:
                sharePopupListener.obtainMessage(1);
                break;
            case R.id.share_to_weibo:
                sharePopupListener.obtainMessage(2);
                break;
            case R.id.share_to_qq_zone_btn:
                sharePopupListener.obtainMessage(3);
                break;
            case R.id.share_pop_cancle_btn:
                sharePopupListener.obtainMessage(4);
                break;
            default:
                break;
        }
        dismiss();

    }

    public interface SharePopupOnClickListener {
        void obtainMessage(int flag);
    }

    public void setOnSharePopupListener(SharePopupOnClickListener l) {
        this.sharePopupListener = l;
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showPopup(View rootView) {
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
        mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }
}
