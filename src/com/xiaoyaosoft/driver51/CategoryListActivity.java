package com.xiaoyaosoft.driver51;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.adapter.CategoryAdapter;
import com.xiaoyaosoft.driver51.adapter.CategoryAdapter.ListViewItem;
import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Category;

public class CategoryListActivity extends BaseActivity {
	private CategoryAdapter mChapterBaseAdapter;
	private List<Category> chapters;
	private ListView lvCategory;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		chapters = DBManager.getCategories(((MyApp) this.getApplication())
				.getLocationCategory().getId());
		lvCategory = ((ListView) findViewById(R.id.lvCategory));
		tvTitle = ((TextView) findViewById(R.id.tvTitle));
		tvTitle.setText("章节练习 - 章节列表");
		mChapterBaseAdapter = new CategoryAdapter(this, chapters);
		lvCategory.setAdapter(mChapterBaseAdapter);
		lvCategory.setOnItemClickListener(itemClickListener);

	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ListViewItem tag = (ListViewItem) view.getTag();
			if (tag.num <= 0) {
				Toast.makeText(getBaseContext(), "没有相关内容.", Toast.LENGTH_SHORT)
						.show();
			} else {
				Intent intent = new Intent();
				intent.putExtra("cid", tag.catId);
				intent.putExtra("title", tag.tvCatTitle.getText());
				intent.setClass(CategoryListActivity.this,
						CategoryActivity.class);
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
		setContentView(R.layout.category);
	}
}