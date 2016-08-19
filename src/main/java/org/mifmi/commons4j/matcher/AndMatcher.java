/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.matcher;

public class AndMatcher<T> extends AbstractMatcher<T> {
	private IMatcher<T> firstMatcher;
	private IMatcher<T> secondMatcher;
	private IMatcher<T>[] matchers;
	
	public AndMatcher(IMatcher<T> firstMatcher, IMatcher<T> secondMatcher) {
		this(firstMatcher, secondMatcher, (IMatcher<T>[])null);
	}
	
	@SafeVarargs
	public AndMatcher(IMatcher<T> firstMatcher, IMatcher<T> secondMatcher, IMatcher<T>... matchers) {
		this.firstMatcher = firstMatcher;
		this.secondMatcher = secondMatcher;
		this.matchers = matchers;
	}

	@Override
	public Class<? super T> getType() {
		return this.firstMatcher.getType();
	}

	@Override
	public boolean matches(T value) {
		if (!this.firstMatcher.matches(value)) {
			return false;
		}
		
		if (!this.secondMatcher.matches(value)) {
			return false;
		}
		
		if (this.matchers != null) {
			for (IMatcher<T> matcher : this.matchers) {
				if (!matcher.matches(value)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
