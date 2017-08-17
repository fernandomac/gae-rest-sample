package edu.gae.domain.entity;

import com.googlecode.objectify.annotation.Index;

import edu.domain.helper.Convert;

public class Address {
	
	private String line1;
	private String line2;
	@Index
	private String city;
	@Index
	private String state;
	private String zipcode;
	
	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return Convert.ObjectToStringFields(this) + " - "+ super.toString();
	}
	
	public boolean hasConsistentValues(){
		return Convert.objectToHashcodeFields(this) > 0; 
	}	
}
