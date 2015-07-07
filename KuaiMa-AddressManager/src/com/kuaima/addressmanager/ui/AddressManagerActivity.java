package com.kuaima.addressmanager.ui;

import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.kuaima.addressmanager.Const;
import com.kuaima.addressmanager.MyApplication;
import com.kuaima.addressmanager.R;
import com.kuaima.addressmanager.adapter.AddressAdapter;
import com.kuaima.addressmanager.dao.Address;
import com.kuaima.addressmanager.dao.AddressDao;
import com.kuaima.addressmanager.util.ToolUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 地址管理主界面
 * 
 * @author chenxiao
 *
 */
public class AddressManagerActivity extends BaseActivity implements View.OnClickListener{

	private static final String TAG = AddressManagerActivity.class
			.getSimpleName();

	private SwipeMenuListView mSwipeMenuListView;
	private Button addBtn;
	private AddressAdapter mAdapter;
	private List<Address> mList;

	public static final String OPEN_TYPE_KEY = "open_type";
	public static final int OPEN_SELECT = 1;
	public static final int OPEN_MANAGE = 2;
	
	private int openType;
	private AddressDao addressDao;
	private long addressId;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Const.ACTION.ADDRESS_ADD)
					|| intent.getAction().equals(Const.ACTION.ADDRESS_MODIFY)) {
				mList.clear();
				mList.addAll(addressDao.queryBuilder().list());
				mAdapter.notifyDataSetChanged();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_manager);

		addressDao = MyApplication.getDaoSession().getAddressDao();
		
		openType = getIntent().getIntExtra(OPEN_TYPE_KEY, OPEN_MANAGE);
		addressId = getIntent().getLongExtra("addressId", -1);
		
		mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.smlv_address);
		addBtn = (Button) findViewById(R.id.btn_add);
		TextView emptyTV = (TextView) findViewById(R.id.tv_empty);
		
		mList = addressDao.queryBuilder().list();
		mAdapter = new AddressAdapter(this, mList, openType, addressId);
		
		mSwipeMenuListView.setEmptyView(emptyTV);
		mSwipeMenuListView.setAdapter(mAdapter);
		mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address address = mList.get(position);
				if (openType == OPEN_MANAGE) {
					Intent intent = new Intent(AddressManagerActivity.this, AddModActivity.class);
					intent.putExtra("address", address);
					startActivity(intent);
				} else {
					Intent data = new Intent();
					data.putExtra("address", address);
					setResult(RESULT_OK, data);
					finish();
				}
			}
		});
		
		if (openType == OPEN_MANAGE) {
			SwipeMenuCreator creator = new SwipeMenuCreator() {
				
				@Override
				public void create(SwipeMenu menu) {
					SwipeMenuItem delItem = new SwipeMenuItem(AddressManagerActivity.this);
					delItem.setBackground(R.drawable.red_gray_selector);
					delItem.setWidth(ToolUtils.dp2px(AddressManagerActivity.this, 90));
					delItem.setTitle("删除");
					delItem.setTitleColor(Color.WHITE);
					delItem.setTitleSize(17);
					menu.addMenuItem(delItem);
					
					SwipeMenuItem defItem = new SwipeMenuItem(AddressManagerActivity.this);
					defItem.setBackground(R.drawable.blue_gray_selector);
					defItem.setWidth(ToolUtils.dp2px(AddressManagerActivity.this, 90));
					defItem.setTitle("默认");
					defItem.setTitleSize(17);
					defItem.setTitleColor(Color.WHITE);
					menu.addMenuItem(defItem);
				}
			};
			
			mSwipeMenuListView.setMenuCreator(creator);
			mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
					switch (index) {
					case 0:
						// 删除
						Address address = mList.remove(position);
						mAdapter.notifyDataSetChanged();
						addressDao.delete(address);
						break;
					case 1:
						// 设置默认
						setDefault(position);
						break;
					default:
						break;
					}
					return true;
				}
			});
		}
		
		addBtn.setOnClickListener(this);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Const.ACTION.ADDRESS_ADD);
		intentFilter.addAction(Const.ACTION.ADDRESS_MODIFY);
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter);
	}

	private void setDefault(int position) {
		for (int i = 0; i < mList.size(); i++) {
			Address address = mList.get(i);
			if (i == position) {
				if (address.getIsDefault()){
					return;
				} else {
					address.setIsDefault(true);
					addressDao.update(address);
				}
			} else if (address.getIsDefault()) {
				address.setIsDefault(false);
				addressDao.update(address);
			}
			mAdapter.notifyDataSetChanged();
		}
	}
	
	@Override
	protected void onDestroy() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			//TODO 添加地址
			
//			mAdapter.setSelectedIndex(3);
			
			Intent intent = new Intent(this, AddModActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
