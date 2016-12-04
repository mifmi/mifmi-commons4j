/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class ComparableMatcher<T extends Comparable<T>> extends AbstractMatcher<T> {
	
	private T pattern;
	private QuantityMatchType matchType;

	public ComparableMatcher(T pattern) {
		this(pattern, QuantityMatchType.Equals);
	}
	
	public ComparableMatcher(T pattern, QuantityMatchType matchType) {
		this.pattern = pattern;
		this.matchType = matchType;
	}

	@Override
	public Class<? super Comparable<T>> getType() {
		return Comparable.class;
	}
	
	@Override
	public boolean matches(T value) {
		if (this.pattern == null) {
			return (value == null);
		}
		if (value == null) {
			return false;
		}
		
		int comp = this.pattern.compareTo(value);
		switch (matchType) {
		case Equals:
			return (comp == 0);
		case LessThan:
			return (comp < 0);
		case LessEquals:
			return (comp <= 0);
		case GreaterThan:
			return (comp > 0);
		case GreaterEquals:
			return (comp >= 0);
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.matchType.name() + " " + this.pattern;
	}
}
