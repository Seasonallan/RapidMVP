package com.season.lib.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
* @ClassName: FullListView 
* @Description: 全部显示的ListView
* @author Allan-Jp 
* @date 2014-9-24 上午9:51:05 
*
 */
public class FullListView extends ListView {

	public FullListView(Context context) {
		super(context);
	}

	public FullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
