/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter;

public class InvalidValueException extends ValueFilterException {
	private static final long serialVersionUID = -6558123723506710727L;

	private Object value;

	private Object valueKey;
	
	private ValueFilter<?, ?> valueFilter;
	
	private String validationResult;

	public InvalidValueException(Object value, ValueFilter<?, ?> valueFilter, String validationResult) {
		this(value, null, valueFilter, validationResult);
	}
	
	public InvalidValueException(InvalidValueException cause, Object valueKey) {
		super(cause);
		this.value = cause.getValue();
		this.valueKey = valueKey;
		this.valueFilter = cause.getValueFilter();
		this.validationResult = cause.getValidationResult();
	}
	
	public InvalidValueException(Object value, Object valueKey, ValueFilter<?, ?> valueFilter, String validationResult) {
		this.value = value;
		this.valueKey = valueKey;
		this.valueFilter = valueFilter;
		this.validationResult = validationResult;
	}

	public Object getValue() {
		return this.value;
	}
	
	public Object getValueKey() {
		return this.valueKey;
	}

	public ValueFilter<?, ?> getValueFilter() {
		return this.valueFilter;
	}

	public String getValidationResult() {
		return this.validationResult;
	}
}
