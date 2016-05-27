package com.example.activity;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.example.cartoon.R;
import com.example.cartoon.R.layout;
import com.example.cartoon.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegisterActivity extends Activity {
	private Button button_register;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		button_register = (Button) findViewById(R.id.button_register);
		btn_back = (Button) findViewById(R.id.btn_back);

		SMSSDK.initSDK(RegisterActivity.this, "78aedb8b83a8",
				"4a5da336d4f85be560f93ba2a2ab1841");


	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.button_register:
			button_register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// 打开注册页面
					RegisterPage registerPage = new RegisterPage();
					registerPage.setRegisterCallback(new EventHandler() {
						public void afterEvent(int event, int result,
								Object data) {
							// 解析注册结果
							if (result == SMSSDK.RESULT_COMPLETE) {
								@SuppressWarnings("unchecked")
								HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
								String country = (String) phoneMap
										.get("country");
								String phone = (String) phoneMap.get("phone");

								// 提交用户信息
								registerUser("中国", phone);
							}
						}

						private void registerUser(String country, String phone) {
							// TODO Auto-generated method stub

						}
					});
					registerPage.show(RegisterActivity.this);

				}
			});
			break;
		case R.id.btn_back:
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();

				}
			});
			break;

		default:
			break;
		}
	}

}
