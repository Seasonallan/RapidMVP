package com.season.rapiddevelopment.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 智能下滑切换scrollview，只需配置上级
 *
 * @author zhangjg
 * @date Feb 13, 2014 6:11:33 PM
 */
public class ReboundScrollView extends ScrollView {

    //移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
    //目的是达到一个延迟的效果
    private static final float MOVE_FACTOR = 0.5f;

    //松开手指后, 界面回到正常位置需要的动画时间
    private static final int ANIM_TIME = 300;

    //手指按下时的Y值, 用于在移动时计算移动距离
    //如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private float startY;

    //手指按下时记录是否可以继续下拉
    private boolean canPullDown = false;

    //手指按下时记录是否可以继续上拉
    private boolean canPullUp = false;

    //在手指滑动的过程中记录是否移动了布局
    private boolean isMoved = false;

    public ReboundScrollView(Context context) {
        super(context);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //scrollview都高度，非内容高度
    private int height;
    //上级
    private ReboundScrollView bindScrollView;

    //设置上级
    public void setTopView(ReboundScrollView topView) {
        isTop = false;
        bindScrollView = topView;
    }

    //是否是上级
    private boolean isTop = true;

    //下啦返回上级，上级弹性向下显示
    public void moveBack(final View.OnClickListener callback) {
        ValueAnimator animator = ValueAnimator.ofFloat(height, 0);
        animator.setDuration(ANIM_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float topMoveF = (float) animation.getAnimatedValue();
                int topMove = (int) topMoveF;
                params.height = height;
                params.topMargin = -topMove;
                setLayoutParams(params);
                if (topMoveF <= 0) {
                    callback.onClick(null);
                }
            }
        });
    }

    //触发上级显示
    public boolean reset() {
        if (bindScrollView != null) {
            bindScrollView.moveBack(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    params.height = height;
                    params.topMargin = 0;
                    setLayoutParams(params);
                }
            });
            return true;
        }
        return false;
    }


    //弹性重置高度
    private void reboundView() {
        ValueAnimator animator = ValueAnimator.ofFloat(currentMarginTop, 0);
        animator.setDuration(ANIM_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float topMoveF = (float) animation.getAnimatedValue();
                int topMove = (int) topMoveF;
                params.height = height;
                params.topMargin = topMove;
                setLayoutParams(params);
            }
        });
    }

    private int currentMarginTop = 0;
    LinearLayout.LayoutParams params;

    private void initHeight() {
        if (height <= 0) {
            //ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
            params = (LinearLayout.LayoutParams) getLayoutParams();
            height = getHeight();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initHeight();
                //判断是否可以上拉和下拉
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();

                //记录按下时的Y值
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:

                if (!isMoved) break;  //如果没有移动布局， 则跳过执行

                if (isTop) {
                    if (currentMarginTop < -200) {
                        ValueAnimator animator = ValueAnimator.ofFloat(currentMarginTop, -height);
                        animator.setDuration(ANIM_TIME).start();
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float topMoveF = (float) animation.getAnimatedValue();
                                int topMove = (int) topMoveF;
                                params.height = height;
                                params.topMargin = topMove;
                                setLayoutParams(params);
                            }
                        });
                    } else {
                        reboundView();
                    }
                } else {
                    if (currentMarginTop > 200) {
                        if (reset()) {
                        } else {
                            reboundView();
                        }
                    } else {
                        reboundView();
                    }

                }

                //将标志位设回false
                canPullDown = false;
                canPullUp = false;
                isMoved = false;

                break;
            case MotionEvent.ACTION_MOVE:

                //在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if (!canPullDown && !canPullUp) {
                    startY = ev.getY();
                    canPullDown = isCanPullDown();
                    canPullUp = isCanPullUp();

                    break;
                }

                //计算手指移动的距离
                float nowY = ev.getY();
                int deltaY = (int) (nowY - startY);

                //是否应该移动布局
                boolean shouldMove =
                        (canPullDown && deltaY > 0)    //可以下拉， 并且手指向下移动
                                || (canPullUp && deltaY < 0)    //可以上拉， 并且手指向上移动
                                || (canPullUp && canPullDown); //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）

                if (shouldMove) {
                    //计算偏移量
                    currentMarginTop = (int) (deltaY * MOVE_FACTOR);
                    params.height = height;
                    params.topMargin = currentMarginTop;
                    setLayoutParams(params);
                    isMoved = true;  //记录移动了布局
                }

                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * 判断是否滚动到顶部
     */
    private boolean isCanPullDown() {
        return getScrollY() == 0 ||
                getChildAt(0).getHeight() < getHeight() + getScrollY();
    }

    /**
     * 判断是否滚动到底部
     */
    private boolean isCanPullUp() {
        return getChildAt(0).getHeight() <= getHeight() + getScrollY();
    }

}
