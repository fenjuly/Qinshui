package org.liurongchan.listener;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.liurongchan.model.Post_Item;
import org.liurongchan.qinshui.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午8:22:53 类说明
 */
public class Posts_Item_Long_Listener implements OnLongClickListener {

	private Context mContext;
	private Post_Item post_Item;
	private String formhash;

	private static final String ACCOUNT_INFORMATION = "accout_information";

	public Posts_Item_Long_Listener(Context mContext, Post_Item post_Item,
			String formhash) {
		this.mContext = mContext;
		this.post_Item = post_Item;
		this.formhash = formhash;
	}

	@Override
	public boolean onLongClick(View v) {

		final String reply_select_url = "http://bbs.stuhome.net/forum.php?mod=post&infloat=yes&action=reply&fid="
				+ post_Item.getFid()
				+ "&extra=page%3D1&tid="
				+ post_Item.getTid() + "&replysubmit=yes&inajax=1";

		final EditText editText = new EditText(mContext);
		editText.setHeight(mContext.getResources().getDimensionPixelSize(
				R.dimen.comment_dialog_height));
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				250) });
		editText.setGravity(Gravity.LEFT | Gravity.TOP);

		AlertDialog.Builder commentDialog = new AlertDialog.Builder(mContext)
				.setTitle("回复水友").setView(editText)
				.setNegativeButton("取消", null).setPositiveButton("确定", null);
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
							String[] params = { reply_select_url, message };
							Comment comment = new Comment();
							comment.execute(params);
						}
					}
				});

		return false;
	}

	private class Comment extends AsyncTask<String, Void, Integer> {

		@SuppressWarnings("unchecked")
		@Override
		protected Integer doInBackground(String... params) {
			int statusCode = 0;
			String noticetrimstr;
			final SharedPreferences mShared = mContext.getSharedPreferences(
					ACCOUNT_INFORMATION, Context.MODE_PRIVATE);

			noticetrimstr = "[quote][size=2][url=forum.php?mod=redirect&goto=findpost&pid="
					+ post_Item.getPid()
					+ "&ptid="
					+ post_Item.getTid()
					+ "][color=#999999]jacob-gu "
					+ post_Item.getTime()
					+ "[/color][/url][/size] "
					+ post_Item.getContent()
					+ "[/quote]";

			try {

				Connection.Response res = Jsoup
						.connect(params[0])
						.data("message", params[1], "formhash", formhash,
								"noticetrimstr", noticetrimstr, "handlekey",
								"reply", "replysubmit", "true")
						.cookies((Map<String, String>) mShared.getAll())
						.method(Method.POST).execute();
				System.out.println(res.parse());
				statusCode = res.statusCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return statusCode;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 200) {
				Toast.makeText(mContext, "回复成功!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(mContext, "回复失败!", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
