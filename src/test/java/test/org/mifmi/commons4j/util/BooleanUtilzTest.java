/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.util.BooleanUtilz;

public class BooleanUtilzTest {

	@Test
	public void testToBoolean() throws Exception {
		assertEquals(false, BooleanUtilz.toBoolean(null));
		assertEquals(false, BooleanUtilz.toBoolean(""));
		assertEquals(false, BooleanUtilz.toBoolean("¥0"));
		assertEquals(false, BooleanUtilz.toBoolean("false"));
		assertEquals(false, BooleanUtilz.toBoolean("on"));
		assertEquals(false, BooleanUtilz.toBoolean("off"));
		assertEquals(false, BooleanUtilz.toBoolean("yes"));
		assertEquals(false, BooleanUtilz.toBoolean("no"));
		assertEquals(false, BooleanUtilz.toBoolean("abc"));
		assertEquals(false, BooleanUtilz.toBoolean(0));
		assertEquals(false, BooleanUtilz.toBoolean(0.0));
		assertEquals(false, BooleanUtilz.toBoolean(Boolean.FALSE));
		assertEquals(true, BooleanUtilz.toBoolean("true"));
		assertEquals(true, BooleanUtilz.toBoolean("True"));
		assertEquals(true, BooleanUtilz.toBoolean("TRUE"));
		assertEquals(true, BooleanUtilz.toBoolean(1));
		assertEquals(true, BooleanUtilz.toBoolean(0.1));
		assertEquals(true, BooleanUtilz.toBoolean(Boolean.TRUE));
	}

	@Test
	public void testToBooleanObject() throws Exception {
		assertEquals(null, BooleanUtilz.toBooleanObject(null));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject(""));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("¥0"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("false"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("on"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("off"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("yes"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("no"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject("abc"));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject(0));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject(0.0));
		assertEquals(Boolean.FALSE, BooleanUtilz.toBooleanObject(Boolean.FALSE));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject("true"));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject("True"));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject("TRUE"));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject(1));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject(0.1));
		assertEquals(Boolean.TRUE, BooleanUtilz.toBooleanObject(Boolean.TRUE));
	}

	@Test
	public void testNot() throws Exception {
		assertEquals(true, BooleanUtilz.not(false));
		assertEquals(false, BooleanUtilz.not(true));
	}

	@Test
	public void testNotObject() throws Exception {
		assertEquals(true, BooleanUtilz.notObject(0));
		assertEquals(false, BooleanUtilz.notObject(1));
	}

	@Test
	public void testAnd() throws Exception {
		assertEquals(false, BooleanUtilz.and(false));
		assertEquals(true, BooleanUtilz.and(true));
		
		assertEquals(false, BooleanUtilz.and(false, false));
		assertEquals(false, BooleanUtilz.and(true, false));
		assertEquals(false, BooleanUtilz.and(false, true));
		assertEquals(true, BooleanUtilz.and(true, true));

		assertEquals(false, BooleanUtilz.and(false, false, false));
		assertEquals(false, BooleanUtilz.and(true, false, false));
		assertEquals(false, BooleanUtilz.and(false, true, false));
		assertEquals(false, BooleanUtilz.and(false, false, true));
		assertEquals(false, BooleanUtilz.and(true, true, false));
		assertEquals(false, BooleanUtilz.and(false, true, true));
		assertEquals(true, BooleanUtilz.and(true, true, true));
	}

	@Test
	public void testAndObject() throws Exception {
		assertEquals(false, BooleanUtilz.andObject(0));
		assertEquals(true, BooleanUtilz.andObject(1));
		
		assertEquals(false, BooleanUtilz.andObject(0, 0));
		assertEquals(false, BooleanUtilz.andObject(1, 0));
		assertEquals(false, BooleanUtilz.andObject(0, 1));
		assertEquals(true, BooleanUtilz.andObject(1, 1));

		assertEquals(false, BooleanUtilz.andObject(0, 0, 0));
		assertEquals(false, BooleanUtilz.andObject(1, 0, 0));
		assertEquals(false, BooleanUtilz.andObject(0, 1, 0));
		assertEquals(false, BooleanUtilz.andObject(0, 0, 1));
		assertEquals(false, BooleanUtilz.andObject(1, 1, 0));
		assertEquals(false, BooleanUtilz.andObject(0, 1, 1));
		assertEquals(true, BooleanUtilz.andObject(1, 1, 1));
	}

	@Test
	public void testOr() throws Exception {
		assertEquals(false, BooleanUtilz.or(false));
		assertEquals(true, BooleanUtilz.or(true));
		
		assertEquals(false, BooleanUtilz.or(false, false));
		assertEquals(true, BooleanUtilz.or(true, false));
		assertEquals(true, BooleanUtilz.or(false, true));
		assertEquals(true, BooleanUtilz.or(true, true));

		assertEquals(false, BooleanUtilz.or(false, false, false));
		assertEquals(true, BooleanUtilz.or(true, false, false));
		assertEquals(true, BooleanUtilz.or(false, true, false));
		assertEquals(true, BooleanUtilz.or(false, false, true));
		assertEquals(true, BooleanUtilz.or(true, true, false));
		assertEquals(true, BooleanUtilz.or(false, true, true));
		assertEquals(true, BooleanUtilz.or(true, true, true));
	}

	@Test
	public void testOrObject() throws Exception {
		assertEquals(false, BooleanUtilz.orObject(0));
		assertEquals(true, BooleanUtilz.orObject(1));
		
		assertEquals(false, BooleanUtilz.orObject(0, 0));
		assertEquals(true, BooleanUtilz.orObject(1, 0));
		assertEquals(true, BooleanUtilz.orObject(0, 1));
		assertEquals(true, BooleanUtilz.orObject(1, 1));

		assertEquals(false, BooleanUtilz.orObject(0, 0, 0));
		assertEquals(true, BooleanUtilz.orObject(1, 0, 0));
		assertEquals(true, BooleanUtilz.orObject(0, 1, 0));
		assertEquals(true, BooleanUtilz.orObject(0, 0, 1));
		assertEquals(true, BooleanUtilz.orObject(1, 1, 0));
		assertEquals(true, BooleanUtilz.orObject(0, 1, 1));
		assertEquals(true, BooleanUtilz.orObject(1, 1, 1));
	}

	@Test
	public void testXor() throws Exception {
		assertEquals(false, BooleanUtilz.xor(false));
		assertEquals(false, BooleanUtilz.xor(true));
		
		assertEquals(false, BooleanUtilz.xor(false, false));
		assertEquals(true, BooleanUtilz.xor(true, false));
		assertEquals(true, BooleanUtilz.xor(false, true));
		assertEquals(false, BooleanUtilz.xor(true, true));

		assertEquals(false, BooleanUtilz.xor(false, false, false));
		assertEquals(true, BooleanUtilz.xor(true, false, false));
		assertEquals(true, BooleanUtilz.xor(false, true, false));
		assertEquals(true, BooleanUtilz.xor(false, false, true));
		assertEquals(true, BooleanUtilz.xor(true, true, false));
		assertEquals(true, BooleanUtilz.xor(false, true, true));
		assertEquals(false, BooleanUtilz.xor(true, true, true));
	}

	@Test
	public void testXorObject() throws Exception {
		assertEquals(false, BooleanUtilz.xorObject(0));
		assertEquals(false, BooleanUtilz.xorObject(1));
		
		assertEquals(false, BooleanUtilz.xorObject(0, 0));
		assertEquals(true, BooleanUtilz.xorObject(1, 0));
		assertEquals(true, BooleanUtilz.xorObject(0, 1));
		assertEquals(false, BooleanUtilz.xorObject(1, 1));

		assertEquals(false, BooleanUtilz.xorObject(0, 0, 0));
		assertEquals(true, BooleanUtilz.xorObject(1, 0, 0));
		assertEquals(true, BooleanUtilz.xorObject(0, 1, 0));
		assertEquals(true, BooleanUtilz.xorObject(0, 0, 1));
		assertEquals(true, BooleanUtilz.xorObject(1, 1, 0));
		assertEquals(true, BooleanUtilz.xorObject(0, 1, 1));
		assertEquals(false, BooleanUtilz.xorObject(1, 1, 1));
	}

}
