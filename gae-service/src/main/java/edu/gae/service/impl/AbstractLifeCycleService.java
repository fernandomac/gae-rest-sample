package edu.gae.service.impl;

import java.util.Date;

import javax.validation.groups.Default;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import edu.gae.domain.constants.ReturnMessage;
import edu.gae.domain.entity.User;
import edu.gae.domain.entity.base.DescendantEntity;
import edu.gae.domain.entity.base.LifeCycleEntity;
import edu.gae.domain.exception.BusinessException;
import edu.gae.domain.exception.NotFoundException;
import edu.gae.service.EntityLifeCycle;

public abstract class AbstractLifeCycleService<D extends LifeCycleEntity, M> implements EntityLifeCycle<M>{

	protected abstract edu.gae.domain.repository.PersistableDao<D> getDao();
	protected abstract Class<D> getDomainClass();
	protected abstract Class<M> getModelClass();
		
	@Override
	public M create(M dto, User user) {
		isValidArgument(dto);
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = mapper.map(dto, getDomainClass());
		setCreateFields(entity, user);	
		EntityValidateService.getInstance().validate(entity, Default.class);		
		entity = getDao().addOrUpdate(entity);
		return mapper.map(entity, getModelClass());		
	}
	
	@Override
	public M amend(Long id, M dto, User user) {
		isValidArgument(dto);
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = getDao().get(id);		
		validateOwnership(entity, user);
		mapper.map(dto, entity);
		EntityValidateService.getInstance().validate(entity, Default.class);
		entity = getDao().addOrUpdate(entity);
		return mapper.map(entity, getModelClass());
	}
	
	@Override
	public M deactivate(Long id, User user) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = getDao().get(id);		
		validateOwnership(entity, user);
		entity.setActive(Boolean.FALSE);
		entity = getDao().addOrUpdate(entity);
		return mapper.map(entity, getModelClass());
	}

	@Override
	public M reactivate(Long id, User user) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = getDao().get(id);		
		validateOwnership(entity, user);
		entity.setActive(Boolean.TRUE);
		entity = getDao().addOrUpdate(entity);
		return mapper.map(entity, getModelClass());
	}
	
	@Override
	public M findById(Long id, User user) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = getDao().get(id);		
		validateOwnership(entity, user);
		return mapper.map(entity, getModelClass());
	}
	
	@Override
	public M findUnique(String key, User user) {
		Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
		D entity = getDao().find(user.getAncestor(), key);
		hasEntityBeenFound(key, entity);
		return mapper.map(entity, getModelClass());
	}		
	
	protected void setCreateFields(D entity, User user){
		entity.setCreateDate(new Date());
		entity.setActive(Boolean.TRUE);
		if (entity instanceof DescendantEntity && user != null){			
			((DescendantEntity) entity).setAncestor(user.getAncestor());			
		}
	}
	
	protected void hasEntityBeenFound(String key, D entity) {
		if (entity == null){
			throw new NotFoundException(key, key);
		}
	}
	
	protected void isValidArgument(Object obj){
		if (obj == null){
			throw new BusinessException(ReturnMessage.INVALID_ARGUMENTO);
		}
	}
	
	protected void validateOwnership(D entity, User user) {		
		if (entity instanceof DescendantEntity && user.getAncestor() != null){			
			if (!((DescendantEntity) entity).getAncestor().equals(user.getAncestor())){
				throw new NotFoundException("ID="+entity.getId(), entity.getClass().getSimpleName());
			}		
		}		
	}
	
}
