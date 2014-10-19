package com.xiaoyaosoft.driver51.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoyaosoft.driver51.R;
import com.xiaoyaosoft.driver51.model.Category;

public class CategoryAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<Category> list;

	public CategoryAdapter(Context paramContext, List<Category> paramList) {
		inflater = LayoutInflater.from(paramContext);
		list = paramList;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int paramInt) {
		return list.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ListViewItem localListViewItem;
		if (paramView == null) {
			paramView = inflater.inflate(R.layout.category_item, null);
			localListViewItem = new ListViewItem();
			localListViewItem.tvCatTitle = ((TextView) paramView
					.findViewById(R.id.tvCatTitle));
			localListViewItem.tvCatNum = ((TextView) paramView
					.findViewById(R.id.tvCatNum));
			paramView.setTag(localListViewItem);
		} else {
			localListViewItem = (ListViewItem) paramView.getTag();
		}
		Category chapter = list.get(paramInt);
		if (paramInt == 9) {
			localListViewItem.tvCatTitle.setText("第" + (paramInt + 1) + "章 : "
					+ chapter.getDisplayName() + "(地区专题)");
		} else {
			localListViewItem.tvCatTitle.setText("第" + (paramInt + 1) + "章 : "
					+ chapter.getName());
		}
		localListViewItem.tvCatNum.setText(chapter.getNum() + "题");
		localListViewItem.catId = chapter.getLocationType();
		localListViewItem.num = chapter.getNum();
		return paramView;

	}

	public class ListViewItem {
		public TextView tvCatTitle;
		public TextView tvCatNum;
		public int catId;
		public int num;

		private ListViewItem() {
		}
	}
}
