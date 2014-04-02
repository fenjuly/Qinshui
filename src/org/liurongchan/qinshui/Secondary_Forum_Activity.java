package org.liurongchan.qinshui;

import org.liurongchan.adapter.SecondaryForumCategory_Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-28 下午2:51:49 类说明
 */
public class Secondary_Forum_Activity extends Activity {

	private Context mContext;

	private ListView mListView;
	
	private String keyword;

	private SecondaryForumCategory_Adapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondary_forum);
		mContext = this;
		Intent intent = getIntent();
		keyword = intent.getExtras().getString("fatherCategory");
		mListView = (ListView) findViewById(R.id.secondary_forum_list);
		mAdapter = new SecondaryForumCategory_Adapter(mContext, selectCategories(keyword));
		mListView.setAdapter(mAdapter);
	}

	private String[] selectCategories(String fatherCategory) {
		if (fatherCategory.equals("聚焦热点")) {
			return getResources().getStringArray(R.array.jujiaoredian);
		} else if (fatherCategory.equals("成电在线")) {
			return getResources().getStringArray(R.array.chengdianzaixian);
		} else if (fatherCategory.equals("技术交流")) {
			return getResources().getStringArray(R.array.jishujiaoliu);
		} else if (fatherCategory.equals("青春校园")) {
			return getResources().getStringArray(R.array.qingchunxiaoyuan);
		} else if (fatherCategory.equals("休闲娱乐")) {
			return getResources().getStringArray(R.array.xiuxianyule);
		} else if (fatherCategory.equals("时事讨论")) {
			return getResources().getStringArray(R.array.shishitaolun);
		} else if (fatherCategory.equals("跳蚤市场")) {
			return getResources().getStringArray(R.array.tiaozaoshichang);
		} else if (fatherCategory.equals("社区大杂烩")) {
			return getResources().getStringArray(R.array.shequdazahui);
		} else if (fatherCategory.equals("站务管理")) {
			return getResources().getStringArray(R.array.zhanwuguanli);
		} else {
			return getResources().getStringArray(R.array.ftpziyuan);
		}
	}

}
