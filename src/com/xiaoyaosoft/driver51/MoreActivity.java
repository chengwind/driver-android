package com.xiaoyaosoft.driver51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class MoreActivity extends BaseActivity {

	private Button btnShareSMS;
	private Button btnShareOthers;
	private Button btnSuggestion;
	private Button btnDisclaimer;
	private Button btnAbout;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle = ((TextView) findViewById(R.id.tvTitle));
		btnShareSMS = ((Button) findViewById(R.id.btnShareSMS));
		btnShareOthers = ((Button) findViewById(R.id.btnShareOthers));
		btnSuggestion = ((Button) findViewById(R.id.btnSuggestion));
		btnDisclaimer = ((Button) findViewById(R.id.btnDisclaimer));
		btnAbout = ((Button) findViewById(R.id.btnAbout));
		tvTitle.setText("关于我们");
		btnShareSMS.setOnClickListener(clicklListener);
		btnShareOthers.setOnClickListener(clicklListener);
		btnSuggestion.setOnClickListener(clicklListener);
		btnDisclaimer.setOnClickListener(clicklListener);
		btnAbout.setOnClickListener(clicklListener);
	}

	private OnClickListener clicklListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btnShareSMS) {
				Utils.sendSMS(MoreActivity.this,
						getString(R.string.infoSoftLongInfo));
			} else if (v.getId() == R.id.btnShareOthers) {
				Utils.shareOthers(MoreActivity.this, Constants.INFO_SUBJECT,
						Constants.INFO_SHORT, "选择分享方式");
			} else if (v.getId() == R.id.btnSuggestion) {
				Utils.sendEmail(MoreActivity.this, Constants.AUTHOR_EMAIL,
						getString(R.string.infoEmailSuggest), "",
						getString(R.string.infoSendemail));
			} else if (v.getId() == R.id.btnDisclaimer) {
				Intent intent = new Intent(MoreActivity.this,
						DisclaimerActivity.class);
				startActivity(intent);
			} else if (v.getId() == R.id.btnAbout) {
				Intent intent = new Intent(getApplicationContext(),
						AboutActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
			}
			return;
		}
	};

	@Override
	protected void setView() {
		setContentView(R.layout.more);
	}
}
