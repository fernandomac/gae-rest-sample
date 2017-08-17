package edu.gae.web.rest.endpoint;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import edu.domain.helper.CharFilter;
import edu.gae.domain.criteria.Period;
import edu.gae.model.Page;
import edu.gae.model.constants.Model;

public abstract class AbstractResource {
	
	private static final Logger log = Logger.getLogger(AbstractResource.class.getName());
	
	protected List<String> buildOrder(UriInfo uriInfo) {
		List<String> order = uriInfo.getQueryParameters().get("order");
		return order;
	}
	
	protected List<String> buildOrderCleanesedField(String fieldName, UriInfo uriInfo) {
		List<String> order = uriInfo.getQueryParameters().get("order");
		if (order != null){
			for (String item : order){
				if (fieldName.equals(item)){
					order.remove(item);
					order.add(fieldName + ".cleanesed");
				}
			}
		}
		return order;
	}

	protected Page buildPage(UriInfo uriInfo) {
		Page page = new Page();
		page.setLimit(NumberUtils.toInt(uriInfo.getQueryParameters().getFirst("size")));
		page.setOffset(NumberUtils.toInt(uriInfo.getQueryParameters().getFirst("page")));
		return page;
	}
	
	protected void addValidStringFilter(String fieldName, UriInfo uriInfo, HashMap<String, Object> searchCriteria){
		String field = uriInfo.getQueryParameters().getFirst(fieldName);
		if (StringUtils.isNotBlank(field)){
			searchCriteria.put(fieldName, field);
		}
	}
	
	protected void addValidNumberFilter(String fieldName, UriInfo uriInfo, HashMap<String, Object> searchCriteria){
		Integer field = NumberUtils.createInteger(uriInfo.getQueryParameters().getFirst(fieldName));
		if (field != null){
			searchCriteria.put(fieldName, field);
		}
	}
	
	protected void addValidPeriodFilter(String fieldName, UriInfo uriInfo, HashMap<String, Object> searchCriteria){
		String dataInicial = uriInfo.getQueryParameters().getFirst("dataInicial");
		String dataFinal = uriInfo.getQueryParameters().getFirst("dataFinal");
		
		if (StringUtils.isNotBlank(dataInicial) && StringUtils.isNotBlank(dataFinal)){
			try {
				searchCriteria.put(fieldName, new Period(DateUtils.parseDate(dataInicial, Model.DATE_FORMAT),
						DateUtils.parseDate(dataFinal, Model.DATE_FORMAT)));
			} catch (ParseException e) {				
				Object [] dados = {dataInicial, dataFinal};
				log.log(Level.WARNING, "Invalid date", dados);
			}
		}
	}
	
	protected void addCleanesedStringFilter(String fieldName, UriInfo uriInfo, HashMap<String, Object> searchCriteria) {
		String value = uriInfo.getQueryParameters().getFirst(fieldName);		 
		 if (StringUtils.isNotBlank(value)){
			 searchCriteria.put(fieldName+".cleanesed", CharFilter.removeMaskChars(CharFilter.replaceSpecial(value)).toLowerCase());
		 }
	}
	
	protected void addStartWithStringFilter(String fieldName, UriInfo uriInfo, HashMap<String, Object> searchCriteria) {
		String value = uriInfo.getQueryParameters().getFirst(fieldName+"Start");		 
		 if (StringUtils.isNotBlank(value)){
			 if (value.length() > 2){
				 value = value.substring(0, 2);
			 }
			 searchCriteria.put(fieldName+".startWith", value.toLowerCase());
		 }
	}
}
