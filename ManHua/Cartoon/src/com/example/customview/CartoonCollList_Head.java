package com.example.customview;

import com.example.cartoon.R;
import com.example.utils.DbManager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CartoonCollList_Head extends LinearLayout {
	private TextView textView_headtitle,textView_author_collListHead;
	private ImageView imageView_collListHead;
	private Context context;
	private TextView textView_start;
	
	
	public CartoonCollList_Head(Context context) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
		View view=LayoutInflater.from(context).inflate(R.layout.cartooncolllist_head, this);
		if(!isInEditMode()){
			textView_headtitle=(TextView) view.findViewById(R.id.textView_headtitle);
			textView_author_collListHead=(TextView) view.findViewById(R.id.textView_author_collListHead);
			imageView_collListHead=(ImageView) view.findViewById(R.id.imageView_collListHead);
			textView_start=(TextView)view.findViewById(R.id.TextView_start);

		}
		
		
	}
	/**
	 * 设置作者与简介
	 * @param author
	 * @param description
	 */
	public void setAuthor(String author,String description){
		textView_author_collListHead.setText(author+"\n");
		textView_author_collListHead.append(description);
	}
	/**
	 * 设置漫画名
	 * @param title
	 */
	public void settitle(String title){
		textView_headtitle.setText(title);
	}
	public void setColl(OnClickListener listener){
		textView_start.setOnClickListener(listener);
	}
	
	public void setHeadImage(String imgurl){
		BitmapUtils bmUtils=new BitmapUtils(context);
		bmUtils.display(imageView_collListHead, imgurl, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				// TODO Auto-generated method stub
				imageView_collListHead.setImageBitmap(arg2);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				// TODO Auto-generated method stub
				Log.e("MAIN", "HeadImageLoadErr----->CartoonCollList_Head");
			}
		});
		
	}
	

}
