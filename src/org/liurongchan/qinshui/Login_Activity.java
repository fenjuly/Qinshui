package org.liurongchan.qinshui;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.liurongchan.utils.MD5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-29 下午4:11:22 类说明
 */
public class Login_Activity extends Activity {

	private Context mContext;

	private EditText usernameText;
	private EditText passwordText;

	private Button confirm;

	private String username;
	private String password;
	
	private static final String ACCOUNT_INFORMATION = "accout_information";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mContext = this;
		usernameText = (EditText) findViewById(R.id.username);
		passwordText = (EditText) findViewById(R.id.password);
		confirm = (Button) findViewById(R.id.confirm);

		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				username = usernameText.getText().toString();
				password = passwordText.getText().toString();
				if (username.equals("")) {
					Toast.makeText(mContext, "用户名不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else if (password.equals("")) {
					Toast.makeText(mContext, "密码不能为空！", Toast.LENGTH_SHORT)
							.show();
				} else {
					MD5 m = new MD5();
					password = m.getMD5ofStr(password);
					Login login = new Login();
					login.execute();
				}
			}
		});
	}

	private class Login extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Document doc = null;
			String formhash = null;
			String loginaction = null;
			try {
				Connection.Response loginres_cookie = Jsoup
						.connect(
								"http://bbs.stuhome.net/member.php?mod=logging&action=login").userAgent("Mozilla")
						.method(Method.POST).execute();
				doc = loginres_cookie.parse();
				
				Element hash = doc.getElementById("scbar_form");
				Elements hashs = hash.getElementsByTag("input");
				for (Element element : hashs) {
					if (element.attr("name").equals("formhash")) {
						formhash = element.attr("value");
					}
				}
				
				Elements login = doc.getElementsByClass("cl");
				loginaction = login.attr("action");
				loginaction = loginaction.replace("amp", "");

				Connection.Response loginres = Jsoup
						.connect(
								"http://bbs.stuhome.net/"+ loginaction + "&inajax=1")
						.data("formhash", formhash, "referer",
								"http://bbs.stuhome.net/", "loginfield",
								"username", "password", password, "questionid",
								"0", "loginsubmit", "true", "username",
								username).cookie("sendmail", "1").cookies(loginres_cookie.cookies())
						.method(Method.POST).execute();
				if(loginres.cookie("v3hW_2132_auth") == null) {
					return false;
				} else {
					final SharedPreferences mShared = mContext.getSharedPreferences(
							ACCOUNT_INFORMATION, Context.MODE_PRIVATE);
					Editor editor = mShared.edit();
					editor.putString("username", username);
					editor.putString("password", password);
					Map<String, String> cookies = loginres.cookies();
					Iterator<String> iterator = cookies.keySet().iterator();
//					Map<String, String> pre_cookies = loginres_cookie.cookies();
//					Iterator<String> iterator2 = pre_cookies.keySet().iterator();
//					while(iterator2.hasNext()){
//						String key = iterator2.next();
//						editor.putString("key", pre_cookies.get(key));
//					}
					while(iterator.hasNext()) {
						String key = iterator.next();
						editor.putString(key, cookies.get(key));
					}
					editor.putString("v3hW_2132_saltkey", loginres_cookie.cookie("v3hW_2132_saltkey"));
					editor.putString("login", "yes");
					editor.commit();
					return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(!result) {
				Toast.makeText(mContext, "用户名或密码错误!", Toast.LENGTH_SHORT)
				.show();
				usernameText.setText("");
				passwordText.setText("");
			} else {
				Toast.makeText(mContext, "登录成功!", Toast.LENGTH_SHORT)
				.show();
				Intent intent = new Intent(mContext, Main_Forum_Activity.class);
				mContext.startActivity(intent);
				finish();
			}
		}

	}

}
