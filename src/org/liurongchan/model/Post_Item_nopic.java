package org.liurongchan.model;


import org.liurongchan.listener.Posts_Item_Long_Listener;
import org.liurongchan.qinshui.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-3 下午5:03:54
 * 类说明
 */
public class Post_Item_nopic implements ListItem {

	public Post_Item_nopic(int fid, int tid, int pid, String content, String name, String time) {
		super();
		this.fid = fid;
		this.tid = tid;
		this.pid = pid;
		this.content = content;
		this.name = name;
		this.time = time;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	private int fid;

	private int tid;

	private int pid;

	private String content;
	private String name;
	
	private String time;
	

	@Override
	public int getLayout() {
		return R.layout.post_content_item;
	}


	@Override
	public View getView(Context context, View convertView,
			LayoutInflater inflater, ViewGroup parent, String formhash) {
		TextView contentText;
		TextView nameText; 
		TextView timeText;
			convertView =  inflater.inflate(R.layout.post_content_item,
					parent, false);
			contentText = (TextView) convertView
					.findViewById(R.id.post_content);
			nameText = (TextView) convertView.findViewById(R.id.post_owner_name);
			timeText = (TextView) convertView.findViewById(R.id.post_time);

			
		timeText.setText(time);
		nameText.setText(name);
		contentText.setText(content);
	
		convertView.setOnLongClickListener(new Posts_Item_Long_Listener(context, this, formhash));
		return convertView;
	}

}
