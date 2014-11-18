package com.sdp.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sdp.custom.CImageMarkView;

public class CImageViewActivity extends Activity  implements OnClickListener{
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cmarkview);
		CImageMarkView cv = (CImageMarkView) this.findViewById(R.id.mark);
		cv.init(this, null,true,null);
		
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CImageViewActivity.this, CImageViewShow.class);  
				startActivity(intent);			
			}
		 });
		
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CImageViewActivity.this, PushScreen.class);  
				startActivity(intent);
			}
		 });
	}

	@Override
	public void onClick(View v) {
		if (v instanceof Button) {  
			String a =(String) v.getTag();
			Toast.makeText(CImageViewActivity.this, a, Toast.LENGTH_SHORT).show();
		}
	}
    
}