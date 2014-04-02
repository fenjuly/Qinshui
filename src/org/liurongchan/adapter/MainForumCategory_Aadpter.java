package org.liurongchan.adapter;

import org.liurongchan.listener.Main_Forum_Category_Listener;
import org.liurongchan.qinshui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-28 上午11:16:07 类说明
 */
public class MainForumCategory_Aadpter extends BaseAdapter {

	private Context mContext;
	private String[] mCategories;

	private LayoutInflater mLayoutInflater;

	public MainForumCategory_Aadpter(Context context, String[] categories) {
		this.mContext = context;
		this.mCategories = categories;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mCategories.length;
	}

	@Override
	public Object getItem(int position) {
		return mCategories[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView categoryText;
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.main_forum_category,
					parent, false);
			categoryText = (TextView) convertView.findViewById(R.id.main_category);
			holder = new ViewHolder(categoryText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			categoryText = holder.categoryText;
		}
		categoryText.setText(mCategories[position]);
		convertView.setOnClickListener(new Main_Forum_Category_Listener(mContext, mCategories[position]));
		return convertView;
	}

	private static class ViewHolder {
		public TextView categoryText;

		public ViewHolder(TextView category) {
			categoryText = category;
		}
	}

}
