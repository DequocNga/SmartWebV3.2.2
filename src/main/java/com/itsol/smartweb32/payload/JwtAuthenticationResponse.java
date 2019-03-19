package com.itsol.smartweb32.payload;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long userID;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public JwtAuthenticationResponse(String accessToken, Long userID) {
		this.accessToken = accessToken;
		this.userID = userID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public JwtAuthenticationResponse(Long id) {
		this.userID = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
