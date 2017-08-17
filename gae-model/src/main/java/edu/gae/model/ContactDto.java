package edu.gae.model;

public class ContactDto {
	
	private String phone;
	private String email;
	
	public ContactDto(){}
	
	public ContactDto(String phone, String email) {
		super();
		this.phone = phone;
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ContactDto [phone=" + phone + ", email=" + email + "]";
	}

}
