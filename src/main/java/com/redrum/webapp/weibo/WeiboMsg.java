package com.redrum.webapp.weibo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class WeiboMsg extends BasicEntity implements Serializable {

	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer id;
	private String title;
	@Column(columnDefinition = "text")
	private String url;
	@Column(columnDefinition = "text")
	private String content;
	@Column(columnDefinition = "text")
	private String souceContent;
	@Column(columnDefinition = "text")
	private String dzContent;
	private String imgUrl;
	private Date createDate;
	private Date lastModifyDate;
	private Date sendDate;
	private String source;
	private String storeType;
	private Boolean immediately;
	private String shotUrl;
	private Integer sendHtzj;
	private Integer sendGht;
	private Integer send55ht;
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getImmediately() {
		return immediately;
	}
	public void setImmediately(Boolean immediately) {
		this.immediately = immediately;
	}
	public String getShotUrl() {
		return shotUrl;
	}
	public void setShotUrl(String shotUrl) {
		this.shotUrl = shotUrl;
	}
	public Integer getSendHtzj() {
		return sendHtzj;
	}
	public void setSendHtzj(Integer sendHtzj) {
		this.sendHtzj = sendHtzj;
	}
	public String getSouceContent() {
		return souceContent;
	}
	public void setSouceContent(String souceContent) {
		this.souceContent = souceContent;
	}
	public String getDzContent() {
		return dzContent;
	}
	public void setDzContent(String dzContent) {
		this.dzContent = dzContent;
	}
	public Integer getSendGht() {
		return sendGht;
	}
	public void setSendGht(Integer sendGht) {
		this.sendGht = sendGht;
	}
	public Integer getSend55ht() {
		return send55ht;
	}
	public void setSend55ht(Integer send55ht) {
		this.send55ht = send55ht;
	}
	
	
	
	

}
