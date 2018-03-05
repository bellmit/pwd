package com.tastebychance.sonchance.loginandregister.bean;


import java.io.Serializable;

public class LoginRes  implements Serializable {
	private Object user_info;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
