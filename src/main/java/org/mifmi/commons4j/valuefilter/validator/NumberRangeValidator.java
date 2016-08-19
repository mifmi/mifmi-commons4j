/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.valuefilter.validator;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.mifmi.commons4j.util.NumberUtilz;
import org.mifmi.commons4j.valuefilter.UnsupportedValueTypeException;

public class NumberRangeValidator extends AbstractValidator<Object> {
	
	public static final String VR_TOO_SMALL = createResultValue(NumberRangeValidator.class, "TOO_SMALL");
	public static final String VR_TOO_BIG = createResultValue(NumberRangeValidator.class, "TOO_BIG");
	
	private BigDecimal min;
	private BigDecimal max;
	
	public NumberRangeValidator(byte min, byte max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(short min, short max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(int min, int max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(long min, long max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(BigInteger min, BigInteger max) {
		this(new BigDecimal(min), new BigDecimal(max));
	}
	
	public NumberRangeValidator(float min, float max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(double min, double max) {
		this(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
	}
	
	public NumberRangeValidator(BigDecimal min, BigDecimal max) {
		this.min = min;
		this.max = max;
	}
	
	public BigDecimal getMin() {
		return min;
	}

	public BigDecimal getMax() {
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
		
		BigDecimal num = NumberUtilz.toBigDecimal(value);
		if (num == null) {
			throw new UnsupportedValueTypeException(value, this);
		}
		
		if (this.min != null && this.min.compareTo(num) > 0) {
			return VR_TOO_SMALL;
		}
		
		if (this.max != null && this.max.compareTo(num) < 0) {
			return VR_TOO_BIG;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "min=" + this.min + ", max=" + this.max;
	}
}
