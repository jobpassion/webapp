package com.redrum.webapp.entity;

import java.sql.Timestamp;

public class ChatMessageEntity {
	private Integer id;
	private String fromId;
	private String toId;
	private String status;
	private String message;
	private Timestamp timestamp;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public static void main(String[] args) {
		System.out.println(new Timestamp(System.currentTimeMillis()));
	}
	
	
	
	

}
