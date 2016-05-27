package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*
 *  解析今日推荐的数据包括 id ,titile ,topic中的title
 */

public class Analysis {
	public static List<Map<String, String>> analysisRecommend(String aString) {
		try {
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			JSONArray array = new JSONObject(aString).getJSONObject("data")
					.getJSONArray("comics");
			Log.i("==", array.length() + ">>");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", array.getJSONObject(i).getString("id"));
				map.put("cover_image_url",
						array.getJSONObject(i).getString("cover_image_url"));
				map.put("created_at",
						array.getJSONObject(i).getString("created_at"));
				map.put("title", array.getJSONObject(i).getString("title"));

				String topicid = array.getJSONObject(i).getJSONObject("topic")
						.getString("id");

				map.put("topic_id", topicid);
				Log.i("====", map.toString() + ">>>>>>>>>>");
				String topictitile = array.getJSONObject(i)
						.getJSONObject("topic").getString("title");

				map.put("topic_titile", topictitile);
				data.add(map);
			}

			return data;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
