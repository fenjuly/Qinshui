package org.liurongchan.listener;

import org.liurongchan.qinshui.Posts_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-31 下午6:06:54 类说明
 */
public class Secondary_Forum_Category_Listener implements OnClickListener {

	private String mCategory;

	private Context mContext;
	
	public Secondary_Forum_Category_Listener(Context context, String category) {
		mCategory = category;
		mContext = context;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, Posts_Activity.class);
		intent.putExtra("secondaryCategory", mCategory);
		mContext.startActivity(intent);
	}

}
