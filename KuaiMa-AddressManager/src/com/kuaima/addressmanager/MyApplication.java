package com.kuaima.addressmanager;


import com.kuaima.addressmanager.dao.DaoMaster;
import com.kuaima.addressmanager.dao.DaoSession;
import com.kuaima.addressmanager.dao.DaoMaster.DevOpenHelper;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class MyApplication extends Application {
	
	private static final String TAG = MyApplication.class.getSimpleName();
	
	private static MyApplication instance;
	private static DaoSession daoSession;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
//		int width = outMetrics.widthPixels;
//		Log.d(TAG, "width = " + width);
//		System.out.println("width = " + width);
//		Log.d(TAG, "width-dp = " + ToolUtils.px2dp(this, width));
//		System.out.println("width-dp = " + ToolUtils.px2dp(this, width));
//		Log.d(TAG, "dpi = " + outMetrics.densityDpi);
//		System.out.println("dpi = " + outMetrics.densityDpi);
		
		Const.DEVICE_DPI = outMetrics.densityDpi;
		
		DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "kuai-ma-db", null);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		DaoMaster daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		
		instance = this;
	}

	public static MyApplication getInstance() {
		return instance;
	}
	
	public static DaoSession getDaoSession() {
		return daoSession;
	}

}
