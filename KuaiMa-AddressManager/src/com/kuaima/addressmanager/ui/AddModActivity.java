package com.kuaima.addressmanager.ui;


import com.kuaima.addressmanager.Const;
import com.kuaima.addressmanager.MyApplication;
import com.kuaima.addressmanager.R;
import com.kuaima.addressmanager.dao.Address;
import com.kuaima.addressmanager.dao.AddressDao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddModActivity extends BaseActivity{

	private AddressDao addressDao;
	
	private EditText usernameET;
	private EditText mobileNoET;
	private EditText areaET;
	private EditText streetET;
	private EditText detailET;
	
	private Address address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_mod);
		
		address = (Address) getIntent().getSerializableExtra("address");
		
		initTopBar();
		
		addressDao = MyApplication.getDaoSession().getAddressDao();
		
		usernameET = (EditText) findViewById(R.id.et_username);
		mobileNoET = (EditText) findViewById(R.id.et_mobile_no);
		areaET = (EditText) findViewById(R.id.et_area);
		streetET = (EditText) findViewById(R.id.et_street);
		detailET = (EditText) findViewById(R.id.et_detail);
		
		if (address != null) {
			usernameET.setText(address.getUsername());
			mobileNoET.setText(address.getMobileNo());
			areaET.setText(address.getArea());
			streetET.setText(address.getStreet());
			detailET.setText(address.getDetail());
			usernameET.requestFocus();
		}
		
		
	}
	
	private void initTopBar() {
		TextView leftTV = (TextView) findViewById(R.id.tv_top_left);
		TextView centerTV = (TextView) findViewById(R.id.tv_top_center);
		TextView rightTV = (TextView) findViewById(R.id.tv_top_right);
		
		leftTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		centerTV.setText(R.string.title_address_manager);
		rightTV.setText(R.string.save);
		rightTV.setVisibility(View.VISIBLE);
		rightTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				save();
			}
		});
		
	}
	
	
	private void save() {
		String username = usernameET.getText().toString();
		
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
			usernameET.requestFocus();
			return;
		}
		String mobileNo = mobileNoET.getText().toString();
		if (TextUtils.isEmpty(mobileNo)) {
			Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
			mobileNoET.requestFocus();
			return;
		}
		
		//TODO 校验手机号码
		String area = areaET.getText().toString();
		if (TextUtils.isEmpty(area)) {
			Toast.makeText(this, "请输入省/市/区", Toast.LENGTH_SHORT).show();
			areaET.requestFocus();
			return;
		}
		String street = streetET.getText().toString();
		if (TextUtils.isEmpty(street)) {
			Toast.makeText(this, "请输入街道", Toast.LENGTH_SHORT).show();
			streetET.requestFocus();
			return;
		}
		
		String detail = detailET.getText().toString();
		if (TextUtils.isEmpty(detail)) {
			Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
			detailET.requestFocus();
			return;
		}
		boolean isDefault = false;
		//TODO 保存
		if (address == null) {
			address = new Address(null, username, mobileNo, area, street, detail, isDefault);
			long id = addressDao.insert(address);
			Log.d("tag", "insert address id = " + id);
		} else {
			address.setUsername(username);
			address.setMobileNo(mobileNo);
			address.setArea(area);
			address.setStreet(street);
			address.setDetail(detail);
			address.setIsDefault(isDefault);
			addressDao.update(address);
			Log.d("tag", "update address id = " + address.getId());
		}
		LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Const.ACTION.ADDRESS_ADD));
		finish();
	}
}
