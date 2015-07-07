package com.kuaima.addressmanager;


import com.kuaima.addressmanager.dao.Address;
import com.kuaima.addressmanager.dao.AddressDao;
import com.kuaima.addressmanager.dao.AddressDao.Properties;
import com.kuaima.addressmanager.ui.AddressManagerActivity;
import com.kuaima.addressmanager.ui.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

	private AddressDao addressDao;
	private Address address;
	
	private TextView receiverTV;
	private TextView mobileNoTV;
	private TextView addressTV;
	private TextView nullTV;
	private RelativeLayout addressRL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addressDao = MyApplication.getDaoSession().getAddressDao();
		
		address = addressDao.queryBuilder().where(Properties.IsDefault.eq(true)).unique();
		
		findViewById(R.id.btn_address_manager).setOnClickListener(this);
		nullTV = (TextView) findViewById(R.id.tv_null);
		addressRL = (RelativeLayout) findViewById(R.id.rl_address);
		receiverTV = (TextView) findViewById(R.id.tv_receiver);
		mobileNoTV = (TextView) findViewById(R.id.tv_mobile_no);
		addressTV = (TextView) findViewById(R.id.tv_address);
		
		if (address != null) {
			setAddress();
		} else {
			addressRL.setVisibility(View.GONE);
			nullTV.setVisibility(View.VISIBLE);
		}
		nullTV.setOnClickListener(this);
		addressRL.setOnClickListener(this);
		
	}
	
	private void setAddress() {
		if (Const.DEVICE_DPI <= DisplayMetrics.DENSITY_280) {
			receiverTV.setText(address.getUsername());
		} else {
			receiverTV.setText(getString(R.string.receiver) + address.getUsername());
		}
		
		mobileNoTV.setText(address.getMobileNo());
		addressTV.setText(address.getArea() + address.getStreet() + address.getDetail());
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_address_manager:
			intent = new Intent(this, AddressManagerActivity.class); 
			startActivity(intent);
			break;
		case R.id.rl_address:
			intent = new Intent(this, AddressManagerActivity.class);
			intent.putExtra(AddressManagerActivity.OPEN_TYPE_KEY, AddressManagerActivity.OPEN_SELECT);
			intent.putExtra("addressId", address == null ? -1 :address.getId());
			startActivityForResult(intent, 1);
			break;
		case R.id.tv_null:
			intent = new Intent(this, AddressManagerActivity.class);
			intent.putExtra(AddressManagerActivity.OPEN_TYPE_KEY, AddressManagerActivity.OPEN_SELECT);
			startActivityForResult(intent, 1);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case 1:
			if (data != null) {
				address = (Address) data.getSerializableExtra("address");
				setAddress();
			}
			break;

		default:
			break;
		}
	}
}
