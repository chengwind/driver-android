package com.xiaoyaosoft.driver51;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class ResultActivity extends BaseActivity {
	private int score = 0;
	private int errorAmt = 0;
	private int yetdoAmt = 0;
	private Button btnExitMock = null;
	private Button btnAnalysisMock = null;
	private String[] ids_done = null;
	// private int[] ids = new int[100];
	// private TextView tvScore;
	TextView tvScore;
	TextView tvTotalAmt;
	TextView tvOkAmt;
	TextView tvErrorAmt;
	TextView tvYetdoAmt;
	ImageView ivResult;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		btnAnalysisMock = ((Button) findViewById(R.id.btnAnalysisMock));
		btnExitMock = ((Button) findViewById(R.id.btnExitMock));
		btnAnalysisMock.setOnClickListener(new btncheckListener());
		btnExitMock.setOnClickListener(new btnbackListener());
		tvScore = ((TextView) findViewById(R.id.tvScore));
		tvTotalAmt = ((TextView) findViewById(R.id.tvTotalAmt));
		tvOkAmt = ((TextView) findViewById(R.id.tvOkAmt));
		tvErrorAmt = ((TextView) findViewById(R.id.tvErrorAmt));
		tvYetdoAmt = ((TextView) findViewById(R.id.tvYetdoAmt));
		ivResult = ((ImageView) findViewById(R.id.ivResult));
		Bundle localBundle = getIntent().getExtras();
		// ids = localBundle.getIntArray("ids");
		ids_done = localBundle.getStringArray("ids_done");
		int i = 0;
		for (; i < ids_done.length; i++) {
			if (Utils.isDone(ids_done[i])) {
				String[] ss = ids_done[i].split(Constants.SEPARATOR);
				String rightAnswer = ss[2];
				String answer = ss[3];
				if (rightAnswer.equals(answer)) {
					score = (1 + score);
				}
			} else {
				yetdoAmt++;
			}
		}
		errorAmt = ids_done.length - score - yetdoAmt;
		if (score >= 90) {
			tvScore.setText(score + " (通过)");
			ivResult.setImageResource(R.drawable.mock_selected_t);
		} else {
			tvScore.setText(score + " (未通过)");
			ivResult.setImageResource(R.drawable.mock_selected_f);
		}
		tvTotalAmt.setText(String.valueOf(ids_done.length));
		tvOkAmt.setText(String.valueOf(score));
		tvErrorAmt.setText(String.valueOf(errorAmt));
		tvYetdoAmt.setText(String.valueOf(yetdoAmt));
		tvTitle.setText("模拟考试成绩单");
		// if (score >= Constants.TOTAL_TEST_SCORE) {
		// btnAnalysisMock.setEnabled(false);
		// }
	}

	class btnbackListener implements View.OnClickListener {
		btnbackListener() {
		}

		public void onClick(View paramView) {
			Intent intent = new Intent();
			intent.setClass(ResultActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	class btncheckListener implements View.OnClickListener {
		btncheckListener() {
		}

		public void onClick(View paramView) {
			if (score >= Constants.TOTAL_TEST_SCORE) {
				Toast.makeText(getBaseContext(), "您考了一百分,没有错误的题目.",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent();
			intent.setClass(ResultActivity.this, MockErrorActivity.class);
			Bundle bundle = new Bundle();
			// bundle.putIntArray("ids", ids);
			bundle.putStringArray("ids_done", ids_done);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void setView() {
		setContentView(R.layout.mock_score);
	}
}