package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.activity.ActivityMore;
import com.example.activity.CartoonCollList;
import com.example.constonce.Constonce;
import com.example.customview.DiscoverItem;
import com.example.customview.MyJazzyViewPager;
import com.example.cartoon.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class DiscoverFragment extends Fragment {
	public String aa = "vack";
	private List<Map<String, String>> totalList = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> hotList, newList, recommentList, allList;
	private MyJazzyViewPager mViewPager;
	private ArrayList<Map<String, String>> headarr = new ArrayList<Map<String, String>>();
	private BitmapUtils bmUtils;
	protected int[] mores = ALLItem_Discover.mores;
	protected String[] moretitles = { "热门专题", "最新更新", "编辑推荐", "所有专题" };

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("SSSSS", ">>>>>");
		bmUtils = new BitmapUtils(getActivity());
		final View view = inflater.inflate(R.layout.fragment_discover, null);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, Constonce.DISTORY_BODY,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.e("MAIN", "+++++++++++++++++++++++++");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						getJsonData(arg0.result);
						Log.d("MAIN", arg0.result);
						initItem(view);
					}
				});

		mViewPager = (MyJazzyViewPager) view
				.findViewById(R.id.jazzyViewpager_discover);
		loadHeadImage();

		for (int i = 0; i < mores.length; i++) {
			final int ii = i;
			view.findViewById(mores[i]).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String action = totalList.get(ii).get("action");
							String title = moretitles[ii];
							Intent intent = new Intent(getActivity(),
									ActivityMore.class);
							intent.putExtra("action", action);
							intent.putExtra("title", title);
							startActivity(intent);
						}
					});

		}

		return view;

	}

	private void loadHeadImage() {
		// TODO Auto-generated method stub
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, Constonce.DISTORY_HEAD,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						getHeadJsonData(arg0.result);
						setJazzyViewPager();
					}

				});
	}

	private void setJazzyViewPager() {
		// TODO Auto-generated method stub
		// mViewPager.setTransitionEffect(TransitionEffect.Stack);
		mViewPager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				final ImageView imageView = new ImageView(getActivity());
				// imageView.setImageResource(mImgIds[position]);

				bmUtils.display(imageView, headarr.get(position).get("pic"),
						new BitmapLoadCallBack<View>() {

							@Override
							public void onLoadCompleted(View arg0, String arg1,
									Bitmap arg2, BitmapDisplayConfig arg3,
									BitmapLoadFrom arg4) {
								// TODO Auto-generated method stub
								imageView.setImageBitmap(arg2);
							}

							@Override
							public void onLoadFailed(View arg0, String arg1,
									Drawable arg2) {
								// TODO Auto-generated method stub

							}
						});

				imageView.setScaleType(ScaleType.CENTER_CROP);
				container.addView(imageView);
				mViewPager.setObjectForPosition(imageView, position);

				/*
				 * imageView.setScaleType(ScaleType.FIT_XY);
				 * container.addView(imageView);
				 * mViewPager.setObjectForPosition(imageView, position);
				 */
				return imageView;
			}

			@Override
			public int getCount() {
				return headarr.size();
			}
		});
	}

	public void getJsonData(String json) {
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			JSONObject dataObj = obj.getJSONObject("data");
			JSONArray array = dataObj.getJSONArray("topics");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject topics = array.getJSONObject(i);
				String action = topics.getString("action");
				String title = topics.getString("title");
				String topic = topics.getString("topics");
				map.put("action", action);
				map.put("title", title);
				map.put("topic", topic);
				totalList.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		hotList = getJson2(totalList.get(0).get("topic"));
		newList = getJson2(totalList.get(1).get("topic"));
		recommentList = getJson2(totalList.get(2).get("topic"));
		allList = getJson2(totalList.get(3).get("topic"));

	}

	private List<Map<String, String>> getJson2(String topic) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			JSONArray array = new JSONArray(topic);
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject obj = array.getJSONObject(i);
				JSONObject user = obj.getJSONObject("user");
				String nickname = user.getString("nickname");
				String cover_image_url = obj.getString("cover_image_url");
				String title = obj.getString("title");
				String description = obj.getString("description");
				String id = obj.getString("id");

				// 作者
				map.put("nickname", nickname);
				// 封面图片
				map.put("cover_image_url", cover_image_url);
				// title
				map.put("title", title);
				// 简介
				map.put("description", description);
				map.put("id", "http://api.kuaikanmanhua.com/v1/topics/" + id);

				list.add(map);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	public void initItem(View view) {
		int[] hotarr = ALLItem_Discover.hotarr;
		int[] newarr = ALLItem_Discover.newarr;
		int[] recommentarr = ALLItem_Discover.recommentarr;
		int[] allarr = ALLItem_Discover.allarr;
		for (int i = 0; i < hotarr.length; i++) {
			final int ii = i;
			DiscoverItem hots = (DiscoverItem) view.findViewById(hotarr[i]);
			hots.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(),
							hotList.get(ii).get("description"),
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getActivity(),
							CartoonCollList.class);
					intent.putExtra("url", hotList.get(ii).get("id"));
					intent.putExtra("nickname", hotList.get(ii).get("nickname"));
					intent.putExtra("title", hotList.get(ii).get("title"));
					intent.putExtra("cover_image_url",
							hotList.get(ii).get("cover_image_url"));
					intent.putExtra("description",
							hotList.get(ii).get("description"));
					startActivity(intent);

				}
			});
			final String hotsurl = hotList.get(ii).get("cover_image_url");
			final ImageView imgView_hots = hots.getImageView();
			hots.setAuthor(hotList.get(ii).get("nickname"));
			hots.setTitle(hotList.get(ii).get("title"));
			// BitmapUtils bmUtils=new BitmapUtils(getActivity());
			bmUtils.display(imgView_hots, hotsurl,
					new BitmapLoadCallBack<View>() {

						@Override
						public void onLoadCompleted(View arg0, String arg1,
								Bitmap arg2, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub
							imgView_hots.setImageBitmap(arg2);
						}

						@Override
						public void onLoadFailed(View arg0, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub
							imgView_hots
									.setImageResource(R.drawable.found_icon);
							// Log.e("ADAPTER", arg1);
						}
					});

		}
		for (int i = 0; i < recommentarr.length; i++) {
			final int ii = i;
			DiscoverItem recomments = (DiscoverItem) view
					.findViewById(recommentarr[i]);

			recomments.setTitle(recommentList.get(ii).get("title"));
			recomments.setAuthor(recommentList.get(ii).get("nickname"));
			recomments.setImage(recommentList.get(ii).get("cover_image_url"));
			recomments.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							CartoonCollList.class);
					intent.putExtra("url", recommentList.get(ii).get("id"));
					intent.putExtra("nickname",
							recommentList.get(ii).get("nickname"));
					intent.putExtra("title", recommentList.get(ii).get("title"));
					intent.putExtra("description",
							recommentList.get(ii).get("description"));
					intent.putExtra("cover_image_url", recommentList.get(ii)
							.get("cover_image_url"));
					startActivity(intent);
				}
			});
		}

		for (int i = 0; i < newarr.length; i++) {
			final int ii = i;
			String newsurl = newList.get(ii).get("cover_image_url");
			DiscoverItem news = (DiscoverItem) view.findViewById(newarr[i]);
			news.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							CartoonCollList.class);
					intent.putExtra("url", newList.get(ii).get("id"));
					intent.putExtra("nickname", newList.get(ii).get("nickname"));
					intent.putExtra("title", newList.get(ii).get("title"));
					intent.putExtra("description",
							newList.get(ii).get("description"));
					intent.putExtra("cover_image_url",
							newList.get(ii).get("cover_image_url"));
					startActivity(intent);
				}
			});
			news.setAuthor(newList.get(ii).get("nickname"));
			news.setTitle(newList.get(ii).get("title"));
			news.setImage(newsurl);
		}
		for (int i = 0; i < allarr.length; i++) {
			final int ii = i;
			DiscoverItem alls = (DiscoverItem) view.findViewById(allarr[i]);
			alls.setTitle(allList.get(ii).get("title"));
			alls.setAuthor(allList.get(ii).get("nickname"));
			alls.setImage(allList.get(ii).get("cover_image_url"));
			alls.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							CartoonCollList.class);
					intent.putExtra("url", allList.get(ii).get("id"));
					intent.putExtra("nickname", allList.get(ii).get("nickname"));
					intent.putExtra("title", allList.get(ii).get("title"));
					intent.putExtra("description",
							allList.get(ii).get("description"));
					intent.putExtra("cover_image_url",
							allList.get(ii).get("cover_image_url"));

					startActivity(intent);
				}
			});
		}

	}

	private void getHeadJsonData(String json) {
		// TODO Auto-generated method stub

		try {
			JSONObject obj = new JSONObject(json);
			JSONObject data = obj.getJSONObject("data");
			JSONArray array = data.getJSONArray("banner_group");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				String pic = array.getJSONObject(i).getString("pic");
				String value = array.getJSONObject(i).getString("value");
				map.put("pic", pic);
				map.put("value", value);
				headarr.add(map);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
