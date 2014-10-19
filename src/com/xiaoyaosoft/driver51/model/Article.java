package com.xiaoyaosoft.driver51.model;

public class Article {
	private String content;
	private int id;
	private int parent_id;
	private String photo;
	private String title;
	private int num;
	private int level;

	public String getContent() {
		return this.content;
	}

	public int getId() {
		return this.id;
	}

	public int getParent_id() {
		return this.parent_id;
	}

	public String getPhoto() {
		return this.photo;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String paramString) {
		this.content = paramString;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setParent_id(int paramInt) {
		this.parent_id = paramInt;
	}

	public void setPhoto(String paramString) {
		this.photo = paramString;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}