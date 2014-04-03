package org.liurongchan.listener;

import org.liurongchan.qinshui.Post_Content_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午1:53:53
 * 类说明
 */
public class Posts_Item_Listener implements OnClickListener {

	private int fid;
	
	private int tid;

	private Context mContext;
	
	public Posts_Item_Listener(Context context, int fid, int tid) {
		this.fid = fid;
		this.tid = tid;
		mContext = context;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, Post_Content_Activity.class);
		intent.putExtra("fid", fid);
		intent.putExtra("tid", tid);
		mContext.startActivity(intent);
	}
}
