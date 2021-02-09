package com.backend.sv1.security.payload.pojos;

import java.util.List;

public class UserJwt {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private List<String> roles;
	private Long id_persona;

	public UserJwt (String accessToken, Long id, String username, List<String> roles, Long id_persona) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.id_persona=id_persona;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
	public Long getId_persona() {
		return id_persona;
	}

	public void setId_persona(Long id_persona) {
		this.id_persona = id_persona;
	}
}
