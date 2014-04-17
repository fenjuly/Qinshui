package org.liurongchan.listener;

import org.liurongchan.qinshui.ImageViewActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author [FeN]July  E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-18 上午12:04:23
 * 类说明
 */
public class ImageView_Listener implements OnClickListener {

	private String url;

	private Context mContext;
	
	public ImageView_Listener(Context context, String url) {
		this.url = url;
		mContext = context;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(mContext, ImageViewActivity.class);
		intent.putExtra("image_url", url);
		mContext.startActivity(intent);
	}

}