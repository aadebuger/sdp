package com.sdp.model;

import java.util.Random;

public class ImagePoint {

	/**
	 * ��ӱ�ע�ǵ���ʱid
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
//		return "�������"+a+"�����ر�ѽ��ѽ��ѽ��ѽ��ѽ���Һܳ�����֪��������ٸ�����";
	}
	public void setMarkStr(String markStr) {
		this.markStr = markStr;
	}
	
	

}
