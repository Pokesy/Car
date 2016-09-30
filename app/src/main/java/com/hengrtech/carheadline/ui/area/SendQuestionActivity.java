package com.hengrtech.carheadline.ui.area;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.constant.NetConstant;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.QuestionParams;
import com.hengrtech.carheadline.net.params.TucaoParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.BitmapUtiles;
import com.hengrtech.carheadline.utils.FileUtiles;
import com.hengrtech.carheadline.utils.Url;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

public class SendQuestionActivity extends BasicTitleBarActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    @Inject
    AppService mInfo;
    private Context context = this;
    private ImageView mCamera;
    private RelativeLayout mClose;
    private TextView mSendWeibo, add_tv, minus_tv, reward_tv;
    private EditText mWeiboEdit;
    private LinearLayout xuanshang_layout;
    /**
     * 统计微博字数
     */
    private TextView mlatterCount, mTitle, code;
    /**
     * 已选择准备上传的图片数量
     */
    private int img_num = 0;

    private ProgressDialog progressDialog, progressDialog1;
    /**
     * 用来保存准备选择上传的图片路径
     */
    private List<String> scrollImg = new ArrayList<String>();

    private List<String> attachIds = new ArrayList<String>();

    private String mTempPhotoName = "";
    private boolean mInputExceed = false;
    private String data = "";
    private int flag = 0;
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private int reward_count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = this.getIntent().getIntExtra("flag", 0);
        progressDialog = new ProgressDialog(this);
        progressDialog1 = new ProgressDialog(this);
        inject();
        initView();
        mPhotosSnpl.setIsPlusSwitchOpened(true);
        mPhotosSnpl.setIsSortable(true);
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
        mPhotosSnpl.init(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_upload;
    }

    private void initView() {
        add_tv = (TextView) findViewById(R.id.add_tv);
        minus_tv = (TextView) findViewById(R.id.minus_tv);
        reward_tv = (TextView) findViewById(R.id.reward_tv);
        mPhotosSnpl = (BGASortableNinePhotoLayout) findViewById(R.id.snpl_moment_add_photos);
        xuanshang_layout = (LinearLayout) findViewById(R.id.xuanshang_layout);
        if (flag == 0) {
            xuanshang_layout.setVisibility(View.GONE);
        } else {
            xuanshang_layout.setVisibility(View.VISIBLE);
        }
        // 获取关闭按钮id
        mClose = (RelativeLayout) findViewById(R.id.close);
        MyOnclickListener mOnclickListener = new MyOnclickListener();
        // 发表按钮
        mSendWeibo = (TextView) findViewById(R.id.sendWeibo);
        // 相机按钮
        mCamera = (ImageView) findViewById(R.id.camera);
        //字数统计文本
        mlatterCount = (TextView) findViewById(R.id.edit_count);
        mWeiboEdit = (EditText) findViewById(R.id.weiboEdit);
        mWeiboEdit.addTextChangedListener(textWatcher);
        mClose.setOnClickListener(mOnclickListener);
        mSendWeibo.setOnClickListener(mOnclickListener);
        mCamera.setOnClickListener(mOnclickListener);
        add_tv.setOnClickListener(mOnclickListener);
        minus_tv.setOnClickListener(mOnclickListener);
        mlatterCount.setText(NetConstant.WEIBOWORDS + "/" + NetConstant.WEIBOWORDS + "字");
    }

    //监测输入框的字数
    TextWatcher textWatcher = new TextWatcher() {

        private CharSequence temp;
        private int selectionStart;
        private int selectionEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int number = NetConstant.WEIBOWORDS - s.length();
            mInputExceed = number < 0;
            mlatterCount.setText(number + "/" + NetConstant.WEIBOWORDS + "字");
            selectionStart = mWeiboEdit.getSelectionStart();
            selectionEnd = mWeiboEdit.getSelectionEnd();
            if (temp.length() > NetConstant.WEIBOWORDS) {
                s.delete(selectionStart - 1, selectionEnd);
                mWeiboEdit.setText(s);
                mWeiboEdit.setSelection(NetConstant.WEIBOWORDS);//设置光标在最后
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, 3, models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            File takePhotoDir = new File(Url.UPLOADTEMPORARYPATH);
            if (!takePhotoDir.exists())
                takePhotoDir.mkdirs();
            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, takePhotoDir, 3, mPhotosSnpl.getData(), true), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int ID = v.getId();
            switch (ID) {
                case R.id.close:
                    SendQuestionActivity.this.finish();
                    break;
                case R.id.sendWeibo:
                    scrollImg = mPhotosSnpl.getData();
                    if (mInputExceed) {
                        Toast.makeText(SendQuestionActivity.this, "最大字数超过限制", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (TextUtils.isEmpty(mWeiboEdit.getText()) && scrollImg.size() == 0) {
                        Toast.makeText(SendQuestionActivity.this, "请填写内容或者选择图片", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (scrollImg.size() != 0) {
                        uploadImages();
                    } else {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setTitle("发布中请等待");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        sendQuestion();
                    }
                    break;
                case R.id.camera:
                    choicePhotoWrapper();
                    break;
                case R.id.add_tv:
                    reward_count++;
                    updateRewardTv();
                    break;
                case R.id.minus_tv:
                    if (reward_count > 1) {
                        reward_count--;
                        updateRewardTv();
                    } else {
                        showShortToast("不能低于1个积分");
                    }
                    break;

            }
        }
    }

    private void updateRewardTv() {
        reward_tv.setText(reward_count + "");
    }


    public void sendQuestion() {
        if (flag == 1) {
            manageRpcCall(mInfo.sendQuestion(getComponent().loginSession().getUserInfo().getToken(),
                    new QuestionParams(
                            getComponent().loginSession().getUserInfo().getMemberId(),
                            mWeiboEdit.getText().toString(), getAttachIds(attachIds), reward_count + "")),
                    new UiRpcSubscriber<UserInfo>(this) {

                        @Override
                        protected void onSuccess(UserInfo info) {
                            progressDialog.dismiss();
                            showShortToast("发布成功");
                            finish();
                        }

                        @Override
                        protected void onEnd() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onApiError(RpcApiError apiError) {
                            super.onApiError(apiError);
                        }
                    });
        } else {
            manageRpcCall(mInfo.sendTucao(getComponent().loginSession().getUserInfo().getToken(),
                    new TucaoParams(
                            getComponent().loginSession().getUserInfo().getMemberId(),
                            mWeiboEdit.getText().toString(), getAttachIds(attachIds))),
                    new UiRpcSubscriber<UserInfo>(this) {

                        @Override
                        protected void onSuccess(UserInfo info) {
                            progressDialog.dismiss();
                            showShortToast("发布成功");
                            finish();
                        }

                        @Override
                        protected void onEnd() {
                            closeProgressDialog();
                        }

                        @Override
                        public void onApiError(RpcApiError apiError) {
                            super.onApiError(apiError);
                        }
                    });
        }
    }

    private void inject() {
        DaggerServiceComponent.builder()
                .globalModule(new GlobalModule((CustomApp) getApplication()))
                .serviceModule(new ServiceModule())
                .build()
                .inject(this);
    }

    public static String getAttachIds(List<String> attach_ids) {
        String ids = "";
        for (int i = 0; i < attach_ids.size(); i++) {
            if (i == 0) {
                ids = attach_ids.get(i);
            } else {
                ids = ids + "," + attach_ids.get(i);
            }
        }
        return ids;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
        if (mPhotosSnpl.getData().size() > 0) {
            mPhotosSnpl.setVisibility(View.VISIBLE);
        } else {
            mPhotosSnpl.setVisibility(View.GONE);
        }
    }


    private void uploadImages() {
        attachIds.clear();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("发布中请等待");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        for (int i = 0; i < scrollImg.size(); i++) {
            AjaxParams params = new AjaxParams();
            try {
                Log.e("原路径", scrollImg.get(i));
                String path = BitmapUtiles.getUploadImgPath(scrollImg.get(i));
                Log.e("压缩后路径", path);
                // Toast.makeText(UploadActivity.this, "压缩后路径" + path, Toast.LENGTH_LONG).show();
                File file = new File(path);
                params.put("image", file);
                params.put("suffix", ".png");
                FinalHttp fh = new FinalHttp();
                fh.post(Url.UPLOADIMGURL, params, new AjaxCallBack<Object>() {
                    @Override
                    public void onLoading(long count, long current) {
                   /*     progressDialog.setProgressNumberFormat("%1dKB/%2dKB");;
                        progressDialog.setMax((int)count/1024);
                        progressDialog.setProgress((int)(current/1024));*/
                    }

                    @Override
                    public void onSuccess(Object o) {
                        progressDialog.dismiss();
                        attachIds.add(getAttachId(o));
                        Log.e("上传照片成功", o.toString());
                        if (attachIds.size() == scrollImg.size()) {
                            //progressDialog1.show(UploadActivity.this,"提示","发布中...",true,false);
                            sendQuestion();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        Log.e("上传照片失败", "");
                        progressDialog.dismiss();
                        Toast.makeText(SendQuestionActivity.this, "上传照片失败！" + strMsg, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                String s = e.getMessage();
                Log.i("---------------sssssss", e.getMessage());
            }
        }
    }

    /**
     * 图片上传后从结果中获取图片的attach_id
     */
    public String getAttachId(Object obj) {
        String img_attachId = "";
        String result = obj.toString();
        try {
            JSONObject jsonObject = new JSONObject(result);
            img_attachId = jsonObject.getString("result");
        } catch (JSONException e) {

        }
        return img_attachId;
    }

    @Override
    public void onBackPressed() {
        FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
        super.onBackPressed();
    }

}
