package com.kuaima.addressmanager.ui;

import com.kuaima.addressmanager.R;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends Activity {
	
	
	
	protected void initTopBar(String title) {
		TextView centerTV = (TextView) findViewById(R.id.tv_top_center);
		centerTV.setText(title);

		findViewById(R.id.tv_top_left).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						onBackPressed();
					}
				});

	}

}
