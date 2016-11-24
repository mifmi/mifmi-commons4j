/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.text.token;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.text.token.StringToken;
import org.mifmi.commons4j.text.token.StringTokenParser;

public class StringTokenParserTest {

	@Test
	public void testSampleCodeEscaping() throws Exception {
		StringTokenParser tokenParser = new StringTokenParser(" abc, de \\t\\nf , hij:=  kn \\n ")
				.setEscape('\\', new char[]{'t', 'n'}, new char[]{'\t', '\n'});
		
		StringToken token1 = tokenParser.nextToken(",", ":=");
		assertEquals(" abc", token1.getToken());
		assertEquals(4, token1.getTokenEndIndex());
		assertArrayEquals(null, token1.getBlockSet());
		assertEquals(",", token1.getTerminator());
		assertEquals(5, token1.getTerminatorEndIndex());
		assertEquals(false, token1.isEos());

		StringToken token2 = tokenParser.nextToken(",", ":=");
		assertEquals(" de \t\nf ", token2.getToken());
		assertEquals(15, token2.getTokenEndIndex());
		assertArrayEquals(null, token2.getBlockSet());
		assertEquals(",", token2.getTerminator());
		assertEquals(16, token2.getTerminatorEndIndex());
		assertEquals(false, token2.isEos());

		StringToken token3 = tokenParser.nextToken(",", ":=");
		assertEquals(" hij", token3.getToken());
		assertEquals(20, token3.getTokenEndIndex());
		assertArrayEquals(null, token3.getBlockSet());
		assertEquals(":=", token3.getTerminator());
		assertEquals(22, token3.getTerminatorEndIndex());
		assertEquals(false, token3.isEos());

		StringToken token4 = tokenParser.nextToken(",", ":=");
		assertEquals("  kn \n ", token4.getToken());
		assertEquals(30, token4.getTokenEndIndex());
		assertArrayEquals(null, token4.getBlockSet());
		assertEquals("", token4.getTerminator());
		assertEquals(30, token4.getTerminatorEndIndex());
		assertEquals(true, token4.isEos());
	}

	@Test
	public void testSampleCodeEscapingAndTrim() throws Exception {
		StringTokenParser tokenParser = new StringTokenParser(" abc, de \\t\\nf , hij:=  kn \\n ")
				.setEscape('\\', new char[]{'t', 'n'}, new char[]{'\t', '\n'})
				.setTrim(true);
		
		StringToken tokenPeek1 = tokenParser.peekNextToken(",", ":=");
		assertEquals("abc", tokenPeek1.getToken());
		assertEquals(4, tokenPeek1.getTokenEndIndex());
		assertArrayEquals(null, tokenPeek1.getBlockSet());
		assertEquals(",", tokenPeek1.getTerminator());
		assertEquals(5, tokenPeek1.getTerminatorEndIndex());
		assertEquals(false, tokenPeek1.isEos());
		
		StringToken token1 = tokenParser.nextToken(",", ":=");
		assertEquals("abc", token1.getToken());
		assertEquals(4, token1.getTokenEndIndex());
		assertArrayEquals(null, token1.getBlockSet());
		assertEquals(",", token1.getTerminator());
		assertEquals(5, token1.getTerminatorEndIndex());
		assertEquals(false, token1.isEos());

		StringToken tokenPeek2 = tokenParser.peekNextToken(",", ":=");
		assertEquals("de \t\nf", tokenPeek2.getToken());
		assertEquals(15, tokenPeek2.getTokenEndIndex());
		assertArrayEquals(null, tokenPeek2.getBlockSet());
		assertEquals(",", tokenPeek2.getTerminator());
		assertEquals(16, tokenPeek2.getTerminatorEndIndex());
		assertEquals(false, tokenPeek2.isEos());

		StringToken token2 = tokenParser.nextToken(",", ":=");
		assertEquals("de \t\nf", token2.getToken());
		assertEquals(15, token2.getTokenEndIndex());
		assertArrayEquals(null, token2.getBlockSet());
		assertEquals(",", token2.getTerminator());
		assertEquals(16, token2.getTerminatorEndIndex());
		assertEquals(false, token2.isEos());

		StringToken tokenPeek3 = tokenParser.peekNextToken(",", ":=");
		assertEquals("hij", tokenPeek3.getToken());
		assertEquals(20, tokenPeek3.getTokenEndIndex());
		assertArrayEquals(null, tokenPeek3.getBlockSet());
		assertEquals(":=", tokenPeek3.getTerminator());
		assertEquals(22, tokenPeek3.getTerminatorEndIndex());
		assertEquals(false, tokenPeek3.isEos());

		StringToken token3 = tokenParser.nextToken(",", ":=");
		assertEquals("hij", token3.getToken());
		assertEquals(20, token3.getTokenEndIndex());
		assertArrayEquals(null, token3.getBlockSet());
		assertEquals(":=", token3.getTerminator());
		assertEquals(22, token3.getTerminatorEndIndex());
		assertEquals(false, token3.isEos());

		StringToken tokenPeek4 = tokenParser.peekNextToken(",", ":=");
		assertEquals("kn", tokenPeek4.getToken());
		assertEquals(30, tokenPeek4.getTokenEndIndex());
		assertArrayEquals(null, tokenPeek4.getBlockSet());
		assertEquals("", tokenPeek4.getTerminator());
		assertEquals(30, tokenPeek4.getTerminatorEndIndex());
		assertEquals(true, tokenPeek4.isEos());

		StringToken token4 = tokenParser.nextToken(",", ":=");
		assertEquals("kn", token4.getToken());
		assertEquals(30, token4.getTokenEndIndex());
		assertArrayEquals(null, token4.getBlockSet());
		assertEquals("", token4.getTerminator());
		assertEquals(30, token4.getTerminatorEndIndex());
		assertEquals(true, token4.isEos());
	}
}
