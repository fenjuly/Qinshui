package org.liurongchan.adapter;

import java.util.List;

import org.liurongchan.model.Post_Item;
import org.liurongchan.qinshui.R;
import org.liurongchan.listener.Posts_Item_Long_Listener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午2:46:40 类说明
 */
public class Post_Item_Adapter extends BaseAdapter {

	private List<Post_Item> post_items;
	private Context mContext;
	private String formhash;

	private LayoutInflater mLayoutInflater;

	public Post_Item_Adapter(Context context, List<Post_Item> post_items, String formhash) {
		mContext = context;
		this.post_items = post_items;
		this.formhash  = formhash;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void refresh(List<Post_Item> mPosts) {
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
		TextView contentText;
		TextView nameText;
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.post_content_item,
					parent, false);
			contentText = (TextView) convertView
					.findViewById(R.id.post_content);
			nameText = (TextView) convertView.findViewById(R.id.post_owner_name);

			holder = new ViewHolder(contentText, nameText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			contentText = holder.contentText;
			nameText = holder.nameText;
		}
		nameText.setText(post_items.get(position).getName());
		contentText.setText(post_items.get(position).getContent());
		convertView.setOnLongClickListener(new Posts_Item_Long_Listener(mContext, post_items.get(position), formhash));
		return convertView;
	}

	private static class ViewHolder {
		public TextView contentText;
		public TextView nameText;

		public ViewHolder(TextView contentText, TextView nameText) {
			this.contentText = contentText;
			this.nameText = nameText;
		}
	}

}
