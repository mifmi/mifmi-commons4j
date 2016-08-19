/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.util.Date;

import org.mifmi.commons4j.valuefilter.UnsupportedValueTypeException;

public class DateRangeValidator extends AbstractValidator<Object> {
	
	public static final String VR_TOO_SMALL = createResultValue(DateRangeValidator.class, "TOO_SMALL");
	public static final String VR_TOO_BIG = createResultValue(DateRangeValidator.class, "TOO_BIG");

	private Date min;
	private Date max;

	public DateRangeValidator(Date min, Date max) {
		this.min = min;
		this.max = max;
	}
	
	public Date getMin() {
		return min;
	}

	public Date getMax() {
		return max;
	}

	@Override
	protected Object[] makeParametersInfo() {
		return new Object[]{this.min, this.max};
	}

	@Override
	public String doValidate(Object value) {
		
		if (value == null) {
			return null;
		}
		
		if (value instanceof Date) {
			Date date = (Date)value;
			
			if (this.min != null && this.min.compareTo(date) > 0) {
				return VR_TOO_SMALL;
			}
			
			if (this.max != null && this.max.compareTo(date) < 0) {
				return VR_TOO_BIG;
			}
		} else {
			throw new UnsupportedValueTypeException(value, this);
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "min=" + this.min + ", max=" + this.max;
	}
}
