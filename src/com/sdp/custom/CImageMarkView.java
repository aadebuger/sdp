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
 * 类名称：CImageMarkView   
 * 类描述：图片标注类 
 *        新增时 标注可以移动、点击提示删除
 *        展示时 仅可点击  
 * 创建人： huanghaishui   
 * 创建时间：2014-5-14 下午2:56:25   
 * 修改人：huanghaishui 
 * 修改时间：2014-5-14 下午2:56:25   
 * 修改备注：   
 * @version    
 *
 */
public class CImageMarkView extends FrameLayout {
	private static int POINTNUM = 5;
	private Context mContext; 
	//标注图片的像素宽度
	private static int MARKIMAGEWIDTH = 0;
	//标注图片的dp宽度
	private static int MARKIMAGEWIDTHDP = 10;
	//标注文字的像素宽度
	private static int MARKTEXTWIDTH = 0;
	//标注文字的dp宽度
	private static int MARKTEXTWIDTHDP = 150;
	//标注文字的像素高度
	private static int MARKTEXTHIGHE = 0;
	//标注文字的dp高度
	private static int MARKTEXTHIGHEDP = 25;
	private static int MARGINBETWEENIMAGEANDTEXT = 20;
	//背景图片的宽度
	private int Width=0;
	//标注列表
	private ArrayList<ImagePoint> addPointList =null;
	//标注临时id数量
	private int tempMarkId = 0;
	//临时变量，存储要删除的标注数据
	private ImagePoint tempDelIp = null;
	//显示标注的标识
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
	 * 方法名：init 
	 * 功能：初始化控件
	 * 参数：
	 * @param windowManager 
	 * @param bitmap
	 * @param isAddView true 为标注新增页面，false 为显示页面
	 * 创建人：huanghaishui  
	 * 创建时间：2014-5-13
	 */
	public void init(Activity activity,Bitmap bitmap,boolean isAddView,ArrayList<ImagePoint> pointList){
		LayoutParams params = (LayoutParams) this.getLayoutParams();
		if (activity.getWindowManager() != null) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Width =  display.getWidth();
			params.width = Width;
			params.height = Width;
			//若屏幕宽度的一半小于设置的文字最大长度，则修改文字最大长度
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
		if(isAddMark){//添加标注
			int moveX = (int) event.getX();
			int moveY = (int) event.getY();
			switch (event.getAction()) {
//			case MotionEvent.ACTION_MOVE:
//				Log.e("ACTION_MOVE x-y", moveX+"-"+moveY);
//				break;
			case MotionEvent.ACTION_DOWN:
				
				if(addPointList.size()==POINTNUM){
					Toast.makeText(mContext, "标注最多只能有5个", Toast.LENGTH_SHORT).show();
//					DataUtil.showShortToast(mContext, "标注最多只能有5个");
					break;
				}
				ImagePoint ip = new ImagePoint();
//				Log.e("当前位置% x-y", (float)moveX/Width+"-"+(float)moveY/Width);
				ip.setX((float)moveX/Width);
				ip.setY((float)moveY/Width);
				ip.setMarkTempId(tempMarkId++);
				int a = new Random().nextInt(100);
				ip.setMarkStr("随机数："+a);
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
 * 类名称：MarkViewClickListener   
 * 类描述： 删除标注的事件  
 * 创建人： huanghaishui   
 * 创建时间：2014-5-12 下午3:43:08   
 * 修改人：huanghaishui 
 * 修改时间：2014-5-12 下午3:43:08   
 * 修改备注：   
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
  * 方法名：showDelTipDialog 
  * 功能：弹出dialog提示删除标注
  * 参数：
  * 创建人：huanghaishui  
  * 创建时间：2014-5-13
  */
 private void showDelTipDialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	builder.setMessage("删除标注？").setCancelable(true)
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					isDialogShow = false;
					dialog.cancel();
				}
			})
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
	 * 方法名：removePointForView 
	 * 功能：移除标注数据
	 * 参数：
	 * @param ip
	 * 创建人：huanghaishui  
	 * 创建时间：2014-5-13
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
	 * 类名称：MoveViewTouchListener   
	 * 类描述： 标注滑动事件  
	 * 创建人： huanghaishui   
	 * 创建时间：2014-5-14 上午10:18:47   
	 * 修改人：huanghaishui 
	 * 修改时间：2014-5-14 上午10:18:47   
	 * 修改备注：   
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
			return false;//不能拦截，否则onclick事件无法响应
		}
		public abstract void onTouchListener(View v,TextView tv,Button btn,ImagePoint ip,MotionEvent event); 
		 
	 }
 
 /**
  * 
  * 方法名：setTouchListenerToText 
  * 功能：为文本框添加滑动事件
  * 参数：
  * @param txt
  * @param btn
  * @param ip
  * 创建人：huanghaishui  
  * 创建时间：2014-5-14
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
					//TODO：不能break，否则无法运行，原因未知
					// 获取触摸事件触摸位置的原始X坐标
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
//					break;
				case MotionEvent.ACTION_MOVE:
					// event.getRawX();获得移动的位置
					int dx = (int) event.getRawX() - lastX;
					int dy = (int) event.getRawY() - lastY;
//					Log.e("滑动-位移量x-y", dx+"-"+dy);
//					Log.e("滑动-标注初始位置", btn.getLeft()+"-"+btn.getBottom()+"-"+btn.getRight()+"-"+btn.getTop());
					int l = btn.getLeft() + dx;
					int b = btn.getBottom() + dy;
					int r = btn.getRight() + dx;
					int t = btn.getTop() + dy;

					// 下面判断移动是否超出屏幕
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
					
//					Log.e("当前位置% x-y", (float)moveX/Width+"-"+(float)moveY/Width);
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
	 * 方法名：showPointList 
	 * 功能：展示标注
	 * 参数：
	 * 创建人：huanghaishui  
	 * 创建时间：2014-5-14
	 */
	private void showPointList(){
		Log.e("当前标注 数量", addPointList.size()+"-");
		this.removeAllViews();
		for(ImagePoint ip:addPointList){
			Button btn = new Button(mContext);
			btn.setBackgroundResource(R.drawable.compose_tag_dot);
			
//			Log.e("当前位置 x-y", ip.getX()+"-"+ip.getY());
//			Log.e("图片在屏幕的高度", y+"");
//			Log.e("图片宽度", Width+"");
//			Log.e("标注图片宽度", MARKIMAGEWIDTH+"");
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
			
			if(Math.abs(Width-moveX)>moveX){//右边区域比较大
				layParamsTxt.gravity = Gravity.LEFT | Gravity.TOP;  
				layParamsTxt.leftMargin = (moveX+MARKIMAGEWIDTH+MARGINBETWEENIMAGEANDTEXT);
				//设置文字背景图
				if(Math.abs(Width-moveY)>moveY){//底部区域大
					txt.setBackgroundResource(R.drawable.compose_tag_bg_1);
				}else{
					txt.setBackgroundResource(R.drawable.compose_tag_bg_2);
				}
			}else{
				layParamsTxt.gravity = Gravity.RIGHT | Gravity.TOP;  
				layParamsTxt.rightMargin = ((Width-moveX)+MARGINBETWEENIMAGEANDTEXT);  
				//设置文字背景图
				if(Math.abs(Width-moveY)>moveY){//底部区域大
					txt.setBackgroundResource(R.drawable.compose_tag_bg_2);
				}else{
					txt.setBackgroundResource(R.drawable.compose_tag_bg_1);
				}
			}
			if(Math.abs(Width-moveY)>moveY){//底部区域大
				layParamsTxt.topMargin = (moveY+MARKIMAGEWIDTH);  
			}else{
				layParamsTxt.topMargin = (moveY-MARKTEXTHIGHE); 
			}
			
			this.addView(txt, layParamsTxt);
			if(!isAddMark){//非新增操作，点击事件
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
			}else{//新增操作，点击事件删除标注
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
* 线程消息
*/
//////////////////////////////////////////////////////////////////////
	 //创建Handler对象  
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
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    } 
}

