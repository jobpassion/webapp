package com.redrum.webapp.weibo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SmzdmIdentifi {
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
