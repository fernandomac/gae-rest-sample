package edu.gae.model;

public class AddressDto {
	
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zipcode;
	
	public AddressDto(){}
	
	public AddressDto(String line1, String line2, String city, String state, String zipcode) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
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
		return "AddressDto [line1=" + line1 + ", line2=" + line2 + ", city=" + city + ", state=" + state + ", zipcode="
				+ zipcode + "]";
	}	

}
