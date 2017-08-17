package edu.gae.domain.repository.datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.googlecode.objectify.cmd.Query;

import edu.gae.domain.criteria.Period;
import edu.gae.domain.exception.NotFoundException;
import edu.gae.domain.repository.PersistableDao;

public abstract class DatastoreAbstractDAO<T> implements PersistableDao<T> {
	
	private static final String WILD_CARD = "*";
	
	protected final Class<T> c;

	public DatastoreAbstractDAO(Class<T> modelClass) {
		this.c = modelClass;
	}
	
	@Override
	public T addOrUpdate(T model) {		
		ofy().cache(false).save().entity(model).now();
		ofy().clear();
		return model;
	}
	
	@Override
	public void delete(T model) {
		if (model == null) throw new RuntimeException("msg_bke_id_not_provided");
		ofy().cache(false).delete().entities(model).now();
		ofy().clear();
	}
	
	@Override
	public T get(Long id) {
		if (id == null) throw new RuntimeException("msg_bke_id_not_provided");
		T entity = ofy().cache(false).load().type(c).id(id).now();
		if (entity == null){
			throw new NotFoundException("ID="+id, c.getSimpleName());
		}
		
		return entity;
	}
	
	@Override
	public List<T> list(Object ancestor, Map<String, Object> filters) {
		return this.list(ancestor, filters, null, null, null);
	}	
	
	@Override
	public List<T> list(Object ancestor, Map<String, Object> filters, Integer limit, Integer offset, Object order) {
		Query<T> q = this.filter(null, filters, limit, offset, order);
		return q.list();
	}
	
	@Override
	public Integer count(Object ancestor, Map<String, Object> filters) {
		Query<T> q = this.filter(null, filters, null, null, null);
		return q.count();
	}

	private Query<T> filter(Object ancestor, Map<String, Object> filters, Integer limit,
			Integer offset, Object order) {
		Query<T> q = ofy().cache(false).load().type(c);
		
		if (ancestor != null){
			q = q.ancestor(ancestor);
		}
		
		q = processFilter(filters, q);
		
		q = processPage(limit, offset, q);
		
		q = processOrder(order, q);
		return q;
	}

	private Query<T> processFilter(Map<String, Object> filters, Query<T> q) {
		if (filters != null) {
			for (String field : filters.keySet()) {
				Object value = filters.get(field);
				if (value instanceof Period) {
					q = buildPeriodFilter(q, field, value);
				} else if (isWildcardStringCriteria(value)) {
					q = buildWildCardStringFilter(q, field, (String) value);
				} else if (isDefaultValidCriteria(value)){
					q = q.filter(field, value);
				}
			}
		}
		return q;
	}

	private boolean isWildcardStringCriteria(Object value) {
		return value instanceof String 
				&& StringUtils.isNotBlank((String)value)
				&& ((String) value).contains(WILD_CARD);
	}

	private Query<T> buildWildCardStringFilter(Query<T> q, String field, String value) {
		q = q.filter(new FilterPredicate(field, 
				com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL, 
				value));
		q = q.filter(new FilterPredicate(field, 
				com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN, 
				field + "\uFFFD") );		
		return q;
	}

	private Query<T> buildPeriodFilter(Query<T> q, String field, Object value) {
		Period periodo = (Period) value;
		q = q.filter(new FilterPredicate(field, 
				com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL, 
				DateUtils.truncate(periodo.getInitialDate(), Calendar.DATE)) );
		q = q.filter(new FilterPredicate(field, 
				com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN, 
				DateUtils.truncate(DateUtils.addDays(periodo.getFinalDate(),1), Calendar.DATE)) );
		return q;
	}

	@SuppressWarnings("unchecked")
	private Query<T> processOrder(Object order, Query<T> q) {
		if (order != null){
			if (order instanceof List<?>){
				for (String item : (List<String>) order){
					q = q.order(item);
				}				
			} else if (order instanceof String && StringUtils.isNotBlank((String) order)){
				q = q.order((String) order);				
			}
			
		}
		return q;
	}

	private Query<T> processPage(Integer limit, Integer offset, Query<T> q) {
		if (limit != null && limit > 0 && offset != null && offset > 0) {
			q = q.limit(limit);
			q = q.offset(offset);
		}
		return q;
	}
	

	public List<?> listQuery(com.google.appengine.api.datastore.Query query){		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(query);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}
	
	private boolean isDefaultValidCriteria(Object value) {
		if (value instanceof String){
			return StringUtils.isNotBlank((String)value);
		} else {
			return value != null;
		}
	}
}