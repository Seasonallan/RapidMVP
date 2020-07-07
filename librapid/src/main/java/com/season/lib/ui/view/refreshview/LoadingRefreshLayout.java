package com.season.lib.ui.view.refreshview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.season.lib.R;


public class LoadingRefreshLayout extends FrameLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView headerImage;
	private final ProgressBar headerProgress;
	private final TextView headerText;

	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingRefreshLayout(Context context) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		headerImage = (ImageView) header
				.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header
				.findViewById(R.id.pull_to_refresh_progress);
		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

	}

	public void reset() {
		headerText.setText("下拉刷新");
		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
	}


	public static final long SECOND = 1000l;
	public static final long MINUTES = 60 * SECOND;
	public static final long HOUR = 60 * MINUTES;
	public static final long DAY = 24 * HOUR;

	public void releaseToRefresh() {
		headerText.setText("松手刷新");
		headerImage.clearAnimation();
		headerImage.startAnimation(rotateAnimation);
	}

	public void refreshing() {
		headerText.setText("刷新中");
		headerImage.clearAnimation();
		headerImage.setVisibility(View.INVISIBLE);
		headerProgress.setVisibility(View.VISIBLE);
	}

	public void pullToRefresh() {
		headerText.setText("下拉刷新");
		headerImage.clearAnimation();
		headerImage.startAnimation(resetRotateAnimation);
	}

}
