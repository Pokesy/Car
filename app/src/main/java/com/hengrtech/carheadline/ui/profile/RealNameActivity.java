/*
 * 文件名: NickNameActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/30
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/30]
 */
public class RealNameActivity extends BasicTitleBarActivity {
    public static final String RESULT_KEY_REAL_NAME = "result_real_name";

    @Bind(R.id.nick_name_input)
    AppCompatEditText mNickNameInputView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nick_name;
    }

    @Override
    public boolean initializeTitleBar() {
        setMiddleTitle(R.string.activity_real_name_name_title);
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mNickNameInputView.setText(getComponent().loginSession().getUserInfo().getRealName());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(RESULT_KEY_REAL_NAME, mNickNameInputView.getText().toString());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
