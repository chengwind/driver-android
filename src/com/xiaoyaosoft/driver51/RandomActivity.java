package com.xiaoyaosoft.driver51;

import android.os.Bundle;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.util.Utils;

public class RandomActivity extends AbstractQuestionActivity {

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("随机练习");
		curPosition = Utils.getRandom(curPosition, questions.size());
		show(curPosition);
		return;
	}

	@Override
	protected void init() {
	}

	@Override
	protected void getCurQuestions() {
		// questions = ttlQuestions;
		MyApp app = ((MyApp) this.getApplication());
		questions = DBManager.getQuestions(app.getCarType(), app
				.getLocationCategory().getId());
	}

	@Override
	public void next() {
		initQuestionView();
		curPosition = Utils.getRandom(curPosition, questions.size());
		show(curPosition);
	}

	@Override
	public void prev() {
		initQuestionView();
		curPosition = Utils.getRandom(curPosition, questions.size());
		show(curPosition);
	}

}