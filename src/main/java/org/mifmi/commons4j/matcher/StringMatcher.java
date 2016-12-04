/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

import java.util.Locale;

public class StringMatcher extends AbstractMatcher<String> {

	private String pattern;
	private PartialMatchType matchType;
	private boolean ignoreCase;
	private String patternUpperCase;
	private Locale locale;

	public StringMatcher(String pattern) {
		this(pattern, PartialMatchType.Equals);
	}

	public StringMatcher(String pattern, PartialMatchType matchType) {
		this(pattern, matchType, false, null);
	}

	public StringMatcher(String pattern, PartialMatchType matchType, boolean ignoreCase) {
		this(pattern, matchType, ignoreCase, Locale.ENGLISH);
	}
	
	public StringMatcher(String pattern, PartialMatchType matchType, boolean ignoreCase, Locale locale) {
		this.pattern = pattern;
		this.matchType = matchType;
		this.ignoreCase = ignoreCase;
		if (ignoreCase) {
			if (this.pattern == null) {
				this.patternUpperCase = null;
			} else {
				this.patternUpperCase = this.pattern.toUpperCase(locale);
			}
		}
		this.locale = locale;
	}

	@Override
	public Class<? super String> getType() {
		return String.class;
	}
	
	@Override
	public boolean matches(String value) {
		if (this.pattern == null) {
			return (value == null);
		}
		if (value == null) {
			return false;
		}
		
		if (this.ignoreCase) {
			String valueUpperCase = value.toUpperCase(this.locale);
			switch (this.matchType) {
			case Equals:
				return valueUpperCase.equals(this.patternUpperCase);
			case StartsWith:
				return valueUpperCase.startsWith(this.patternUpperCase);
			case EndsWith:
				return valueUpperCase.endsWith(this.patternUpperCase);
			case Contains:
				return valueUpperCase.contains(this.patternUpperCase);
			default:
				return false;
			}
		} else {
			switch (this.matchType) {
			case Equals:
				return value.equals(this.pattern);
			case StartsWith:
				return value.startsWith(this.pattern);
			case EndsWith:
				return value.endsWith(this.pattern);
			case Contains:
				return value.contains(this.pattern);
			default:
				return false;
			}
		}
	}
	
	public static IMatcher<String> compileWildcard(String pattern, boolean ignoreCase, Locale locale) {
		if (pattern.length() == 0) {
			return new StringMatcher(pattern, PartialMatchType.Equals, ignoreCase, locale);
		}
		
		if (pattern.equals("*")) {
			return new AnythingMatcher<>();
		}
		
		boolean startsWild = (pattern.startsWith("*"));
		boolean endsWild = (pattern.endsWith("*"));
		
		if (startsWild && endsWild) {
			return new StringMatcher(pattern.substring(1, pattern.length() - 1), PartialMatchType.Contains, ignoreCase, locale);
		} else if(startsWild) {
			return new StringMatcher(pattern.substring(1), PartialMatchType.EndsWith, ignoreCase, locale);
		} else if(endsWild) {
			return new StringMatcher(pattern.substring(0, pattern.length() - 1), PartialMatchType.StartsWith, ignoreCase, locale);
		} else {
			return new StringMatcher(pattern, PartialMatchType.Equals, ignoreCase, locale);
		}
	}

	@Override
	public String toString() {
		return this.matchType.name() + " " + this.pattern
				+ ", ignoreCase=" + this.ignoreCase + "[" + this.locale.toString() + "]";
	}
}
