package org.liurongchan.qinshui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-31 下午1:43:05 类说明
 */
public class Load_Activity extends Activity {

	private Context mContext;

	private static final String ACCOUNT_INFORMATION = "accout_information";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.load);
		final SharedPreferences mShared = mContext.getSharedPreferences(
				ACCOUNT_INFORMATION, Context.MODE_PRIVATE);
		if (mShared.getString("login", "no").equals("yes")) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(mContext,
							Main_Forum_Activity.class);
					startActivity(intent);
					finish();
				}
			}, 2000);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(mContext,
							Login_Activity.class);
					startActivity(intent);
					finish();
				}
			}, 2000);
		}

	}

}
