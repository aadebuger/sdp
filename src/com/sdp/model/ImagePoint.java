package com.sdp.model;

import java.util.Random;

public class ImagePoint {

	/**
	 * 添加标注是的临时id
	 */
	private int markTempId;
	private float x;
	private float y;
	private String markStr;
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getMarkTempId() {
		return markTempId;
	}
	public void setMarkTempId(int markTempId) {
		this.markTempId = markTempId;
	}
	public String getMarkStr() {
		return markStr;
//		int a = new Random().nextInt(100);
//		return "随机数："+a+"测试特别长呀长呀长呀长呀长呀，我很长，不知道输入多少个字了";
	}
	public void setMarkStr(String markStr) {
		this.markStr = markStr;
	}
	
	

}
