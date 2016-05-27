package com.example.customview;

import com.example.cartoon.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiscoverItem extends LinearLayout{
	
	private TextView textView_title;
	private TextView textView_author;
	private ImageView imageView;
	private Context context;

	public DiscoverItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}
	
	public DiscoverItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		View view=inflater.inflate(R.layout.itemdiscover, this);
		if(!isInEditMode()){
			textView_title=(TextView) view.findViewById(R.id.textView_itemdistory_title);
			textView_author=(TextView) view.findViewById(R.id.textView_itemdistory_author);
			imageView=(ImageView) view.findViewById(R.id.imageView_itemdistory);
			}
		
		
		
		
	}
	/**
	 * 设置标题
	 * @param str
	 */
	public void setTitle(String str){
		textView_title.setText(str);
	}
	/**
	 * 设置作者
	 * @param str
	 */
	public void setAuthor(String str){
		textView_author.setText(str);
	}
	/**
	 * 设置图片
	 * @param bm
	 */
	public void setImage(Bitmap bm){
		imageView.setImageBitmap(bm);
	}
	/**
	 * 设置图片
	 * @param bm
	 */
	public void setImage(int resID){
		imageView.setImageResource(resID);
	}
	/**
	 * 设置图片
	 * @param bm
	 */
	public void setImage(String url){
		BitmapUtils bmUtils=new BitmapUtils(context);
		bmUtils.display(imageView, url, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				// TODO Auto-generated method stub
				imageView.setImageBitmap(arg2);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				// TODO Auto-generated method stub
				imageView.setVisibility(View.GONE);
			}
		});
	}
	
	public ImageView getImageView(){
		return imageView;
	}

}
