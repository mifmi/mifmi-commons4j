/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

import java.time.temporal.TemporalAccessor;

import org.mifmi.commons4j.util.DateUtilz;

public class ChronoMatcher extends AbstractMatcher<TemporalAccessor> {

	private TemporalAccessor value;
	private QuantityMatchType matchType;
	private boolean bothSupportOnly;
	private boolean nullGreater;
	private boolean unsupportedGreater;
	

	public ChronoMatcher(TemporalAccessor value) {
		this(value, QuantityMatchType.Equals);
	}
	
	public ChronoMatcher(TemporalAccessor value, QuantityMatchType matchType) {
		this(value, matchType, false);
	}
	
	public ChronoMatcher(TemporalAccessor value, QuantityMatchType matchType, boolean bothSupportOnly) {
		this(value, matchType, bothSupportOnly, false, false);
	}
	
	public ChronoMatcher(TemporalAccessor value, QuantityMatchType matchType, boolean bothSupportOnly, boolean nullGreater, boolean unsupportedGreater) {
		this.value = value;
		this.matchType = matchType;
		this.bothSupportOnly = bothSupportOnly;
		this.nullGreater = nullGreater;
		this.unsupportedGreater = unsupportedGreater;
	}

	public static IMatcher<TemporalAccessor> between(TemporalAccessor from, TemporalAccessor to) {
		return between(from, to, false);
	}
	public static IMatcher<TemporalAccessor> between(TemporalAccessor from, TemporalAccessor to, boolean bothSupportOnly) {
		return between(from, to, bothSupportOnly, false, false);
	}
	public static IMatcher<TemporalAccessor> between(TemporalAccessor from, TemporalAccessor to, boolean bothSupportOnly, boolean nullGreater, boolean unsupportedGreater) {
		IMatcher<TemporalAccessor> matcher = null;
		
		if (from != null) {
			matcher = new ChronoMatcher(from, QuantityMatchType.GreaterEquals, bothSupportOnly, nullGreater, unsupportedGreater);
		}
		
		if (to != null) {
			IMatcher<TemporalAccessor> toMatcher = new ChronoMatcher(to, QuantityMatchType.LessEquals, bothSupportOnly, nullGreater, unsupportedGreater);
			if (matcher == null) {
				matcher = toMatcher;
			} else {
				matcher = matcher.and(toMatcher);
			}
		}
		
		return matcher;
	}

	@Override
	public Class<? super TemporalAccessor> getType() {
		return TemporalAccessor.class;
	}

	@Override
	public boolean matches(TemporalAccessor value) {
		if (!matches(value, this.matchType, this.value, this.bothSupportOnly, this.nullGreater, this.unsupportedGreater)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean matches(TemporalAccessor value, QuantityMatchType matchType, TemporalAccessor date, boolean bothSupportOnly, boolean nullGreater, boolean unsupportedGreater) {
		int comp1 = DateUtilz.compareChrono(value, date, bothSupportOnly, nullGreater, unsupportedGreater);
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
