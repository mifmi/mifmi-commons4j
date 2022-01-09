/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.mifmi.commons4j.util.StringUtilz;

public class StringUtilzTest {

	@Test
	public void testCountChar() throws Exception {
		assertEquals(0, StringUtilz.count(null, 'a'));
		assertEquals(0, StringUtilz.count("", 'a'));
		
		assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", (char[])null));

		assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", 'x'));
		
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", 'a'));
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", 'b'));
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", 'c'));
		
		assertEquals(18, StringUtilz.count("aaabbbcccaaabbbccc", 'a', 'b', 'c'));
	}

	@Test
	public void testCountString() throws Exception {
		assertEquals(0, StringUtilz.count(null, "a"));
		assertEquals(0, StringUtilz.count("", "a"));
		
		try {
			assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", (String)null));
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// NOP
		}

		try {
			assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", ""));
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// NOP
		}
		
		assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", (String[])null));

		assertEquals(0, StringUtilz.count("aaabbbcccaaabbbccc", "x"));
		
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", "a"));
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", "b"));
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", "c"));

		assertEquals(18, StringUtilz.count("aaabbbcccaaabbbccc", "a", "b", "c"));
		
		assertEquals(2, StringUtilz.count("aaabbbcccaaabbbccc", "aa"));
		assertEquals(2, StringUtilz.count("aaabbbcccaaabbbccc", "bb"));
		assertEquals(2, StringUtilz.count("aaabbbcccaaabbbccc", "cc"));

		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", "aa", "bb", "cc"));
		
		assertEquals(4, StringUtilz.count("aaabbbcccaaabbbccc", "aaa", "ab", "bbb"));
		assertEquals(6, StringUtilz.count("aaabbbcccaaabbbccc", "aaa", "ab", "bbb", "bc", "ccc"));
	}

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
	public void testSplit_Char() throws Exception {
		assertArrayEquals(null, StringUtilz.split(null, ','));
		assertArrayEquals(new String[] {""}, StringUtilz.split("", ','));
		
		assertArrayEquals(new String[] {"", ""}, StringUtilz.split(",", ','));
		assertArrayEquals(new String[] {"", "", "", ""}, StringUtilz.split(",,,", ','));
		
		assertArrayEquals(new String[] {" Foo", " Bar", " Baz "}, StringUtilz.split(" Foo, Bar, Baz ", ','));
		assertArrayEquals(new String[] {"Foo", "Bar", "Baz"}, StringUtilz.split(" Foo, Bar, Baz ", ',', true));
	}

	@Test
	public void testSplit_Str() throws Exception {
		assertArrayEquals(null, StringUtilz.split(null, ","));
		assertArrayEquals(new String[] {""}, StringUtilz.split("", ","));
		
		assertArrayEquals(new String[] {"", ""}, StringUtilz.split(",", ","));
		assertArrayEquals(new String[] {"", "", "", ""}, StringUtilz.split(",,,", ","));
		
		assertArrayEquals(new String[] {" Foo", " Bar", " Baz "}, StringUtilz.split(" Foo, Bar, Baz ", ","));
		assertArrayEquals(new String[] {"Foo", "Bar", "Baz"}, StringUtilz.split(" Foo, Bar, Baz ", ",", true));
	}

	@Test
	public void testSplit_StrArr() throws Exception {
		assertArrayEquals(null, StringUtilz.split(null, new String[] {",", "SPLIT", "SPL"}));
		assertArrayEquals(new String[] {""}, StringUtilz.split("", new String[] {",", "SPLIT", "SPL"}));
		
		assertArrayEquals(new String[] {"", ""}, StringUtilz.split(",", new String[] {",", "SPLIT", "SPL"}));
		assertArrayEquals(new String[] {"", "", "", ""}, StringUtilz.split(",SPLSPLIT", new String[] {",", "SPLIT", "SPL"}));
		assertArrayEquals(new String[] {"", "", "", "IT"}, StringUtilz.split(",SPLSPLIT", new String[] {",", "SPL", "SPLIT"}));
		
		assertArrayEquals(new String[] {" Foo", " Bar", " Baz "}, StringUtilz.split(" Foo, BarSPLIT Baz ", new String[] {",", "SPLIT", "SPL"}));
		assertArrayEquals(new String[] {"Foo", "Bar", "Baz"}, StringUtilz.split(" Foo, BarSPLIT Baz ", new String[] {",", "SPLIT", "SPL"}, true));
	}

	@Test
	public void testSplit_Pattern() throws Exception {
		assertArrayEquals(null, StringUtilz.split(null, Pattern.compile("[,:|]")));
		assertArrayEquals(new String[] {""}, StringUtilz.split("", Pattern.compile("[,:|]")));
		
		assertArrayEquals(new String[] {"", ""}, StringUtilz.split(",", Pattern.compile("[,:|]")));
		assertArrayEquals(new String[] {"", "", "", ""}, StringUtilz.split(",:|", Pattern.compile("[,:|]")));
		
		assertArrayEquals(new String[] {" Foo", " Bar", " Baz "}, StringUtilz.split(" Foo, Bar: Baz ", Pattern.compile("[,:|]")));
		assertArrayEquals(new String[] {"Foo", "Bar", "Baz"}, StringUtilz.split(" Foo, Bar: Baz ", Pattern.compile("[,:|]"), true));
	}

	@Test
	public void testJoin() throws Exception {
		assertEquals("FooBarBaz", StringUtilz.join((String)null, "Foo", "Bar", "Baz"));
		assertEquals("FooBarBaz", StringUtilz.join((String)null, Arrays.asList("Foo", "Bar", "Baz")));
		
		assertEquals("FooBarBaz", StringUtilz.join("", "Foo", "Bar", "Baz"));
		assertEquals("FooBarBaz", StringUtilz.join("", Arrays.asList("Foo", "Bar", "Baz")));

		assertEquals("", StringUtilz.join(", ", (Object[])null));
		assertEquals("", StringUtilz.join(", ", (Collection<?>)null));
		
		assertEquals("", StringUtilz.join(", "));
		assertEquals("", StringUtilz.join(", ", Arrays.asList()));

		assertEquals("Foo/Bar/Baz", StringUtilz.join("/", "Foo", "Bar", "Baz"));
		assertEquals("Foo/Bar/Baz", StringUtilz.join("/", Arrays.asList("Foo", "Bar", "Baz")));
		
		assertEquals("Foo", StringUtilz.join(", ", "Foo"));
		assertEquals("Foo", StringUtilz.join(", ", Arrays.asList("Foo")));
		
		assertEquals("Foo, Bar, Baz", StringUtilz.join(", ", "Foo", "Bar", "Baz"));
		assertEquals("Foo, Bar, Baz", StringUtilz.join(", ", Arrays.asList("Foo", "Bar", "Baz")));
	}

	@Test
	public void testJoin_StringBuilder() throws Exception {
		assertEquals("FooBarBaz", StringUtilz.join(new StringBuilder(), (String)null, "Foo", "Bar", "Baz").toString());
		assertEquals("FooBarBaz", StringUtilz.join((String)null, Arrays.asList("Foo", "Bar", "Baz")).toString());
		
		assertEquals("FooBarBaz", StringUtilz.join(new StringBuilder(), "", "Foo", "Bar", "Baz").toString());
		assertEquals("FooBarBaz", StringUtilz.join(new StringBuilder(), "", Arrays.asList("Foo", "Bar", "Baz")).toString());

		assertEquals("", StringUtilz.join(new StringBuilder(), ", ", (Object[])null).toString());
		assertEquals("", StringUtilz.join(new StringBuilder(), ", ", (Collection<?>)null).toString());
		
		assertEquals("", StringUtilz.join(new StringBuilder(), ", ").toString());
		assertEquals("", StringUtilz.join(new StringBuilder(), ", ", Arrays.asList()).toString());

		assertEquals("Foo/Bar/Baz", StringUtilz.join(new StringBuilder(), "/", "Foo", "Bar", "Baz").toString());
		assertEquals("Foo/Bar/Baz", StringUtilz.join(new StringBuilder(), "/", Arrays.asList("Foo", "Bar", "Baz")).toString());
		
		assertEquals("Foo", StringUtilz.join(new StringBuilder(), ", ", "Foo").toString());
		assertEquals("Foo", StringUtilz.join(new StringBuilder(), ", ", Arrays.asList("Foo")).toString());
		
		assertEquals("Foo, Bar, Baz", StringUtilz.join(new StringBuilder(), ", ", "Foo", "Bar", "Baz").toString());
		assertEquals("Foo, Bar, Baz", StringUtilz.join(new StringBuilder(), ", ", Arrays.asList("Foo", "Bar", "Baz")).toString());
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

	@Test
	public void testSwapCase() throws Exception {
		assertEquals(null, StringUtilz.swapCase(null));
		assertEquals("", StringUtilz.swapCase(""));
		
		assertEquals("AB-CD", StringUtilz.swapCase("ab-cd"));
		assertEquals("ab-cd", StringUtilz.swapCase("AB-CD"));
		assertEquals("aB-Cd", StringUtilz.swapCase("Ab-cD"));
	}

	@Test
	public void testCapitalize() throws Exception {
		assertEquals(null, StringUtilz.capitalize(null));
		assertEquals("", StringUtilz.capitalize(""));
		
		assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", StringUtilz.capitalize("the quick brown fox jumps over the lazy dog"));
		
		assertEquals(" The Quick Brown Fox  Jumps  Over The Lazy Dog ", StringUtilz.capitalize(" the quick brown fox  jumps  over the lazy dog "));
		
		assertEquals("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", StringUtilz.capitalize("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG"));
		assertEquals("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", StringUtilz.capitalize("tHE qUICK bROWN fOX jUMPS oVER tHE lAZY dOG"));
		
		assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", StringUtilz.capitalize("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG", true));
		assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", StringUtilz.capitalize("tHE qUICK bROWN fOX jUMPS oVER tHE lAZY dOG", true));
		
		assertEquals("The Quick Brown Fox\r\nJumps\tOver The Lazy Dog", StringUtilz.capitalize("the quick brown fox\r\njumps\tover the lazy dog"));

		assertEquals("THE qUICK-BROWN-FOX,JUMPS.OVER tHE lAZY dOG", StringUtilz.capitalize("tHE qUICK-bROWN-fOX,jUMPS.oVER tHE lAZY dOG", '-', ',', '.'));
		assertEquals("The quick-Brown-Fox,Jumps.Over the lazy dog", StringUtilz.capitalize("tHE qUICK-bROWN-fOX,jUMPS.oVER tHE lAZY dOG", true, '-', ',', '.'));
		
		assertEquals("THE qUICK-BROWN-FOX,JUMPS.OVER tHE lAZY dOG", StringUtilz.capitalize("tHE qUICK-bROWN-fOX,jUMPS.oVER tHE lAZY dOG", new Predicate<Integer>() {
			@Override
			public boolean test(Integer t) {
				int cp = t.intValue();
				return (cp == '-' || cp == ',' || cp == '.');
			}
		}));

		assertEquals("The quick-Brown-Fox,Jumps.Over the lazy dog", StringUtilz.capitalize("tHE qUICK-bROWN-fOX,jUMPS.oVER tHE lAZY dOG", true, new Predicate<Integer>() {
			@Override
			public boolean test(Integer t) {
				int cp = t.intValue();
				return (cp == '-' || cp == ',' || cp == '.');
			}
		}));
	}

	@Test
	public void testInitials() throws Exception {
		assertEquals(null, StringUtilz.initials(null));
		assertEquals("", StringUtilz.initials(""));
		
		assertEquals("tqbfjotld", StringUtilz.initials("the quick brown fox jumps over the lazy dog"));
		
		assertEquals("tqbfjotld", StringUtilz.initials(" the quick brown fox  jumps  over the lazy dog "));
		
		assertEquals("TQBFJOTLD", StringUtilz.initials("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG"));
		assertEquals("tqbfjotld", StringUtilz.initials("tHE qUICK bROWN fOX jUMPS oVER tHE lAZY dOG"));
		
		assertEquals("tqbfjotld", StringUtilz.initials("the quick brown fox\r\njumps\tover the lazy dog"));

		assertEquals("tbfjo", StringUtilz.initials("the quick-brown-fox,jumps.over the lazy dog", '-', ',', '.'));

		assertEquals("tbfjo", StringUtilz.initials("the quick-brown-fox,jumps.over the lazy dog", new Predicate<Integer>() {
			@Override
			public boolean test(Integer t) {
				int cp = t.intValue();
				return (cp == '-' || cp == ',' || cp == '.');
			}
		}));
	}
	
	@Test
	public void testUnescape() throws Exception {
		assertEquals(null, StringUtilz.unescape(null, '\\', null, null, false));
		assertEquals(null, StringUtilz.unescape(null, '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals(null, StringUtilz.unescape(null, '\\', null, null, true));
		
		assertEquals("", StringUtilz.unescape("", '\\', null, null, false));
		assertEquals("", StringUtilz.unescape("", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals("", StringUtilz.unescape("", '\\', null, null, true));
		
		assertEquals("\t", StringUtilz.unescape("\\t", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals("A\tA", StringUtilz.unescape("A\\tA", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals("A\t\tA", StringUtilz.unescape("A\\t\\tA", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals("A\tA\tA", StringUtilz.unescape("A\\tA\\tA", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		
		assertEquals("X", StringUtilz.unescape("\\X", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		assertEquals("AXA", StringUtilz.unescape("A\\XA", '\\', new char[] {'\t'}, new char[] {'t'}, false));
		
		assertEquals("😀", StringUtilz.unescape("\\uD83D\\uDE00", '\\', null, null, true));
		assertEquals("😀", StringUtilz.unescape("\\u{1F600}", '\\', null, null, true));
		assertEquals("😀", StringUtilz.unescape("\\U0001F600", '\\', null, null, true));
		
		assertEquals("uD83XuDE0X", StringUtilz.unescape("\\uD83X\\uDE0X", '\\', null, null, true));
		assertEquals("u{1F60X}", StringUtilz.unescape("\\u{1F60X}", '\\', null, null, true));
		assertEquals("u{1F600", StringUtilz.unescape("\\u{1F600", '\\', null, null, true));
		assertEquals("U0001F60X", StringUtilz.unescape("\\U0001F60X", '\\', null, null, true));
	}
}
