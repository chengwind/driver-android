package com.xiaoyaosoft.driver51;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;

public class CategoryActivity extends AbstractQuestionActivity {
	private int curCid;
	private Intent mIntent;
	private String title;

	public void finish() {
		try {
			if (curPosition > 1) {
				DBManager.updateLast(curCid > 9 ? "cat10" : ("cat" + curCid),
						curPosition);
			}
			super.finish();
		} catch (Exception localException) {
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText(title);
		lastAnswered = DBManager.getLastId(curCid > 9 ? "cat10"
				: ("cat" + curCid));
		if (questions.size() == 0) {
			Toast.makeText(getBaseContext(), "没有相关的题目.", Toast.LENGTH_SHORT)
					.show();
			finish();
			return;
		}
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
		mIntent = getIntent();
		curCid = mIntent.getIntExtra("cid", 1);
		title = mIntent.getStringExtra("title");
	}

	@Override
	protected void getCurQuestions() {
		questions = DBManager.getQuestionsByCat(curCid);
	}

}
