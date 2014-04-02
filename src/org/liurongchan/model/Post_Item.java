package org.liurongchan.model;

/**
 * @author [FeN]July E-mail: newfenjuly@gmail.com
 * @version 创建时间：2014-4-2 下午2:23:31 类说明
 */
public class Post_Item {

	public Post_Item(int fid, int tid, int pid, String content, String name, String time) {
		super();
		this.fid = fid;
		this.tid = tid;
		this.pid = pid;
		this.content = content;
		this.name = name;
		this.time = time;
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

	private int fid;

	private int tid;

	private int pid;

	private String content;
	private String name;
	
	private String time;

}
