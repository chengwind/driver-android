package com.xiaoyaosoft.driver51;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.util.Utils;

public class SearchArticleActivity extends ShowTipActivity {
	String filter;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);

	}

	@Override
	protected void init() {
		if (getIntent().getExtras() != null) {
			filter = (String) getIntent().getExtras().get("filter");
		}
		if (Utils.isBlank(filter)) {
			Toast.makeText(getBaseContext(), "请输入查询关键字.", Toast.LENGTH_SHORT)
					.show();
			finish();
			return;
		}
		mTitle = "查询文章:" + filter;
		getArticles();
	}

	protected void getArticles() {
		articles = DBManager.filterArticles(filter);
	}
}
