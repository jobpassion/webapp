package com.redrum.webapp.weibo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

public class BasicEntity {
	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer id;
	@Transient
	private String clazz = this.getClass().getName();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	
}
