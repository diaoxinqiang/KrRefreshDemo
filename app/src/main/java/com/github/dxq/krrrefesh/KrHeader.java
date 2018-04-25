//
// Source code recreated from mImageView .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.github.dxq.krrrefesh;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 郑重声明：本源码均来自互联网，仅供个人欣赏、学习之用，
 * 版权归36氪产品发行公司所有，任何组织和个人不得公开传播或用于任何商业盈利用途，
 * 否则一切后果由该组织或个人承担。
 * 本人不承担任何法律及连带责任！请自觉于下载后24小时内删除
 *
 */
public class KrHeader extends FrameLayout implements PtrUIHandler {
    private ImageView mScaleImageView;
    
    
    private LottieAnimationView mLoadingLottieView;
    private TextView mRefreshInfoTextView;
    
    private boolean isShowRefreshInfo;

    public KrHeader(Context context) {
        this(context, (AttributeSet)null);
    }

    public KrHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public KrHeader(Context context, AttributeSet attributeSet, int defStyleRes) {
        super(context, attributeSet, defStyleRes);
        this.init(context);
    }

    @TargetApi(21)
    public KrHeader(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        super(context, attributeSet, defStyleAttr, defStyleRes);
        this.init(context);
    }

    private void init() {
        this.mScaleImageView.setVisibility(GONE);
        this.mLoadingLottieView.setVisibility(VISIBLE);
        this.mRefreshInfoTextView.setVisibility(GONE);
        this.mLoadingLottieView.playAnimation();
    }

    private void init(Context var1) {
        View var2 = inflate(var1, R.layout.header_kr, this);
        this.mScaleImageView = (ImageView)var2.findViewById(R.id.pre);
        this.mLoadingLottieView = (LottieAnimationView)var2.findViewById(R.id.loading);
        this.mRefreshInfoTextView = (TextView)var2.findViewById(R.id.tv_refresh_info);
    }

    private void onUIRefreshPrepare() {
        this.mScaleImageView.setVisibility(VISIBLE);
        this.mLoadingLottieView.setVisibility(GONE);
        this.mRefreshInfoTextView.setVisibility(GONE);
        this.mLoadingLottieView.setProgress(0f);
        this.mLoadingLottieView.cancelAnimation();
    }

    private void onUIRefreshComplete() {
        if (this.isShowRefreshInfo) {
            this.mScaleImageView.setVisibility(GONE);
            this.mLoadingLottieView.setVisibility(GONE);
            this.mRefreshInfoTextView.setVisibility(VISIBLE);
        }

    }

    public TextView getCompleteView() {
        return this.mRefreshInfoTextView;
    }

    /**
     * 根据手势上下拉缩放imageview
     * @param frame
     * @param isUnderTouch
     * @param status
     * @param ptrIndicator
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int offset = frame.getOffsetToRefresh();
        int currentPosY = ptrIndicator.getCurrentPosY();
        if (currentPosY >= offset) {
            this.mScaleImageView.setScaleX(1.0F);
            this.mScaleImageView.setScaleY(1.0F);
        } else if (status == 2) {
            //根据偏移量计算缩放比例
            float scale = (float)(offset - currentPosY) / (float)offset;
            this.mScaleImageView.setScaleX(1.0F - scale);
            this.mScaleImageView.setScaleY(1.0F - scale);
        }

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout var1) {
        this.init();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout var1) {
        this.onUIRefreshComplete();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout var1) {
        this.onUIRefreshPrepare();
    }

    @Override
    public void onUIReset(PtrFrameLayout var1) {
        this.onUIRefreshPrepare();
    }

    public void setShowRefreshInfo(boolean showRefreshInfo) {
        this.isShowRefreshInfo = showRefreshInfo;
    }
}
