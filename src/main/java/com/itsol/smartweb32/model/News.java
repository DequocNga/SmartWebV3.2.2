package com.itsol.smartweb32.model;
// Generated Mar 10, 2019 9:23:25 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * News generated by hbm2java
 */
@Entity
@Table(name = "news", catalog = "smart_web_3")
public class News implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	/* private Users users; */
	private Long userID;
	private Categories categories;
	private String title;
	private String avatar;
	private String text;
	private Date createdAt;
	private Date updatedAt;

	public News() {
	}

	public News(String title, String avatar, String text, Long usersID) {
		super();
		this.title = title;
		this.avatar = avatar;
		this.text = text;
		this.userID = usersID;
	}

	public News(Long usersID, Categories categories, String text) {
		super();
		this.userID = usersID;
		this.categories = categories;
		this.text = text;
	}

	public News(Long usersID, Categories categories, String text, Date createdAt, Date updatedAt) {
		this.userID = usersID;
		this.categories = categories;
		this.text = text;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "user_id", nullable = false) public Users getUsers() {
	 * return this.users; }
	 * 
	 * public void setUsers(Users users) { this.users = users; }
	 */

	@Column(name = "user_id", length = 20)
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categories_id", nullable = false)
	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	@Column(name = "text", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "avatar", nullable = false, length = 65535)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "title", nullable = false, length = 65535)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, length = 19)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false, length = 19)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}