package com.xiaoyaosoft.driver51;

import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.util.Utils;

public class SearchQuestionActivity extends AbstractQuestionActivity {
	String filter;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("查询题库:" + filter);
		if (questions.size() == 0) {
			Toast.makeText(getBaseContext(), "没有查到相关的题目.", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			show(curPosition);
		}
		return;
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
		}
	}

	@Override
	protected void getCurQuestions() {
		questions = DBManager.filterQuestions(
				((MyApp) this.getApplication()).getCarType(),
				((MyApp) this.getApplication()).getLocationCategory().getId(),
				filter);
		// for (Question question : ttlQuestions) {
		// if (Utils.filterQuestion(filter, question)) {
		// questions.add(question);
		// }
		// }
	}
}