package edu.gae.model;

import java.util.Date;

public interface LifeCycleDto extends ModelDto {
	
	Boolean getActive();
	
	Date getCreateDate();
	
	void setCreateDate(Date createDate);

	void setActive(Boolean active);
	
}
