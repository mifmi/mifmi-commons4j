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
import java.util.regex.Pattern;

public class RegexFilenameFilter implements FilenameFilter {

	private Pattern[] filenamePatterns;
	
	public RegexFilenameFilter(Pattern... filenamePatterns) {
		this.filenamePatterns = filenamePatterns;
	}

	@Override
	public boolean accept(File dir, String name) {
		if (this.filenamePatterns == null) {
			return false;
		}
		
		for (Pattern filenamePattern : this.filenamePatterns) {
			if (filenamePattern.matcher(name).matches()) {
				return true;
			}
		}
		
		return false;
	}

}
