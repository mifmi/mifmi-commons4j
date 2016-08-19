/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

import java.util.regex.Pattern;

public class RegexMatcher extends AbstractMatcher<String> {
	
	private Pattern pattern;

	public RegexMatcher(Pattern pattern) {
		this.pattern = pattern;
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

		return this.pattern.matcher(value).matches();
	}

	@Override
	public String toString() {
		return (this.pattern == null) ? "null" : this.pattern.toString();
	}
}
