/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2020 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.web.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mifmi.commons4j.web.util.HTMLUtilz;

public class HTMLUtilzTest {
	
	@Test
	public void testEscapeBasicHTML() throws Exception {
		assertEquals(null, HTMLUtilz.escapeBasicHTML(null));
		assertEquals("", HTMLUtilz.escapeBasicHTML(""));
		
		assertEquals("&amp;", HTMLUtilz.escapeBasicHTML("&"));
		assertEquals("&lt;", HTMLUtilz.escapeBasicHTML("<"));
		assertEquals("&gt;", HTMLUtilz.escapeBasicHTML(">"));
		assertEquals("&quot;", HTMLUtilz.escapeBasicHTML("\""));
		assertEquals("&apos;", HTMLUtilz.escapeBasicHTML("'"));
		assertEquals("&#39;", HTMLUtilz.escapeBasicHTML("'", false, true));
		
		assertEquals(" ", HTMLUtilz.escapeBasicHTML(" "));
		assertEquals("\u00A0", HTMLUtilz.escapeBasicHTML("\u00A0"));
		assertEquals("&nbsp;", HTMLUtilz.escapeBasicHTML(" ", true));
		assertEquals("&nbsp;", HTMLUtilz.escapeBasicHTML("\u00A0", true));
		assertEquals("a", HTMLUtilz.escapeBasicHTML("a"));
		
		assertEquals("&amp;&amp;&amp;", HTMLUtilz.escapeBasicHTML("&&&"));
		assertEquals("&amp;x&amp;x&amp;", HTMLUtilz.escapeBasicHTML("&x&x&"));
		assertEquals("x&amp;x&amp;x&amp;x", HTMLUtilz.escapeBasicHTML("x&x&x&x"));
	}

	@Test
	public void testEscapeHTML5Fully() throws Exception {
		assertEquals(null, HTMLUtilz.escapeHTML5Fully(null));
		assertEquals("", HTMLUtilz.escapeHTML5Fully(""));
		
		assertEquals("&amp;", HTMLUtilz.escapeHTML5Fully("&"));
		assertEquals("&lt;", HTMLUtilz.escapeHTML5Fully("<"));
		assertEquals("&gt;", HTMLUtilz.escapeHTML5Fully(">"));
		assertEquals("&quot;", HTMLUtilz.escapeHTML5Fully("\""));
		assertEquals("&apos;", HTMLUtilz.escapeHTML5Fully("'"));
		assertEquals("&nbsp;", HTMLUtilz.escapeHTML5Fully("\u00A0"));
		
		assertEquals("&copy;", HTMLUtilz.escapeHTML5Fully("\u00A9"));
		
		assertEquals("&Zscr;", HTMLUtilz.escapeHTML5Fully("\uD835\uDCB5"));
		assertEquals("&zscr;", HTMLUtilz.escapeHTML5Fully("\uD835\uDCCF"));
		
		assertEquals("&#x61;", HTMLUtilz.escapeHTML5Fully("a"));
		assertEquals("&#97;", HTMLUtilz.escapeHTML5Fully("a", true));
		
		assertEquals("&#x1f4f1;", HTMLUtilz.escapeHTML5Fully("\uD83D\uDCF1"));
		assertEquals("&#128241;", HTMLUtilz.escapeHTML5Fully("\uD83D\uDCF1", true));
		
		assertEquals("&amp;&amp;&amp;", HTMLUtilz.escapeHTML5Fully("&&&"));
		assertEquals("&amp;&#x78;&amp;&#x78;&amp;", HTMLUtilz.escapeHTML5Fully("&x&x&"));
		assertEquals("&#x78;&amp;&#x78;&amp;&#x78;&amp;&#x78;", HTMLUtilz.escapeHTML5Fully("x&x&x&x"));
		
		assertEquals("&#x61;&#x61;&#x61;", HTMLUtilz.escapeHTML5Fully("aaa"));
		assertEquals("&#x61;&#x78;&#x61;&#x78;&#x61;", HTMLUtilz.escapeHTML5Fully("axaxa"));
		assertEquals("&#x78;&#x61;&#x78;&#x61;&#x78;&#x61;&#x78;", HTMLUtilz.escapeHTML5Fully("xaxaxax"));
	}

	@Test
	public void testUnescapeHTML5() throws Exception {
		assertEquals(null, HTMLUtilz.unescapeHTML5(null));
		assertEquals("", HTMLUtilz.unescapeHTML5(""));
		
		assertEquals("&", HTMLUtilz.unescapeHTML5("&amp;"));
		assertEquals("<", HTMLUtilz.unescapeHTML5("&lt;"));
		assertEquals(">", HTMLUtilz.unescapeHTML5("&gt;"));
		assertEquals("\"", HTMLUtilz.unescapeHTML5("&quot;"));
		assertEquals("'", HTMLUtilz.unescapeHTML5("&apos;"));
		assertEquals("\u00A0", HTMLUtilz.unescapeHTML5("&nbsp;"));
		
		assertEquals("\u00A9", HTMLUtilz.unescapeHTML5("&copy;"));
		assertEquals("\u00A9", HTMLUtilz.unescapeHTML5("&COPY;"));
		
		assertEquals("\uD835\uDCB5", HTMLUtilz.unescapeHTML5("&Zscr;"));
		assertEquals("\uD835\uDCCF", HTMLUtilz.unescapeHTML5("&zscr;"));
		
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#97;"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#097;"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#x61;"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#x0061;"));
		
		assertEquals("&", HTMLUtilz.unescapeHTML5("&amp"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#97"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#097"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#x61"));
		assertEquals("a", HTMLUtilz.unescapeHTML5("&#x0061"));
		
		assertEquals("\uD83D\uDCF1", HTMLUtilz.unescapeHTML5("&#x1f4f1;"));
		
		assertEquals(" ", HTMLUtilz.unescapeHTML5(" "));
		assertEquals("a", HTMLUtilz.unescapeHTML5("a"));
		
		assertEquals("&&&", HTMLUtilz.unescapeHTML5("&amp;&amp;&amp;"));
		assertEquals("&x&x&", HTMLUtilz.unescapeHTML5("&amp;x&amp;x&amp;"));
		assertEquals("x&x&x&x", HTMLUtilz.unescapeHTML5("x&amp;x&amp;x&amp;x"));
		
		assertEquals("aaa", HTMLUtilz.unescapeHTML5("&#97;&#97;&#97;"));
		assertEquals("axaxa", HTMLUtilz.unescapeHTML5("&#97;x&#97;x&#97;"));
		assertEquals("xaxaxax", HTMLUtilz.unescapeHTML5("x&#97;x&#97;x&#97;x"));
		
		assertEquals("aaa", HTMLUtilz.unescapeHTML5("&#x61;&#x61;&#x61;"));
		assertEquals("axaxa", HTMLUtilz.unescapeHTML5("&#x61;x&#x61;x&#x61;"));
		assertEquals("xaxaxax", HTMLUtilz.unescapeHTML5("x&#x61;x&#x61;x&#x61;x"));
		
		assertEquals("&&&", HTMLUtilz.unescapeHTML5("&amp&amp&amp"));
		assertEquals("& & &", HTMLUtilz.unescapeHTML5("&amp &amp &amp"));
		assertEquals(" & & & ", HTMLUtilz.unescapeHTML5(" &amp &amp &amp "));
		
		assertEquals("aaa", HTMLUtilz.unescapeHTML5("&#97&#97&#97"));
		assertEquals("axaxa", HTMLUtilz.unescapeHTML5("&#97x&#97x&#97"));
		assertEquals("xaxaxax", HTMLUtilz.unescapeHTML5("x&#97x&#97x&#97x"));
		
		assertEquals("aaa", HTMLUtilz.unescapeHTML5("&#x61&#x61&#x61"));
		assertEquals("axaxa", HTMLUtilz.unescapeHTML5("&#x61x&#x61x&#x61"));
		assertEquals("xaxaxax", HTMLUtilz.unescapeHTML5("x&#x61x&#x61x&#x61x"));
		
		assertEquals("&", HTMLUtilz.unescapeHTML5("&"));
		assertEquals("&;", HTMLUtilz.unescapeHTML5("&;"));
		assertEquals("&xxx;", HTMLUtilz.unescapeHTML5("&xxx;"));
		assertEquals("&xxx", HTMLUtilz.unescapeHTML5("&xxx"));
		assertEquals("&ampx", HTMLUtilz.unescapeHTML5("&ampx"));
		
		assertEquals("&#", HTMLUtilz.unescapeHTML5("&#"));
		assertEquals("&#;", HTMLUtilz.unescapeHTML5("&#;"));
		assertEquals("&#axx;", HTMLUtilz.unescapeHTML5("&#axx;"));
		assertEquals("&#axx", HTMLUtilz.unescapeHTML5("&#axx"));
		assertEquals("&#9999999999", HTMLUtilz.unescapeHTML5("&#9999999999"));
		
		assertEquals("&#x", HTMLUtilz.unescapeHTML5("&#x"));
		assertEquals("&#x;", HTMLUtilz.unescapeHTML5("&#x;"));
		assertEquals("&#xxx;", HTMLUtilz.unescapeHTML5("&#xxx;"));
		assertEquals("&#xxx", HTMLUtilz.unescapeHTML5("&#xxx"));
		assertEquals("&#x9999999999", HTMLUtilz.unescapeHTML5("&#x9999999999"));

		assertEquals("x&#xxxx", HTMLUtilz.unescapeHTML5("x&#xxxx"));
	}
}
