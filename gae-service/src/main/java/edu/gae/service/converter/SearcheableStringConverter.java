package edu.gae.service.converter;

import org.dozer.DozerConverter;

import edu.gae.domain.entity.base.SearcheableString;

public class SearcheableStringConverter extends DozerConverter<String, SearcheableString>{

	public SearcheableStringConverter() {
		super(String.class, SearcheableString.class);		
	}

	@Override
	public SearcheableString convertTo(String source, SearcheableString destination) {
		if (source != null){
			destination = new SearcheableString(source); 
		}		
		return destination;
	}
	
	@Override
	public String convertFrom(SearcheableString source, String destination) {
		if (source != null){
			destination = source.getValue();
		}
		return destination;
	}
	
}
