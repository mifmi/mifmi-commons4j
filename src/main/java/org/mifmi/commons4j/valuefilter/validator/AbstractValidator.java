/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import org.mifmi.commons4j.valuefilter.InvalidValueException;
import org.mifmi.commons4j.valuefilter.ValueFilter;

public abstract class AbstractValidator<T> implements Validator<T>, ValueFilter<T, T> {

	private Object[] parameters = null;
	
	protected static String createResultValue(Class<? extends Validator<?>> validatorClass, String resultName) {
		return validatorClass.getCanonicalName() + "." + resultName;
	}
	
	public abstract String doValidate(Object value);

	protected abstract Object[] makeParametersInfo();
	
	public Object[] getParameters() {
		if (this.parameters == null) {
			this.parameters = makeParametersInfo();
		}
		return this.parameters;
	}
	
	public String validate(T value) {
		return validateObject(value);
	}
	
	public String validateObject(Object value) {
		return doValidate(value);
	}
	
	public <R extends T> R filter(T value) {
		@SuppressWarnings("unchecked")
		R r = (R)filterObject(value);
		return r;
	}
	
	public Object filterObject(Object value) {
		String ret = validateObject(value);
		if (ret != null) {
			throw new InvalidValueException(value, this, ret);
		}
		
		return value;
	}
}
