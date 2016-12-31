/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.util.StringUtilz;

public class StringUtilzTest {

	@Test
	public void testStartsWithChar() throws Exception {
		assertEquals(false, StringUtilz.startsWith(null, 'a'));
		assertEquals(false, StringUtilz.startsWith("", 'a'));
		
		assertEquals(true, StringUtilz.startsWith("a", 'a'));
		assertEquals(false, StringUtilz.startsWith("a", 'b'));
		
		assertEquals(true, StringUtilz.startsWith("abc", 'a'));
		assertEquals(false, StringUtilz.startsWith("abc", 'b'));
	}

	@Test
	public void testEndsWithChar() throws Exception {
		assertEquals(false, StringUtilz.endsWith(null, 'a'));
		assertEquals(false, StringUtilz.endsWith("", 'a'));
		
		assertEquals(true, StringUtilz.endsWith("a", 'a'));
		assertEquals(false, StringUtilz.endsWith("a", 'b'));
		
		assertEquals(true, StringUtilz.endsWith("abc", 'c'));
		assertEquals(false, StringUtilz.endsWith("abc", 'b'));
	}

	@Test
	public void testStartsWithString() throws Exception {
		assertEquals(false, StringUtilz.startsWith(null, "a"));
		assertEquals(false, StringUtilz.startsWith("", "a"));

		assertEquals(true, StringUtilz.startsWith("a", (String)null));
		assertEquals(true, StringUtilz.startsWith("a", ""));
		
		assertEquals(true, StringUtilz.startsWith("a", "a"));
		assertEquals(false, StringUtilz.startsWith("a", "b"));
		assertEquals(false, StringUtilz.startsWith("a", "ab"));
		assertEquals(false, StringUtilz.startsWith("a", "bc"));
		
		assertEquals(true, StringUtilz.startsWith("abc", "a"));
		assertEquals(false, StringUtilz.startsWith("abc", "b"));
		assertEquals(false, StringUtilz.startsWith("abc", "c"));
		assertEquals(true, StringUtilz.startsWith("abc", "ab"));
		assertEquals(false, StringUtilz.startsWith("abc", "bc"));
	}

	@Test
	public void testEndsWithString() throws Exception {
		assertEquals(false, StringUtilz.endsWith(null, "a"));
		assertEquals(false, StringUtilz.endsWith("", "a"));

		assertEquals(true, StringUtilz.endsWith("a", (String)null));
		assertEquals(true, StringUtilz.endsWith("a", ""));
		
		assertEquals(true, StringUtilz.endsWith("a", "a"));
		assertEquals(false, StringUtilz.endsWith("a", "b"));
		assertEquals(false, StringUtilz.endsWith("a", "ab"));
		assertEquals(false, StringUtilz.endsWith("a", "bc"));
		
		assertEquals(false, StringUtilz.endsWith("abc", "a"));
		assertEquals(false, StringUtilz.endsWith("abc", "b"));
		assertEquals(true, StringUtilz.endsWith("abc", "c"));
		assertEquals(false, StringUtilz.endsWith("abc", "ab"));
		assertEquals(true, StringUtilz.endsWith("abc", "bc"));
	}

	@Test
	public void testStartsWithStringArray() throws Exception {
		assertEquals(null, StringUtilz.startsWith(null, "a", "b", "c"));
		assertEquals(null, StringUtilz.startsWith("", "a", "b", "c"));

		assertEquals(null, StringUtilz.startsWith("a", (String[])null));
		assertEquals(null, StringUtilz.startsWith("a", new String[]{null}));
		assertEquals("", StringUtilz.startsWith("a", new String[]{""}));
		
		assertEquals("a", StringUtilz.startsWith("a", "a", "b", "c"));
		assertEquals("b", StringUtilz.startsWith("b", "a", "b", "c"));
		assertEquals("c", StringUtilz.startsWith("c", "a", "b", "c"));
		assertEquals(null, StringUtilz.startsWith("d", "a", "b", "c"));

		assertEquals("a", StringUtilz.startsWith("abc", "x", "a", "ab", "abc"));
		assertEquals("ab", StringUtilz.startsWith("abc", "x", "ab", "abc", "a"));
		assertEquals("abc", StringUtilz.startsWith("abc", "x", "abc", "a", "ab"));
	}

	@Test
	public void testEndsWithStringArray() throws Exception {
		assertEquals(null, StringUtilz.endsWith(null, "a", "b", "c"));
		assertEquals(null, StringUtilz.endsWith("", "a", "b", "c"));

		assertEquals(null, StringUtilz.endsWith("a", (String[])null));
		assertEquals(null, StringUtilz.endsWith("a", new String[]{null}));
		assertEquals("", StringUtilz.endsWith("a", new String[]{""}));
		
		assertEquals("a", StringUtilz.endsWith("a", "a", "b", "c"));
		assertEquals("b", StringUtilz.endsWith("b", "a", "b", "c"));
		assertEquals("c", StringUtilz.endsWith("c", "a", "b", "c"));
		assertEquals(null, StringUtilz.endsWith("d", "a", "b", "c"));

		assertEquals("c", StringUtilz.endsWith("abc", "x", "c", "bc", "abc"));
		assertEquals("bc", StringUtilz.endsWith("abc", "x", "bc", "abc", "c"));
		assertEquals("abc", StringUtilz.endsWith("abc", "x", "abc", "c", "bc"));
	}

	@Test
	public void testSurroundsWithChar() throws Exception {
		assertEquals(false, StringUtilz.surroundsWith(null, 'a', 'c'));
		assertEquals(false, StringUtilz.surroundsWith("", 'a', 'c'));

		assertEquals(true, StringUtilz.surroundsWith("abc", 'a', -1));
		assertEquals(true, StringUtilz.surroundsWith("abc", -1, 'c'));
		assertEquals(true, StringUtilz.surroundsWith("abc", -1, -1));

		assertEquals(true, StringUtilz.surroundsWith("abc", 'a', 'c'));
		assertEquals(true, StringUtilz.surroundsWith("abc", 'a', 'c'));
		
		assertEquals(false, StringUtilz.surroundsWith("abc", 'b', 'c'));
		assertEquals(false, StringUtilz.surroundsWith("abc", 'a', 'b'));
	}

	@Test
	public void testSurroundsWithString() throws Exception {
		assertEquals(false, StringUtilz.surroundsWith(null, "a", "c"));
		assertEquals(false, StringUtilz.surroundsWith("", "a", "c"));

		assertEquals(true, StringUtilz.surroundsWith("abc", "a", null));
		assertEquals(true, StringUtilz.surroundsWith("abc", null, "c"));
		assertEquals(true, StringUtilz.surroundsWith("abc", null, null));

		assertEquals(true, StringUtilz.surroundsWith("abc", "a", "c"));
		assertEquals(true, StringUtilz.surroundsWith("abc", "a", "c"));

		assertEquals(true, StringUtilz.surroundsWith("abc", "ab", "c"));
		assertEquals(true, StringUtilz.surroundsWith("abc", "a", "bc"));

		assertEquals(false, StringUtilz.surroundsWith("abc", "abc", "c"));
		assertEquals(false, StringUtilz.surroundsWith("abc", "a", "abc"));
		
		assertEquals(false, StringUtilz.surroundsWith("abc", "b", "c"));
		assertEquals(false, StringUtilz.surroundsWith("abc", "a", "b"));
	}

}
