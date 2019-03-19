package com.itsol.smartweb32.payload;

import javax.validation.constraints.NotBlank;

public class AddNewsRequest {

	@NotBlank
	private String title;

	@NotBlank
	private String avatar;

	@NotBlank
	private String text;

	public AddNewsRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
