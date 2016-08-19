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
 * Helper methods for working with Object instances.
 * 
 * @author mozq
 */
public final class ObjectUtilz {

	/**
	 * This class has only static methods.
	 */
	private ObjectUtilz() {
		// NOP
	}

	/**
	 * Null safe comparison of Comparables.
	 * A null value is considered less than a non-null value.
	 * 
	 * @param o1 the first object to be compared, may be null.
	 * @param o2 the second object to be compared, may be null.
	 * @return a negative value if o1 < o2, zero if o1 = o2 and a positive value if o1 > o2.
	 */
	public static <T extends Comparable<? super T>> int compare(T o1, T o2) {
		return compare(o1, o2, false);
	}

	/**
	 * Null safe comparison of Comparables.
	 * 
	 * @param o1 the first object to be compared, may be null
	 * @param o2 the second object to be compared, may be null
	 * @param nullGreater if true then a null value is considered greater than a non-null value, otherwise a null value is considered less than a non-null value
	 * @return a negative value if o1 < o2, zero if o1 = o2 and a positive value if o1 > o2
	 */
	public static <T extends Comparable<? super T>> int compare(T o1, T o2, boolean nullGreater) {
		if (o1 == null) {
			if (o2 == null) {
				return 0;
			} else {
				return (nullGreater) ? 1 : -1;
			}
		} else if (o2 == null) {
			return (nullGreater) ? -1 : 1;
		}
		
		return o1.compareTo(o2);
	}
}
