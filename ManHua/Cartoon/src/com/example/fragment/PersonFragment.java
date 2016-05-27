package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.activity.CartoonCollList;
import com.example.activity.InfoActivity;
import com.example.activity.LoginActivity;
import com.example.cartoon.R;
import com.example.control.RoundImageView;
import com.example.utils.DbManager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
/**
 * 
 * @author VACK
 *
 */
public class PersonFragment extends Fragment {
	private RoundImageView imageView_login;
	private TextView textView_Collect_project;
	private TextView textView_Collect_single, textView_not_login;
	/*
	 * private TextView line_Collect_project; private TextView
	 * line_Collect_single;
	 */
	private ListView listView_Collect;
	private int[] line = { R.id.line_Collect_single, R.id.line_Collect_project };
	private ArrayList<Map<String, String>> totalList = new ArrayList<Map<String, String>>();;
	DbManager dbManager;
	// private SimpleAdapter adapter;
	private String currentList = "single";
	private SimpleCursorAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		dbManager = new DbManager(getActivity());
		// initdbForTest();
		final View view = inflater.inflate(R.layout.fragment_person, null);
		currentList = "single";
		// 找到登陆头像的控件
		imageView_login = (RoundImageView) view
				.findViewById(R.id.roundImageView);
		textView_Collect_project = (TextView) view
				.findViewById(R.id.textView_Collect_project);
		textView_Collect_single = (TextView) view
				.findViewById(R.id.textView_Collect_single);
		textView_not_login = (TextView) view
				.findViewById(R.id.textView_not_login);
		/*
		 * line_Collect_project = (TextView) view
		 * .findViewById(R.id.line_Collect_project); line_Collect_single =
		 * (TextView) view .findViewById(R.id.line_Collect_single);
		 */
		listView_Collect = (ListView) view.findViewById(R.id.listView_Collect);
		getSingleData();
		
		// 收藏专题的监听事件
		textView_Collect_project.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentList = "coll";
				totalList.clear();
				textView_Collect_project.setTextColor(Color.BLACK);
				// line_Collect_project.setBackgroundColor(Color.GREEN);
				textView_Collect_single.setTextColor(Color.GRAY);
				// line_Collect_project.setBackgroundColor(Color.GRAY);
				for (int i = 0; i < line.length; i++) {
					view.findViewById(line[i]).setBackgroundColor(Color.GRAY);
				}
				view.findViewById(line[1]).setBackgroundColor(Color.BLACK);
				// -"_id","picurl","author","collname","collurl"
				
				Cursor cs = dbManager.queryColl();
				Log.e("ADAPTER", cs.getCount() + "");

				for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
					String picurl = cs.getString(cs.getColumnIndex("picurl"));
					String author = cs.getString(cs.getColumnIndex("author"));
					String collname = cs.getString(cs.getColumnIndex("collname"));
					String collurl = cs.getString(cs.getColumnIndex("collurl"));
					String description = cs.getString(cs.getColumnIndex("description"));
					Map<String, String> map = new HashMap<String, String>();
					map.put("picurl", picurl);
					map.put("author", author);
					map.put("collname", collname);
					map.put("collurl", collurl);
					map.put("description", description);
					totalList.add(map);

				}
				adapter = new SimpleCursorAdapter(getActivity(),
						android.R.layout.simple_list_item_2, cs, new String[] {
								"collname", "author" }, new int[] { android.R.id.text1,
								android.R.id.text2 }, 0);

				listView_Collect.setAdapter(adapter);
			}
		});
		// 收藏单篇的监听事件
		textView_Collect_single.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentList = "single";

				textView_Collect_single.setTextColor(Color.BLACK);
				textView_Collect_project.setTextColor(Color.GRAY);
				for (int i = 0; i < line.length; i++) {
					view.findViewById(line[i]).setBackgroundColor(Color.GRAY);
				}
				view.findViewById(line[0]).setBackgroundColor(Color.BLACK);
				

				
				getSingleData();
				
				

			}
		});
		listView_Collect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("ADAPTER", "POS" + position);
				createDialog(position);
			}
		});

		// 设置登陆头像的点击事件
		imageView_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 点击登陆头像跳转
				Log.e("====", "=========onClick==>>");

				Intent intent = new Intent(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
				Log.e("====", "=========intent==>>");

			}
		});
		return view;
	}

	protected void getSingleData() {
		// TODO Auto-generated method stub
		totalList.clear();
		Cursor cs = dbManager.querySingle();
		// Cursor-"_id","picurl","author","singlename","collname","singleurl"

		// -"_id","picurl","author","singlename","collname","singleurl"
		for (cs.moveToFirst(); !cs.isAfterLast(); cs.moveToNext()) {
			String picurl = cs.getString(cs.getColumnIndex("picurl"));
			String author = cs.getString(cs.getColumnIndex("author"));
			String singlename = cs.getString(cs
					.getColumnIndex("singlename"));
			String collname = cs.getString(cs
					.getColumnIndex("collname"));
			String singleurl = cs.getString(cs
					.getColumnIndex("singleurl"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("picurl", picurl);
			map.put("author", author);
			map.put("singlename", singlename);
			map.put("collname", collname);
			map.put("singleurl", singleurl);
			totalList.add(map);
		}

		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, cs, new String[] {
						"singlename", "collname" }, new int[] {
						android.R.id.text1, android.R.id.text2 }, 0);
		listView_Collect.setAdapter(adapter);
	}

	/**
	 * 尝试向数据库中插入数据；
	 * 
	 * @author ysf
	 */
	/*
	 * private void initdbForTest() { // TODO Auto-generated method stub
	 * //https://www.baidu.com/img/bdlogo.png
	 * 
	 * //插入专题数据 ContentValues中包含 专题的url，封面图片：picurl 作者名：author //专题名：collname
	 * ContentValues collvalues=new ContentValues(); collvalues.put("collurl",
	 * "www.baidu.com"); collvalues.put("picurl",
	 * "https://www.baidu.com/img/bdlogo.png"); collvalues.put("author",
	 * "vack"); collvalues.put("collname", "baidu");
	 * dbManager.insertColl(collvalues); //插入单篇数据 ContentValues包含
	 * 单篇的url:singleurl //封面图片：picurl,作者名：author, 单篇名：singlename 专题名：collname
	 * //picurl varchar(128),author varchar(64),singlename varchar(64),collname
	 * varchar(64),singleurl varchar(128) ContentValues singlevalues=new
	 * ContentValues(); singlevalues.put("singleurl", "www.baidu.com");
	 * singlevalues.put("singlename", "baidu"); singlevalues.put("picurl",
	 * "https://www.baidu.com/img/bdlogo.png");
	 * singlevalues.put("author","vack"); singlevalues.put("singlename",
	 * "baidu"); singlevalues.put("collname", "baidu");
	 * dbManager.insertSingle(singlevalues);
	 * 
	 * 
	 * }
	 */

	protected void createDialog(int pos) {
		AlertDialog.Builder bulilder = new AlertDialog.Builder(getActivity());
		final int position = pos;
		bulilder.setTitle(totalList.get(position).get("collname"));
		bulilder.setItems(new String[] { "打开", "移出" },
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0:
							if (currentList == "single") {
								
								Intent intent = new Intent(getActivity(),
										InfoActivity.class);
								intent.putExtra("id", totalList.get(position)
										.get("singleurl"));
								// intent.putExtra("nickname",
								// totalList.get(position).get("author"));
								intent.putExtra("title", totalList
										.get(position).get("singlename"));
								startActivity(intent);

							} else {
								Intent intent = new Intent(getActivity(),
										CartoonCollList.class);
								intent.putExtra("url", totalList.get(position)
										.get("collurl"));
								intent.putExtra("nickname",
										totalList.get(position).get("author"));
								intent.putExtra(
										"description",
										totalList.get(position).get(
												"description"));
								intent.putExtra("title", totalList
										.get(position).get("collname"));
								intent.putExtra("cover_image_url", totalList
										.get(position).get("picurl"));
								Log.e("==========",
										"===>>"
												+ totalList.get(position).get(
														"collurl"));
								startActivity(intent);
							}
							break;
						case 1:
							if (currentList.equals("single")) {
								dbManager.removeSingle(totalList.get(position)
										.get("singlename"));
								adapter.notifyDataSetChanged();
							} else if (currentList.equals("coll")) {
								dbManager.removeColl(totalList.get(position)
										.get("collname"));
								adapter.notifyDataSetChanged();
							}
							break;
						default:
							break;
						}
					}
				});

		bulilder.show();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sp = getActivity().getSharedPreferences(
				"loginMessage", 0);
		String pic = sp.getString("profile_image_url", "err");
		String name = sp.getString("name", "未登录");
		Log.e("eeee", pic + name);
		textView_not_login.setText(name);
		BitmapUtils bmUtils = new BitmapUtils(getActivity());
		bmUtils.display(new ImageView(getActivity()), pic,
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						// TODO Auto-generated method stub
						Drawable mbdrawable = new BitmapDrawable(
								getResources(), arg2);

						imageView_login.setImageDrawable(mbdrawable);
						// imageView_login.setImageBitmap(arg2);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						// TODO Auto-generated method stub

					}
				});
		if(!name.equals("未登录")){
			imageView_login.setOnClickListener(null);
		}
	}
}
