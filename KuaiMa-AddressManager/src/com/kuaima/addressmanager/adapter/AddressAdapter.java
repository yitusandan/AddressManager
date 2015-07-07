package com.kuaima.addressmanager.adapter;

import java.util.List;

import com.kuaima.addressmanager.Const;
import com.kuaima.addressmanager.R;
import com.kuaima.addressmanager.dao.Address;
import com.kuaima.addressmanager.ui.AddressManagerActivity;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Address> mList;
	private int mType;
	private long mAddressId;
	
	public AddressAdapter(Context context, List<Address> addressList) {
		mContext = context;
		mList = addressList;
	}
	
	public AddressAdapter(Context context, List<Address> addressList, int openType) {
		mContext = context;
		mList = addressList;
		mType = openType;
	}
	
	public AddressAdapter(Context context, List<Address> addressList, int openType, long addressId) {
		mContext = context;
		mList = addressList;
		mType = openType;
		mAddressId = addressId;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Address getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_item_address, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Address address = getItem(position);
		if (Const.DEVICE_DPI <= DisplayMetrics.DENSITY_280) {
			viewHolder.usernameTV.setText(address.getUsername());
		} else {
			viewHolder.usernameTV.setText("收货人：" + address.getUsername());
		}
		viewHolder.mobileNoTV.setText(address.getMobileNo());
		if (address.getIsDefault()) {
			viewHolder.defaultIV.setVisibility(View.VISIBLE);
		} else {
			viewHolder.defaultIV.setVisibility(View.GONE);
		}
		
		//TODO selectedIV
		if (mType == AddressManagerActivity.OPEN_SELECT && mAddressId == address.getId()) {
			viewHolder.selectedIV.setVisibility(View.VISIBLE);
		} else {
			viewHolder.selectedIV.setVisibility(View.GONE);
		}
		
		viewHolder.addressTV.setText(address.getArea() 
				+ address.getStreet() + address.getDetail());
		
		
		return convertView;
	}

	public void setSelectedIndex(int selectedIndex) {
		mAddressId = selectedIndex < 0 ? 0 : selectedIndex;
		mAddressId = selectedIndex > getCount() ? getCount() : selectedIndex;
		notifyDataSetChanged();
	}
	
	static class ViewHolder {
		
		TextView usernameTV;
		TextView mobileNoTV;
		ImageView defaultIV;
		ImageView selectedIV;
		TextView addressTV;
		
		public ViewHolder(View view) {
			usernameTV = (TextView) view.findViewById(R.id.tv_username);
			mobileNoTV = (TextView) view.findViewById(R.id.tv_mobile_no);
			defaultIV = (ImageView) view.findViewById(R.id.iv_default);
			selectedIV = (ImageView) view.findViewById(R.id.iv_selected);
			addressTV = (TextView) view.findViewById(R.id.tv_address);
		}
		
	}
}
