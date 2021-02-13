/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

/**
 * Helper methods for Exception.
 * 
 * @author mozq
 */
public final class ExceptionUtilz {

	/**
	 * This class has only static methods.
	 */
	private ExceptionUtilz() {
		// NOP
	}

	/**
	 * Returns whether the cause of Throwable is the specified class.
	 * The cause is checked retroactively.
	 * 
	 * @param t Throwable instance
	 * @param causeClass cause class
	 * @return true if Throwable or cause of Throwable is specified class, false otherwise
	 */
	public static boolean isCause(Throwable t, Class<? extends Throwable> causeClass) {
		if (t == null) {
			return false;
		}
		
		if (causeClass.isInstance(t)) {
			return true;
		} else {
			return isCause(t.getCause(), causeClass);
		}
	}
}
