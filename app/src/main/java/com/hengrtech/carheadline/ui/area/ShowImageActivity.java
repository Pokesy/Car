package com.hengrtech.carheadline.ui.area;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalBitmap;

public class ShowImageActivity extends BasicTitleBarActivity implements View.OnClickListener {
    private GridView mGridView;
    private List<String> list;
    private ChildAdapter adapter;
    private FinalBitmap finalBitmap;
    private RelativeLayout mPicConfirm, mPicCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finalBitmap = FinalBitmap.create(this);
        finalBitmap.configBitmapLoadThreadSize(10);
        finalBitmap.configMemoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 1024));
        finalBitmap.configLoadingImage(R.mipmap.friends_sends_pictures_no);
        finalBitmap.clearCache();
        mGridView = (GridView) findViewById(R.id.child_grid);
        mPicCancel = (RelativeLayout) findViewById(R.id.pic_cancel);
        mPicConfirm = (RelativeLayout) findViewById(R.id.pic_confirm);
        mPicConfirm.setOnClickListener(this);
        mPicCancel.setOnClickListener(this);
        //mPicCancel.setOnTouchListener(new TouchHelper(ShowImageActivity.this,"#1AA3D1","#4FC1E9","color"));
        mPicConfirm.setOnTouchListener(new TouchHelper(ShowImageActivity.this, "#1AA3D1", "#4FC1E9", "color"));
        list = getIntent().getStringArrayListExtra("data");

        adapter = new ChildAdapter(this, list, mGridView, finalBitmap);
        mGridView.setAdapter(adapter);
        //// TODO: 2016/9/13  
        if (getIntent().getIntExtra("fromActivity", 0) == 4) {
            adapter.setImgSelected();
        }

    }
    @Override public int getLayoutId () {
        return R.layout.show_image_activity;
    }
    @Override
    public void onBackPressed() {
        finalBitmap.onPause();
        finalBitmap.onDestroy();
        pic_confirm();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.pic_cancel:
                finalBitmap.onPause();
                finalBitmap.onDestroy();
                finish();
                break;
            case R.id.pic_confirm:
                pic_confirm();
                break;
            default:
                break;
        }
    }

    /**
     * 确认选择图片，并返回
     */
    private void pic_confirm() {
        Intent data = new Intent();
        List<Integer> selectedID = adapter.getSelectItems();
        List<String> selectedImgPath = new ArrayList<String>();
        for (int i = 0; i < selectedID.size(); i++) {
            selectedImgPath.add(list.get(selectedID.get(i)));
            Log.e("图片路径", list.get(selectedID.get(i)));
            Log.e("图片路径212", selectedID.size()+"");
        }
        data.putStringArrayListExtra("data", (ArrayList<String>) selectedImgPath);

        setResult(999, data);
        finish();
    }

}
