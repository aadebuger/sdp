package com.sdp.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sdp.adapter.ListViewAdapter;
import com.sdp.model.DataInfo;
import com.sdp.model.ImagePoint;

public class PushScreen extends Activity implements OnClickListener,
		OnItemClickListener, OnScrollListener {
	private ArrayList<DataInfo> newsList;
	private ListView listView;
	private ListViewAdapter lvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listtest);
		testData();
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void testData() {
		newsList = new ArrayList<DataInfo>();
		DataInfo di = new DataInfo();
		ArrayList<ImagePoint> ipList = new ArrayList<ImagePoint>();
		ImagePoint ip = new ImagePoint();
		ip.setMarkStr("111111111");
		ip.setX((float) 0.4);
		ip.setY((float) 0.5);
		ipList.add(ip);

		ip = new ImagePoint();
		ip.setMarkStr("222222222");
		ip.setX((float) 0.6);
		ip.setY((float) 0.7);
		ipList.add(ip);

		ip = new ImagePoint();
		ip.setMarkStr("sdfsdfsdf");
		ip.setX((float) 0.3);
		ip.setY((float) 0.9);
		ipList.add(ip);
		di.setImagePointList(ipList);
		newsList.add(di);

		DataInfo di2 = new DataInfo();
		ArrayList<ImagePoint> ipList2 = new ArrayList<ImagePoint>();
		ip = new ImagePoint();
		ip.setMarkStr("dgert");
		ip.setX((float) 0.2);
		ip.setY((float) 0.2);
		ipList2.add(ip);

		ip = new ImagePoint();
		ip.setMarkStr("tyutyut");
		ip.setX((float) 0.8);
		ip.setY((float) 0.4);
		ipList2.add(ip);

		ip = new ImagePoint();
		ip.setMarkStr("bnmgh");
		ip.setX((float) 0.3);
		ip.setY((float) 0.45);
		ipList2.add(ip);
		di2.setImagePointList(ipList2);
		newsList.add(di2);

		di2 = new DataInfo();
		ipList2 = new ArrayList<ImagePoint>();
		ip = new ImagePoint();
		ip.setMarkStr("0.111111");
		ip.setX((float) 0.1);
		ip.setY((float) 0.1);
		ipList2.add(ip);
		di2.setImagePointList(ipList2);
		newsList.add(di2);
	}

	private void initView() {

		listView = (ListView) this.findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		listView.setCacheColorHint(0);
		listView.setDivider(null);

		lvAdapter = new ListViewAdapter(PushScreen.this, this, newsList);
		listView.setAdapter(lvAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = view.getCount() - 1;
		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
				&& view.getLastVisiblePosition() == itemsLastIndex
				&& itemsLastIndex > 8) {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> av, View arg1, int position,
			long arg3) {
		DataInfo info = null;
		ListView listView = (ListView) av;
		info = (DataInfo) listView.getItemAtPosition(position);

	}

	

}
