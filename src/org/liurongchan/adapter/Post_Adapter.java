package org.liurongchan.adapter;

import java.util.List;

import org.liurongchan.listener.Posts_Item_Listener;
import org.liurongchan.model.Post;
import org.liurongchan.qinshui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-31 下午2:36:24
 * 类说明
 */
public class Post_Adapter extends BaseAdapter {

	private List<Post> posts;
	private Context mContext;
	
	private int fid;
	
	private LayoutInflater mLayoutInflater;
	
	public Post_Adapter(Context context, List<Post> posts, int fid) {
		mContext = context;
		this.posts = posts;
		this.fid = fid;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void refresh(List<Post> mPosts) {
		posts = mPosts;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return posts.size();
	}

	@Override
	public Object getItem(int position) {
		return posts.get(position);
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
		categoryText.setText(posts.get(position).getTitle());
		convertView.setOnClickListener(new Posts_Item_Listener(mContext, fid, posts.get(position).getId()));
		return convertView;
	}
	
	private static class ViewHolder {
		public TextView categoryText;

		public ViewHolder(TextView category) {
			categoryText = category;
		}
	}

}