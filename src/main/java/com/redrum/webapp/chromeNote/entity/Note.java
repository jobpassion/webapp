package com.redrum.webapp.chromeNote.entity;

import java.sql.Timestamp;

public class Note {
	private Integer id;
	private String userId;
	private String title;
	private String desc;
	private String url;
	private String list;
	private String tag;
	private Timestamp updated;
	private Timestamp created;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Timestamp getUpdated() {
		return updated;
	}
	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	

}
