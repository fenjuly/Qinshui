package org.liurongchan.model;

import java.util.ArrayList;
import java.util.List;

import org.liurongchan.listener.ImageView_Listener;
import org.liurongchan.listener.Posts_Item_Long_Listener;
import org.liurongchan.qinshui.R;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午2:23:31 类说明
 */
public class Post_Item implements ListItem {

	public Post_Item(int fid, int tid, int pid, String content, String name,
			String time, List<String> pics) {
		super();
		this.fid = fid;
		this.tid = tid;
		this.pid = pid;
		this.content = content;
		this.name = name;
		this.time = time;
		this.pics = pics;
		targetList = new ArrayList<Post_Item.ImageTarget>();
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	private int fid;

	private int tid;

	private int pid;

	private String content;
	private String name;

	private String time;

	private List<String> pics;

	private ArrayList<ImageTarget> targetList;
	
	private static List<ImageView> img_pics = new ArrayList<ImageView>();

	@Override
	public int getLayout() {
		return R.layout.post_content_item;
	}

	@Override
	public View getView(Context context, View convertView,
			LayoutInflater inflater, ViewGroup parent, String formhash) {
		TextView contentText;
		TextView nameText;
		TextView timeText;
		LinearLayout layout;

		if(convertView == null) {
			convertView = inflater.inflate(R.layout.post_content_item, parent,
					false);
		}
		layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
		ImageView img;
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.rightMargin = 15;
		ll.topMargin = 3;
		img_pics.clear();
		for (int i = 0; i < pics.size(); i++) {
			img = new ImageView(context);
			img.setLayoutParams(ll);
			img_pics.add(img);
			layout.addView(img);
		}
		contentText = (TextView) convertView.findViewById(R.id.post_content);
		nameText = (TextView) convertView.findViewById(R.id.post_owner_name);
		timeText = (TextView) convertView.findViewById(R.id.post_time);

		timeText.setText(time);
		nameText.setText(name);
		contentText.setText(content);
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				if (pics.get(i).startsWith("data")) {
//					ImageTarget target = new ImageTarget(img_pics.get(i));
//					targetList.add(target); // hold a strong reference to target
					Picasso.with(context)
							.load("http://bbs.stuhome.net/" + pics.get(i)).resize(150, 150)
							.into(img_pics.get(i));

					img_pics.get(i).setScaleType(ScaleType.CENTER_CROP);
					img_pics.get(i).setOnClickListener(
							new ImageView_Listener(context,
									"http://bbs.stuhome.net/" + pics.get(i)));
				} else {
//					ImageTarget target = new ImageTarget(img_pics.get(i));
//					targetList.add(target);
					Picasso.with(context).load(pics.get(i))
							.error(R.drawable.placeholder_fail).resize(100, 100).into(img_pics.get(i));
					img_pics.get(i).setScaleType(ScaleType.CENTER_CROP);
					img_pics.get(i).setOnClickListener(
							new ImageView_Listener(context, pics.get(i)));
				}
			}
		}
		convertView.setOnLongClickListener(new Posts_Item_Long_Listener(
				context, this, formhash));

		return convertView;
	}

	// this class is to resize the imageview when the bitmap too large to be
	// loaded , but there are some problems that i haven't solve
	// my original idea id like this :

	// Picasso.with(context).load(pics.get(i))
	// .error(R.drawable.placeholder_fail)
	// .into(new ImageTarge(img_pics.get(i)));
	//
	//
	// the problem i found is when i invoke the code, only onPrepareLoad method
	// are executed . not until i slide the screen the onBitmapLoaded are
	// executed.

	// i have solved the problem .see :
	// https://github.com/square/picasso/issues/251
	
	
	//-------------------------------------------------------4.18
	//this solution is discarded ,but i don't wanna delete it

	private class ImageTarget implements Target {

		private int width = 0;
		private int height = 0;
		private float ratio;

		private ImageView imageView;

		public ImageTarget(ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
			ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
			if (ratio > 1) {
				if (bitmap.getHeight() > 2048) {
					height = 2048;
					width = (int) (((float) 1 / ratio) * height);
				} else {
					width = bitmap.getWidth();
					height = bitmap.getHeight();
				}
			} else {
				if (bitmap.getWidth() > 2048) {
					width = 2048;
					height = (int) (ratio * width);
				} else {
					width = bitmap.getWidth();
					height = bitmap.getHeight();
				}
			}
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			imageView.setImageBitmap(bitmap);
			targetList.remove(this);
		}

		@Override
		public void onBitmapFailed(Drawable arg0) {
		}

		@Override
		public void onPrepareLoad(Drawable arg0) {
		}

	}
	

}
