package com.sdp.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sdp.custom.CImageMarkView;
import com.sdp.model.ImagePoint;

public class CImageViewShow extends Activity  implements OnClickListener{
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cmarkview2);
		ThisApplication app = (ThisApplication)this.getApplication();
		ArrayList<ImagePoint> list = app.getPointList();
		CImageMarkView cv = (CImageMarkView) this.findViewById(R.id.mark2);
		cv.init(this, null,false,list);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof Button) {  
			String a =(String) v.getTag();
			Toast.makeText(CImageViewShow.this, a, Toast.LENGTH_SHORT).show();
		}
	}
    
}