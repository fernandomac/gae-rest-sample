package edu.gae.domain.entity.ref;

import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlEnumValue;

import edu.gae.domain.entity.base.Selectable;


public enum UserProfile implements Selectable {	
	
	@XmlEnumValue("Customer") CUSTOMER(0, "CUSTOMER", "Customer"),
	@XmlEnumValue("Admin") ADMIN(1, "ADMIN", "Administrador"),
	@XmlEnumValue("Master") MASTER(2, "MASTER", "Master");
	
	private static final Logger log = Logger.getLogger(UserProfile.class.getName());
	
	private Integer code;
	private String key;
	private String value;
	
	private UserProfile(Integer code, String key, String value){
		this.code = code;
		this.key = key;	
		this.value = value;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public static UserProfile equals(String input){
		for(UserProfile aux : UserProfile.values()){
			if (aux.value.equalsIgnoreCase(input) || aux.key.equalsIgnoreCase(input)){
				return aux;
			}
		}
		log.info("Error enum UserProfile. Invalid key: "+input);
		return null;
	}
	
	public static UserProfile equals(Integer code){
		for(UserProfile aux: UserProfile.values()){
			if (aux.code.equals(code)){
				return aux;
			}
		}
		log.info("Error enum UserProfile. Invalid code: "+code);
		return null;
	}

	@Override
	public String getKey() {	
		return key;
	}	
	
	@Override
	public String getValue() {
		return value;
	}
	
}
