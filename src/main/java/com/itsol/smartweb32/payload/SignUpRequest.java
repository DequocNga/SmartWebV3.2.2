package com.itsol.smartweb32.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
	@NotBlank
	@Size(min = 4, max = 40)
	private String name;

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	/*
	 * @NotBlank
	 * 
	 * @Size(min = 3, max = 28) private String fullname;
	 */

	@NotBlank
	@Size(min = 6, max = 30)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

	/*
	 * @NotBlank private Roles roles;
	 */
	/*
	 * public Roles getRoles() { return roles; }
	 * 
	 * public void setRoles(Roles roles) { this.roles = roles; }
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * public String getFullname() { return fullname; }
	 * 
	 * public void setFullname(String fullname) { this.fullname = fullname; }
	 */

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
