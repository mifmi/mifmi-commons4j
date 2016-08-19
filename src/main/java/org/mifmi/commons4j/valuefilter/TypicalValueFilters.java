/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypicalValueFilters<T, U> implements ValueFilter<T, U> {
	
	private List<ValueFilter<T, U>> filters;

	public TypicalValueFilters() {
		this.filters = new ArrayList<ValueFilter<T, U>>();
	}
	
	public TypicalValueFilters(int capacity) {
		this.filters = new ArrayList<ValueFilter<T, U>>(capacity);
	}
	
	public TypicalValueFilters(Collection<ValueFilter<T, U>> filters) {
		if (filters == null) {
			throw new NullPointerException();
		}
		this.filters = new ArrayList<ValueFilter<T, U>>(filters);
	}
	
	public TypicalValueFilters(ValueFilter<T, U>... filters) {
		if (filters == null) {
			throw new NullPointerException();
		}
		this.filters = new ArrayList<ValueFilter<T, U>>(filters.length);
		for (ValueFilter<T, U> filter : filters) {
			this.filters.add(filter);
		}
	}

	public TypicalValueFilters<T, U> add(ValueFilter<T, U> filter) {
		this.filters.add(filter);
		return this;
	}

	public TypicalValueFilters<T, U> addAll(Collection<ValueFilter<T, U>> filters) {
		this.filters.addAll(filters);
		return this;
	}
	
	public TypicalValueFilters<T, U> remove(ValueFilter<T, U> filter) {
		this.filters.remove(filter);
		return this;
	}
	
	public int size() {
		return this.filters.size();
	}
	
	public <R extends U> R filter(T value) {
		@SuppressWarnings("unchecked")
		R r = (R)filterObject(value);
		return r;
	}

	public Object filterObject(Object value) {
		Object v = value;
		for (ValueFilter<T, U> filter : this.filters) {
			v = filter.filterObject(v);
		}
		return v;
	}
}
