package com.xiaoyaosoft.driver51;

import android.os.Bundle;

import com.xiaoyaosoft.driver51.db.DBManager;

public class FigureActivity extends AbstractQuestionActivity {
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("图标练习");
		lastAnswered = DBManager.getLastId("figure");
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

	public void finish() {
		try {
			if (curPosition > 1) {
				DBManager.updateLast("figure", curPosition);
			}
			super.finish();
		} catch (Exception localException) {
		}
	}

	@Override
	protected void getCurQuestions() {
		questions = DBManager.getFigureQuestions(
				((MyApp) this.getApplication()).getCarType(),
				((MyApp) this.getApplication()).getLocationCategory().getId());
	}

}
