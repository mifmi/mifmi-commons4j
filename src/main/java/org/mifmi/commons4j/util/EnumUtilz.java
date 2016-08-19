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
 * Helper methods for working with Enum instances.
 * 
 * @author mozq
 */
public final class EnumUtilz {

	private EnumUtilz() {
		// NOP
	}

	public static <E extends Enum<E>> E valueOf(Class<E> enumType, String name) {
		return valueOf(enumType, name, null);
	}
	
	public static <E extends Enum<E>> E valueOf(Class<E> enumType, String name, E defaultValue) {
		if (name == null || name.isEmpty()) {
			return defaultValue;
		}
		
		E enumValue;
		try {
			enumValue = Enum.valueOf(enumType, name);
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
		
		return enumValue;
	}
	
	public static <E extends Enum<E>> boolean hasValue(Class<E> enumType, String name) {
		return valueOf(enumType, name, null) != null;
	}
}
