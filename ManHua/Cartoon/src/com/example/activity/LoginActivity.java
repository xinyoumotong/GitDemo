package com.example.activity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.authorize.e;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.*;

import com.example.cartoon.R;
import com.mob.tools.utils.UIHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements PlatformActionListener {
	private Button button_login;
	private ImageView ImageView_back;
	private TextView textView_register;
	private ImageButton imageButton_login_weibo;
	SharedPreferences preferences;
	
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		ShareSDK.initSDK(LoginActivity.this);
		button_login = (Button) findViewById(R.id.button_login);
		ImageView_back = (ImageView) findViewById(R.id.ImageView_back);
		textView_register = (TextView) findViewById(R.id.textView_register);
		imageButton_login_weibo = (ImageButton) findViewById(R.id.imageButton_login_weibo);
		textView_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SMSSDK.initSDK(LoginActivity.this, "78aedb8b83a8",
						"4a5da336d4f85be560f93ba2a2ab1841");

				// 打开注册页面
				RegisterPage registerPage = new RegisterPage();
				registerPage.setRegisterCallback(new EventHandler() {
					public void afterEvent(int event, int result, Object data) {
						// 解析注册结果
						if (result == SMSSDK.RESULT_COMPLETE) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
							String country = (String) phoneMap.get("country");
							String phone = (String) phoneMap.get("phone");

							// 提交用户信息
							registerUser("中国", phone);
						}
					}

					private void registerUser(String country, String phone) {
						// TODO Auto-generated method stub

					}
				});
				registerPage.show(LoginActivity.this);

			}
		});

		ImageView_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.button_login:

			break;

		case R.id.imageButton_login_weibo:

			Platform wechat = ShareSDK.getPlatform(LoginActivity.this,
					SinaWeibo.NAME);
			wechat.setPlatformActionListener(this);
			wechat.SSOSetting(false);
			wechat.showUser(null);
			break;

		default:
			break;
		}
	}

	// 第三方登录监听器<!-----------------------------------!>
	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub

	}
    
	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		String id, name, description, profile_image_url;
		id = res.get("id").toString();// ID
		name = res.get("name").toString();// 用户名
		description = res.get("description").toString();// 描述
		profile_image_url = res.get("profile_image_url").toString();// 头像链接
		String str = "ID: " + id + ";\n" + "用户名： " + name + ";\n" + "描述："
				+ description + ";\n" + "用户头像地址：" + profile_image_url;
		preferences = getSharedPreferences("loginMessage", MODE_PRIVATE);
		editor = preferences.edit();
		editor.putString("profile_image_url", profile_image_url);
		editor.putString("name", name);
		editor.commit();
		finish();
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub

	}
}
