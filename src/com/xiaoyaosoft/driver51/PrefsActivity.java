package com.xiaoyaosoft.driver51;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class PrefsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	protected TextView tvTitle;
	ImageButton btnBack;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title);
		setContentView(R.layout.prefs);
		tvTitle = ((TextView) findViewById(R.id.tvTitle));
		intent = getIntent();
		tvTitle.setText("设置");
		btnBack = (ImageButton) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		initSummaries(getPreferenceScreen());
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	private void initSummaries(PreferenceGroup pg) {
		for (int i = 0; i < pg.getPreferenceCount(); ++i) {
			Preference p = pg.getPreference(i);
			if (p instanceof PreferenceGroup)
				initSummaries((PreferenceGroup) p); // recursion
			else
				setSummary(p);
		}
	}

	/**
	 * Set the summaries of the given preference
	 */
	private void setSummary(Preference p) {
		if (p instanceof ListPreference) {
			ListPreference listPref = (ListPreference) p;
			p.setSummary(listPref.getEntry());
		}
		if (p instanceof EditTextPreference) {
			EditTextPreference editTextPref = (EditTextPreference) p;
			p.setSummary(editTextPref.getText());
		}
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		Preference pref = findPreference(key);
		setSummary(pref);
	}

}
