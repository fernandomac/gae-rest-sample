package edu.gae.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.bval.jsr303.ApacheValidationProvider;

import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.exception.BusinessException;

public class EntityValidateService {

	private static EntityValidateService entityValidateService;	
	private Validator validator;
	
	private EntityValidateService(){
		ValidatorFactory avf =
	            Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();		
		this.validator = avf.getValidator();;
	}
	
	public static EntityValidateService getInstance(){
		if (entityValidateService == null){
			entityValidateService = new EntityValidateService();
		}
		return entityValidateService;
	}	

	public void validate(Object entity, Class<?> ... ruleSet){
	
		Set<ConstraintViolation<Object>> violations = validator.validate(entity, ruleSet);
		
		StringBuffer sb = new StringBuffer();
		
		List<String> nullFields = new ArrayList<String>();
		
		List<String> patternFields = new ArrayList<String>();
		
		for(ConstraintViolation<Object> violation : violations){		
			if (!matchNullAnnotation(violation, nullFields) 
					&& !matchPatternAnnotation(violation, patternFields)){
				sb.append(String.format(" %s ", violation.getMessageTemplate()));
			}			
		}
		
		if (!nullFields.isEmpty()){
			sb.append(String.format(" %s  %s .", ReturnMessage.NOT_NULL_MESSAGE, nullFields.toString()));
		}
		
		if (!patternFields.isEmpty()){
			sb.append(String.format(" %s  %s .", ReturnMessage.INVALID_ARGUMENTO, patternFields.toString()));
		}
		
		if (!violations.isEmpty()){
			throw new BusinessException(sb.toString());
		}
				
	}
	
	private boolean matchNullAnnotation(ConstraintViolation<Object> violation, List<String> nullFields){
		boolean isNullAnnotation = false;
		if (violation.getConstraintDescriptor().getAnnotation() instanceof NotNull) {
			nullFields.add(violation.getPropertyPath().toString());
			isNullAnnotation = true;
		}
		return isNullAnnotation;
	}
	
	private boolean matchPatternAnnotation(ConstraintViolation<Object> violation, List<String> patternFields){
		boolean isPatternAnnotation = false;
		if (violation.getConstraintDescriptor().getAnnotation() instanceof Pattern) {
			patternFields.add(violation.getPropertyPath().toString());
			isPatternAnnotation = true;
		}
		return isPatternAnnotation;
	}
}
