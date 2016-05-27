package com.example.fragment;

import com.example.activity.ActivityMore;
import com.example.cartoon.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QueryFragment extends Fragment implements OnClickListener {
	TextView textView1, textView2, textView3, textView4, textView5, textView6,
			textView7;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_query, null);
		initData(view);
		return view;
	}

	// 初始化数据
	public void initData(View view) {
		textView1 = (TextView) view.findViewById(R.id.textView2);
		textView2 = (TextView) view.findViewById(R.id.textView3);
		textView3 = (TextView) view.findViewById(R.id.textView4);
		textView4 = (TextView) view.findViewById(R.id.textView5);
		textView5 = (TextView) view.findViewById(R.id.textView6);
		textView6 = (TextView) view.findViewById(R.id.textView7);
		textView7 = (TextView) view.findViewById(R.id.textView8);
		setListener();
	}

	// 绑定监听器
	public void setListener() {
		textView1.setOnClickListener(this);
		textView2.setOnClickListener(this);
		textView3.setOnClickListener(this);
		textView4.setOnClickListener(this);
		textView5.setOnClickListener(this);
		textView6.setOnClickListener(this);
		textView7.setOnClickListener(this);

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// 图标点击跳转
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView textView = (TextView) v;
		Intent intent = new Intent(getActivity(), ActivityMore.class);
		Bundle bundle = new Bundle();
		bundle.putString("action", textView.getText() + "");
		bundle.putString("title", textView.getText() + "");
		bundle.putString("type", "type");
		intent.putExtras(bundle);
		getActivity().startActivity(intent);
		Toast.makeText(getActivity(), ">>>>" + textView.getText() + "", 0)
				.show();
	}
}
