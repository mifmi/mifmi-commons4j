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
 * Helper methods for working with boolean primitives and Boolean instances.
 * 
 * @author mozq
 */
public final class BooleanUtilz {

	/**
	 * This class has only static methods.
	 */
	private BooleanUtilz() {
		// NOP
	}

	/**
	 * Converts an object to a primitive boolean.
	 * "", zero and null are convert to false.
	 * 
	 * @param o the object to convert
	 * @return true or false
	 */
	public static boolean toBoolean(Object o) {
		Boolean b = toBooleanObject(o);
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}

	/**
	 * Converts an object to a Boolean object.
	 * "" and zero are convert to false.
	 * Returns the result for a non-null argument and null for a null argument.
	 * 
	 * @param o the object to convert
	 * @return true, false or null
	 */
	public static Boolean toBooleanObject(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Boolean) {
			return (Boolean)o;
		}
		if (o instanceof Number) {
			return (NumberUtilz.isZero((Number)o)) ? Boolean.FALSE : Boolean.TRUE;
		}
		if (o instanceof CharSequence) {
			return Boolean.valueOf(o.toString());
		}
		return Boolean.TRUE;
	}

	/**
	 * Returns (!val).
	 * 
	 * @param val a boolean value
	 * @return true if the argument is false and false otherwise
	 */
	public static boolean not(boolean val) {
		return !val;
	}

	/**
	 * Returns (!val) for a non-null argument and null for a null argument.
	 * 
	 * @param val a boolean value
	 * @return true if the argument is false and false otherwise, or null
	 */
	public static boolean notObject(Object val) {
		return !toBoolean(val);
	}

	/**
	 * Returns (val1 &amp;&amp; val2).
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return true if the arguments are true and false otherwise
	 */
	public static boolean and(boolean val1, boolean val2) {
		return val1 && val2;
	}

	/**
	 * Returns (vals[0] &amp;&amp; vals[1] &amp;&amp; ...).
	 * 
	 * @param vals boolean values
	 * @return true if the arguments are true and false otherwise
	 */
	public static boolean and(boolean... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		for (boolean val : vals) {
			if (!val) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns (val1 &amp;&amp; val2).
	 * "", zero and null are regarded as false.
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return true if the arguments are true and false otherwise
	 */
	public static boolean andObject(Object val1, Object val2) {
		return toBoolean(val1) && toBoolean(val2);
	}

	/**
	 * Returns (vals[0] &amp;&amp; vals[1] &amp;&amp; ...).
	 * "", zero and null are regarded as false.
	 * 
	 * @param vals boolean values
	 * @return true if the arguments are true and false otherwise
	 */
	public static boolean andObject(Object... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		for (Object val : vals) {
			if (!toBoolean(val)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns (val1 || val2).
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return false if the arguments are false and true otherwise
	 */
	public static boolean or(boolean val1, boolean val2) {
		return val1 || val2;
	}

	/**
	 * Returns (vals[0] || vals[1] || ...).
	 * 
	 * @param vals boolean values
	 * @return false if the arguments are false and true otherwise
	 */
	public static boolean or(boolean... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		for (boolean val : vals) {
			if (val) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns (val1 || val2).
	 * "", zero and null are regarded as false.
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return false if the arguments are false and true otherwise
	 */
	public static boolean orObject(Object val1, Object val2) {
		return toBoolean(val1) || toBoolean(val2);
	}

	/**
	 * Returns (vals[0] || vals[1] || ...).
	 * "", zero and null are regarded as false.
	 * 
	 * @param vals boolean values
	 * @return false if the arguments are false and true otherwise
	 */
	public static boolean orObject(Object... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		for (Object val : vals) {
			if (toBoolean(val)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns (val1 != val2).
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return true if the arguments are different and false otherwise
	 */
	public static boolean xor(boolean val1, boolean val2) {
		return (val1 != val2);
	}

	/**
	 * Returns (vals[0] != vals[1] != vals[2] != ...).
	 * 
	 * @param vals boolean values
	 * @return true if the arguments are different and false otherwise
	 */
	public static boolean xor(boolean... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		boolean b = vals[0];
		for (int i = 1; i < vals.length; i++) {
			if (vals[i] != b) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns (val1 != val2).
	 * "", zero and null are regarded as false.
	 * 
	 * @param val1 first boolean value
	 * @param val2 second boolean value
	 * @return true if the arguments are different and false otherwise
	 */
	public static boolean xorObject(Object val1, Object val2) {
		return (toBoolean(val1) != toBoolean(val2));
	}

	/**
	 * Returns (vals[0] != vals[1] != vals[2] != ...).
	 * "", zero and null are regarded as false.
	 * 
	 * @param vals boolean values
	 * @return true if the arguments are different and false otherwise
	 */
	public static boolean xorObject(Object... vals) {
		if (vals == null || vals.length == 0) {
			return false;
		}
		
		boolean b = toBoolean(vals[0]);
		for (int i = 1; i < vals.length; i++) {
			if (toBoolean(vals[i]) != b) {
				return true;
			}
		}
		
		return false;
	}
}
