package com.example.activity;

import com.example.cartoon.R;
import com.example.fragment.QueryFragment;
import com.example.fragment.QuerySpecificFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements TextWatcher,
		OnClickListener {

	private Fragment queryFragment, querySpFragment;

	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private EditText editText_query;
	private TextView textView_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initData();
	}

	// 初始化数据
	public void initData() {
		textView_cancel = (TextView) this.findViewById(R.id.textView_cancel);
		textView_cancel.setOnClickListener(this);
		editText_query = (EditText) this.findViewById(R.id.editText_query);
		queryFragment = new QueryFragment();

		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.linnear_query, queryFragment);
		transaction.commit();
		editText_query.addTextChangedListener(this);

	}

	// 文本改变时触发
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		querySpFragment = new QuerySpecificFragment();
		transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.linnear_query, querySpFragment);

		Bundle bundle = new Bundle();
		bundle.putString("editext", editText_query.getText() + "");
		if (!querySpFragment.isVisible())
			querySpFragment.setArguments(bundle);
		transaction.commit();
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

		if ("".equals(editText_query.getText() + "")) {
			transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.linnear_query, queryFragment);
			transaction.commit();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		editText_query.setText("");

	}
}
