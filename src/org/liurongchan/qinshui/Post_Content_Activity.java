package org.liurongchan.qinshui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.liurongchan.adapter.Post_Item_Adapter;
import org.liurongchan.model.ListItem;
import org.liurongchan.model.Post_Item;
import org.liurongchan.model.Post_Item_nopic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
 * @version 创建时间：2014-4-2 下午1:48:57 类说明
 */
public class Post_Content_Activity extends Activity {

	private Context mContext;

	private int fid;
	private int tid;

	private ListView post_item_list;

	private List<ListItem> post_items;

	private ImageView previous_content_page;
	private ImageView next_content_page;
	private TextView now_content_page;
	private ImageView comment;

	private Post_Item_Adapter post_Item_Adapter;

	private String url;
	private String reply_url;
	private String formhash;

	private int max_page;
	private int now_page;

	private boolean isloadmaxpage = false;
	private boolean isfirstrefresh = false;

	private ProgressDialog progressDialog;

	private static final String ACCOUNT_INFORMATION = "accout_information";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_content);
		mContext = this;
		fid = getIntent().getExtras().getInt("fid");
		tid = getIntent().getExtras().getInt("tid");

		post_item_list = (ListView) findViewById(R.id.post_content_list);

		previous_content_page = (ImageView) findViewById(R.id.previous_content_page);
		next_content_page = (ImageView) findViewById(R.id.next_content_page);
		now_content_page = (TextView) findViewById(R.id.now_content_page);
		comment = (ImageView) findViewById(R.id.comment);

		previous_content_page.setOnClickListener(new OnPreViousPageClick());
		next_content_page.setOnClickListener(new OnNextPageClick());
		now_content_page.setOnClickListener(new OnChoosePageClick());
		comment.setOnClickListener(new OnCommentClick());

		post_items = new ArrayList<ListItem>();
		url = "http://bbs.stuhome.net/forum.php?mod=viewthread&tid=" + tid
				+ "&extra=page%3D1";
		Post_Items p = new Post_Items();
		progressDialog = ProgressDialog.show(mContext, null, "请稍后");
		p.execute(url);
	}

	private class Post_Items extends AsyncTask<String, Void, Boolean> {

		@SuppressWarnings("unchecked")
		@Override
		protected Boolean doInBackground(String... params) {
			Document doc = null;
			final SharedPreferences mShared = mContext.getSharedPreferences(
					ACCOUNT_INFORMATION, Context.MODE_PRIVATE);
			try {
				// doc = Jsoup.connect(params[0]).userAgent("Mozilla")
				// .timeout(10 * 1000)
				// .cookies((Map<String, String>) mShared.getAll()).get();
				Connection.Response res = Jsoup.connect(params[0])
						.userAgent("Mozilla").timeout(10 * 1000)
						.cookies((Map<String, String>) mShared.getAll())
						.method(Method.GET).execute();
				Iterator<String> iterator = res.cookies().keySet().iterator();
				Editor editor = mShared.edit();
				while (iterator.hasNext()) {
					String key = iterator.next();
					editor.putString(key, res.cookie(key));
				}
				editor.commit();

				doc = res.parse();
				Elements inputs = doc.getElementsByTag("input");
				for (Element input : inputs) {
					if (input.attr("name").equals("formhash")) {
						formhash = input.attr("value");
					}
				}

				int pid;
				String name;
				String content;
				String time;
				List<String> pics;
				post_items.clear();
				Elements plhins = doc.getElementsByClass("plhin");
				for (Element plhin : plhins) {
					pid = Integer.valueOf(plhin.id().substring("pid".length()));
					Elements xw1s = plhin.getElementsByClass("xw1");
					name = xw1s.text();
					Elements t_fs = plhin.getElementsByClass("t_f");
					content = t_fs.text();
					time = plhin.getElementById("authorposton" + pid).text();

					Elements e_pics = plhin.select("div.mbn.savephotop");
					pics = new ArrayList<String>();
					if (e_pics != null) {
						for (Element e_pic : e_pics) {
							Elements imgs = e_pic.getElementsByTag("img");
							pics.add(imgs.attr("zoomfile"));
						}
					}
					if (pics.size() == 0) {
						post_items.add(new Post_Item_nopic(fid, tid, pid,
								content, name, time));
					} else {
						post_items.add(new Post_Item(fid, tid, pid, content,
								name, time, pics));
					}
				}

				if (!isloadmaxpage) {
					Element pgt = doc.getElementById("pgt");
					if (pgt == null) {
						max_page = 1;
						now_page = 1;
						isloadmaxpage = true;
						return false;
					} else {
						if (pgt.getElementsByTag("strong").text().equals("")) {
							max_page = 1;
							now_page = 1;
						} else {
							max_page = Integer.valueOf(pgt.getElementsByTag(
									"strong").text());
							now_page = max_page;
							Elements page_numbers = pgt.getElementsByTag("a");
							int now_number = 1;
							String str_now_number = "";
							for (Element page_number : page_numbers) {
								str_now_number = page_number.text();
								if (str_now_number.startsWith("... ")) {
									now_number = Integer.valueOf(str_now_number
											.substring("... ".length()));
								} else if (str_now_number.equals("下一页")) {
									continue;
								} else if (str_now_number.equals("返回列表")) {
									continue;
								} else if (str_now_number.equals("")) {
									continue;
								} else {
									now_number = Integer
											.valueOf(str_now_number);
								}
								if (now_number > max_page) {
									max_page = now_number;
								}
							}
							isloadmaxpage = true;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (!result) {
				Toast.makeText(mContext, "该帖已被移走!", Toast.LENGTH_SHORT).show();
			} else {
				if (!isfirstrefresh) {
					post_Item_Adapter = new Post_Item_Adapter(mContext,
							post_items, formhash);
					post_item_list.setAdapter(post_Item_Adapter);
					isfirstrefresh = true;
				} else {
					post_Item_Adapter.refresh(post_items);
				}
			}
			progressDialog.dismiss();
			now_content_page.setText("第" + now_page + "页" + ",共" + max_page
					+ "页");
			super.onPostExecute(result);
		}

	}

	private class Comment extends AsyncTask<String, Void, Integer> {

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(String... params) {
			int statusCode = 0;
			final SharedPreferences mShared = mContext.getSharedPreferences(
					ACCOUNT_INFORMATION, Context.MODE_PRIVATE);

			try {

				Connection.Response res = Jsoup
						.connect(params[0])
						.data("message", params[1], "formhash", formhash,
								"usesig", "1", "subject", "", "posttime", "",
								"handlekey", "reply", "replysubmit", "true")
						.cookies((Map<String, String>) mShared.getAll())
						.method(Method.POST).execute();
				System.out.println(res.parse());
				System.out.println(formhash);
				statusCode = res.statusCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return statusCode;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result == 200) {
				Toast.makeText(mContext, "回复成功!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "回复失败!", Toast.LENGTH_SHORT).show();
			}
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
				Post_Items p = new Post_Items();
				progressDialog = ProgressDialog.show(mContext, null, "请稍后");
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
				Post_Items p = new Post_Items();
				progressDialog = ProgressDialog.show(mContext, null, "请稍后");
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
							if (content.equals("")) {
								Toast.makeText(mContext, "请输入数字！",
										Toast.LENGTH_SHORT).show();
							} else if (!isNumeric(content)) {
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
									Post_Items p = new Post_Items();
									progressDialog = ProgressDialog.show(
											mContext, null, "请稍后");
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

	private class OnCommentClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			reply_url = "http://bbs.stuhome.net/forum.php?mod=post&infloat=yes&action=reply&fid="
					+ fid
					+ "&extra=page%3D1&tid="
					+ tid
					+ "&replysubmit=yes&handlekey=fastpost&inajax=1";

			final EditText editText = new EditText(mContext);
			editText.setHeight(mContext.getResources().getDimensionPixelSize(
					R.dimen.comment_dialog_height));
			editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					250) });
			editText.setGravity(Gravity.LEFT | Gravity.TOP);

			AlertDialog.Builder commentDialog = new AlertDialog.Builder(
					mContext).setTitle("回复楼主").setView(editText)
					.setNegativeButton("取消", null)
					.setPositiveButton("确定", null);
			final AlertDialog dialog = commentDialog.create();
			dialog.show();
			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							String message = editText.getText().toString();
							byte[] m = message.getBytes();
							if (m.length < 6) {
								Toast.makeText(mContext, "输入字节数不能小于6!",
										Toast.LENGTH_SHORT).show();
							} else {
								dialog.dismiss();
								final String[] params = { reply_url, message };
								final Comment comment = new Comment();
								progressDialog = ProgressDialog.show(mContext,
										null, "请稍后");

								comment.execute(params);
							}
						}
					});
		}

	}

}
