package com.xiaoyaosoft.driver51.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyaosoft.driver51.R;
import com.xiaoyaosoft.driver51.model.Article;

public class TipsAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<Article> list;
	Context a;

	public TipsAdapter(Context paramContext, List<Article> paramList) {
		this.inflater = LayoutInflater.from(paramContext);
		this.list = paramList;
		a = paramContext;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int paramInt) {
		return this.list.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ListViewItem lvItem;
		if (paramView == null) {
			paramView = this.inflater.inflate(R.layout.category_item, null);
			lvItem = new ListViewItem();
			lvItem.tvCatTitle = ((TextView) paramView
					.findViewById(R.id.tvCatTitle));
			lvItem.tvCatNum = ((TextView) paramView.findViewById(R.id.tvCatNum));
			lvItem.ivForward = ((ImageView) paramView
					.findViewById(R.id.ivForward));

			paramView.setTag(lvItem);
		} else {
			lvItem = (ListViewItem) paramView.getTag();
		}
		Article article = list.get(paramInt);
		lvItem.tvCatTitle.setText((paramInt + 1) + "." + article.getTitle());
		lvItem.article = article;
		if (article.getLevel() == 2) {
			lvItem.tvCatNum.setText(String.valueOf(article.getNum()));
			lvItem.ivForward.setImageResource(R.drawable.plus);
		} else {
			lvItem.tvCatNum.setText(article.getNum() + "篇");
			lvItem.ivForward.setImageResource(R.drawable.forward);
		}
		return paramView;

	}

	public class ListViewItem {
		TextView tvCatTitle;
		TextView tvCatNum;
		ImageView ivForward;
		public Article article;

		private ListViewItem() {
		}
	}
}