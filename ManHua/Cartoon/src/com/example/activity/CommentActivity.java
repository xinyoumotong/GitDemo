package com.example.activity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.R.color;

import com.example.cartoon.R;
import com.example.constonce.Constonce;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener {
	private ListView list_comment;
	private TextView ivnew_comment, ivhot_comment;
	public LinkedList<Map<String, String>> list_data = new LinkedList<Map<String, String>>();
	public String id = "";
	CommentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_comment);
		initData();
	}

	// 初始化数据
	public void initData() {
		id = getIntent().getStringExtra("id");
		adapter = new CommentAdapter();
		list_comment = (ListView) this.findViewById(R.id.listView_comment);
		list_comment.setAdapter(adapter);
		ivnew_comment = (TextView) this.findViewById(R.id.textView_newcomment);
		ivhot_comment = (TextView) this.findViewById(R.id.textView_hotcomment);
		ivnew_comment.setOnClickListener(this);
		ivhot_comment.setOnClickListener(this);
		downsString(1);
	}

	// 适配器

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 最新评论

		case R.id.textView_newcomment:
			ivnew_comment.setTextColor(Color.WHITE);
			ivnew_comment.setBackgroundColor(Color.GRAY);
			ivhot_comment.setTextColor(Color.BLACK);
			ivhot_comment.setBackgroundColor(Color.WHITE);

			if (list_data.size() == 0)
				downsString(1);
			break;
		case R.id.textView_hotcomment:
			ivnew_comment.setTextColor(Color.BLACK);
			ivnew_comment.setBackgroundColor(Color.WHITE);
			ivhot_comment.setTextColor(Color.WHITE);
			ivhot_comment.setBackgroundColor(Color.GRAY);

			break;

		default:
			break;
		}
	}

	public void downsString(int type) {
		// 最新评论
		if (type == 1) {
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.send(HttpMethod.GET, Constonce.COMMENT_HEAD + id
					+ Constonce.COMMENT_FOOT, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					// TODO Auto-generated method stub
					analysis(arg0.result);

				}
			});
		}
		// 最热门的评论
		if (type == 2) {

		}
	}

	// 解析json数据
	public void analysis(String astring) {
		try {
			JSONArray array = new JSONObject(astring).getJSONObject("data")
					.getJSONArray("comments");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject json = array.getJSONObject(i);

				map.put("content", json.getString("content"));
				map.put("created_at", json.getString("created_at"));
				map.put("likes_count", json.getString("likes_count"));
				map.put("avatar_url",
						json.getJSONObject("user").getString("avatar_url"));
				map.put("nickname",
						json.getJSONObject("user").getString("nickname"));
				list_data.add(map);
			}
			adapter.notifyDataSetChanged();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 适配器
	public class CommentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list_data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HandlView1 handlView1;
			if (convertView == null) {
				handlView1 = new HandlView1();
				convertView = getLayoutInflater().inflate(
						R.layout.item_comment_eachitem, null);
				handlView1.textView_name = (TextView) convertView

				.findViewById(R.id.textView_name);
				handlView1.textView_time = (TextView) convertView
						.findViewById(R.id.textView_time);
				handlView1.textView_comm = (TextView) convertView
						.findViewById(R.id.textView_comm);
				handlView1.textView_nb = (TextView) convertView
						.findViewById(R.id.textView_nb);
				handlView1.imageView = (ImageView) convertView
						.findViewById(R.id.imageView_portrait);
				convertView.setTag(handlView1);
			} else {
				handlView1 = (HandlView1) convertView.getTag();
			}
			handlView1.textView_time.setText(list_data.get(position).get(
					"created_at"));
			handlView1.textView_comm.setText(list_data.get(position).get(
					"content"));
			Bundle list_comm;
			handlView1.textView_nb.setText(list_data.get(position).get(
					"likes_count"));
			handlView1.textView_name.setText(list_data.get(position).get(
					"nickname"));
			BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
			bitmapUtils.display(handlView1.imageView, list_data.get(position)
					.get("avatar_url"));
			return convertView;
		}

		public class HandlView1 {
			ImageView imageView;
			TextView textView_time, textView_comm, textView_name, textView_nb;
		}

	}

}
