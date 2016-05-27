package com.example.Listener;

import com.example.activity.SearchActivity;
import com.example.activity.SetActivity;
import com.example.cartoon.R;
import com.example.fragment.DiscoverFragment;
import com.example.fragment.PersonFragment;
import com.example.fragment.RecommendFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.TransactionTooLargeException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageClick implements OnClickListener {

	private RelativeLayout linearlayout_main;
	private FragmentManager manager;
	private Fragment discover, person, recommend;
	private LinearLayout linearLayout_button, linearLayout_top;
	private FragmentTransaction transaction;
	private Context context;
	private TextView textView_title;
	private ImageView imageSearch;

	public ImageClick() {
		// TODO Auto-generated constructor stub
	}

	public ImageClick(RelativeLayout linearlayout_main,
			FragmentManager manager, Context context) {

		this.linearlayout_main = linearlayout_main;
		this.manager = manager;
		this.context = context;
		discover = new DiscoverFragment();
		person = new PersonFragment();
		recommend = new RecommendFragment();
		linearLayout_button = (LinearLayout) linearlayout_main
				.findViewWithTag("linearlayout_button");
		linearLayout_top = (LinearLayout) linearlayout_main
				.findViewWithTag("linearlayout_top");
		linearLayout_button.getChildAt(0).getId();
		textView_title = (TextView) linearLayout_top.getChildAt(1);
		imageSearch = (ImageView) linearLayout_top.getChildAt(2);
		imageSearch.setTag("查询");
		replaceLinear();

	}

	// 刚刚运行的时候把站位的布局替换成推荐fragment
	public void replaceLinear() {

		transaction = manager.beginTransaction();
		transaction.replace(linearlayout_main
				.findViewWithTag("layout_fragment").getId(), recommend);
		transaction.commit();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		transaction = manager.beginTransaction();
		if (view.getId() == linearLayout_button.getChildAt(0).getId()) {
			transaction.replace(
					linearlayout_main.findViewWithTag("layout_fragment")
							.getId(), recommend);
			textView_title.setText("快看！");
			transaction.commit();
			changeTop(1);
		}
		if (view.getId() == linearLayout_button.getChildAt(1).getId()) {
			transaction.replace(
					linearlayout_main.findViewWithTag("layout_fragment")
							.getId(), discover);
			textView_title.setText("发现");
			transaction.commit();
			changeTop(2);
		}
		if (view.getId() == linearLayout_button.getChildAt(2).getId()) {
			transaction.replace(
					linearlayout_main.findViewWithTag("layout_fragment")
							.getId(), person);
			textView_title.setText("我");
			transaction.commit();
			changeTop(3);
		}

		if (view.getId() == linearLayout_top.getChildAt(2).getId()
				&& "设置".equals(imageSearch.getTag() + "")) {
			Intent intent = new Intent(context, SetActivity.class);
			context.startActivity(intent);
		}
		if (view.getId() == linearLayout_top.getChildAt(2).getId()
				&& "查询".equals(imageSearch.getTag() + "")) {
			Intent intent = new Intent(context, SearchActivity.class);
			context.startActivity(intent);
		}

	}

	// 改变头部和底部的颜色、图片
	public void changeTop(int type) {
		change_Gray();
		imageSearch.setTag("查询");
		switch (type) {
		case 1:
			ImageView iButton1 = (ImageView) linearLayout_button.getChildAt(0);
			iButton1.setImageResource(R.drawable.today_icon);

			imageSearch
					.setImageResource(R.drawable.abc_ic_search_api_mtrl_alpha);
			linearLayout_top.setBackgroundColor(Color.rgb(255, 204, 0));
			break;
		case 2:
			ImageView iButton2 = (ImageView) linearLayout_button.getChildAt(1);
			iButton2.setImageResource(R.drawable.found_icon);
			imageSearch.setImageResource(R.drawable.search_icon);

			linearLayout_top.setBackgroundColor(Color.WHITE);

			break;
		case 3:
			ImageView iButton3 = (ImageView) linearLayout_button.getChildAt(2);
			iButton3.setImageResource(R.drawable.person_icon);
			imageSearch.setImageResource(R.drawable.serch);
			imageSearch.setTag("设置");
			linearLayout_top.setBackgroundColor(Color.WHITE);
			break;

		default:
			break;
		}

	}

	// 改变底部图片为灰色
	public void change_Gray() {
		ImageView iButton1 = (ImageView) linearLayout_button.getChildAt(0);
		iButton1.setImageResource(R.drawable.today_icon_gray);
		ImageView iButton2 = (ImageView) linearLayout_button.getChildAt(1);
		iButton2.setImageResource(R.drawable.found_icon_gray);
		ImageView iButton3 = (ImageView) linearLayout_button.getChildAt(2);
		iButton3.setImageResource(R.drawable.person_icon_gray);

	}
}
