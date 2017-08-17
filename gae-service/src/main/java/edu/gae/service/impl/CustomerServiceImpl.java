package edu.gae.service.impl;

import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import edu.gae.domain.entity.Customer;
import edu.gae.domain.entity.User;
import edu.gae.domain.repository.PersistableDao;
import edu.gae.domain.repository.datastore.DatastoreCustomerDao;
import edu.gae.model.CustomerDto;
import edu.gae.model.Page;
import edu.gae.model.result.CustomerResultDto;
import edu.gae.service.CustomerService;
import edu.gae.service.converter.DozerHelper;

public class CustomerServiceImpl extends AbstractLifeCycleService<Customer, CustomerDto> implements CustomerService {

	private static CustomerService service;
	
	private CustomerServiceImpl() {}
	
	public static CustomerService getInstace(){
		if (service == null){
			service = new CustomerServiceImpl();
		}
		return service;
	}
	
	@Override
	public List<CustomerResultDto> listByCriteria(User user, Map<String, Object> searchCriteria, Page page, List<String> order) {
		List<Customer> domain = getDao().list(user.getAncestor(), searchCriteria, page.getLimit(), page.getOffset(), order);
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		return DozerHelper.map(mapper, domain, CustomerResultDto.class);
	}

	@Override
	protected PersistableDao<Customer> getDao() {
		return DatastoreCustomerDao.getInstance();
	}

	@Override
	protected Class<Customer> getDomainClass() {
		return Customer.class;
	}

	@Override
	protected Class<CustomerDto> getModelClass() {		
		return CustomerDto.class;
	}	
}
