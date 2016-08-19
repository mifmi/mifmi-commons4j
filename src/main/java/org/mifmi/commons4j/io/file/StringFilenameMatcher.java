/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.io.file;

import java.io.File;
import java.io.FilenameFilter;

import org.mifmi.commons4j.matcher.StringMatcher;

public class StringFilenameMatcher implements FilenameFilter {

	private StringMatcher[] filenameMatchers;
	
	public StringFilenameMatcher(StringMatcher... filenameMatchers) {
		this.filenameMatchers = filenameMatchers;
	}

	@Override
	public boolean accept(File dir, String name) {
		if (this.filenameMatchers == null) {
			return false;
		}
		
		for (StringMatcher filenameMatcher : this.filenameMatchers) {
			if (filenameMatcher.matches(name)) {
				return true;
			}
		}
		
		return false;
	}

}
