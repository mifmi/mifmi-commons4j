/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

public class SameValidator extends AbstractValidator<Object> {
	
	public static final String VR_NOT_VALID = createResultValue(SameValidator.class, "NOT_VALID");

	private Object sameValue;
	
	public SameValidator(String sameValue) {
		this.sameValue = sameValue;
	}

	public Object getSameValue() {
		return sameValue;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.sameValue};
	}

	@Override
	public String doValidate(Object value) {
		if (this.sameValue == null || value == null) {
			if (this.sameValue == value) {
				return null;
			} else {
				return VR_NOT_VALID;
			}
		}
		
		if (!(this.sameValue.equals(value))) {
			return VR_NOT_VALID;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "sameValue=" + this.sameValue;
	}
}
