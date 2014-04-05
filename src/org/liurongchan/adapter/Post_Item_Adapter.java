package org.liurongchan.adapter;

import java.util.List;

import org.liurongchan.model.ListItem;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午2:46:40 类说明
 */
public class Post_Item_Adapter extends BaseAdapter {

	private List<ListItem> post_items;
	private Context mContext;
	private String formhash;

	private LayoutInflater mLayoutInflater;
	

	public Post_Item_Adapter(Context context, List<ListItem> post_items, String formhash) {
		mContext = context;
		this.post_items = post_items;
		this.formhash  = formhash;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void refresh(List<ListItem> mPosts) {
		post_items = mPosts;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return post_items.size();
	}

	@Override
	public Object getItem(int position) {
		return post_items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return post_items.get(position).getView(mContext, convertView, mLayoutInflater, parent, formhash);
	}



}
