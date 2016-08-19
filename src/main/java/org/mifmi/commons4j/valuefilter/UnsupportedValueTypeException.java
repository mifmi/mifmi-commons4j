/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter;

public class UnsupportedValueTypeException extends ValueFilterException {
	private static final long serialVersionUID = -6558123723506710727L;

	private Object value;
	
	private ValueFilter<?, ?> valueFilter;
	
	public UnsupportedValueTypeException(Object value, ValueFilter<?, ?> valueFilter) {
		this.value = value;
		this.valueFilter = valueFilter;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValueFilter<?, ?> getValueFilter() {
		return valueFilter;
	}

	public void setValueFilter(ValueFilter<?, ?> valueFilter) {
		this.valueFilter = valueFilter;
	}
}
