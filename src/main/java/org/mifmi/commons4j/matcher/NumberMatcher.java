/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

import java.math.BigDecimal;

import org.mifmi.commons4j.util.NumberUtilz;

public class NumberMatcher extends AbstractMatcher<Number> {

	private Number value;
	private QuantityMatchType matchType;
	private BigDecimal decValue;
	
	
	public NumberMatcher(Number value, QuantityMatchType matchType) {
		this.value = value;
		this.matchType = matchType;
		this.decValue = null;
	}
	
	public static IMatcher<Number> between(Number from, Number to) {
		IMatcher<Number> matcher = null;
		
		if (from != null) {
			matcher = new NumberMatcher(from, QuantityMatchType.GreaterEquals);
		}
		
		if (to != null) {
			IMatcher<Number> toMatcher = new NumberMatcher(to, QuantityMatchType.LessEquals);
			if (matcher == null) {
				matcher = toMatcher;
			} else {
				matcher = matcher.and(toMatcher);
			}
		}
		
		return matcher;
	}

	@Override
	public Class<? super Number> getType() {
		return Number.class;
	}

	@Override
	public boolean matches(Number value) {
		if (this.decValue == null) {
			this.decValue = NumberUtilz.toBigDecimal(this.value);
		}
		if (!match(value, this.matchType, this.decValue)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean match(Number value, QuantityMatchType matchType, BigDecimal decValue) {
		int comp1 = NumberUtilz.compare(value, decValue, true);
		switch (matchType) {
		case Equals:
			return (comp1 == 0);
		case LessThan:
			return (comp1 < 0);
		case LessEquals:
			return (comp1 <= 0);
		case GreaterThan:
			return (comp1 > 0);
		case GreaterEquals:
			return (comp1 >= 0);
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.matchType.name() + " " + ((this.value == null) ? "null" : this.value.toString());
	}
}
