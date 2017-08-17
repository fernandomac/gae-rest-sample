package edu.gae.domain.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class Login {
	
	@Id
	private Long id;
	@Index
	private String token;
	@Index
	private User user;
	private Date loginDate;
	
	public Login(){}
	
	public Login(String token, User user){
		this.token = token;
		this.user = user;
		this.loginDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", token=" + token + ", user=" + user + ", loginDate=" + loginDate + "]";
	}

}
