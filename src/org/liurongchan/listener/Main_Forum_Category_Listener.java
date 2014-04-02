package org.liurongchan.listener;

import org.liurongchan.qinshui.Secondary_Forum_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-28 下午3:26:32
 * 类说明
 */
public class Main_Forum_Category_Listener implements OnClickListener {

	private String mCategory;
	
	private Context mContext;
	
	public Main_Forum_Category_Listener(Context context, String category) {
		this.mCategory = category;
		this.mContext = context;
	}
	
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, Secondary_Forum_Activity.class);
		intent.putExtra("fatherCategory", mCategory);
		mContext.startActivity(intent);
	}

}
