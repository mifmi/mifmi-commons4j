/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

import java.util.Date;

import org.mifmi.commons4j.util.ObjectUtilz;

public class DateMatcher extends AbstractMatcher<Date> {

	private Date value;
	private QuantityMatchType matchType;
	
	public DateMatcher(Date value, QuantityMatchType matchType) {
		this.value = value;
		this.matchType = matchType;
	}
	
	public static IMatcher<Date> between(Date from, Date to) {
		IMatcher<Date> matcher = null;
		
		if (from != null) {
			matcher = new DateMatcher(from, QuantityMatchType.GreaterEquals);
		}
		
		if (to != null) {
			IMatcher<Date> toMatcher = new DateMatcher(to, QuantityMatchType.LessEquals);
			if (matcher == null) {
				matcher = toMatcher;
			} else {
				matcher = matcher.and(toMatcher);
			}
		}
		
		return matcher;
	}

	@Override
	public Class<? super Date> getType() {
		return Date.class;
	}

	@Override
	public boolean matches(Date value) {
		if (!matches(value, this.matchType, this.value)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean matches(Date value, QuantityMatchType matchType, Date date) {
		int comp1 = ObjectUtilz.compare(value, date);
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
