package com.example.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.activity.ActivityMore;
import com.example.activity.CartoonCollList;

import com.example.cartoon.R;
import com.example.constonce.Constonce;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QuerySpecificFragment extends Fragment implements
		OnRefreshListener<ListView> {
	private String editext_values;
	private PullToRefreshListView ptrListVirew;
	private HttpUtils httpUtils;
	private BitmapUtils bmUtils;
	AMAdapter adapter;
	public int page = 1;

	private LinkedList<Map<String, String>> aList = new LinkedList<Map<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_query_specific, null);
		editext_values = getArguments().getString("editext") + "";
		init(view);
		return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		ptrListVirew = (PullToRefreshListView) view
				.findViewById(R.id.listView_more);

		httpUtils = new HttpUtils();
		bmUtils = new BitmapUtils(getActivity());

		ptrListVirew.setMode(Mode.PULL_FROM_END);
		ptrListVirew.setAdapter(adapter);
		loadData(page);
		adapter = new AMAdapter();
		ptrListVirew.setAdapter(adapter);

		// 按钮点击
		ptrListVirew.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(QuerySpecificFragment.this
						.getActivity(), CartoonCollList.class);
				intent.putExtra("nickname",
						aList.get(position - 1).get("nickname"));
				intent.putExtra("title", aList.get(position - 1).get("title"));
				intent.putExtra("cover_image_url",
						aList.get(position - 1).get("cover_image_url"));
				intent.putExtra("description",
						aList.get(position - 1).get("description"));
				intent.putExtra("url", aList.get(position - 1).get("id"));
				startActivity(intent);
			}
		});
	}

	private void loadData(int page) {
		String url = Constonce.SEARCH_PATH1 + editext_values
				+ Constonce.SEARCH_PATH2 + (15 * page - 15)
				+ Constonce.SEARCH_PATH3 + (15 * page - 1);
		Log.i("===", url);

		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Log.i("===", arg0.result);
				getJsonData(arg0.result);

			}

		});

	}

	// 适配器
	private class AMAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return aList.size() - 1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return aList.get(position - 1);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position - 1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder mViewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_more, null);
				mViewHolder = new ViewHolder();
				mViewHolder.imageView_more = (ImageView) convertView
						.findViewById(R.id.imageView_item_more);
				mViewHolder.textView_title = (TextView) convertView
						.findViewById(R.id.textView1_item_more);
				mViewHolder.textView_author = (TextView) convertView
						.findViewById(R.id.textView2_item_more);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			mViewHolder.textView_author.setText(aList.get(position).get(
					"nickname"));
			mViewHolder.textView_title
					.setText(aList.get(position).get("title"));
			bmUtils.display(mViewHolder.imageView_more, aList.get(position)
					.get("cover_image_url"));
			return convertView;
		}
	}

	class ViewHolder {
		public ImageView imageView_more;
		public TextView textView_title, textView_author;
	}

	// 解析得到json数据
	private void getJsonData(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(json);
			JSONObject data = obj.getJSONObject("data");
			JSONArray topics = data.getJSONArray("topics");
			for (int i = 0; i < topics.length(); i++) {
				JSONObject obj2 = topics.getJSONObject(i);
				String cover_image_url = obj2.getString("cover_image_url");
				String title = obj2.getString("title");
				String description = obj2.getString("description");
				String id = obj2.getString("id");
				JSONObject user = obj2.getJSONObject("user");
				String nickname = user.getString("nickname");
				Map<String, String> map = new HashMap<String, String>();
				// 封面图片
				map.put("cover_image_url", cover_image_url);
				// 全集名
				map.put("title", title);
				// 简介
				map.put("description", description);
				// url
				map.put("id", "http://api.kuaikanmanhua.com/v1/topics/" + id);
				// 作者名
				map.put("nickname", nickname);
				Log.i("=====", map.toString() + " ");
				aList.addLast(map);

			}
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		page++;
		loadData(page);
	}

}
