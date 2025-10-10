package com.Task.TaskService.Model;

import java.util.UUID;

public class Users {

	private UUID id=UUID.randomUUID();
	private String userName;
	private String password;
	private String email;
	
	public UUID getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
