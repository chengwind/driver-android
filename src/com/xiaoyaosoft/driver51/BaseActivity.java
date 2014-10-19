package com.xiaoyaosoft.driver51;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends Activity {

	protected TextView tvTitle;
	ImageButton btnBack;
	ImageButton btnSetting;
//	protected List<Question> ttlQuestions;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setView();
		tvTitle = ((TextView) findViewById(R.id.tvTitle));
		btnBack = (ImageButton) findViewById(R.id.btnBack);
		btnSetting = (ImageButton) findViewById(R.id.btnSetting);
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				backAction();
			}
		});
	}

	protected void backAction() {
		finish();
	}

	abstract protected void setView();

	// protected String getCarType() {
	// SharedPreferences sharedPrefs = PreferenceManager
	// .getDefaultSharedPreferences(this);
	// String carType = sharedPrefs.getString("carType", "1");
	// return carType;
	// }

	protected void startAnimation(View view) {
		Animation animation = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		view.startAnimation(animation);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}