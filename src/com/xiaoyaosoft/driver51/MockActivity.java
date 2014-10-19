package com.xiaoyaosoft.driver51;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Question;
import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class MockActivity extends AbstractQuestionActivity {
	String[] ids_done;
	int yetdo = 100;

	private Handler handle = null;
	private Runnable run = new Runnable() {
		int i = 45;
		int j = 0;

		public void run() {
			String timing = "45:00";
			if ((i > 9) && (j > 0)) {
				j = j - 1;
				if (j < 10) {
					timing = String.valueOf(i) + ":0" + String.valueOf(j);
				} else {
					timing = String.valueOf(i) + ":" + String.valueOf(j);
				}
			} else if ((i < 10) && (j > 0)) {
				j = j - 1;
				if (j < 10) {
					timing = "0" + String.valueOf(i) + ":0" + String.valueOf(j);
				} else {
					timing = "0" + String.valueOf(i) + ":" + String.valueOf(j);
				}
			} else if ((i > 0) && (j <= 0)) {
				i = i - 1;
				j = 59;
				if (i < 10) {
					timing = "0" + String.valueOf(i) + ":" + String.valueOf(59);
				} else {
					timing = String.valueOf(i) + ":" + String.valueOf(59);
				}
			} else {
				timing = "00:00";
			}

			if ((i > 0) || (j > 0)) {
				tvNumber.setText(timing);
				handle.postDelayed(run, Constants.INTERVAL_TIME);
			} else if ((i <= 0) && (j <= 0)) {
				Toast.makeText(MockActivity.this, "考试时间到, 自动交卷.",
						Toast.LENGTH_SHORT).show();
				tvNumber.setText(timing);
				leftAction();
			}
		}
	};

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		tvNumber.setText("45:00");
		tvTitle.setText("模拟考试");
		btnLeft.setText("交卷");
		btnLeft.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.icon_finish_f), null, null);
		btnRight.setText("未做(" + yetdo + ")");
		btnRight.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.icon_yetdo_f), null, null);
		show(curPosition);
		handle = new Handler();
		startTimer();
		return;
	}

	@Override
	protected void init() {
		// setContentView(R.layout.view_question);
	}

	public void startTimer() {
		Toast.makeText(this, "考试共45分钟,倒计时现在开始.", Toast.LENGTH_SHORT).show();
		handle.removeCallbacks(run);
		handle.postDelayed(run, Constants.INTERVAL_TIME);
	}

	@Override
	protected void getCurQuestions() {
		questions = new ArrayList<Question>();
		MyApp app = ((MyApp) this.getApplication());
		List<Question> ttlQuestions = DBManager.getQuestions(app.getCarType(),
				app.getLocationCategory().getId());
		Random rand = new Random();
		if (ttlQuestions.size() > Constants.TOTAL_TEST_QUESTIONS) {
			while (questions.size() < Constants.TOTAL_TEST_QUESTIONS) {
				Question question = ttlQuestions.get(rand.nextInt(ttlQuestions
						.size()));
				if (!questions.contains(question)) {
					question.setSelected("0");
					questions.add(question);
				}
			}
		} else {
			questions = new ArrayList<Question>(ttlQuestions);
		}
		ids_done = new String[questions.size()];
		for (int i = 0; i < ids_done.length; i++) {
			ids_done[i] = String.valueOf(i + 1) + Constants.SEPARATOR
					+ questions.get(i).getId();
		}
	}

	@Override
	protected void checkIt(int paramInt) {
		String selected = "";
		if (paramInt == rbA.getId()) {
			selected = "A";
			rbA.setButtonDrawable(R.drawable.rb_option_t);
			rbB.setButtonDrawable(R.drawable.rb_option_f);
			rbC.setButtonDrawable(R.drawable.rb_option_f);
			rbD.setButtonDrawable(R.drawable.rb_option_f);
		} else if (paramInt == rbB.getId()) {
			selected = "B";
			rbB.setButtonDrawable(R.drawable.rb_option_t);
			rbA.setButtonDrawable(R.drawable.rb_option_f);
			rbC.setButtonDrawable(R.drawable.rb_option_f);
			rbD.setButtonDrawable(R.drawable.rb_option_f);
		} else if (paramInt == rbC.getId()) {
			selected = "C";
			rbC.setButtonDrawable(R.drawable.rb_option_t);
			rbA.setButtonDrawable(R.drawable.rb_option_f);
			rbB.setButtonDrawable(R.drawable.rb_option_f);
			rbD.setButtonDrawable(R.drawable.rb_option_f);
		} else if (paramInt == rbD.getId()) {
			selected = "D";
			rbD.setButtonDrawable(R.drawable.rb_option_t);
			rbA.setButtonDrawable(R.drawable.rb_option_f);
			rbB.setButtonDrawable(R.drawable.rb_option_f);
			rbC.setButtonDrawable(R.drawable.rb_option_f);
		}
		saveSelected(selected);
		curQuestion.setSelected(selected);
		if (!Utils.isDone(ids_done[(-1 + curPosition)])) {
			yetdo--;
			btnRight.setText("未做(" + yetdo + ")");
		}
		ids_done[(-1 + curPosition)] = curPosition + Constants.SEPARATOR
				+ curQuestion.getId() + Constants.SEPARATOR
				+ curQuestion.getAnswer() + Constants.SEPARATOR + selected;
		next();
	}

	@Override
	public void show(int postion) {
		if (postion < 1 || postion > questions.size()) {
			postion = 1;
		}
		// mNmberTextView.setText(postion + "/" + questions.size());
		tvTitle.setText("模拟考试 ( " + postion + "/" + questions.size() + " )");
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

		if (Utils.isNotBlank(curQuestion.getImg())) {
			ivFigure.setVisibility(View.VISIBLE);
			mBitmap = Utils.getImageFromAssetsFile(this, "images/question/"
					+ curQuestion.getImg());
			ivFigure.setImageBitmap(mBitmap);
		} else {
			ivFigure.setVisibility(View.GONE);
		}
		if ("A".equals(curQuestion.getSelected())) {
			rbA.setButtonDrawable(R.drawable.rb_option_t);
			rbA.setChecked(true);
		} else if ("B".equals(curQuestion.getSelected())) {
			rbB.setButtonDrawable(R.drawable.rb_option_t);
			rbB.setChecked(true);
		} else if ("C".equals(curQuestion.getSelected())) {
			rbC.setButtonDrawable(R.drawable.rb_option_t);
			rbC.setChecked(true);
		} else if ("D".equals(curQuestion.getSelected())) {
			rbD.setButtonDrawable(R.drawable.rb_option_t);
			rbD.setChecked(true);
		}
		startAnimation(llQuestion);
	}

	@Override
	protected void leftAction() {
		int yetdoAmt = 0;
		for (int i = 0; i < ids_done.length; i++) {
			if (!Utils.isDone(ids_done[i])) {
				yetdoAmt++;
			}
		}
		String msge = "";
		if (yetdoAmt == 0) {
			msge = "已做完所有题目, 确定要交卷?";
		} else {
			msge = "还有 " + yetdoAmt + " 题没做完, 确定要交卷?";
		}
		new AlertDialog.Builder(this)
				.setMessage(msge)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						handle.removeCallbacks(run);
						// int[] ids = new int[questions.size()];
						// for (int i = 0; i < questions.size(); i++) {
						// ids[i] = questions.get(i).getId();
						// }
						Intent intent = new Intent();
						intent.setClass(MockActivity.this, ResultActivity.class);
						Bundle bundle = new Bundle();
						// bundle.putIntArray("ids", ids);
						bundle.putStringArray("ids_done", ids_done);
						intent.putExtras(bundle);
						startActivity(intent);
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						paramDialogInterface.dismiss();
					}
				}).create().show();

	}

	@Override
	protected void rightAction() {
		Intent intent = new Intent();
		intent.putExtra("ids_done", ids_done);
		intent.setClass(this, MockStatusActivity.class);
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent paramIntent) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				initQuestionView();
				curPosition = paramIntent.getExtras().getInt("number");
				show(curPosition);
			}
		}
	}

	public void onBackPressed() {
		exit();
	}

	public void exit() {
		new AlertDialog.Builder(this)
				.setMessage("确定退出模拟考试?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						handle.removeCallbacks(run);
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						paramDialogInterface.dismiss();
					}
				}).create().show();
	}

	@Override
	protected void backAction() {
		exit();
	}
}