package com.sdp.custom;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdp.model.ImagePoint;
import com.sdp.screen.R;
import com.sdp.screen.ThisApplication;

/**
 * 
 *    
 * �����ƣ�CImageMarkView   
 * ��������ͼƬ��ע�� 
 *        ����ʱ ��ע�����ƶ��������ʾɾ��
 *        չʾʱ ���ɵ��  
 * �����ˣ� huanghaishui   
 * ����ʱ�䣺2014-5-14 ����2:56:25   
 * �޸��ˣ�huanghaishui 
 * �޸�ʱ�䣺2014-5-14 ����2:56:25   
 * �޸ı�ע��   
 * @version    
 *
 */
public class CImageMarkView extends FrameLayout {
	private static int POINTNUM = 5;
	private Context mContext; 
	//��עͼƬ�����ؿ��
	private static int MARKIMAGEWIDTH = 0;
	//��עͼƬ��dp���
	private static int MARKIMAGEWIDTHDP = 10;
	//��ע���ֵ����ؿ��
	private static int MARKTEXTWIDTH = 0;
	//��ע���ֵ�dp���
	private static int MARKTEXTWIDTHDP = 150;
	//��ע���ֵ����ظ߶�
	private static int MARKTEXTHIGHE = 0;
	//��ע���ֵ�dp�߶�
	private static int MARKTEXTHIGHEDP = 25;
	private static int MARGINBETWEENIMAGEANDTEXT = 20;
	//����ͼƬ�Ŀ��
	private int Width=0;
	//��ע�б�
	private ArrayList<ImagePoint> addPointList =null;
	//��ע��ʱid����
	private int tempMarkId = 0;
	//��ʱ�������洢Ҫɾ���ı�ע����
	private ImagePoint tempDelIp = null;
	//��ʾ��ע�ı�ʶ
	private boolean isAddMark = false;
	
	private Activity mActivity = null;
	public CImageMarkView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}
	public CImageMarkView(Context context) {  
        super(context); 
        mContext = context;
        initView();  
    }
	private void initView()
	{
		addPointList = new ArrayList<ImagePoint>(); 
		tempMarkId = 0;
		MARKIMAGEWIDTH = dip2px(mContext, MARKIMAGEWIDTHDP);
		MARKTEXTWIDTH = dip2px(mContext, MARKTEXTWIDTHDP);
		MARKTEXTHIGHE = dip2px(mContext, MARKTEXTHIGHEDP);
	}
	
	/**
	 * 
	 * ��������init 
	 * ���ܣ���ʼ���ؼ�
	 * ������
	 * @param windowManager 
	 * @param bitmap
	 * @param isAddView true Ϊ��ע����ҳ�棬false Ϊ��ʾҳ��
	 * �����ˣ�huanghaishui  
	 * ����ʱ�䣺2014-5-13
	 */
	public void init(Activity activity,Bitmap bitmap,boolean isAddView,ArrayList<ImagePoint> pointList){
		LayoutParams params = (LayoutParams) this.getLayoutParams();
		if (activity.getWindowManager() != null) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Width =  display.getWidth();
			params.width = Width;
			params.height = Width;
			//����Ļ��ȵ�һ��С�����õ�������󳤶ȣ����޸�������󳤶�
			if((Width/2)<MARKTEXTWIDTH){
				MARKTEXTWIDTH = Width/2 -MARKIMAGEWIDTH-MARGINBETWEENIMAGEANDTEXT;
			}
		}
		this.mActivity = activity;
		isAddMark = isAddView;
		this.setLayoutParams(params);
//		this.setBackgroundColor( Color.rgb(255, 182, 193));
		this.setBackgroundResource(R.drawable.a);
		if(!isAddMark && pointList!=null){
			this.addPointList = pointList;
			showPointList();
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int[] location = new  int[2] ;
		this.getLocationInWindow(location);
//		Log.e("getLocationInWindow", location[0]+"-"+location[1]);
//		this.getLocationOnScreen(location);
		if(isAddMark){//��ӱ�ע
			int moveX = (int) event.getX();
			int moveY = (int) event.getY();
			switch (event.getAction()) {
//			case MotionEvent.ACTION_MOVE:
//				Log.e("ACTION_MOVE x-y", moveX+"-"+moveY);
//				break;
			case MotionEvent.ACTION_DOWN:
				
				if(addPointList.size()==POINTNUM){
					Toast.makeText(mContext, "��ע���ֻ����5��", Toast.LENGTH_SHORT).show();
//					DataUtil.showShortToast(mContext, "��ע���ֻ����5��");
					break;
				}
				ImagePoint ip = new ImagePoint();
//				Log.e("��ǰλ��% x-y", (float)moveX/Width+"-"+(float)moveY/Width);
				ip.setX((float)moveX/Width);
				ip.setY((float)moveY/Width);
				ip.setMarkTempId(tempMarkId++);
				int a = new Random().nextInt(100);
				ip.setMarkStr("�������"+a);
				addPointList.add(ip);
//				addPointView(ip,moveX,moveY);
				showPointList();
	            ThisApplication app = (ThisApplication)this.mActivity.getApplication();
	            app.setPointList(addPointList);
				break;
			}
		}else {
			
		}
		return super.onTouchEvent(event);
	}

	
/**
 * 	
 *    
 * �����ƣ�MarkViewClickListener   
 * �������� ɾ����ע���¼�  
 * �����ˣ� huanghaishui   
 * ����ʱ�䣺2014-5-12 ����3:43:08   
 * �޸��ˣ�huanghaishui 
 * �޸�ʱ�䣺2014-5-12 ����3:43:08   
 * �޸ı�ע��   
 * @version    
 *
 */
 private abstract class MarkViewClickListener implements View.OnClickListener{
		 
		 private ImagePoint ip;
		 public MarkViewClickListener(ImagePoint ip) {
			 this.ip = ip;
		 }
		@Override
		public void onClick(View v) {
			onClick(v,ip);
			
		}
		public abstract void onClick(View v,ImagePoint ip); 
		 
	 }
 
 /**
  * 
  * ��������showDelTipDialog 
  * ���ܣ�����dialog��ʾɾ����ע
  * ������
  * �����ˣ�huanghaishui  
  * ����ʱ�䣺2014-5-13
  */
 private void showDelTipDialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	builder.setMessage("ɾ����ע��").setCancelable(true)
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					isDialogShow = false;
					dialog.cancel();
				}
			})
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					isDialogShow = false;
					Message msg = handler.obtainMessage();
					msg.arg1 = 1;
					handler.sendMessage(msg);
					dialog.cancel();
				}
			});
	builder.show();
}
 	/**
	 * 
	 * ��������removePointForView 
	 * ���ܣ��Ƴ���ע����
	 * ������
	 * @param ip
	 * �����ˣ�huanghaishui  
	 * ����ʱ�䣺2014-5-13
	 */
	private void removePointForView(){
		for(int i=0;i<addPointList.size();i++){
			ImagePoint temp = addPointList.get(i);
			if(temp.getMarkTempId() == tempDelIp.getMarkTempId()){
				addPointList.remove(i);
			}
		}
		showPointList();
		
	}
	
	
	/**
	 * 
	 *    
	 * �����ƣ�MoveViewTouchListener   
	 * �������� ��ע�����¼�  
	 * �����ˣ� huanghaishui   
	 * ����ʱ�䣺2014-5-14 ����10:18:47   
	 * �޸��ˣ�huanghaishui 
	 * �޸�ʱ�䣺2014-5-14 ����10:18:47   
	 * �޸ı�ע��   
	 * @version    
	 *
	 */
 private abstract class MoveViewTouchListener implements View.OnTouchListener{
		 
		 private Button btn;
		 private ImagePoint ip;
		 private TextView tv;
		 public MoveViewTouchListener(TextView tv,Button btn,ImagePoint ip) {
			 this.btn = btn;
			 this.ip = ip;
			 this.tv = tv;
		 }
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			onTouchListener(v,tv,btn,ip,event);
			return false;//�������أ�����onclick�¼��޷���Ӧ
		}
		public abstract void onTouchListener(View v,TextView tv,Button btn,ImagePoint ip,MotionEvent event); 
		 
	 }
 
 /**
  * 
  * ��������setTouchListenerToText 
  * ���ܣ�Ϊ�ı�����ӻ����¼�
  * ������
  * @param txt
  * @param btn
  * @param ip
  * �����ˣ�huanghaishui  
  * ����ʱ�䣺2014-5-14
  */
 private void setTouchListenerToText(TextView txt,Button btn,ImagePoint ip){
	 txt.setOnTouchListener(new MoveViewTouchListener(txt,btn,ip) {
			int lastX, lastY;

			@Override
			public void onTouchListener(View v, TextView tv, Button btn,
					ImagePoint ip, MotionEvent event) {
				int ea = event.getAction();
				switch (ea) {
				case MotionEvent.ACTION_DOWN:
					//TODO������break�������޷����У�ԭ��δ֪
					// ��ȡ�����¼�����λ�õ�ԭʼX����
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
//					break;
				case MotionEvent.ACTION_MOVE:
					// event.getRawX();����ƶ���λ��
					int dx = (int) event.getRawX() - lastX;
					int dy = (int) event.getRawY() - lastY;
//					Log.e("����-λ����x-y", dx+"-"+dy);
//					Log.e("����-��ע��ʼλ��", btn.getLeft()+"-"+btn.getBottom()+"-"+btn.getRight()+"-"+btn.getTop());
					int l = btn.getLeft() + dx;
					int b = btn.getBottom() + dy;
					int r = btn.getRight() + dx;
					int t = btn.getTop() + dy;

					// �����ж��ƶ��Ƿ񳬳���Ļ
					if (l < 0) {
						l = 0;
						r = l + btn.getWidth();
					}
					if (t < 0) {
						t = 0;
						b = t + btn.getHeight();
					}
					if (r > Width) {
						r = Width;
						l = r - btn.getWidth();
					}
					if (b > Width) {
						b = Width;
						t = b - btn.getHeight();
					}
					
//					Log.e("��ǰλ��% x-y", (float)moveX/Width+"-"+(float)moveY/Width);
					ip.setX((float)l/Width);
					ip.setY((float)t/Width);
					for(int i=0;i<addPointList.size();i++){
						ImagePoint temp = addPointList.get(i);
						if(temp.getMarkTempId() == ip.getMarkTempId()){
							addPointList.remove(i);
						}
					}
					addPointList.add(ip);
					showPointList();
					break;
				}
				
			}
	    });
 }
 
 private boolean isDialogShow = false;
	/**
	 * 
	 * ��������showPointList 
	 * ���ܣ�չʾ��ע
	 * ������
	 * �����ˣ�huanghaishui  
	 * ����ʱ�䣺2014-5-14
	 */
	private void showPointList(){
		Log.e("��ǰ��ע ����", addPointList.size()+"-");
		this.removeAllViews();
		for(ImagePoint ip:addPointList){
			Button btn = new Button(mContext);
			btn.setBackgroundResource(R.drawable.compose_tag_dot);
			
//			Log.e("��ǰλ�� x-y", ip.getX()+"-"+ip.getY());
//			Log.e("ͼƬ����Ļ�ĸ߶�", y+"");
//			Log.e("ͼƬ���", Width+"");
//			Log.e("��עͼƬ���", MARKIMAGEWIDTH+"");
			int moveX = (int) (Width*ip.getX());
			int moveY = (int) (Width*ip.getY());
			FrameLayout.LayoutParams layParams = new FrameLayout.LayoutParams(MARKIMAGEWIDTH,MARKIMAGEWIDTH);  
            layParams.gravity = Gravity.LEFT | Gravity.TOP;  
            layParams.leftMargin = (moveX+MARKIMAGEWIDTH)>Width?Width-MARKIMAGEWIDTH:moveX;  
            layParams.topMargin = (moveY+MARKIMAGEWIDTH)>Width?Width-MARKIMAGEWIDTH:moveY;  
			this.addView(btn, layParams);
			
			TextView txt = new TextView(mContext);  
            txt.setText(ip.getMarkStr());  
            txt.setTextColor(Color.rgb(255, 255, 255));  
            txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);  
            txt.setGravity(Gravity.CENTER);  
			txt.setSingleLine(true);
			txt.setMaxWidth(MARKTEXTWIDTH);
			txt.setEllipsize(TextUtils.TruncateAt.END);
			FrameLayout.LayoutParams layParamsTxt = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,MARKTEXTHIGHE);  
			
			if(Math.abs(Width-moveX)>moveX){//�ұ�����Ƚϴ�
				layParamsTxt.gravity = Gravity.LEFT | Gravity.TOP;  
				layParamsTxt.leftMargin = (moveX+MARKIMAGEWIDTH+MARGINBETWEENIMAGEANDTEXT);
				//�������ֱ���ͼ
				if(Math.abs(Width-moveY)>moveY){//�ײ������
					txt.setBackgroundResource(R.drawable.compose_tag_bg_1);
				}else{
					txt.setBackgroundResource(R.drawable.compose_tag_bg_2);
				}
			}else{
				layParamsTxt.gravity = Gravity.RIGHT | Gravity.TOP;  
				layParamsTxt.rightMargin = ((Width-moveX)+MARGINBETWEENIMAGEANDTEXT);  
				//�������ֱ���ͼ
				if(Math.abs(Width-moveY)>moveY){//�ײ������
					txt.setBackgroundResource(R.drawable.compose_tag_bg_2);
				}else{
					txt.setBackgroundResource(R.drawable.compose_tag_bg_1);
				}
			}
			if(Math.abs(Width-moveY)>moveY){//�ײ������
				layParamsTxt.topMargin = (moveY+MARKIMAGEWIDTH);  
			}else{
				layParamsTxt.topMargin = (moveY-MARKTEXTHIGHE); 
			}
			
			this.addView(txt, layParamsTxt);
			if(!isAddMark){//����������������¼�
	            btn.setOnClickListener(new MarkViewClickListener(ip){
					@Override
					public void onClick(View v,ImagePoint ip) {
//						DataUtil.showShortToast(mContext, ip.getMarkStr());
						Toast.makeText(mContext, ip.getMarkStr(), Toast.LENGTH_SHORT).show();
					}
				 });
	            
	            txt.setOnClickListener(new MarkViewClickListener(ip){
					@Override
					public void onClick(View v,ImagePoint ip) {
						Toast.makeText(mContext, ip.getMarkStr(), Toast.LENGTH_SHORT).show();
//						DataUtil.showShortToast(mContext, ip.getMarkStr());
					}
				 });  
			}else{//��������������¼�ɾ����ע
				btn.setOnClickListener(new MarkViewClickListener(ip){
					@Override
					public void onClick(View v,ImagePoint ip) {
						if(!isDialogShow){
							tempDelIp = ip;
							showDelTipDialog();
							isDialogShow = true;
						}
					}
				 });
			    
			    txt.setOnClickListener(new MarkViewClickListener(ip){
					@Override
					public void onClick(View v, ImagePoint ip) {
						if(!isDialogShow){
							tempDelIp = ip;
							showDelTipDialog();
							isDialogShow = true;
						}
					}
				 }); 
			    setTouchListenerToText(txt,btn,ip);
			}
		}
	}
//////////////////////////////////////////////////////////////////////
/*TODO:
* 
* �߳���Ϣ
*/
//////////////////////////////////////////////////////////////////////
	 //����Handler����  
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 1:
				removePointForView();
				break;
			}
		}
    	
    };  
    
    
	/** 
     * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    } 
}

