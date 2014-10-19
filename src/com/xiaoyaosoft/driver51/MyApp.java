package com.xiaoyaosoft.driver51;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.xiaoyaosoft.driver51.db.DBHelper;
import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Category;

public class MyApp extends Application {

	private static final String PREF_CAR_TYPE = "carType";
	private static final String PREF_LOCATION_CATID = "locationCategoryId";

	private String carType;
	private Category locationCategory;

	// private List<Question> ttlQuestions;

	@Override
	public void onCreate() {
		super.onCreate();
		DBHelper helper = new DBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		DBManager.close(db);
		init();
	}

	private void init() {
		this.carType = initCarType();
		this.locationCategory = initLocationCategory();
		// this.ttlQuestions = DBManager.getQuestions(carType,
		// locationCategory.getId());
	}

	protected String initCarType() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String ct = pref.getString(PREF_CAR_TYPE, "1");
		return ct;
	}

	protected Category initLocationCategory() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		int catId = pref.getInt(PREF_LOCATION_CATID, 134);
		Category cat = DBManager.getCategory(catId);
		return cat;
	}

	public void setCarType(String carType) {
		if (!this.carType.equals(carType)) {
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(PREF_CAR_TYPE, carType);
			editor.commit();
			this.carType = carType;
		}
	}

	public void setLocationCategory(int catId) {
		if (this.getLocationCategory().getId() != catId) {
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = pref.edit();
			editor.putInt(PREF_LOCATION_CATID, catId);
			editor.commit();
			Category cat = DBManager.getCategory(catId);
			this.locationCategory = cat;
		}
	}

	public Category getLocationCategory() {
		return locationCategory;
	}

	public void setLocationCategory(Category locationCategory) {
		this.locationCategory = locationCategory;
	}

	public String getCarType() {
		return carType;
	}

}
