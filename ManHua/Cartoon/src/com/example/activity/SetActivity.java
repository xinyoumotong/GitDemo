package com.example.activity;

import java.io.File;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.example.cartoon.R;
import com.example.utils.DataCleanManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends Activity {
	private TextView textview_clean_cache;
	private TextView text_recommend;
	private DataCleanManager dataCleanManager;
	private ImageView imageView_back_set;
	private TextView textview_check_update;
	private TextView text_about, textView_good;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		// ShareSDK.initSDK(this);
		textView_good = (TextView) this.findViewById(R.id.textView_goodcom);
		textview_clean_cache = (TextView) findViewById(R.id.clean_cache);
		text_recommend = (TextView) findViewById(R.id.text_recommend);
		imageView_back_set = (ImageView) findViewById(R.id.imageView_back_set);
		dataCleanManager = new DataCleanManager();
		textview_check_update = (TextView) findViewById(R.id.textview_check_update);
		text_about = (TextView) findViewById(R.id.text_about);

		textView_good.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 跳转到安卓市场赏给好评
				Intent intent = new Intent(Intent.ACTION_VIEW);
				ComponentName cn = new ComponentName("com.qihoo.appstore",
						"com.qihoo.appstore.activities.SearchDistributionActivity");
				intent.setComponent(cn);
				intent.setData(Uri
						.parse("market://details?id=com.xiaoma.tuofu"));
				startActivity(intent);

			}
		});

		textview_clean_cache.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("====", "=========SetActivity1==>>");
				// dataCleanManager.cleanDatabases(SetActivity.this);
				dataCleanManager.cleanExternalCache(SetActivity.this);
				// dataCleanManager.cleanInternalCache(SetActivity.this);
				Toast.makeText(SetActivity.this, "清除成功", 0).show();
				Log.e("====", "=========SetActivity2==>>");

			}
		});
		text_recommend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareSDK.initSDK(SetActivity.this);
				OnekeyShare oks = new OnekeyShare();
				// 关闭sso授权
				oks.disableSSOWhenAuthorize();

				// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
				// oks.setNotification(R.drawable.ic_launcher,
				// getString(R.string.app_name));
				// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
				oks.setTitle(getString(R.string.share));
				// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
				oks.setTitleUrl("http://sharesdk.cn");
				// text是分享文本，所有平台都需要这个字段
				oks.setText("我是分享文本");
				// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
				//oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
				// url仅在微信（包括好友和朋友圈）中使用
				oks.setUrl("http://sharesdk.cn");
				// comment是我对这条分享的评论，仅在人人网和QQ空间使用
				oks.setComment("我是测试评论文本");
				// site是分享此内容的网站名称，仅在QQ空间使用
				oks.setSite(getString(R.string.app_name));
				// siteUrl是分享此内容的网站地址，仅在QQ空间使用
				oks.setSiteUrl("http://sharesdk.cn");

				// 启动分享GUI
				oks.show(SetActivity.this);
			}

		});

		imageView_back_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		textview_check_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SetActivity.this, "已是最新版本", 0).show();

			}
		});

		text_about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetActivity.this,
						AboutActivity.class);
				startActivity(intent);

			}
		});
	}
}
