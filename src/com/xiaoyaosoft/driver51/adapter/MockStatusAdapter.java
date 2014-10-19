package com.xiaoyaosoft.driver51.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyaosoft.driver51.R;
import com.xiaoyaosoft.driver51.util.Constants;
import com.xiaoyaosoft.driver51.util.Utils;

public class MockStatusAdapter extends BaseAdapter {
	String[] answers;
	LayoutInflater inflater;

	public MockStatusAdapter(Context paramContext, String[] paramArrayOfInt) {
		this.inflater = LayoutInflater.from(paramContext);
		this.answers = paramArrayOfInt;
	}

	public void setData(String[] data) {
		answers = data;
	}

	public int getCount() {
		return answers.length;
	}

	public Object getItem(int paramInt) {
		return Integer.valueOf(this.answers[paramInt]);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public String showAnswer(String s) {
		String str;
		if (Utils.isDone(s)) {
			String[] ss = s.split(Constants.SEPARATOR);
			str = "已选 : " + ss[3];
		} else {
			str = "未做";
		}
		return str;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ListViewItem localListViewItem = null;
		if (paramView == null) {
			paramView = this.inflater.inflate(R.layout.mock_status_item, null);
			localListViewItem = new ListViewItem();
			localListViewItem.tvMockSeleted = ((TextView) paramView
					.findViewById(R.id.tvMockSeleted));
			localListViewItem.tvMockNo = ((TextView) paramView
					.findViewById(R.id.tvMockNo));
			localListViewItem.ivExclamation = ((ImageView) paramView
					.findViewById(R.id.ivExclamation));
			paramView.setTag(localListViewItem);
		} else {
			localListViewItem = (ListViewItem) paramView.getTag();
		}
		String mockNo = getPosition(answers[paramInt]);
		localListViewItem.mockNo = mockNo;
		localListViewItem.tvMockNo.setText("第" + mockNo + "题");
		localListViewItem.tvMockSeleted.setText(showAnswer(answers[paramInt]));
		if (Utils.isDone(answers[paramInt])) {
			localListViewItem.ivExclamation.setVisibility(View.VISIBLE);
			localListViewItem.ivExclamation.setImageResource(R.drawable.done);
		} else {
			localListViewItem.ivExclamation.setVisibility(View.VISIBLE);
			localListViewItem.ivExclamation
					.setImageResource(R.drawable.exclamation);
		}
		return paramView;
	}

	private String getPosition(String s) {
		String[] ss = s.split(Constants.SEPARATOR);
		return ss[0];
	}

	public class ListViewItem {
		public String mockNo;
		TextView tvMockSeleted;
		TextView tvMockNo;
		ImageView ivExclamation;

		private ListViewItem() {
		}
	}
}