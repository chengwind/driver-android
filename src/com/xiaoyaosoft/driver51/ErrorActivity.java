package com.xiaoyaosoft.driver51;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.util.Utils;

public class ErrorActivity extends AbstractQuestionActivity {

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvTitle.setText("我的错题");
		btnLeft.setText("删除此题");
		btnLeft.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.icon_del_f), null, null);
		enableRB(false);
		if (questions.size() == 0) {
			Toast.makeText(getBaseContext(), "没有做错的题目.", Toast.LENGTH_SHORT)
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
		questions = DBManager.getErrorQuestions();
	}

	@Override
	protected void show(int postion) {
		if (questions.size() == 0) {
			return;
		}
		if (postion < 1 || postion > questions.size()) {
			postion = 1;
		}
		tvNumber.setText(postion + "/" + questions.size());
		curQuestion = questions.get(postion - 1);
		curQuestion = DBManager.getQuestion(curQuestion.getId());
		tvQuestion.setText(postion + ". " + curQuestion.getQuestion());
		rbA.setText("A. " + curQuestion.getOptionA());
		rbB.setText("B. " + curQuestion.getOptionB());
		rbC.setText("C. " + curQuestion.getOptionC());
		rbD.setText("D. " + curQuestion.getOptionD());
		if (Utils.isBlank(curQuestion.getOptionC())
				&& Utils.isBlank(curQuestion.getOptionD())) {
			rbC.setVisibility(View.GONE);
			rbD.setVisibility(View.GONE);
		} else {
			rbC.setVisibility(View.VISIBLE);
			rbD.setVisibility(View.VISIBLE);
		}
		if (Utils.isBlank(curQuestion.getImg())) {
			ivFigure.setVisibility(View.GONE);
		} else {
			ivFigure.setVisibility(View.VISIBLE);
			mBitmap = Utils.getImageFromAssetsFile(this, "images/question/"
					+ curQuestion.getImg());
			ivFigure.setImageBitmap(mBitmap);
		}
		if (curQuestion.getAnswer().equals("A")) {
			rbA.setButtonDrawable(R.drawable.mock_selected_t);
		} else if (curQuestion.getAnswer().equals("B")) {
			rbB.setButtonDrawable(R.drawable.mock_selected_t);
		} else if (curQuestion.getAnswer().equals("C")) {
			rbC.setButtonDrawable(R.drawable.mock_selected_t);
		} else if (curQuestion.getAnswer().equals("D")) {
			rbD.setButtonDrawable(R.drawable.mock_selected_t);
		}
		if ("A".equals(curQuestion.getSelected())
				&& !curQuestion.getAnswer().equals("A")) {
			rbA.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("B".equals(curQuestion.getSelected())
				&& !curQuestion.getAnswer().equals("B")) {
			rbB.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("C".equals(curQuestion.getSelected())
				&& !curQuestion.getAnswer().equals("C")) {
			rbC.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("D".equals(curQuestion.getSelected())
				&& !curQuestion.getAnswer().equals("D")) {
			rbD.setButtonDrawable(R.drawable.mock_selected_f);
		}
		tvExplain.setText(curQuestion.getExplain());
		explainLayout.setVisibility(View.VISIBLE);
		if (curQuestion.getIsFav() == 0) {
			btnRight.setText("收藏此题");
			btnRight.setCompoundDrawablesWithIntrinsicBounds(null,
					getResources().getDrawable(R.drawable.icon_fav_f), null,
					null);
		} else {
			btnRight.setText("取消收藏");
			btnRight.setCompoundDrawablesWithIntrinsicBounds(null,
					getResources().getDrawable(R.drawable.icon_fav_t), null,
					null);
		}
		startAnimation(llQuestion);
	}

	@Override
	protected void leftAction() {
		try {
			DBManager.deleteError(curQuestion.getId());
			questions = DBManager.getErrorQuestions();
			if (questions.size() == 0) {
				Toast.makeText(getBaseContext(), "没有做错的题目.", Toast.LENGTH_SHORT)
						.show();
				finish();
			} else {
				if (curPosition > questions.size()) {
					curPosition = questions.size();
				}
				initQuestionView();
				show(curPosition);
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "第" + curPosition + "题删除失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

}
