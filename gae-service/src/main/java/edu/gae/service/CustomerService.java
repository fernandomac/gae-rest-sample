package edu.gae.service;

import edu.gae.model.CustomerDto;
import edu.gae.model.result.CustomerResultDto;

public interface CustomerService extends EntityLifeCycle<CustomerDto>, EntitySearch<CustomerResultDto> {
	
}
