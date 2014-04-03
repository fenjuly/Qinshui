package org.liurongchan.model;

import java.util.ArrayList;
import java.util.List;

import org.liurongchan.listener.Posts_Item_Long_Listener;
import org.liurongchan.qinshui.R;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午2:23:31 类说明
 */
public class Post_Item implements ListItem{

	public Post_Item(int fid, int tid, int pid, String content, String name, String time, List<String> pics) {
		super();
		this.fid = fid;
		this.tid = tid;
		this.pid = pid;
		this.content = content;
		this.name = name;
		this.time = time;
		this.pics = pics;
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

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	private int fid;

	private int tid;

	private int pid;

	private String content;
	private String name;
	
	private String time;
	
	private List<String>  pics;

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
		List<ImageView> img_pics = null;
		LinearLayout layout = null;
			convertView =  inflater.inflate(R.layout.post_content_item,
					parent, false);
			contentText = (TextView) convertView
					.findViewById(R.id.post_content);
			nameText = (TextView) convertView.findViewById(R.id.post_owner_name);
			timeText = (TextView) convertView.findViewById(R.id.post_time);
			

		layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
			ImageView img;
			img_pics = new ArrayList<ImageView>();
			LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			ll.rightMargin = 15;
			ll.topMargin = 3;
			for (int i = 0; i < pics.size(); i++) {
				img = new ImageView(context);
				img.setLayoutParams(ll);
				img_pics.add(img);
			}
			
		timeText.setText(time);
		nameText.setText(name);
		contentText.setText(content);
		if(pics != null) {
			for (int i= 0; i < pics.size(); i++) {
				Picasso.with(context).load("http://bbs.stuhome.net/" + pics.get(i))
				.error(R.drawable.placeholder_fail).into(img_pics.get(i));
				layout.addView(img_pics.get(i));
			}
		}
		convertView.setOnLongClickListener(new Posts_Item_Long_Listener(context, this, formhash));
		return convertView;
	}

}
