package com.xiaoyaosoft.driver51.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xiaoyaosoft.driver51.R;
import com.xiaoyaosoft.driver51.util.Constants;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "51driver.db";
	private static final int DATABASE_VERSION = 10;
	private Context context;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		initDatabase(Constants.FONTS_PATH + "/" + Constants.INFDLL_NAME, true);
		Log.w(Constants.TAG, "Creating database, version : " + DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(Constants.TAG, "Upgrading database, version : " + oldVersion
				+ " -- > " + newVersion);
		initDatabase(Constants.FONTS_PATH + "/" + Constants.INFDLL_NAME, true);
	}

	private void initDatabase(String paramString, boolean force) {
		InputStream is = null;
		FileOutputStream outputDatabase = null;
		if (!new File(paramString).exists() || force) {
			try {
				is = context.getResources().openRawResource(R.raw.inffonts);
				outputDatabase = new FileOutputStream(paramString);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					outputDatabase.write(buffer, 0, length);
				}
				is.close();
				outputDatabase.flush();
				outputDatabase.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
