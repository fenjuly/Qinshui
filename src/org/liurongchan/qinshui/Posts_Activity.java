package org.liurongchan.qinshui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.liurongchan.adapter.Post_Adapter;
import org.liurongchan.model.Post;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-31 下午2:35:18 类说明
 */
public class Posts_Activity extends Activity {

	private Context mContext;

	private ListView mSecondaryList;

	private Post_Adapter post_Adapter;

	private List<Post> forum_posts;

	private TextView page_text;
	private ImageView previous_page;
	private ImageView next_page;

	private int id;
	private int max_page;
	private int now_page;

	private boolean isloadmaxpage = false;
	private boolean isfirstrefresh = false;

	String url;
	private static final String ACCOUNT_INFORMATION = "accout_information";

	private static final Map<String, Integer> SECONDARY_CATEGORY = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1410556396714226136L;

		{
			put("保研考研", R.string.保研考研);
			put("LaTeX技术交流", R.string.LaTeX技术交流);
			put("聚焦两会", R.string.聚焦两会);
			put("镜头下的成电", R.string.镜头下的成电);
			put("一周锐评", R.string.一周锐评);
			put("名师博文", R.string.名师博文);
			put("大学生热点", R.string.大学生热点);
			put("成电UED", R.string.成电UED);
			put("前端之美", R.string.前端之美);
			put("互联网资讯", R.string.互联网资讯);
			put("数字前端", R.string.数字前端);
			put("数学之美", R.string.数学之美);
			put("电脑FAQ", R.string.电脑FAQ);
			put("硬件数码", R.string.硬件数码);
			put("Unix_Linux", R.string.Unix_Linux);
			put("程序员", R.string.程序员);
			put("电子设计", R.string.电子设计);
			put("就业创业", R.string.就业创业);
			put("手机之家", R.string.手机之家);
			put("出国留学", R.string.出国留学);
			put("相约回家", R.string.相约回家);
			put("学习交流", R.string.学习交流);
			put("成电轨迹", R.string.成电轨迹);
			put("情感专区", R.string.情感专区);
			put("生活百科", R.string.生活百科);
			put("老乡会", R.string.老乡会);
			put("成电骑迹", R.string.成电骑迹);
			put("摄影艺术", R.string.摄影艺术);
			put("旅游专版", R.string.旅游专版);
			put("动漫时代", R.string.动漫时代);
			put("会心一笑", R.string.会心一笑);
			put("影视天地", R.string.影视天地);
			put("游戏世界", R.string.游戏世界);
			put("经典图吧", R.string.经典图吧);
			put("娱乐花边", R.string.娱乐花边);
			put("体坛风云", R.string.体坛风云);
			put("音乐空间", R.string.音乐空间);
			put("情系舞缘", R.string.情系舞缘);
			put("校园新闻", R.string.校园新闻);
			put("时政要闻", R.string.时政要闻);
			put("社会百态", R.string.社会百态);
			put("科技教育", R.string.科技教育);
			put("军事国防", R.string.军事国防);
			put("经济相关", R.string.经济相关);
			put("二手专区", R.string.二手专区);
			put("店铺专区", R.string.店铺专区);
			put("房屋租赁", R.string.房屋租赁);
			put("水手之家", R.string.水手之家);
			put("历史_文化_人物", R.string.历史_文化_人物);
			put("文人墨客", R.string.文人墨客);
			put("我的大学生活", R.string.我的大学生活);
			put("毕业感言", R.string.毕业感言);
			put("母校发展_我来献策", R.string.母校发展_我来献策);
			put("站务公告", R.string.站务公告);
			put("站务综合", R.string.站务综合);
			put("影视资源", R.string.影视资源);
			put("体育资源", R.string.体育资源);
			put("动漫资源", R.string.动漫资源);
			put("软件资源", R.string.软件资源);
			put("音乐资源", R.string.音乐资源);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posts);
		mContext = this;
		page_text = (TextView) findViewById(R.id.now_page);
		previous_page = (ImageView) findViewById(R.id.previous_page);
		next_page = (ImageView) findViewById(R.id.next_page);
		previous_page.setOnClickListener(new OnPreViousPageClick());
		next_page.setOnClickListener(new OnNextPageClick());
		page_text.setOnClickListener(new OnChoosePageClick());
		mSecondaryList = (ListView) findViewById(R.id.posts_list);
		forum_posts = new ArrayList<Post>();
		String category = getIntent().getExtras()
				.getString("secondaryCategory");
		id = Integer.valueOf(getResources().getString(
				SECONDARY_CATEGORY.get(category)));
		url = "http://bbs.stuhome.net/forum.php?mod=forumdisplay&fid=" + id;
		Posts p = new Posts();
		p.execute(url);
	}

	private class Posts extends AsyncTask<String, Void, Void> {

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(String... params) {
			Document doc = null;
			final SharedPreferences mShared = mContext.getSharedPreferences(
					ACCOUNT_INFORMATION, Context.MODE_PRIVATE);
			try {
				doc = Jsoup.connect(params[0]).userAgent("Mozilla")
						.timeout(10 * 1000)
						.cookies((Map<String, String>) mShared.getAll()).get();
				Elements tbodies = doc.getElementsByTag("tbody");
				String str_tid = "";
				String title = "";
				int tid = 0;
				forum_posts.clear();
				for (Element tbody : tbodies) {

					str_tid = tbody.id();

					if (str_tid.equals("")) {
						continue;
					} else if (str_tid.startsWith("stickthread_")) {
						tid = Integer.valueOf(str_tid.substring("stickthread_"
								.length()));
					} else if (str_tid.startsWith("normalthread_")) {
						tid = Integer.valueOf(str_tid.substring("normalthread_"
								.length()));
					} else if (str_tid.equals("separatorline")) {
						continue;
					}

					Elements titles = tbody.select("a.s.xst");
					title = titles.text();
					forum_posts.add(new Post(title, tid));
				}

				if (!isloadmaxpage) {
					Element fd_page_bottom = doc.getElementById("fd_page_bottom");
					if (fd_page_bottom.getElementsByTag("strong").text()
							.equals("")) {
						max_page = 1;
						now_page = 1;
					} else {
						max_page = Integer.valueOf(fd_page_bottom
								.getElementsByTag("strong").text());
						now_page = max_page;
						Elements page_numbers = fd_page_bottom
								.getElementsByTag("a");
						int now_number = 1;
						String str_now_number = "";
						for (Element page_number : page_numbers) {
							str_now_number = page_number.text();
							if (str_now_number.startsWith("... ")) {
								now_number = Integer.valueOf(str_now_number
										.substring("... ".length()));
							} else if (str_now_number.equals("下一页")) {
								continue;
							} else {
								now_number = Integer.valueOf(str_now_number);
							}
							if (now_number > max_page) {
								max_page = now_number;
							}
						}
						isloadmaxpage = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (!isfirstrefresh) {
				post_Adapter = new Post_Adapter(mContext, forum_posts, id);
				mSecondaryList.setAdapter(post_Adapter);
				isfirstrefresh = true;
			} else {
				post_Adapter.refresh(forum_posts);
			}
			page_text.setText("第" + now_page + "页" + ",共" + max_page + "页");
			super.onPostExecute(result);
		}

	}

	private class OnPreViousPageClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (now_page == 1) {
				Toast.makeText(mContext, "已经是第一页了！", Toast.LENGTH_SHORT).show();
			} else {
				now_page--;
				url = url + "&page=" + now_page;
				Posts p = new Posts();
				p.execute(url);
			}
		}

	}

	private class OnNextPageClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (now_page == max_page) {
				Toast.makeText(mContext, "已经是最后了！", Toast.LENGTH_SHORT).show();
			} else {
				now_page++;
				url = url + "&page=" + now_page;
				Posts p = new Posts();
				p.execute(url);
			}
		}

	}

	private class OnChoosePageClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			final EditText editText = new EditText(mContext);
			editText.setHeight(mContext.getResources().getDimensionPixelSize(
					R.dimen.dialog_height));
			editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					250) });
			editText.setGravity(Gravity.LEFT | Gravity.TOP);

			AlertDialog.Builder commentDialog = new AlertDialog.Builder(
					mContext).setTitle("选择你想去的页数").setView(editText)
					.setNegativeButton("取消", null)
					.setPositiveButton("确定", null);
			final AlertDialog dialog = commentDialog.create();
			dialog.show();
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							int des_page;
							String content = editText.getText().toString();
							if (!isNumeric(content)) {
								Toast.makeText(mContext, "只能输入数字！",
										Toast.LENGTH_SHORT).show();
							} else {
								des_page = Integer.valueOf(content);
								if (des_page < 1 || des_page > max_page) {
									Toast.makeText(mContext, "请输入正确的数字！",
											Toast.LENGTH_SHORT).show();
								} else {
									dialog.dismiss();
									now_page = des_page;
									url = url + "&page=" + des_page;
									Posts p = new Posts();
									p.execute(url);
								}
							}
						}
					});
		}

		public boolean isNumeric(String str) {
			for (int i = str.length(); --i >= 0;) {
				if (!Character.isDigit(str.charAt(i))) {
					return false;
				}
			}
			return true;
		}

	}

}
