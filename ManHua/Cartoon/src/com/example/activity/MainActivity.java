package com.example.activity;

import com.example.Listener.ImageClick;
import com.example.cartoon.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 主页面底部三个图标

	private ImageView imageView_discover, imageView_person,
			imageView_recommend, imageView_search;
	private FragmentManager manager;
	private LinearLayout linearlayout_button, linearlayout_top;
	private TextView tTitle;
	private RelativeLayout linearlayout_main;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
	}

	// 初始化
	public void initData() {
		tf = Typeface.createFromAsset(getAssets(), "fzgt.ttf");
		tTitle = (TextView) this.findViewById(R.id.textView_title);
		tTitle.setTypeface(tf, Typeface.ITALIC);

		findView();
		// 为layout_fragment背给包
		findViewById(R.id.layout_fragment).setTag("layout_fragment");
		linearlayout_button.setTag("linearlayout_button");
		linearlayout_top.setTag("linearlayout_top");
		// 设置监听器
		setListener();
	}

	public void findView() {
		imageView_discover = (ImageView) findViewById(R.id.imageView_discover);
		imageView_person = (ImageView) findViewById(R.id.imageView_person);
		imageView_recommend = (ImageView) findViewById(R.id.imageView_recommend);
		imageView_search = (ImageView) findViewById(R.id.imageView_search);
		manager = getFragmentManager();
		linearlayout_main = (RelativeLayout) findViewById(R.id.linearLayout_main);
		linearlayout_button = (LinearLayout) findViewById(R.id.linearlayout_button);
		linearlayout_top = (LinearLayout) findViewById(R.id.linearLayout_tophead);
	}

	// 绑定监听器
	public void setListener() {
		ImageClick imageOnClick = new ImageClick(linearlayout_main, manager,
				this);
		imageView_discover.setOnClickListener(imageOnClick);
		imageView_person.setOnClickListener(imageOnClick);
		imageView_recommend.setOnClickListener(imageOnClick);
		imageView_search.setOnClickListener(imageOnClick);

	}
}
