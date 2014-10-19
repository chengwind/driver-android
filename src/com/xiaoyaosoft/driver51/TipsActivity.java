package com.xiaoyaosoft.driver51;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.xiaoyaosoft.driver51.adapter.TipsAdapter;
import com.xiaoyaosoft.driver51.adapter.TipsAdapter.ListViewItem;
import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Article;
import com.xiaoyaosoft.driver51.util.Utils;

public class TipsActivity extends BaseActivity {
	private ListView lvTips;
	private TipsAdapter tipsAdapter;
	private List<Article> articles = new ArrayList<Article>();
	private String title;
	private int parent_id;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		Intent intent = getIntent();
		parent_id = intent.getIntExtra("parentid", 0);
		title = intent.getStringExtra("title");
		if (Utils.isBlank(title)) {
			title = "文章宝典";
		} else {
			title = "文章宝典 - " + title;
		}
		articles = DBManager.getArticleCats(parent_id);
		tvTitle.setText(title);
		lvTips = ((ListView) findViewById(R.id.lvTips));
		tipsAdapter = new TipsAdapter(this, articles);
		lvTips.setFastScrollEnabled(true);
		lvTips.setAdapter(tipsAdapter);
		lvTips.setOnItemClickListener(favonClicklListener);
	}

	private OnItemClickListener favonClicklListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int paramInt,
				long id) {
			ListViewItem lvi = (ListViewItem) view.getTag();
			if (lvi.article != null && lvi.article.getLevel() == 2) {
				Intent intent = new Intent();
				intent.putExtra("title",
						((Article) articles.get(paramInt)).getTitle());
				intent.putExtra("parentid",
						((Article) articles.get(paramInt)).getId());
				intent.setClass(TipsActivity.this, TipsActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.putExtra("title",
						((Article) articles.get(paramInt)).getTitle());
				intent.putExtra("parentid",
						((Article) articles.get(paramInt)).getId());
				intent.setClass(TipsActivity.this, ShowTipActivity.class);
				startActivity(intent);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void setView() {
		setContentView(R.layout.article);
	}
}
