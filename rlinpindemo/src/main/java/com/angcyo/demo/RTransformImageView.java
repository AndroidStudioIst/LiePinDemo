package com.angcyo.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/04/13 13:46
 * 修改人员：Robi
 * 修改时间：2018/04/13 13:46
 * 修改备注：
 * Version: 1.0.0
 */
public class RTransformImageView extends AppCompatImageView {
    public Drawable[] drawables;
    int scrollState = ViewPager.SCROLL_STATE_IDLE;
    private int curPosition = 0;
    private int nextPosition = 0;
    private float positionOffset = 0f;

    public RTransformImageView(Context context) {
        super(context);
    }

    public RTransformImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImages(int[] images) {
        drawables = new Drawable[images.length];
        for (int i = 0; i < images.length; i++) {
            drawables[i] = ContextCompat.getDrawable(getContext(), images[i]);
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                int oldState = RTransformImageView.this.scrollState;
                scrollState = state;
                if (oldState == ViewPager.SCROLL_STATE_IDLE && state != oldState) {
                    curPosition = nextPosition;
                    RTransformImageView.this.positionOffset = 0;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //Log.e("angcyo", " " + position + " " + positionOffset + " " + positionOffsetPixels);

                if (scrollState != ViewPager.SCROLL_STATE_IDLE) {
                    if (positionOffset == 0) {
                        curPosition = position;
                    } else {
                        if (position < curPosition) {
                            RTransformImageView.this.positionOffset = 1 - positionOffset;
                            //向左
                            if (scrollState == ViewPager.SCROLL_STATE_DRAGGING) {
                                nextPosition = position;
                            }
                        } else {
                            //向右
                            RTransformImageView.this.positionOffset = positionOffset;
                            if (scrollState == ViewPager.SCROLL_STATE_DRAGGING) {
                                nextPosition = position + 1;
                            }
                        }
                    }
                }
                postInvalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (drawables != null) {
            for (int i = 0; i < drawables.length; i++) {
                drawables[i].setBounds(0, 0, w, h);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw(canvas, curPosition, (int) ((1 - positionOffset) * 255));
        draw(canvas, nextPosition, (int) (positionOffset * 255));
    }

    private void draw(Canvas canvas, int position, int alpha /*0-255 越小越透明*/) {
        if (drawables == null) {
            return;
        }
        if (position >= 0 && position < drawables.length) {
            //Log.w("angcyo", "draw--> " + position + " " + alpha + " ");

            drawables[position].setAlpha(alpha);
            drawables[position].draw(canvas);
        }
    }
}
