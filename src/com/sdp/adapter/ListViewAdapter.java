package com.sdp.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sdp.custom.CImageMarkView;
import com.sdp.model.DataInfo;
import com.sdp.screen.R;


public class ListViewAdapter extends BaseAdapter {
	private ArrayList<DataInfo> list;
	private LayoutInflater inflater;
	private Activity activity;
	String iurl;
	public ListViewAdapter(Activity activity,Context context, ArrayList<DataInfo> items) {
		this.list = items;
		this.activity = activity;
//		mImageLoader = new CImageLoader(context,Constant.GGPATH,R.drawable.noiamge);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;	
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_listview_info, null);
			holder = new ViewHolder();
			holder.attr0 = (CImageMarkView)convertView.findViewById(R.id.lmark);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		 
		DataInfo info = list.get(position);
		Log.e("", info.getImagePointList().size()+"");
		holder.attr0.init(activity, null,false,info.getImagePointList());
		return convertView;
	}
	 private final class ViewHolder{ 
		 	private CImageMarkView attr0;
	 }
	 
	 
	public void addItem(DataInfo info){
		
		list.add(info);
	}
	public void clearList(){
		if(list.size()>0){
			for(int i=list.size()-1;i>=0;i--){
				list.remove(i);
			}
		}
	}
	

}
