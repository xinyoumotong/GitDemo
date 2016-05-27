package com.example.adapter;

import java.io.ObjectInputStream.GetField;
import java.util.LinkedList;

import java.util.Map;

import com.example.cartoon.R;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecBasedAdapter extends BaseAdapter {
	LinkedList<Map<String, String>> data;
	Context context;

	public RecBasedAdapter(LinkedList<Map<String, String>> data, Context context) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HanderView handerView;

		if (convertView == null) {
			handerView = new HanderView();
			convertView = ((Activity) context).getLayoutInflater().inflate(
					R.layout.item_recommend_list, null);
			handerView.imageView_item = (ImageView) convertView
					.findViewById(R.id.imageView_item);
			handerView.textView_title = (TextView) convertView
					.findViewById(R.id.textView_title);
			handerView.textView_praise = (TextView) convertView
					.findViewById(R.id.textView_praise);
			handerView.textView_comment = (TextView) convertView
					.findViewById(R.id.textView_comment);
			handerView.textView_topictitle = (TextView) convertView
					.findViewById(R.id.textView_topictitle);
			convertView.setTag(handerView);
		} else {
			handerView = (HanderView) convertView.getTag();
		}
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(handerView.imageView_item,
				data.get(position).get("cover_image_url"));
		handerView.textView_title.setText(data.get(position).get("title"));
		
		handerView.textView_topictitle.setText(data.get(position).get(
				"topic_titile"));
		handerView.textView_praise.setText(data.get(position).get(
				"shared_count"));
		handerView.textView_comment.setText(data.get(position).get(
				"likes_count"));
		
		return convertView;
	}

	class HanderView {
		ImageView imageView_item;
		TextView textView_title, textView_topictitle, textView_praise,
				textView_comment;

	}

}
