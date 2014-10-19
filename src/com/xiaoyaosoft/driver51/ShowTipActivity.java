package com.xiaoyaosoft.driver51;

import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoyaosoft.driver51.db.DBManager;
import com.xiaoyaosoft.driver51.model.Article;
import com.xiaoyaosoft.driver51.util.ISwipeLeftRight;
import com.xiaoyaosoft.driver51.util.Utils;

public class ShowTipActivity extends BaseActivity implements ISwipeLeftRight {
	static final int GOTO_DIALOG = 2;
	List<Article> articles;
	private Button btnNext;
	private Button btnPrev;
	private Article cArticle;
	private ImageView iv1;
	protected int j = 0;
	private Bitmap mBitmap;
	private TextView tvNumber;
	protected String mTitle;
	private int maxSize;
	private int parent_id;
	private TextView t1;
	private TextView t2;
	private TextView title;
	RelativeLayout rlArticle;
	protected ScrollView svMain;

	protected TextView tvGotoHint;
	protected TextView tvMin;
	protected TextView tvMax;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		init();
		title = ((TextView) findViewById(R.id.tvTitle));
		title.setText(mTitle);
		iv1 = ((ImageView) findViewById(R.id.article_img));
		btnPrev = ((Button) findViewById(R.id.btnPrev));
		btnPrev.setOnClickListener(new btntopListener());
		btnNext = ((Button) findViewById(R.id.btnNext));
		btnNext.setOnClickListener(new btndownListener());
		t1 = ((TextView) findViewById(R.id.article_title));
		t2 = ((TextView) findViewById(R.id.article_text));
		tvNumber = ((TextView) findViewById(R.id.tvNumber));
		tvNumber.setOnClickListener(btnGotoClick);
		svMain = (ScrollView) findViewById(R.id.svMain);
		rlArticle = (RelativeLayout) this.findViewById(R.id.rlArticle);
		if (articles.size() == 0) {
			Toast.makeText(getBaseContext(), "没有相关的文章.", Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {
			maxSize = articles.size();
			tvNumber.setText(1 + j + "/" + articles.size());
			show(j);
		}
	}

	protected void init() {
		Intent intent = getIntent();
		parent_id = intent.getIntExtra("parentid", 1);
		mTitle = intent.getStringExtra("title");
		articles = DBManager.getArticles(parent_id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected View.OnClickListener btnGotoClick = new View.OnClickListener() {
		public void onClick(View paramView) {
			gotoAction();
		}
	};

	protected void gotoAction() {
		if (articles.size() > 1) {
			showDialog(GOTO_DIALOG);
		} else {
			Toast.makeText(getBaseContext(), "只有一篇文章,不需要快速跳转.",
					Toast.LENGTH_SHORT).show();
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case GOTO_DIALOG:
			final Dialog dialog1 = new Dialog(this,
					android.R.style.Theme_Dialog);
			dialog1.setContentView(R.layout.goto_dialog);
			dialog1.setCancelable(true);
			dialog1.setTitle("快速跳至文章...");
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
					j = sbGoto.getProgress();
					show(j);
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
			sbGoto.setMax(articles.size() - 1);
			sbGoto.setProgress(j);
			tvGotoHint.setText(j + 1 + "."
					+ Utils.getSubStr(cArticle.getTitle(), 8));
			tvMin.setText("1");
			tvMax.setText(String.valueOf(articles.size()));
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
				Article cccArticle = articles.get(progress);
				tvGotoHint.setText(progress + 1 + "."
						+ Utils.getSubStr(cccArticle.getTitle(), 8));
			}
		}
	};

	public void show(int paramInt) {
		cArticle = ((Article) articles.get(paramInt));
		cArticle.getId();
		tvNumber.setText(paramInt + 1 + "/" + articles.size());
		t1.setText((paramInt + 1) + ". " + cArticle.getTitle());
		if (Utils.isNotBlank(cArticle.getPhoto())) {
			mBitmap = Utils.getImageFromAssetsFile(this, "images/article/"
					+ cArticle.getPhoto());
			iv1.setVisibility(View.VISIBLE);
			iv1.setImageBitmap(mBitmap);
		} else {
			iv1.setVisibility(View.GONE);
		}
		t2.setText(cArticle.getContent());
		startAnimation(rlArticle);
	}

	class btndownListener implements View.OnClickListener {
		btndownListener() {
		}

		public void onClick(View paramView) {
			next();
			svMain.fullScroll(ScrollView.FOCUS_UP);
		}
	}

	class btntopListener implements View.OnClickListener {
		btntopListener() {
		}

		public void onClick(View paramView) {
			prev();
			svMain.fullScroll(ScrollView.FOCUS_UP);
		}
	}

	@Override
	protected void setView() {
		setContentView(R.layout.view_article);
	}

	@Override
	public void prev() {
		if (j == 0)
			Toast.makeText(getBaseContext(), "当前已经是第一篇.", Toast.LENGTH_SHORT)
					.show();
		else {
			j = (-1 + j);
			show(j);
		}
	}

	@Override
	public void next() {
		if (j == -1 + maxSize)
			Toast.makeText(getBaseContext(), "当前已经是最后一篇.", Toast.LENGTH_SHORT)
					.show();
		else {
			j = (1 + j);
			show(j);
		}
	}
}
