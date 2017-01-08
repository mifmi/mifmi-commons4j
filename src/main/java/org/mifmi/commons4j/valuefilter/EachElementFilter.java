/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class EachElementFilter implements ValueFilter<Object, Object> {

	private ValueFilter<?, ?> filter;
	
	public EachElementFilter(ValueFilter<?, ?> filter) {
		this.filter = filter;
	}

	public <R extends Object> R filter(Object value) {
		R r = (R)filterObject(value);
		return r;
	}

	public Object filterObject(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value.getClass().isArray()) {
			Object[] values = (Object[])value;
			int len = values.length;
			for (int i = 0; i < len; i++) {
				Object val = values[i];
				try {
					Object ret = this.filter.filterObject(val);
					if (val != ret) {
						values[i] = ret;
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException(e, Integer.valueOf(i));
				}
			}
		} else if (value instanceof Map<?, ?>) {
			Map<? super Object, ? super Object> values = (Map<? super Object, ? super Object>)value;
			for (Map.Entry<Object, Object> entry : values.entrySet()) {
				Object key = entry.getKey();
				Object val = entry.getValue();
				try {
					Object ret = this.filter.filterObject(val);
					if (val != ret) {
						entry.setValue(val);
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException(e, key);
				}
			}
		} else if (value instanceof ListIterator<?> || value instanceof List<?>) {
			ListIterator<? super Object> values;
			if (value instanceof ListIterator<?>) {
				values = (ListIterator<? super Object>)value;
			} else {
				values = ((List<? super Object>)value).listIterator();
			}
			while (values.hasNext()) {
				int idx = values.nextIndex();
				Object val = values.next();
				try {
					Object ret = this.filter.filterObject(val);
					if (val != ret) {
						values.set(ret);
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException(e, Integer.valueOf(idx));
				}
			}
		} else if (value instanceof Iterator<?> || value instanceof Iterable<?>) {
			int idx = 0;
			Iterator<?> values;
			if (value instanceof Iterator<?>) {
				values = (Iterator<?>)value;
			} else {
				values = ((Iterable<?>)value).iterator();
			}
			while (values.hasNext()) {
				Object val = values.next();
				try {
					Object ret = this.filter.filterObject(val);
					if (val != ret) {
						throw new UnsupportedValueTypeException(value, this);
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException(e, Integer.valueOf(idx));
				}
				idx++;
			}
		} else if (value instanceof Enumeration<?>) {
			int idx = 0;
			Enumeration<?> values = (Enumeration<?>)value;
			while (values.hasMoreElements()) {
				Object val = values.nextElement();
				try {
					Object ret = this.filter.filterObject(val);
					if (val != ret) {
						throw new UnsupportedValueTypeException(value, this);
					}
				} catch (InvalidValueException e) {
					throw new InvalidValueException(e, Integer.valueOf(idx));
				}
				idx++;
			}
		} else {
			throw new UnsupportedValueTypeException(value, this);
		}
		
		return value;
	}
}
