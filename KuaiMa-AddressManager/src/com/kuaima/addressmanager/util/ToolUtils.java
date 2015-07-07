package com.kuaima.addressmanager.util;

import com.kuaima.addressmanager.MyApplication;

import android.content.Context;
import android.util.TypedValue;

public class ToolUtils {
	
	public static float dp2px (float dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, 
				MyApplication.getInstance().getResources().getDisplayMetrics());
	}
	
	public static float px2dp(float px) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,
				MyApplication.getInstance().getResources().getDisplayMetrics());
	}
	
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(dp * scale + 0.5f);
	}
	
	public static int px2dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int)(px / scale + 0.5f);
	}
	
	
}
