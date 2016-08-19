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

public class ExtensionFilenameMatcher implements FilenameFilter {

	boolean ignoreCase;
	private String[] extensions;
	private String[] lowercaseExtensions;
	
	public ExtensionFilenameMatcher(boolean ignoreCase, String... extensions) {
		this.ignoreCase = ignoreCase;
		this.extensions = extensions;
		if (!ignoreCase || extensions == null) {
			this.lowercaseExtensions = null;
		} else {
			int len = extensions.length;
			String[] lowercaseExtensions = new String[len];
			for (int i = 0; i < len; i++) {
				lowercaseExtensions[i] = extensions[i].toLowerCase();
			}
			
			this.lowercaseExtensions = lowercaseExtensions;
		}
	}

	@Override
	public boolean accept(File dir, String name) {
		if (this.extensions == null) {
			return false;
		}
		
		if (this.ignoreCase) {
			String lowercaseExt = FileUtilz.getExt(name).toLowerCase();
			for (String lowercaseExtension : this.lowercaseExtensions) {
				if (lowercaseExt.equals(lowercaseExtension)) {
					return true;
				}
			}
		} else {
			String ext = FileUtilz.getExt(name);
			for (String extension : this.extensions) {
				if (ext.equals(extension)) {
					return true;
				}
			}
		}
		
		return false;
	}

}
