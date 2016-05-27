package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.cartoon.R;
import com.example.customview.CartoonCollList_Head;
import com.example.utils.DbManager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 显示全集全集的activity
 * @author ysf
 *
 */
public class CartoonCollList extends Activity {
	private ListView listView_CollList;
	private List<Map<String, String>> totaList = new ArrayList<Map<String, String>>();

	private ArrayList<String> list = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private DbManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cartooncolllist);
		listView_CollList = (ListView) findViewById(R.id.listView_collList);
		manager=new DbManager(getApplicationContext());
		Bundle bundle = getIntent().getExtras();

		// url "http://api.kuaikanmanhua.com/v1/topics/"+id
		final String urlstr = bundle.getString("url");	
		// 作者
		final String nickname = bundle.getString("nickname");
		// 标题
		final String title = bundle.getString("title");
		// 简介
		final String description = bundle.getString("description");
		// 封面图片
		final String cover_image_url = bundle.getString("cover_image_url");

		CartoonCollList_Head head = new CartoonCollList_Head(
				getApplicationContext());
		head.setAuthor(nickname, description);
		head.settitle(title);
		head.setHeadImage(cover_image_url);
		head.setColl(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//插入专题数据 ContentValues中包含 专题的url:collurl，封面图片：picurl 作者名：author
				//专题名：collname 
				ContentValues values=new ContentValues();
				values.put("collurl", urlstr);
				values.put("picurl", cover_image_url);
				values.put("author", nickname);
				values.put("collname", title);
				values.put("description", description);
				manager.insertColl(values);
				Toast.makeText(CartoonCollList.this, "收藏成功", 0).show();
				
			}
		});
		listView_CollList.addHeaderView(head);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.cartooncolllist_item, R.id.textView_cartoonCollList,
				list);
		listView_CollList.setAdapter(adapter);
		Log.d("ADAPTER", urlstr);

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, urlstr, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("JSON", "ASDSAFDASFSAFSAFSADSADASD");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				getJSON(arg0.result);

				adapter.notifyDataSetChanged();

			}
		});
		listView_CollList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("eeee", position+"");
				if (position>0) {
					Intent intent = new Intent(CartoonCollList.this,
							InfoActivity.class);
					intent.putExtra("id", totaList.get(position - 1).get("id"));
					startActivity(intent);
				}
			}
		});

	}

	public void getJSON(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			JSONObject data = obj.getJSONObject("data");
			JSONArray array = data.getJSONArray("comics");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				String cover_image_url = object.getString("cover_image_url");
				String title = object.getString("title");
				String id = object.getString("id");
				String url = object.getString("url");
				String updated_at = object.getString("updated_at");
				Map<String, String> map = new HashMap<String, String>();
				map.put("cover_image_url", cover_image_url);
				map.put("title", title);
				map.put("url", url);
				map.put("updated_at", updated_at);
				map.put("id", id);
				list.add(title);
				totaList.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
