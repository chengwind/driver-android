package com.xiaoyaosoft.driver51;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Question;
import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class MockErrorActivity extends AbstractQuestionActivity {

	private String[] ids_done;

	// private int[] ids = new int[100];

	private List<String> errorList = new ArrayList<String>();

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		btnRight.setText("收藏此题");
		btnLeft.setText("退出考试");
		btnLeft.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.icon_exit_f), null, null);
		this.btnLeft.setVisibility(View.GONE);
		tvTitle.setText("错题分析");
		enableRB(false);
		show(curPosition);
	}

	@Override
	protected void getCurQuestions() {
		questions = new ArrayList<Question>();
		for (int i = 0; i < ids_done.length; i++) {
			String[] ss = ids_done[i].split(Constants.SEPARATOR);
			int id = Integer.valueOf(ss[1]);
			if (Utils.isDone(ids_done[i])) {
				String rightAnswer = ss[2];
				String answer = ss[3];
				if (!rightAnswer.equals(answer)) {
					errorList.add(ids_done[i]);
					questions.add(DBManager.getQuestion(id));
				}
			} else {
				errorList.add(ids_done[i]);
				questions.add(DBManager.getQuestion(id));
			}
		}
	}

	@Override
	protected void init() {
		// setContentView(R.layout.view_question);
		Bundle localBundle = getIntent().getExtras();
		// ids = localBundle.getIntArray("ids");
		ids_done = localBundle.getStringArray("ids_done");
	}

	public void show(int postion) {
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
		if ("A".equals(curQuestion.getSelected())) {
			rbA.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("B".equals(curQuestion.getSelected())) {
			rbB.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("C".equals(curQuestion.getSelected())) {
			rbC.setButtonDrawable(R.drawable.mock_selected_f);
		} else if ("D".equals(curQuestion.getSelected())) {
			rbD.setButtonDrawable(R.drawable.mock_selected_f);
		}
		String selected = "未选";
		if (Utils.isDone(errorList.get(postion - 1))) {
			String[] ss = errorList.get(postion - 1).split(Constants.SEPARATOR);
			selected = ss[3];
		}
		tvExplain.setText("正确答案是 : " + curQuestion.getAnswer() + " ( 你选的是 : "
				+ selected + " )" + "\n" + curQuestion.getExplain());
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

	protected void leftAction() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
