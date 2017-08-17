package edu.gae.domain.entity.base;

import com.googlecode.objectify.annotation.Id;

public abstract class AbstractEntityById implements EntityById{
	
	@Id
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
}
