package com.season.rapiddevelopment.ui.view.refreshview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.season.rapiddevelopment.ui.base.BaseRecycleAdapter;

public class PullToRefreshListView extends PullToRefreshBase<RecyclerView> {

	public PullToRefreshListView(Context context) {
		super(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	RecyclerView recyclerView;
	LinearLayoutManager layoutManager;
	@Override
	protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
		recyclerView = new RecyclerView(context, attrs);
		recyclerView.setVerticalScrollBarEnabled(false);
		//scrollView.setVerticalFadingEdgeEnabled(false);
		//scrollView.setScrollbarFadingEnabled(false);
		recyclerView.setId(android.R.id.list);

		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);
		return recyclerView;
	}

	boolean enableFooterView = true;

	/**
	 * 隐藏底部加载更多
	 */
	public void disableFooterView() {
		enableFooterView = false;
	}

	boolean enableAutoLoading;
	/**
	 * 启动底部自动加载更多
	 */
	public void enableAutoLoadingMore(){
		enableAutoLoading = true;
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (mRecycleAdapter == null){
					return;
				}
				if (mRecycleAdapter.canAutoLoadingMore()){
					if (dy > 4) {
						if (layoutManager.findLastCompletelyVisibleItemPosition() >= layoutManager.getItemCount() - 3) {
							startLoadingMore();
						}
					}
				}
			}
		});
	}

	private void startLoadingMore(){
		if (!mRecycleAdapter.isLoadingMore && onRefreshListener != null) {
			mRecycleAdapter.isLoadingMore = true;
			mRecycleAdapter.notifyDataSetChanged();
			onRefreshListener.onLoadingMore();
		}
	}

	/**
	 * 加载更多出现错误
	 */
	public void errorLoadingMore(){
		if (mRecycleAdapter != null){
			mRecycleAdapter.error();
		}
	}

	/**
	 * 没有更多数据了
	 */
	public void noMore(){
		if (mRecycleAdapter != null){
			mRecycleAdapter.noMore();
		}
	}

	@Override
	public void onRefreshComplete() {
		super.onRefreshComplete();
		if (mRecycleAdapter != null) {
			mRecycleAdapter.isLoadingMore = false;
		}
	}

	@Override
	protected boolean isReadyForPullDown() {
		return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
	}

	BaseRecycleAdapter mRecycleAdapter;
	public void setAdapter(BaseRecycleAdapter adapter) {
		this.mRecycleAdapter = adapter;
		this.mRecycleAdapter.setFooterEnabled(enableFooterView, enableAutoLoading);
		this.mRecycleAdapter.mOnClickListener = new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if (mRecycleAdapter.canLoadingMore()){
					startLoadingMore();
				}
			}
		};
		recyclerView.setAdapter(mRecycleAdapter);
	}

}
