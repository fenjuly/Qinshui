package org.liurongchan.qinshui;

import org.liurongchan.adapter.MainForumCategory_Aadpter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Main_Forum_Activity extends Activity {

	private Context mContext;

	private ListView mListView;
	private ImageView account_manage;

	private MainForumCategory_Aadpter mAdpter;

	private LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_forum);
		mContext = this;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mListView = (ListView) findViewById(R.id.main_forum_list);
		mAdpter = new MainForumCategory_Aadpter(mContext, getResources()
				.getStringArray(R.array.maincategories));
		View headerView = mLayoutInflater.inflate(R.layout.main_forum_head,
				null, false);
		account_manage = (ImageView) headerView.findViewById(R.id.account_manage);
		account_manage.setOnClickListener(new AccountManage());

		mListView.addHeaderView(headerView);
		mListView.setAdapter(mAdpter);
	}


	private class AccountManage implements OnClickListener {

		@Override
		public void onClick(View v) {
			Toast.makeText(mContext, "切换新账号!", Toast.LENGTH_SHORT)
			.show();
			Intent intent = new Intent(mContext,
					Login_Activity.class);
			startActivity(intent);
			finish();
		}
		
	}

}
