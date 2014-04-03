package org.liurongchan.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-3 下午4:40:22
 * 类说明
 */
public interface ListItem {
	public int getLayout();


	public View getView(Context context, View convertView,
			LayoutInflater inflater, ViewGroup parent, String formhash);
	
	public int getFid();
	

	public void setFid(int fid);

	public int getTid();

	public void setTid(int tid);

	public int getPid();
	
	public void setPid(int pid);

	public String getContent();

	public void setContent(String content);

	public String getName();

	public void setName(String name);

	public String getTime();

	public void setTime(String time);
}
