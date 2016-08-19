/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

public class StrLengthValidator extends AbstractValidator<Object> {
	
	public static final String VR_TOO_SHORT = createResultValue(StrLengthValidator.class, "TOO_SHORT");
	public static final String VR_TOO_LONG = createResultValue(StrLengthValidator.class, "TOO_LONG");

	private Integer min;
	private Integer max;

	public StrLengthValidator(Integer min, Integer max) {
		this.min = min;
		this.max = max;
	}
	
	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.min, this.max};
	}

	@Override
	public String doValidate(Object value) {
		
		int len;
		if (value == null) {
			len = 0;
		} else {
			len = value.toString().length();
		}
		
		if (this.min != null && len < this.min.intValue()) {
			return VR_TOO_SHORT;
		}
		
		if (this.max != null && this.max.intValue() < len) {
			return VR_TOO_LONG;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "min=" + this.min + ", max=" + this.max;
	}
}
