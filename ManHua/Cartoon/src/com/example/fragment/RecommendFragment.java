package com.example.fragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.activity.InfoActivity;
import com.example.adapter.RecBasedAdapter;
import com.example.cartoon.R;
import com.example.constonce.Constonce;
import com.example.utils.Analysis;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class RecommendFragment extends Fragment implements OnRefreshListener2,
		OnItemClickListener {

	private PullToRefreshListView rListView;
	private RecBasedAdapter recBasedAdapter;
	private LinkedList<Map<String, String>> data = new LinkedList<Map<String, String>>();
	int page = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_recommend, null);
		rListView = (PullToRefreshListView) view
				.findViewById(R.id.refreshListView);
		initData(view);
		return view;
	}

	// 初始化数据
	public void initData(View view) {
		recBasedAdapter = new RecBasedAdapter(data, getActivity());
		rListView = (PullToRefreshListView) view
				.findViewById(R.id.refreshListView);
		rListView.setMode(Mode.BOTH);
		rListView.setAdapter(recBasedAdapter);
		rListView.setOnRefreshListener(this);
		rListView.setOnItemClickListener(this);
		getData(page, 1);
	}

	// 从网络上下载数据
	public void getData(int page, final int type) {
		HttpUtils httpUtils = new HttpUtils();

		httpUtils.send(HttpMethod.GET, Constonce.TODAY + page
				+ Constonce.TODAY_LIMIT, new RequestCallBack<String>() {
			// @Override
			public void onFailure(HttpException arg0, String arg1) {
			}

			@Override
			public void onSuccess(ResponseInfo<String> aString) {
				// TODO Auto-generated method stub
				// 解析数据
				JSONArray array = null;
				try {
					array = new JSONObject(aString.result)
							.getJSONObject("data").getJSONArray("comics");
				} catch (Exception e) {
					// TODO: handle exception
				}

				Log.i("==", array.length() + ">>");
				for (int i = 0; i < array.length(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject obj;
					try {
						obj = array.getJSONObject(i);
						map.put("id", obj.getString("id"));
						map.put("cover_image_url",
								obj.getString("cover_image_url"));
						map.put("created_at", obj.getString("created_at"));
						map.put("title", obj.getString("title"));

						map.put("likes_count", array.getJSONObject(i)
								.getString("likes_count"));
						map.put("shared_count", array.getJSONObject(i)
								.getString("shared_count"));

						String topicid = obj.getJSONObject("topic").getString(
								"id");
						map.put("topic_id", topicid);
						String topictitile = obj.getJSONObject("topic")
								.getString("title");
						map.put("topic_titile", topictitile);

					} catch (Exception e) {
						// TODO: handle exception
					}

					data.add(map);
					if (type == 1) {
						data.addFirst(map);
					} else {
						data.addLast(map);
					}
				}

				recBasedAdapter.notifyDataSetChanged();
				rListView.onRefreshComplete();
			}
		});
	}

	// 刷新数据
	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		// 这个主要用来刷新评论，跟点赞
		page++;
		getData(page, 1);
	}

	// 点击来每一项来跳转到具体信息
	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		// 主意来刷新具体数据
		page++;
		getData(page, 2);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posistion,
			long arg3) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getActivity(), InfoActivity.class);
		// 传title ，id过来
		intent.putExtra("title", data.get(posistion - 1).get("title"));
		intent.putExtra("id", data.get(posistion - 1).get("id"));
		getActivity().startActivity(intent);

	}
}
