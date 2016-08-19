/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package test.org.mifmi.commons4j.config;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.mifmi.commons4j.config.Config;
import org.mifmi.commons4j.config.ResourceBundleConfig;

public class ConfigTest {

	@Test
	public void testSampleCode() throws Exception {
		Config config = new ResourceBundleConfig(
				"testconfig",
				new Locale("ja", "JP"),
				new Locale("en", "US")
				);
		String stringVal = config.get("test.string");
		int intVal = config.getAsInt("test.int");
		boolean booleanVal = config.getAsBoolean("test.boolean");
		String[] arrayVal = config.getAsArray("test.array");
		
		String localeJaJP = config.get("test.locale.ja_JP");
		String localeJa = config.get("test.locale.ja");
		String localeEnUS = config.get("test.locale.en_US");
		String localeEn = config.get("test.locale.en");
		String localeDefault = config.get("test.locale.default");
		
		assertEquals("テスト", stringVal);
		assertEquals(12345, intVal);
		assertEquals(true, booleanVal);
		assertArrayEquals(new String[]{"ふー", "ばー", "ばず"}, arrayVal);
		
		assertEquals("locale ja_JP", localeJaJP);
		assertEquals("locale ja", localeJa);
		assertEquals("locale en_US", localeEnUS);
		assertEquals("locale en", localeEn);
		assertEquals("locale default", localeDefault);
	}
}
