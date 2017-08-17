package edu.gae.domain.entity.base;

import com.googlecode.objectify.annotation.Index;

import edu.domain.helper.CharFilter;

public class SearcheableString {
	
	private String value;
	@Index
	private String cleanesed;
	@Index
	private String startWith;
	
	public SearcheableString(){}
	
	public SearcheableString(String value){
		this.value = value;
		this.cleanesed = CharFilter.removeMaskChars(CharFilter.replaceSpecial(value)).toLowerCase();
		this.startWith = getStartChars(cleanesed);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCleanesed() {
		return cleanesed;
	}	

	public String getStartWith() {
		return startWith;
	}
	
	private String getStartChars(String input) {		
		if (input != null && input.length() > 2){
			return input.substring(0, 2);
		}
		return input;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", cleanesed=" + cleanesed + ", startWith=" + startWith + "]";
	}	
}
