package com.xiaoyaosoft.driver51;

import java.text.MessageFormat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaoyaosoft.driver51.util.Constants;

public class AboutActivity extends BaseActivity {
	TextView txt1;
	TextView txt2;
	TextView txt3;
	TextView txt4;
	int versionCode;
	String versionName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getVersion();
		txt1 = (TextView) findViewById(R.id.txt1);
		txt1.setText(MessageFormat.format(getString(R.string.infoSoftName),
				versionName, String.valueOf(versionCode)));
		getVersion();
		txt1 = (TextView) findViewById(R.id.txt1);
		txt2 = (TextView) findViewById(R.id.txt2);
		txt3 = (TextView) findViewById(R.id.txt3);
		txt4 = (TextView) findViewById(R.id.txt4);
		txt1.setText(MessageFormat.format(getString(R.string.infoSoftName),
				versionName, String.valueOf(versionCode)));
		txt2.setText(MessageFormat.format("{0}", Constants.AUTHOR_NAME));
		txt3.setText(MessageFormat.format(getString(R.string.infoEmail),
				Constants.AUTHOR_EMAIL));
		txt4.setText(MessageFormat.format("QQ : {0}", Constants.AUTHOR_QQ));

	}

	public void onResume(Context context) {
		super.onResume();
	}

	public void onPause(Context context) {
		super.onPause();
	}

	private void getVersion() {
		try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(
					getApplicationContext().getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			versionName = pinfo.versionName;
			versionCode = pinfo.versionCode;
		} catch (NameNotFoundException e) {
		}
	}

	@Override
	protected void setView() {
		setContentView(R.layout.about);
	}

}
