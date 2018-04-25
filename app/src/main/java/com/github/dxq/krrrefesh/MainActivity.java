package com.github.dxq.krrrefesh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
public class MainActivity extends AppCompatActivity implements PtrHandler {
    private PtrFrameLayout mPtr;
    private KrHeader krHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPtr = (PtrFrameLayout) findViewById(R.id.ptr);
        this.krHeader = new KrHeader(this);
        krHeader.setShowRefreshInfo(true);
        krHeader.getCompleteView().setText("暂无更新内容");
        this.mPtr.setHeaderView(this.krHeader);
        this.mPtr.addPtrUIHandler(this.krHeader);
        this.mPtr.setPtrHandler(this);
        this.mPtr.setDurationToCloseHeader(1000);

        this.mPtr.setDurationToClose(200);
        this.mPtr.setLoadingMinTime(1000);
        this.mPtr.setEnabledNextPtrAtOnce(true);
        ImageView iv_test = findViewById(R.id.iv_test);
        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return true;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtr.refreshComplete();

            }
        }, 300);
    }
}
