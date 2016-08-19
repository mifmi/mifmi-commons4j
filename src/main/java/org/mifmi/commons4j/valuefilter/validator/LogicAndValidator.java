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

public class LogicAndValidator implements Validator<Object>, ValueFilter<Object, Object> {

	private Validator<?>[] validators;
	
	public LogicAndValidator(Validator<?>... validators) {
		this.validators = validators;
	}

	@Override
	public Object[] getParameters() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String validate(Object value) {
		return validateObject(value);
	}

	@Override
	public String validateObject(Object value) {
		String validationResult = null;
		for (Validator<?> validator : this.validators) {
			validationResult = validator.validateObject(value);
			if (validationResult != null) {
				break;
			}
		}
		return validationResult;
	}

	@Override
	public <R> R filter(Object value) {
		@SuppressWarnings("unchecked")
		R r = (R)filterObject(value);
		return r;
	}

	@Override
	public Object filterObject(Object value) {
		String ret = validateObject(value);
		if (ret != null) {
			throw new InvalidValueException(value, this, ret);
		}
		
		return value;
	}
}
