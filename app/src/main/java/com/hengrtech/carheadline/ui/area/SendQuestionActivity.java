package com.hengrtech.carheadline.ui.area;

/**
 * 发表微博
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hengrtech.carheadline.CustomApp;
import com.hengrtech.carheadline.Face.FaceParser;
import com.hengrtech.carheadline.Face.FaceView;
import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.injection.GlobalModule;
import com.hengrtech.carheadline.net.AppService;
import com.hengrtech.carheadline.net.RpcApiError;
import com.hengrtech.carheadline.net.UiRpcSubscriber;
import com.hengrtech.carheadline.net.constant.NetConstant;
import com.hengrtech.carheadline.net.model.UserInfo;
import com.hengrtech.carheadline.net.params.QuestionParams;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;
import com.hengrtech.carheadline.ui.serviceinjection.DaggerServiceComponent;
import com.hengrtech.carheadline.ui.serviceinjection.ServiceModule;
import com.hengrtech.carheadline.utils.BitmapUtiles;
import com.hengrtech.carheadline.utils.FileUtiles;
import com.hengrtech.carheadline.utils.Url;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

public class SendQuestionActivity extends BasicTitleBarActivity implements FaceView.Work {
  @Inject AppService mInfo;
  private Context context = this;
  private ImageView mCamera, mAlbum, mFace;
  private RelativeLayout mClose, mSendWeibo;
  private FaceParser mFaceParser;
  private EditText mWeiboEdit;
  private String data = "";
  private LinearLayout mbottom, mFaceArea, mDividingLineT, mDividingLineB;
  private String tempContent = null;
  /**
   * 统计微博字数
   */
  private TextView mlatterCount, mImageLarge, mTitle,code;
  /**
   * 用来存放微博图片的水平滑动组件容器
   */
  private HorizontalScrollView imgScrollView;
  private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;
  /**
   * 已选择准备上传的图片数量
   */
  private int img_num = 0;

  private List<ImageView> imgList = new ArrayList<ImageView>();
  private ProgressDialog progressDialog, progressDialog1;
  /**
   * 用来保存准备选择上传的图片路径
   */
  private List<String> scrollImg = new ArrayList<String>();

  private List<String> attachIds = new ArrayList<String>();

  private String mTempPhotoName = "";
  private FaceView mFaceView;
  private int mFaceFlag = 0;
  private static int FACESHOW = 1;
  private int isToLoadFace = 1;
  private static int LOADFACE = 1;
  private boolean mInputExceed = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    progressDialog = new ProgressDialog(this);
    progressDialog1 = new ProgressDialog(this);
    inject();
    initView();
    initList();
  }

  @Override public int getLayoutId () {
    return R.layout.activity_upload;
  }
  private void initView() {
    // 获取关闭按钮id
    mClose = (RelativeLayout) findViewById(R.id.close);
    MyOnclickListener mOnclickListener = new MyOnclickListener();
    MyOnTouchListener myOnTouchListener = new MyOnTouchListener();
    // 发表按钮
    mSendWeibo = (RelativeLayout) findViewById(R.id.sendWeibo);
    // 相机按钮
    mCamera = (ImageView) findViewById(R.id.camera);
    // 图片按钮
    mAlbum = (ImageView) findViewById(R.id.album);
    //表情图标
    mFace = (ImageView) findViewById(R.id.Upload_face);
    //字数统计文本
    mlatterCount = (TextView) findViewById(R.id.weibo_count);
    //微博图片
    imgScrollView = (HorizontalScrollView) findViewById(R.id.weibo_img);
    //显示统计要上传的图片大小
    mImageLarge = (TextView) findViewById(R.id.image_large);
    //表亲显示区
    mFaceArea = (LinearLayout) findViewById(R.id.Upload_FaceArea);
    //分割线
    mDividingLineB = (LinearLayout) findViewById(R.id.between_edt_face_bot);
    mDividingLineT = (LinearLayout) findViewById(R.id.between_edt_face_top);
    code = (TextView) findViewById(R.id.code);
    //标题
    mTitle = (TextView) findViewById(R.id.upload_title);
    img1 = (ImageView) findViewById(R.id.weibo_img1);
    img2 = (ImageView) findViewById(R.id.weibo_img2);
    img3 = (ImageView) findViewById(R.id.weibo_img3);
    img4 = (ImageView) findViewById(R.id.weibo_img4);
    img5 = (ImageView) findViewById(R.id.weibo_img5);
    img6 = (ImageView) findViewById(R.id.weibo_img6);
    img7 = (ImageView) findViewById(R.id.weibo_img7);
    img8 = (ImageView) findViewById(R.id.weibo_img8);
    img9 = (ImageView) findViewById(R.id.weibo_img9);

    img1.setOnClickListener(mOnclickListener);
    img2.setOnClickListener(mOnclickListener);
    img3.setOnClickListener(mOnclickListener);
    img3.setOnClickListener(mOnclickListener);
    img4.setOnClickListener(mOnclickListener);
    img5.setOnClickListener(mOnclickListener);
    img6.setOnClickListener(mOnclickListener);
    img7.setOnClickListener(mOnclickListener);
    img8.setOnClickListener(mOnclickListener);
    img9.setOnClickListener(mOnclickListener);
    mWeiboEdit = (EditText) findViewById(R.id.weiboEdit);
    mWeiboEdit.addTextChangedListener(textWatcher);
    mClose.setOnClickListener(mOnclickListener);
    mSendWeibo.setOnClickListener(mOnclickListener);
    mCamera.setOnClickListener(mOnclickListener);
    mAlbum.setOnClickListener(mOnclickListener);
    mFace.setOnClickListener(mOnclickListener);
    imgScrollView.setOnTouchListener(myOnTouchListener);
    mlatterCount.setText(NetConstant.WEIBOWORDS + "/" + NetConstant.WEIBOWORDS);

    mWeiboEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          mFaceArea.setVisibility(View.GONE);
          mDividingLineT.setVisibility(View.GONE);
          mDividingLineB.setVisibility(View.GONE);
        } else {

        }
      }
    });
    if (getIntent().getStringExtra("weiboType") != null) {
      mCamera.setVisibility(View.GONE);
      mAlbum.setVisibility(View.GONE);
      mImageLarge.setVisibility(View.INVISIBLE);
      mTitle.setText("转发微博");
    }
    CameralActivity.setIMGcallback(new CameralActivity.IMGCallBack() {

      @Override public void callback(String data) {
        SendQuestionActivity.this.data = data;
      }
    });
    PhotoAct.setIMGcallback(new PhotoAct.IMGCallBack1() {

      @Override public void callback(String data) {
        SendQuestionActivity.this.data = data;
      }
    });
  }

  private void initList() {
    MyOnTouchListener myOnTouchListener = new MyOnTouchListener();
    imgList.add(img1);
    imgList.add(img2);
    imgList.add(img3);
    imgList.add(img4);
    imgList.add(img5);
    imgList.add(img6);
    imgList.add(img7);
    imgList.add(img8);
    imgList.add(img9);
    for (int i = 0; i < imgList.size(); i++) {
      imgList.get(i).setOnTouchListener(myOnTouchListener);
    }
  }

  //监测输入框的字数
  TextWatcher textWatcher = new TextWatcher() {

    private CharSequence temp;
    private int selectionStart;
    private int selectionEnd;

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      temp = s;
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override public void afterTextChanged(Editable s) {
      int number = NetConstant.WEIBOWORDS - s.length();
      mInputExceed = number < 0;
      mlatterCount.setText(number + "/" + NetConstant.WEIBOWORDS);
      selectionStart = mWeiboEdit.getSelectionStart();
      selectionEnd = mWeiboEdit.getSelectionEnd();
      Log.d("selectionFlag", "flag");
      Log.d("selectionStart", selectionStart + "");
      Log.d("selectionEnd", selectionEnd + "");
      if (temp.length() > NetConstant.WEIBOWORDS) {
        Log.d("selection-temp", temp + "");
        s.delete(selectionStart - 1, selectionEnd);
        int tempSelection = selectionStart;
        Log.d("selection这是显示出来的", s + "");
        mWeiboEdit.setText(s);
        Log.d("selection跳0", tempSelection + "");
        mWeiboEdit.setSelection(NetConstant.WEIBOWORDS);//设置光标在最后
      }
    }
  };

  @Override public void onClick(int id, String item_str) {
    replace(id, item_str, mWeiboEdit);
  }

  private void replace(int id, String item_str, TextView view) {
    Drawable drawable = getResources().getDrawable(id);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

    SpannableString spannableString = new SpannableString(item_str);
    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
    spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    view.append(spannableString);
  }

  private class MyOnTouchListener implements View.OnTouchListener {
    @Override public boolean onTouch(View v, MotionEvent event) {
      if (event.getAction() == MotionEvent.ACTION_DOWN) {
        v.setOnLongClickListener(new View.OnLongClickListener() {
          @Override public boolean onLongClick(View v) {
            Intent mIntent = new Intent(SendQuestionActivity.this, ShowImageActivity.class);
            mIntent.putStringArrayListExtra("data", (ArrayList<String>) scrollImg);
            mIntent.putExtra("fromActivity", 4);
            startActivityForResult(mIntent, 8);
            return false;
          }
        });
      }
      return false;
    }
  }

  private class MyOnclickListener implements View.OnClickListener {

    @Override public void onClick(View v) {
      int ID = v.getId();
      switch (ID) {
        case R.id.close:
          SendQuestionActivity.this.finish();
          break;
        case R.id.sendWeibo:
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
          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          File file = new File(Environment.getExternalStorageDirectory() + "/tox/photos");
          mTempPhotoName = System.currentTimeMillis() + ".png";
          if (!file.exists()) {
            file.mkdirs();

            File photo = new File(file, mTempPhotoName);
            Uri u = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
          } else {

            File photo = new File(file, mTempPhotoName);
            Uri u = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
          }

          startActivityForResult(intent, 1);
          break;
        case R.id.album:
          //Intent intent3 = new Intent(UploadActivity.this, PhotoAct.class);
          Intent intent3 = new Intent(SendQuestionActivity.this, ScanPhotoActivity.class);
          startActivityForResult(intent3, 9);
          break;
        case R.id.weibo_img:
          Intent mIntent = new Intent(SendQuestionActivity.this, ShowImageActivity.class);
          mIntent.putStringArrayListExtra("data", (ArrayList<String>) scrollImg);
          startActivityForResult(mIntent, 8);
          break;
        case R.id.Upload_face:
          InputMethodManager imm =
              (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(mWeiboEdit.getWindowToken(),
              InputMethodManager.HIDE_NOT_ALWAYS);
          if (mFaceFlag == FACESHOW) {
            mFaceArea.setVisibility(View.GONE);
            mDividingLineT.setVisibility(View.GONE);
            mDividingLineB.setVisibility(View.GONE);
            mFaceFlag = 0;
          } else {
            //判断是否是第一次加载表情

            if (isToLoadFace == LOADFACE) {
              loadFaces();
              isToLoadFace = 0;
            }
            mFaceFlag = 1;
            mFaceArea.setVisibility(View.VISIBLE);
            mDividingLineT.setVisibility(View.VISIBLE);
            mDividingLineB.setVisibility(View.VISIBLE);
          }
      }
    }
  }

  //加载表情
  private void loadFaces() {
    mFaceView = new FaceView(context, null, this);
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    mFaceArea.addView(mFaceView, layoutParams);
    FaceParser.init(this);
    mFaceParser = FaceParser.getInstance();
  }

  public void sendQuestion() {
    manageRpcCall(mInfo.sendQuestion(getComponent().loginSession().getUserInfo().getToken(),
        new QuestionParams(
            getComponent().loginSession().getUserInfo().getMemberId(),
            mWeiboEdit.getText().toString(),getAttachIds(attachIds), "10")),
        new UiRpcSubscriber<UserInfo>(this) {

          @Override protected void onSuccess(UserInfo info) {
            progressDialog.dismiss();
          }

          @Override protected void onEnd() {
            closeProgressDialog();
          }

          @Override public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
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



  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */
    if (requestCode == 8 && resultCode == 999) {
      Log.e("scroll返回", "");
      List<String> imgPathList = data.getStringArrayListExtra("data");
      if (imgPathList.size() <= 0) {
        scrollImg.clear();
        imgScrollView.setVisibility(View.GONE);
        setImageGone(imgList);
        img_num = 0;
      } else {
        scrollImg.clear();
        setImageGone(imgList);
        img_num = 0;
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!isExistsInList(imgPathList.get(i), scrollImg)) {
            if (img_num < 9) {
              scrollImg.add(imgPathList.get(i));
              imgList.get(img_num).setVisibility(View.VISIBLE);
              imgList.get(img_num).setImageBitmap(BitmapUtiles.loadBitmap(imgPathList.get(i), 4));
              img_num++;
            }
          }
        }
      }
    }
    /**
     * 表示从ScanPhotoActivity返回
     */
    if (resultCode == 99 && requestCode == 9) {
      List<String> imgPathList = data.getStringArrayListExtra("data");
      Log.e("选图返回", "");
      if (imgPathList.size() > 0) {
        imgScrollView.setVisibility(View.VISIBLE);

        for (int i = 0; i < imgPathList.size(); i++) {
          Log.e("图片路径", imgPathList.get(i));
          if (!isExistsInList(imgPathList.get(i), scrollImg)) {
            if (img_num < 9) {
              scrollImg.add(imgPathList.get(i));
              Log.e(">>", scrollImg.get(i));
              imgList.get(img_num).setVisibility(View.VISIBLE);
              imgList.get(img_num).setImageBitmap(BitmapUtiles.loadBitmap(imgPathList.get(i), 2));
              img_num++;
            }
          }
        }
      }
      /**
       * 表示从拍照的Activity返回
       */
    } else if (requestCode == 1 && resultCode == RESULT_OK) {
      File temFile = new File(Environment.getExternalStorageDirectory() + "/tox/photos/" +
          mTempPhotoName);
      if (temFile.exists()) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        imgScrollView.setVisibility(View.VISIBLE);
        if (!isExistsInList(temFile.getPath(), scrollImg)) {
          if (img_num < 9) {
            imgList.get(img_num)
                .setImageBitmap(BitmapFactory.decodeFile(temFile.getPath(), options));
            imgList.get(img_num).setVisibility(View.VISIBLE);
            img_num++;
            imgList.get(img_num).setVisibility(View.VISIBLE);
            scrollImg.add(temFile.getPath());
          }
        }
      }
    }
    long count = BitmapUtiles.getFileSize(scrollImg);
    mImageLarge.setText("图片:" + getImgCount((int) count));
  }

  private String getImgCount(int count) {
    DecimalFormat df = new DecimalFormat("0.00");
    if (count < 100) {
      return count + "KB";
    }

    String result = df.format(count / 1000.0) + "MB";
    return result;
  }

  private void setImageGone(List<ImageView> imageLists) {
    for (int i = 0; i < img_num; i++) {
      imageLists.get(i).setVisibility(View.GONE);
    }
  }

  //判断图片路径是否已是选中的图片
  private boolean isExistsInList(String path, List<String> pathList) {
    if (pathList == null || pathList.size() == 0) {
      return false;
    } else {
      for (int i = 0; i < pathList.size(); i++) {
        if (path.equalsIgnoreCase(pathList.get(i))) {
          return true;
        }
      }
    }
    return false;
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
        String path = BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i));
        Log.e("压缩后路径", path);
        // Toast.makeText(UploadActivity.this, "压缩后路径" + path, Toast.LENGTH_LONG).show();
        File file = new File(BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i)));

        params.put("image", file);
        params.put("image", scrollImg.get(i));
        params.put("suffix", ".png");
        FinalHttp fh = new FinalHttp();
        fh.post(Url.UPLOADIMGURL, params, new AjaxCallBack<Object>() {
          @Override public void onLoading(long count, long current) {
                   /*     progressDialog.setProgressNumberFormat("%1dKB/%2dKB");;
                        progressDialog.setMax((int)count/1024);
                        progressDialog.setProgress((int)(current/1024));*/
          }

          @Override public void onSuccess(Object o) {
            progressDialog.dismiss();
            attachIds.add(getAttachId(o));
            Log.e("上传照片成功", o.toString());
            if (attachIds.size() == scrollImg.size()) {
              //progressDialog1.show(UploadActivity.this,"提示","发布中...",true,false);
              sendQuestion();
            }
          }

          @Override public void onFailure(Throwable t, int errorNo, String strMsg) {
            Log.e("上传照片失败", "");
            progressDialog.dismiss();
            Toast.makeText(SendQuestionActivity.this, "上传照片失败！", Toast.LENGTH_LONG).show();
          }
        });
      } catch (Exception e) {
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

  @Override public void onBackPressed() {
    FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
    super.onBackPressed();
  }

  public class WeiboSendSuccessEvent {

  }
}
