package com.xiaoyaosoft.driver51;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;

public class FavActivity extends AbstractQuestionActivity {

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("我的收藏");
		btnRight.setText("取消收藏");
		if (questions.size() == 0) {
			Toast.makeText(getBaseContext(), "没有收藏的题目.", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			show(curPosition);
		}
	}

	@Override
	protected void init() {
		// setContentView(R.layout.view_question);
	}

	@Override
	protected void getCurQuestions() {
		questions = DBManager.getFavQuestions();
	}

	@Override
	protected void rightAction() {
		try {
			DBManager.addFav(curQuestion.getId(), false);
			questions = DBManager.getFavQuestions();
			if (questions.size() == 0) {
				Toast.makeText(getBaseContext(), "没有收藏的题目.",
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				if (curPosition > questions.size()) {
					curPosition = questions.size();
				}
				rg.clearCheck();
				rbA.setButtonDrawable(R.drawable.rb_option_f);
				rbB.setButtonDrawable(R.drawable.rb_option_f);
				rbC.setButtonDrawable(R.drawable.rb_option_f);
				rbD.setButtonDrawable(R.drawable.rb_option_f);
				tvExplain.setText("");
				explainLayout.setVisibility(View.INVISIBLE);
				questionLayout.setBackgroundResource(R.drawable.question_bg_n);
				show(curPosition);
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "第" + curPosition + "题取消收藏出错！",
					Toast.LENGTH_SHORT).show();
		}
	}
}
