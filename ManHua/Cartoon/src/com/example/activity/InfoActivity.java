package com.example.activity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo.ShareParams;

import com.example.cartoon.R;
import com.example.constonce.Constonce;
import com.example.utils.DbManager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.R.color;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示单篇的activity
 * 
 * @author huasheng
 * 
 */
public class InfoActivity extends Activity implements OnClickListener {
	private TextView tvtitle, tvmore;
	private DbManager dbManager;
	private ImageView ivback, imageView_comm, imageView_collect,
			imageView_goodhand, imageView_shared;
	private Intent intent;
	String id;
	private ImageBaseAdapter adapter;
	public LinkedList<Map<String, String>> list_message = new LinkedList<Map<String, String>>();

	private LinkedList<String> list_image = new LinkedList<String>();
	private ListView listview_recommend_specific, listview_recommend_comment;
	
	private DbManager manager;
	Intent intent1;
	String cover_image_url;
	String title;
	String description;
	String nickname;
	String url;
	ContentValues values=new ContentValues();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_specific);
		ShareSDK.initSDK(InfoActivity.this);
		iniData();
		manager=new DbManager(getApplicationContext());
	}

	// 初始化数据
	public void iniData() {
		imageView_shared = (ImageView) this.findViewById(R.id.imageView_shared);
		// 分享绑定监听器
		imageView_shared.setOnClickListener(this);
		dbManager = new DbManager(getApplicationContext());
		imageView_goodhand = (ImageView) this
				.findViewById(R.id.imageView_goodhand);
		imageView_goodhand.setOnClickListener(this);
		intent = getIntent();
		adapter = new ImageBaseAdapter();
		ImageView comm = (ImageView) this.findViewById(R.id.imageView_comm);
		comm.setOnClickListener(this);
		listview_recommend_specific = (ListView) this
				.findViewById(R.id.listview_recommend_specific);
		tvtitle = (TextView) this.findViewById(R.id.textView_title);
		tvtitle.setText(intent.getStringExtra("title"));
		values.put("singlename", tvtitle.getText().toString());
		//values.put("singleurl", intent.getExtras().getString("id"));
		tvmore = (TextView) this.findViewById(R.id.textView_all);
		// 绑定监听事件
		tvmore.setOnClickListener(this);
		ivback = (ImageView) this.findViewById(R.id.imageView_back);
		// 为返回按钮添加监听事件
		ivback.setOnClickListener(this);
		listview_recommend_specific.setAdapter(adapter);
		// 为listview添加尾部view

		// View view = getLayoutInflater().inflate(R.layout.itme_specific_foot,
		// null);
		// imageView_collect = (ImageView) view
		// .findViewById(R.id.imageView_collection);
		// imageView_collect.setOnClickListener(this);
		// listview_recommend_specific.addFooterView(view);
		listview_recommend_specific.setBackgroundColor(Color.WHITE);
		getData();
	}

	// 获取intent 传来的数据来进行下载
	public void getData() {
		id = intent.getStringExtra("id");
		values.put("singleurl", id);
		HttpUtils httpUtils = new HttpUtils();

		// 获取图片json
		httpUtils.send(HttpMethod.GET, Constonce.TODAY_ITEM + id,
				new RequestCallBack<String>() {

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

	// 解析json数据
	public void analysis(String astring) {
		try {
			JSONArray jsonObject = new JSONObject(astring)
					.getJSONObject("data").getJSONArray("images");

			for (int i = 0; i < jsonObject.length(); i++) {
				list_image.addLast(jsonObject.getString(i));
			}
			JSONObject json = new JSONObject(astring).getJSONObject("data")
					.getJSONObject("topic");
			JSONObject user = json.getJSONObject("user");
			Map<String, String> map = new HashMap<String, String>();
			String cover_image_url = json.getString("cover_image_url");
			String title = json.getString("title");
			String description = json.getString("description");
			String nickname = user.getString("nickname");
			String url = "http://api.kuaikanmanhua.com/v1/topics/"
					+ json.getString("id");
			
			map.put("cover_image_url", cover_image_url);
			map.put("title", title);
			map.put("description", description);

			map.put("nickname", nickname);
			map.put("url", url);
			list_message.add(map);

			adapter.notifyDataSetChanged();
			values.put("collname", title);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// 适配器
	public class ImageBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list_image.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list_image.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final HandlView handlView;
			if (convertView == null) {
				handlView = new HandlView();
				convertView = getLayoutInflater().inflate(
						R.layout.item_recommend_image, null);
				handlView.imageView = (ImageView) convertView
						.findViewById(R.id.imageView_item);
				convertView.setTag(handlView);
			} else {
				handlView = (HandlView) convertView.getTag();
			}

			BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
			BitmapDisplayConfig config = new BitmapDisplayConfig();

			bitmapUtils.display(handlView.imageView, list_image.get(position),
					new BitmapLoadCallBack<View>() {

						@Override
						public void onLoadCompleted(View arg0, String arg1,
								Bitmap arg2, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub

							handlView.imageView.setImageBitmap(arg2);

						}

						@Override
						public void onLoadFailed(View arg0, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub

						}

					});

			return convertView;
		}

		class HandlView {
			ImageView imageView;

		}

	}

	//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView_comm:
			Intent intent = new Intent(getApplicationContext(),
					CommentActivity.class);
			intent.putExtra("id", id);
			this.startActivity(intent);
			break;
		// case R.id.imageView_collection:
		// ContentValues values = new ContentValues();
		// values.put("url", Constonce.TODAY_ITEM + id);
		//
		// /*
		// * if (dbManager.insert("collection", values)) {
		// * Toast.makeText(getApplicationContext(), "收藏成功", 0).show(); } else
		// * { Toast.makeText(getApplicationContext(), "收藏失败", 0).show(); }
		// */
		//
		// break;
		case R.id.textView_all:
			skipActivity();

			break;
		case R.id.imageView_back:
			finish();
			break;
		// 向数据库中插入数据来收藏数据
		case R.id.imageView_goodhand:

			//插入单篇数据 ContentValues包含 单篇的url:singleurl 
			//封面图片：picurl,作者名：author, 单篇名：singlename 专题名：collname 
			//String cover_image_url;
			//String title;
			//String description;
			//String nickname;
			//String url;
			
			
			
			values.put("picurl", cover_image_url);
			values.put("author", nickname);
			//values.put("singlename", title);
			manager.insertSingle(values);
			Toast.makeText(getApplicationContext(), "收藏成功", 0).show();
			break;

		// 分享内容
		case R.id.imageView_shared:
			// 分享到新浪微博
			ShareParams sp = new ShareParams();
			sp.setText(Constonce.TODAY_ITEM + id);
			Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
			weibo.share(sp);
			Toast.makeText(getApplicationContext(), "分享成功", 0).show();
			break;
		default:
			break;
		}
	}

	public void skipActivity() {
		intent1= new Intent(getApplicationContext(),
				CartoonCollList.class);
		Bundle bundle = new Bundle();
		bundle.putString("url", list_message.get(0).get("url"));
		bundle.putString("nickname", list_message.get(0).get("nickname"));
		bundle.putString("title", list_message.get(0).get("title"));
		bundle.putString("description", list_message.get(0).get("description"));
		bundle.putString("cover_image_url",
				list_message.get(0).get("cover_image_url"));
		intent1.putExtras(bundle);
		this.startActivity(intent1);

	}

}
