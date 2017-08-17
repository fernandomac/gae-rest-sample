package edu.gae.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginDto {	
	
	private String name;	
    private String email;
	private String profile;
	private String token;
	private String logo;
	private String status;
	private Long ancestor;
	
	public LoginDto(){}
	
	public LoginDto(String name, String email,  String profile, String token, String logo, Long ancestor){
		this.name = name;
		this.email = email;
		this.profile = profile;		
		this.token = token;
		this.logo = logo;
		this.ancestor = ancestor;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}	

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAncestor() {
		return ancestor;
	}

	public void setAncestor(Long ancestor) {
		this.ancestor = ancestor;
	}

	@Override
	public String toString() {
		return "LoginDto [name=" + name + ", email=" + email + ", profile=" + profile + ", token=" + token + ", logo="
				+ logo + ", status=" + status + ", ancestor=" + ancestor + "]";
	}		
}
