package com.xiaoyaosoft.driver51;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Question;
import com.xiaoyaosoft.driver51.util.ISwipeLeftRight;
import com.xiaoyaosoft.driver51.util.Utils;

public abstract class AbstractQuestionActivity extends BaseActivity implements
		ISwipeLeftRight {
	static final int DIALOG_CONFIRM_BACKTOLAST = 0;
	static final int GOTO_DIALOG = 2;
	protected Button btnNext;
	protected Button btnPrev;
	protected Question curQuestion;
	protected int curPosition = 1;
	protected int lastAnswered = 0;
	protected RelativeLayout explainLayout;
	protected ImageView ivFigure;
	protected Bitmap mBitmap;
	protected TextView tvNumber;
	protected Button btnLeft;
	protected Button btnRight;
	protected LinearLayout questionLayout;
	protected RadioButton rbA = null;
	protected RadioButton rbB = null;
	protected RadioButton rbC = null;
	protected RadioButton rbD = null;
	protected RadioGroup rg = null;
	protected TextView tvQuestion;
	protected TextView tvExplain;
	protected ScrollView svMain;

	List<Question> questions;

	LinearLayout llQuestion;

	protected TextView tvGotoHint;
	protected TextView tvMin;
	protected TextView tvMax;

	public void saveSelected(String selected) {
		DBManager.saveSelected(curQuestion.getId(), curQuestion.getAnswer(),
				selected);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		baseinit();
	}

	@Override
	protected void setView() {
		setContentView(R.layout.view_question);
	}

	protected void baseinit() {
		init();
		explainLayout = ((RelativeLayout) findViewById(R.id.explainLayout));
		questionLayout = ((LinearLayout) findViewById(R.id.questionLayout));
		ivFigure = ((ImageView) findViewById(R.id.ivFigure));
		btnPrev = ((Button) findViewById(R.id.btnPrev));
		btnPrev.setOnClickListener(btnPrevClick);
		btnNext = ((Button) findViewById(R.id.btnNext));
		btnNext.setOnClickListener(btnNextClick);
		btnRight = ((Button) findViewById(R.id.btnRight));
		btnRight.setOnClickListener(btnRightClick);
		btnLeft = ((Button) findViewById(R.id.btnLeft));
		btnLeft.setOnClickListener(btnLeftClick);
		tvQuestion = ((TextView) findViewById(R.id.tvQuestion));
		tvExplain = ((TextView) findViewById(R.id.tvExplain));
		tvNumber = ((TextView) findViewById(R.id.tvNumber));
		rg = ((RadioGroup) findViewById(R.id.rg1));
		rbA = ((RadioButton) findViewById(R.id.rbA));
		rbB = ((RadioButton) findViewById(R.id.rbB));
		rbC = ((RadioButton) findViewById(R.id.rbC));
		rbD = ((RadioButton) findViewById(R.id.rbD));
		svMain = (ScrollView) findViewById(R.id.svMain);
		// rg.setOnCheckedChangeListener(onCheckedChangeListener);
		rbA.setOnClickListener(rbClicklListener);
		rbB.setOnClickListener(rbClicklListener);
		rbC.setOnClickListener(rbClicklListener);
		rbD.setOnClickListener(rbClicklListener);
		tvNumber.setOnClickListener(btnGotoClick);
		// if (ttlQuestions == null || ttlQuestions.size() == 0) {
		// MyApp app = ((MyApp) this.getApplication());
		// ttlQuestions = DBManager.getQuestions(app.getCarType(), app
		// .getLocationCategory().getId());
		// }
		getCurQuestions();
		llQuestion = (LinearLayout) this.findViewById(R.id.llQuestion);
	}

	protected void gotoAction() {
		if (questions.size() > 1) {
			showDialog(GOTO_DIALOG);
		} else {
			Toast.makeText(getBaseContext(), "只有一个题目,不需要快速跳转.",
					Toast.LENGTH_SHORT).show();
		}
	}

	protected View.OnClickListener btnGotoClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			gotoAction();
		}
	};

	abstract protected void getCurQuestions();

	abstract protected void init();

	// protected Question getQuestion(int i) {
	// Question question = null;
	// for (Question q : ttlQuestions) {
	// if (i == q.getId()) {
	// question = q;
	// break;
	// }
	// }
	// return question;
	// }

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CONFIRM_BACKTOLAST:
			Dialog dialog = new AlertDialog.Builder(this)
					.setMessage("是否回到上次练习退出时的题目？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									curPosition = lastAnswered;
									show(curPosition);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									paramDialogInterface.dismiss();
								}
							}).create();
			return dialog;
		case GOTO_DIALOG:
			final Dialog dialog1 = new Dialog(this,
					android.R.style.Theme_Dialog);
			dialog1.setContentView(R.layout.goto_dialog);
			dialog1.setCancelable(true);
			dialog1.setTitle("快速跳至题目...");
			tvGotoHint = (TextView) dialog1.findViewById(R.id.tvGotoHint);
			ImageButton btnBack = (ImageButton) dialog1
					.findViewById(R.id.btnBack);
			Button btnOk = (Button) dialog1.findViewById(R.id.btnOk);
			tvMin = (TextView) dialog1.findViewById(R.id.tvMin);
			tvMax = (TextView) dialog1.findViewById(R.id.tvMax);
			btnBack.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					dialog1.dismiss();
				}
			});
			final SeekBar sbGoto = (SeekBar) dialog1.findViewById(R.id.sbGoto);
			sbGoto.setOnSeekBarChangeListener(sbGotoListener);
			btnOk.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					dialog1.dismiss();
					curPosition = sbGoto.getProgress() + 1;
					initQuestionView();
					show(curPosition);
				}
			});

			return dialog1;
		default:
			return null;
		}

	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case GOTO_DIALOG:
			SeekBar sbGoto = (SeekBar) dialog.findViewById(R.id.sbGoto);
			sbGoto.setMax(questions.size() - 1);
			sbGoto.setProgress(curPosition - 1);
			tvGotoHint.setText(curPosition + "."
					+ Utils.getSubStr(curQuestion.getQuestion(), 8));
			tvMin.setText("1");
			tvMax.setText(String.valueOf(questions.size()));
			break;
		}
		super.onPrepareDialog(id, dialog);
	}

	OnSeekBarChangeListener sbGotoListener = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onProgressChanged(SeekBar seekBark, int progress,
				boolean fromUser) {
			if (fromUser) {
				Question cccQuestion = questions.get(progress);
				tvGotoHint.setText(progress + 1 + "."
						+ Utils.getSubStr(cccQuestion.getQuestion(), 8));
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

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
		enableRB(true);
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
		btnLeft.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.btn_test_f), null, null);
		startAnimation(llQuestion);
	}

	private OnClickListener rbClicklListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			RadioButton rb = (RadioButton) v;
			if (!rb.isChecked())
				return;
			checkIt(v.getId());
		}
	};

	protected void checkIt(int paramInt) {
		rbA.setButtonDrawable(R.drawable.rb_option_f);
		rbB.setButtonDrawable(R.drawable.rb_option_f);
		rbC.setButtonDrawable(R.drawable.rb_option_f);
		rbD.setButtonDrawable(R.drawable.rb_option_f);
		String selected = "";
		if (paramInt == rbA.getId()) {
			selected = "A";
		} else if (paramInt == rbB.getId()) {
			selected = "B";
		} else if (paramInt == rbC.getId()) {
			selected = "C";
		} else if (paramInt == rbD.getId()) {
			selected = "D";
		}
		if (curQuestion.getAnswer().equals("A")) {
			rbA.setButtonDrawable(R.drawable.mock_selected_t);
			if (paramInt == rbB.getId()) {
				rbB.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbC.getId()) {
				rbC.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbD.getId()) {
				rbD.setButtonDrawable(R.drawable.mock_selected_f);
			}
		} else if (curQuestion.getAnswer().equals("B")) {
			rbB.setButtonDrawable(R.drawable.mock_selected_t);
			if (paramInt == rbA.getId()) {
				rbA.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbC.getId()) {
				rbC.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbD.getId()) {
				rbD.setButtonDrawable(R.drawable.mock_selected_f);
			}
		} else if (curQuestion.getAnswer().equals("C")) {
			rbC.setButtonDrawable(R.drawable.mock_selected_t);
			if (paramInt == rbA.getId()) {
				rbA.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbB.getId()) {
				rbB.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbD.getId()) {
				rbD.setButtonDrawable(R.drawable.mock_selected_f);
			}
		} else if (curQuestion.getAnswer().equals("D")) {
			rbD.setButtonDrawable(R.drawable.mock_selected_t);
			if (paramInt == rbB.getId()) {
				rbB.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbC.getId()) {
				rbC.setButtonDrawable(R.drawable.mock_selected_f);
			} else if (paramInt == rbA.getId()) {
				rbA.setButtonDrawable(R.drawable.mock_selected_f);
			}

		}
		if (selected.equals(curQuestion.getAnswer())) {
			tvExplain.setText(curQuestion.getExplain());
			explainLayout.setVisibility(View.VISIBLE);
			questionLayout.setBackgroundResource(R.drawable.question_bg_a);
			// next();
		} else {
			saveSelected(selected);
			tvExplain.setText("正确答案是：" + curQuestion.getAnswer() + "\n"
					+ curQuestion.getExplain());
			explainLayout.setVisibility(View.VISIBLE);
			questionLayout.setBackgroundResource(R.drawable.question_bg_w);
		}
	}

	protected void enableRB(boolean bool) {
		rbA.setEnabled(bool);
		rbB.setEnabled(bool);
		rbC.setEnabled(bool);
		rbD.setEnabled(bool);
	}

	private View.OnClickListener btnLeftClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			leftAction();
		}
	};

	protected void leftAction() {
		if (explainLayout.getVisibility() == View.INVISIBLE) {
			explainLayout.setVisibility(View.VISIBLE);
			tvExplain.setText("正确答案是：" + curQuestion.getAnswer() + "\n\n"
					+ curQuestion.getExplain());
			if (curQuestion.getAnswer().equals("A")) {
				rbA.setButtonDrawable(R.drawable.mock_selected_t);
			} else if (curQuestion.getAnswer().equals("B")) {
				rbB.setButtonDrawable(R.drawable.mock_selected_t);
			} else if (curQuestion.getAnswer().equals("C")) {
				rbC.setButtonDrawable(R.drawable.mock_selected_t);
			} else {
				rbD.setButtonDrawable(R.drawable.mock_selected_t);
			}
			btnLeft.setText("答题模式");
			btnLeft.setCompoundDrawablesWithIntrinsicBounds(null,
					getResources().getDrawable(R.drawable.btn_test_t), null,
					null);
			enableRB(true);
		} else {
			rg.clearCheck();
			rbA.setButtonDrawable(R.drawable.rb_option_f);
			rbB.setButtonDrawable(R.drawable.rb_option_f);
			rbC.setButtonDrawable(R.drawable.rb_option_f);
			rbD.setButtonDrawable(R.drawable.rb_option_f);
			explainLayout.setVisibility(View.INVISIBLE);
			btnLeft.setText("查看答案");
			btnLeft.setCompoundDrawablesWithIntrinsicBounds(null,
					getResources().getDrawable(R.drawable.btn_test_f), null,
					null);
			questionLayout.setBackgroundResource(R.drawable.question_bg_n);
			enableRB(false);
		}
	}

	private View.OnClickListener btnRightClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			rightAction();
		}
	};

	protected void rightAction() {
		int fav = curQuestion.getIsFav();
		if (fav == 0) {
			try {
				DBManager.addFav(curQuestion.getId(), true);
				Toast.makeText(getBaseContext(), "第" + curPosition + "题已经收藏！",
						Toast.LENGTH_SHORT).show();
				questionLayout.setBackgroundResource(R.drawable.question_bg_c);
				btnRight.setText("取消收藏");
				btnRight.setCompoundDrawablesWithIntrinsicBounds(null,
						getResources().getDrawable(R.drawable.icon_fav_t),
						null, null);
				curQuestion.setIsFav(1);
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), "第" + curPosition + "题收藏出错！",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			try {
				DBManager.addFav(curQuestion.getId(), true);
				Toast.makeText(getBaseContext(), "第" + curPosition + "题已取消收藏！",
						Toast.LENGTH_SHORT).show();
				questionLayout.setBackgroundResource(R.drawable.question_bg_n);
				btnRight.setText("收藏此题");
				btnRight.setCompoundDrawablesWithIntrinsicBounds(null,
						getResources().getDrawable(R.drawable.icon_fav_f),
						null, null);
				curQuestion.setIsFav(0);
			} catch (Exception e) {
				Toast.makeText(getBaseContext(),
						"第" + curPosition + "题取消收藏出错！", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	private View.OnClickListener btnNextClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			next();
			svMain.fullScroll(ScrollView.FOCUS_UP);
		}
	};

	private View.OnClickListener btnPrevClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			prev();
			svMain.fullScroll(ScrollView.FOCUS_UP);
		}
	};

	public void next() {
		if (curPosition == questions.size()) {
			Toast.makeText(getBaseContext(), "当前已经是最后一道题！", Toast.LENGTH_SHORT)
					.show();
		} else {
			curPosition = (1 + curPosition);
			initQuestionView();
			show(curPosition);
		}
	}

	public void prev() {
		if (curPosition == 1)
			Toast.makeText(getBaseContext(), "当前已经是第一道题！", Toast.LENGTH_SHORT)
					.show();
		else {
			curPosition = (-1 + curPosition);
			initQuestionView();
			show(curPosition);
		}
	}

	protected void initQuestionView() {
		rg.clearCheck();
		tvExplain.setText("");
		rbA.setButtonDrawable(R.drawable.rb_option_f);
		rbB.setButtonDrawable(R.drawable.rb_option_f);
		rbC.setButtonDrawable(R.drawable.rb_option_f);
		rbD.setButtonDrawable(R.drawable.rb_option_f);
		explainLayout.setVisibility(View.INVISIBLE);
		questionLayout.setBackgroundResource(R.drawable.question_bg_n);
		if (!(this instanceof ErrorActivity || this instanceof MockActivity || this instanceof MockErrorActivity)) {
			btnLeft.setText("查看答案");
			btnLeft.setCompoundDrawablesWithIntrinsicBounds(null,
					getResources().getDrawable(R.drawable.btn_test_f), null,
					null);
		}
	}
}