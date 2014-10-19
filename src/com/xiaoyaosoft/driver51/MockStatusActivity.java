package com.xiaoyaosoft.driver51;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaoyaosoft.driver51.adapter.MockStatusAdapter;
import com.xiaoyaosoft.driver51.adapter.MockStatusAdapter.ListViewItem;
import com.xiaoyaosoft.driver51.util.Utils;

public class MockStatusActivity extends BaseActivity {
	private Intent intent;
	private String[] answers;
	private String[] yetdo;
	private String[] done;
	private MockStatusAdapter adapter;
	private ListView lvMockStatus;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		intent = getIntent();
		answers = new String[100];
		answers = intent.getStringArrayExtra("ids_done");

		lvMockStatus = ((ListView) findViewById(R.id.lvMockStatus));
		getYetdo();
		adapter = new MockStatusAdapter(this, yetdo);
		lvMockStatus.setAdapter(adapter);
		lvMockStatus.setFastScrollEnabled(true);
		lvMockStatus.setOnItemClickListener(itemClickListener);
		btnSetting.setImageResource(R.drawable.done_32);
		btnSetting.setVisibility(View.VISIBLE);
		btnSetting.setTag("0");
		tvTitle.setText("未做(" + yetdo.length + ")");
		btnSetting.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if ("1".equals(btnSetting.getTag())) {
					btnSetting.setImageResource(R.drawable.done_32);
					adapter.setData(yetdo);
					adapter.notifyDataSetChanged();
					btnSetting.setTag("0");
					tvTitle.setText("未做(" + yetdo.length + ")");
				} else {
					btnSetting.setImageResource(R.drawable.exclamation_32);
					adapter.setData(done);
					adapter.notifyDataSetChanged();
					btnSetting.setTag("1");
					tvTitle.setText("已做(" + done.length + ")");
				}
			}
		});
	}

	AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			ListViewItem tag = (ListViewItem) paramView.getTag();
			intent.putExtra("number", Integer.parseInt(tag.mockNo));
			setResult(RESULT_OK, intent);
			finish();
		}
	};

	@Override
	protected void setView() {
		setContentView(R.layout.mock_status);
	}

	private void getYetdo() {
		List<String> yetdoList = new ArrayList<String>();
		List<String> doneList = new ArrayList<String>();
		for (int i = 0; i < answers.length; i++) {
			if (!Utils.isDone(answers[i])) {
				yetdoList.add(answers[i]);
			} else {
				doneList.add(answers[i]);
			}
		}
		yetdo = (String[]) yetdoList.toArray(new String[yetdoList.size()]);
		done = (String[]) doneList.toArray(new String[doneList.size()]);
	}
}