package edu.gae.domain.entity.base;

import java.util.Date;

import com.googlecode.objectify.annotation.Index;

public class AbstractLifeCycleEntity extends AbstractEntityById implements LifeCycleEntity{
	
	private Date createDate;
	@Index
	private Boolean active;
	
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public Boolean getActive() {
		return active;
	}
	
	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	
}
