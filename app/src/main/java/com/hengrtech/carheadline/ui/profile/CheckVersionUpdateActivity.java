package com.hengrtech.carheadline.ui.profile;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengrtech.carheadline.R;
import com.hengrtech.carheadline.ui.basic.BasicTitleBarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CheckVersionUpdateActivity extends BasicTitleBarActivity {
    @Bind(R.id.current_version_tv)
    TextView currentVersionTv;
    @Bind(R.id.new_version_tv)
    TextView newVersionTv;
    @Bind(R.id.update_btn)
    Button updateBtn;
    @Bind(R.id.activity_check_version_update)
    LinearLayout activityCheckVersionUpdate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_version_update;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            currentVersionTv.setText("当前版本:V" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean initializeTitleBar() {
        setLeftTitleButton(R.mipmap.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleBarTextColor(getResources().getColor(R.color.title_text_color));
        setMiddleTitle(R.string.setting_update);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
