package edu.gae.domain.entity.base;

import java.util.Date;

public interface LifeCycleEntity extends EntityById {
	
	Boolean getActive();
	
	Date getCreateDate();
	
	void setCreateDate(Date createDate);

	void setActive(Boolean active);
	
}
