package com.season.rapiddevelopment.ui.view.refreshview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;


public abstract class PullToRefreshBase<T extends View> extends LinearLayout {


    final class SmoothScrollRunnable implements Runnable {

        static final int ANIMATION_DURATION_MS = 190;
        static final int ANIMATION_FPS = 1000 / 60;

        private final Interpolator interpolator;
        private final int scrollToY;
        private final int scrollFromY;
        private final Handler handler;

        private boolean continueRunning = true;
        private long startTime = -1;
        private int currentY = -1;

        public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
            this.handler = handler;
            this.scrollFromY = fromY;
            this.scrollToY = toY;
            this.interpolator = new AccelerateDecelerateInterpolator();
        }

        @Override
        public void run() {

            /**
             * Only set startTime if this is the first time we're starting, else
             * actually calculate the Y delta
             */
            if (startTime == -1) {
                startTime = System.currentTimeMillis();
            } else {

                /**
                 * We do do all calculations in long to reduce software float
                 * calculations. We use 1000 as it gives us good accuracy and
                 * small rounding errors
                 */
                long normalizedTime = (1000 * (System.currentTimeMillis() - startTime)) / ANIMATION_DURATION_MS;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

                final int deltaY = Math.round((scrollFromY - scrollToY)
                        * interpolator.getInterpolation(normalizedTime / 1000f));
                this.currentY = scrollFromY - deltaY;
                setHeaderScroll(currentY);
            }

            // If we're not at the target Y, keep going...
            if (continueRunning && scrollToY != currentY) {
                handler.postDelayed(this, ANIMATION_FPS);
            }
        }

        public void stop() {
            this.continueRunning = false;
            this.handler.removeCallbacks(this);
        }
    }

    ;

    static final float FRICTION = 2.0f;

    static final int PULL_TO_REFRESH = 0x0;
    static final int RELEASE_TO_REFRESH = 0x1;
    static final int REFRESHING = 0x2;

    private int touchSlop;

    private float initialMotionY;
    private float lastMotionX;
    private float lastMotionY;
    private boolean isBeingDragged = false;

    protected int state = PULL_TO_REFRESH;

    private boolean disableScrollingWhileRefreshing = true;

    T refreshableView;
    private boolean isPullToRefreshEnabled = true;

    private LoadingRefreshLayout headerLayout;
    private int headerHeight;

    private final Handler handler = new Handler();

    protected OnRefreshListener onRefreshListener;

    private SmoothScrollRunnable currentSmoothScrollRunnable;


    public PullToRefreshBase(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public final T getRefreshableView() {
        return refreshableView;
    }

    public final boolean isRefreshing() {
        return state == REFRESHING ;
    }

    public final void setPullToRefreshEnabled(boolean enable) {
        this.isPullToRefreshEnabled = enable;
    }

    public final void setDisableScrollingWhileRefreshing(boolean disableScrollingWhileRefreshing) {
        this.disableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
    }


    /**
     * 数据加载结束
     */
    public void onRefreshComplete() {
        if (true || state != PULL_TO_REFRESH) {
            resetHeader();
        }


    }

    public final void setOnRefreshListener(OnRefreshListener listener) {
        onRefreshListener = listener;
    }

    public final void setRefreshing(boolean doScroll) {
        if (!isRefreshing()) {
            setRefreshingInternal(doScroll);
            state = REFRESHING;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isPullToRefreshEnabled) {
            return false;
        }

        if (isRefreshing() && disableScrollingWhileRefreshing) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
            return false;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                if (isBeingDragged) {
                    lastMotionY = event.getY();
                    this.pullEvent();
                    return true;
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    lastMotionY = initialMotionY = event.getY();
                    return true;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (isBeingDragged) {
                    isBeingDragged = false;

                    if (state == RELEASE_TO_REFRESH && null != onRefreshListener) {
                        setRefreshingInternal(true);
                        onRefreshListener.onRefresh();
                    } else {
                        smoothScrollTo(0);
                    }
                    return true;
                }
                break;
            }
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (!isPullToRefreshEnabled) {
            return false;
        }

        if (isRefreshing() && disableScrollingWhileRefreshing) {
            return true;
        }

        final int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isBeingDragged = false;
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN && isBeingDragged) {
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                if (isReadyForPull()) {

                    final float y = event.getY();
                    final float dy = y - lastMotionY;
                    final float yDiff = Math.abs(dy);
                    final float xDiff = Math.abs(event.getX() - lastMotionX);
                    if (yDiff > touchSlop && yDiff > xDiff) {
                        if (dy >= 0.0001f
                                && isReadyForPullDown()) {
                            lastMotionY = y;
                            isBeingDragged = true;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    lastMotionY = initialMotionY = event.getY();
                    lastMotionX = event.getX();
                    isBeingDragged = false;
                }
                break;
            }
        }

        return isBeingDragged;
    }

    protected void addRefreshableView(T refreshableView) {
        addView(refreshableView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
    }

    protected abstract T createRefreshableView(Context context, AttributeSet attrs);


    protected abstract boolean isReadyForPullDown();

    protected void resetHeader() {
        state = PULL_TO_REFRESH;
        isBeingDragged = false;

        if (null != headerLayout) {
            headerLayout.reset();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                smoothScrollTo(0);
            }
        }, 100);

    }

    public void refresh() {
        state = REFRESHING;
        if (null != headerLayout) {
            headerLayout.refreshing();
        }
        smoothScrollTo(-headerHeight);
    }

    public void setRefreshingInternal(boolean doScroll) {
        state = REFRESHING;

        if (null != headerLayout) {
            headerLayout.refreshing();
        }
        if (doScroll) {
            smoothScrollTo(-headerHeight);
        }
    }

    protected final void setHeaderScroll(int y) {
        scrollTo(0, y);
    }

    public final void smoothScrollTo(int y) {
        if (null != currentSmoothScrollRunnable) {
            currentSmoothScrollRunnable.stop();
        }

        if (this.getScrollY() != y) {
            this.currentSmoothScrollRunnable = new SmoothScrollRunnable(handler, getScrollY(), y);
            handler.post(currentSmoothScrollRunnable);
        }
    }


    private void init(Context context, AttributeSet attrs) {

        setOrientation(LinearLayout.VERTICAL);

        touchSlop = ViewConfiguration.getTouchSlop();

        refreshableView = this.createRefreshableView(context, attrs);
        this.addRefreshableView(refreshableView);

        headerLayout = new LoadingRefreshLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = -2;
        addView(headerLayout, 0, params);

        measureView(headerLayout);
        headerHeight = headerLayout.getMeasuredHeight();

        setPadding(0, -headerHeight, 0, 0);
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private boolean pullEvent() {

        final int newHeight;
        final int oldHeight = this.getScrollY();

        newHeight = Math.round(Math.min(initialMotionY - lastMotionY, 0) / FRICTION);

        setHeaderScroll(newHeight);

        if (newHeight != 0) {
            if (state == PULL_TO_REFRESH && headerHeight < Math.abs(newHeight)) {
                state = RELEASE_TO_REFRESH;

                headerLayout.releaseToRefresh();

                return true;

            } else if (state == RELEASE_TO_REFRESH && headerHeight >= Math.abs(newHeight)) {
                state = PULL_TO_REFRESH;
                headerLayout.pullToRefresh();

                return true;
            }
        }

        return oldHeight != newHeight;
    }

    private boolean isReadyForPull() {
        return isReadyForPullDown();
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadingMore();
    }


    @Override
    public void setLongClickable(boolean longClickable) {
        getRefreshableView().setLongClickable(longClickable);
    }
}
