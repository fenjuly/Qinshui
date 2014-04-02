package org.liurongchan.model;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-3-31 下午2:40:48 类说明
 */
public class Post {
	
	
	private String title;
	private int id;
	
	public Post(String title, int id) {
		this.title = title;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
