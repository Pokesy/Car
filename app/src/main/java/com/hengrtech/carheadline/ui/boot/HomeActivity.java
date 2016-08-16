/*
 * 文件名: HomeActivity
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/15
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.ui.boot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hengrtech.carheadline.R;

/**
 * 主 <BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/15]
 */
public class HomeActivity extends AppCompatActivity {
  @Bind(R.id.tool_bar)
  Toolbar toolBar;
  @Bind(R.id.list_view)
  RecyclerView listView;
  @Bind(R.id.collapsingToolbar)
  CollapsingToolbarLayout collapsingToolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
    toolBar.setLogo(R.mipmap.ic_launcher);
    //toolBar.setTitle("测试代码");

    collapsingToolbar.setTitle("折叠啊");

    listView.setLayoutManager(new LinearLayoutManager(this));
    listView.setAdapter(new CustomAdapter());
  }

  class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.list_item, parent, false);
      return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
      //holder.text
    }

    @Override
    public int getItemCount() {
      return 50;
    }
  }

  class CustomViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.text)
    TextView text;

    public CustomViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
