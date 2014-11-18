package com.sdp.screen;


import java.util.ArrayList;

import com.sdp.model.ImagePoint;

import android.app.Application;


public class ThisApplication extends Application {
	
    private ArrayList<ImagePoint> pointList;
	@Override
    public void onCreate() {
	    super.onCreate();
	}
	

	public ArrayList<ImagePoint> getPointList() {
		return pointList;
	}

	public void setPointList(ArrayList<ImagePoint> pointList) {
		this.pointList = pointList;
	}
	
	
}