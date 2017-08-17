package edu.gae.domain.entity.ref;

import javax.xml.bind.annotation.XmlEnumValue;

import edu.gae.domain.entity.base.Selectable;

public enum UserStatus implements Selectable {
	@XmlEnumValue("New") NEW(0, "NEW", "New"),
	@XmlEnumValue("Active") ACTIVE(1, "ACTIVE", "Active"),
	@XmlEnumValue("Blocked") BLOCKED(2, "BLOCKED", "Blocked");
	
	private Integer code;
	private String key;
	private String value;
	
	private UserStatus(Integer code, String key, String value){
		this.code = code;
		this.key = key;	
		this.value = value;
	}
	
	public Integer getCode() {
		return code;
	}
	
	@Override
	public String getKey() {		
		return key;
	}

	@Override
	public String getValue() {		
		return value;
	}
	
	public UserStatus equals(String key){
		for(UserStatus aux : UserStatus.values()){
			if (aux.key.equalsIgnoreCase(key) || aux.value.equalsIgnoreCase(key) ){
				return aux;
			}
		}
		System.err.println("Error enum UserStatus. Invalid key: "+key);
		return null;
	}
	
	public UserStatus equals(Integer code){
		for(UserStatus aux: UserStatus.values()){
			if (aux.code.equals(code)){
				return aux;
			}
		}
		System.err.println("Error enum UserStatus. Invalid code: "+code);
		return null;
	}
	
	public static UserStatus keyof(String key){
		for(UserStatus aux : UserStatus.values()){
			if (aux.key.equalsIgnoreCase(key) || aux.value.equalsIgnoreCase(key) ){
				return aux;
			}
		}
		System.err.println("Error enum UserStatus. Invalid key: "+key);
		return null;
	}	
}
