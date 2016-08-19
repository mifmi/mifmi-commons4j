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

public class ValueFilters implements ValueFilter<Object, Object> {
	
	private List<ValueFilter<?, ?>> filters;

	public ValueFilters() {
		this.filters = new ArrayList<ValueFilter<?, ?>>();
	}
	
	public ValueFilters(int capacity) {
		this.filters = new ArrayList<ValueFilter<?, ?>>(capacity);
	}
	
	public ValueFilters(Collection<ValueFilter<?, ?>> filters) {
		if (filters == null) {
			throw new NullPointerException();
		}
		this.filters = new ArrayList<ValueFilter<?, ?>>(filters);
	}
	
	public ValueFilters(ValueFilter<?, ?>... filters) {
		if (filters == null) {
			throw new NullPointerException();
		}
		this.filters = new ArrayList<ValueFilter<?, ?>>(filters.length);
		for (ValueFilter<?, ?> filter : filters) {
			this.filters.add(filter);
		}
	}

	public ValueFilters add(ValueFilter<?, ?> filter) {
		this.filters.add(filter);
		return this;
	}

	public ValueFilters addIf(ValueFilter<?, ?> filter, boolean expr) {
		if (expr) {
			this.filters.add(filter);
		}
		return this;
	}

	public ValueFilters addAll(Collection<? extends ValueFilter<?, ?>> filters) {
		this.filters.addAll(filters);
		return this;
	}
	
	public ValueFilters remove(ValueFilter<?, ?> filter) {
		this.filters.remove(filter);
		return this;
	}
	
	public int size() {
		return this.filters.size();
	}
	
	public <R extends Object> R filter(Object value) {
		@SuppressWarnings("unchecked")
		R r = (R)filterObject(value);
		return r;
	}

	public Object filterObject(Object value) {
		Object v = value;
		for (ValueFilter<?, ?> filter : this.filters) {
			v = filter.filterObject(v);
		}
		return v;
	}
}
