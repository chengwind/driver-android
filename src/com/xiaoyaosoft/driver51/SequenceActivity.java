package com.xiaoyaosoft.driver51;

import android.os.Bundle;

import com.xiaoyaosoft.driver51.db.DBManager;

public class SequenceActivity extends AbstractQuestionActivity {

	public void finish() {
		try {
			if (curPosition > 1) {
				DBManager.updateLast("sequence", curPosition);
			}
			super.finish();
			return;
		} catch (Exception localException) {
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("顺序练习");
		lastAnswered = DBManager.getLastId("sequence");
		if (lastAnswered == 0) {
			curPosition = 1;
			show(curPosition);
		} else {
			curPosition = 1;
			show(curPosition);
			showDialog(DIALOG_CONFIRM_BACKTOLAST);
		}
		return;
	}

	@Override
	protected void init() {
		// setContentView(R.layout.view_question);
	}

	@Override
	protected void getCurQuestions() {
		// questions = ttlQuestions;
		MyApp app = ((MyApp) this.getApplication());
		questions = DBManager.getQuestions(app.getCarType(), app
				.getLocationCategory().getId());
	}

}