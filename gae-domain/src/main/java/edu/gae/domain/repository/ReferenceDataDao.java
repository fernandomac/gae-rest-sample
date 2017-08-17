package edu.gae.domain.repository;

import edu.gae.domain.entity.base.Selectable;

public interface ReferenceDataDao<T extends Selectable> extends ReadOnlyDao<T>, SelectableDao<T>{
	
}
