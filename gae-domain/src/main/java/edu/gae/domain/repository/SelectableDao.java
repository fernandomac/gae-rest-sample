package edu.gae.domain.repository;

import edu.gae.domain.entity.base.Selectable;

public interface SelectableDao<T extends Selectable> extends ReadOnlyDao<T> {

	@Override
	T find(Object ancestor, String key);
}
