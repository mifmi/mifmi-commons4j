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
import static org.junit.Assert.assertTrue;

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
		assertEquals(-1, StringUtilz.startsWith(null, "a", "b", "c"));
		assertEquals(-1, StringUtilz.startsWith("", "a", "b", "c"));

		assertEquals(-1, StringUtilz.startsWith("a", (String[])null));
		assertEquals(0, StringUtilz.startsWith("a", new String[]{null}));
		assertEquals(0, StringUtilz.startsWith("a", new String[]{""}));
		
		assertEquals(0, StringUtilz.startsWith("a", "a", "b", "c"));
		assertEquals(1, StringUtilz.startsWith("b", "a", "b", "c"));
		assertEquals(2, StringUtilz.startsWith("c", "a", "b", "c"));
		assertEquals(-1, StringUtilz.startsWith("d", "a", "b", "c"));

		assertEquals(1, StringUtilz.startsWith("abc", "x", "a", "ab", "abc"));
		assertEquals(1, StringUtilz.startsWith("abc", "x", "ab", "abc", "a"));
		assertEquals(1, StringUtilz.startsWith("abc", "x", "abc", "a", "ab"));

		assertEquals(2, StringUtilz.startsWith("abc", new String[] {"x", "c", "bc", "abc"}, 1));
		assertEquals(1, StringUtilz.startsWith("abc", new String[] {"x", "bc", "abc", "c"}, 1));
		assertEquals(3, StringUtilz.startsWith("abc", new String[] {"x", "abc", "c", "bc"}, 1));
	}

	@Test
	public void testEndsWithStringArray() throws Exception {
		assertEquals(-1, StringUtilz.endsWith(null, "a", "b", "c"));
		assertEquals(-1, StringUtilz.endsWith("", "a", "b", "c"));

		assertEquals(-1, StringUtilz.endsWith("a", (String[])null));
		assertEquals(0, StringUtilz.endsWith("a", new String[]{null}));
		assertEquals(0, StringUtilz.endsWith("a", new String[]{""}));
		
		assertEquals(0, StringUtilz.endsWith("a", "a", "b", "c"));
		assertEquals(1, StringUtilz.endsWith("b", "a", "b", "c"));
		assertEquals(2, StringUtilz.endsWith("c", "a", "b", "c"));
		assertEquals(-1, StringUtilz.endsWith("d", "a", "b", "c"));

		assertEquals(1, StringUtilz.endsWith("abc", "x", "c", "bc", "abc"));
		assertEquals(1, StringUtilz.endsWith("abc", "x", "bc", "abc", "c"));
		assertEquals(1, StringUtilz.endsWith("abc", "x", "abc", "c", "bc"));

		assertEquals(2, StringUtilz.endsWith("abc", new String[] {"x", "a", "ab", "abc"}, 1));
		assertEquals(1, StringUtilz.endsWith("abc", new String[] {"x", "ab", "abc", "a"}, 1));
		assertEquals(3, StringUtilz.endsWith("abc", new String[] {"x", "abc", "a", "ab"}, 1));
	}

	@Test
	public void testReplaceAllCharArray() throws Exception {
		assertEquals(null, StringUtilz.replaceAll((String)null, new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z'}));
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", null, new char[] {'A', 'M', 'Z'}));
		assertEquals("bcdefghijklnopqrstuvwxy", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, null));
		
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {}, new char[] {}));
		
		assertEquals("AbcdefghijklMnopqrstuvwxyZ", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z'}));

		assertEquals("AbcdefghijklMnopqrstuvwxyZ", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z', 'X'}));

		assertEquals("AbcdefghijklMnopqrstuvwxyM", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, new char[] {'A', 'M'}));
		assertEquals("AbcdefghijklAnopqrstuvwxyA", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, new char[] {'A'}));
		assertEquals("bcdefghijklnopqrstuvwxy", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new char[] {'a', 'm', 'z'}, new char[] {}));
	}

	@Test
	public void testReplaceAllStringBuilderCharArray() throws Exception {
		assertEquals(null, StringUtilz.replaceAll((StringBuilder)null, new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z'}));
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), null, new char[] {'A', 'M', 'Z'}).toString());
		assertEquals("bcdefghijklnopqrstuvwxy", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, null).toString());
		
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {}, new char[] {}).toString());
		
		assertEquals("AbcdefghijklMnopqrstuvwxyZ", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z'}).toString());

		assertEquals("AbcdefghijklMnopqrstuvwxyZ", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, new char[] {'A', 'M', 'Z', 'X'}).toString());

		assertEquals("AbcdefghijklMnopqrstuvwxyM", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, new char[] {'A', 'M'}).toString());
		assertEquals("AbcdefghijklAnopqrstuvwxyA", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, new char[] {'A'}).toString());
		assertEquals("bcdefghijklnopqrstuvwxy", StringUtilz.replaceAll(new StringBuilder("abcdefghijklmnopqrstuvwxyz"), new char[] {'a', 'm', 'z'}, new char[] {}).toString());
	}

	@Test
	public void testReplaceAllStringArray() throws Exception {
		assertEquals(null, StringUtilz.replaceAll(null, new String[] {"abc", "lmn", "xyz"}, new String[] {"A-B-C", "L-M-N", "X-Y-Z"}));
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", null, new String[] {"A-B-C", "L-M-N", "X-Y-Z"}));
		assertEquals("defghijkopqrstuvw", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, (String[])null));
		
		assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {}, new String[] {}));

		try {
			assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {null}, new String[] {"x"}));
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// NOP
		}
		
		try {
			assertEquals("abcdefghijklmnopqrstuvwxyz", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {""}, new String[] {"x"}));
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// NOP
		}
		
		assertEquals("A-B-CdefghijkL-M-NopqrstuvwX-Y-Z", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"A-B-C", "L-M-N", "X-Y-Z"}));
		assertEquals("AdefghijkMopqrstuvwZ", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"A", "M", "Z"}));
		assertEquals("defghijkopqrstuvw", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"", "", ""}));
		assertEquals("defghijkopqrstuvw", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {null, null, null}));
		
		
		assertEquals("A-B-CdefghijkL-M-NopqrstuvwX-Y-Z", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"A-B-C", "L-M-N", "X-Y-Z", "XXX"}));
		
		assertEquals("A-B-CdefghijkL-M-NopqrstuvwL-M-N", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"A-B-C", "L-M-N"}));
		assertEquals("A-B-CdefghijkA-B-CopqrstuvwA-B-C", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {"A-B-C"}));
		assertEquals("defghijkopqrstuvw", StringUtilz.replaceAll("abcdefghijklmnopqrstuvwxyz", new String[] {"abc", "lmn", "xyz"}, new String[] {}));
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
