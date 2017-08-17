package edu.gae.domain.entity;

import com.googlecode.objectify.annotation.Index;

import edu.domain.helper.Convert;

public class Contact {	
	
	private String phone;
	@Index
	private String email;
	
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
		return Convert.ObjectToStringFields(this) + " - "+ super.toString();
	}
	
	public boolean hasConsistentValues(){
		return Convert.objectToHashcodeFields(this) > 0; 
	}

}
