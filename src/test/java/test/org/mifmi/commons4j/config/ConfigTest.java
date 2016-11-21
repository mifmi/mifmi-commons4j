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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.mifmi.commons4j.config.Config;
import org.mifmi.commons4j.config.ResourceBundleConfig;

public class ConfigTest {

	@Test
	public void testSampleCodeResourceBundle() throws Exception {
		Config config = new ResourceBundleConfig(
				"testconfig",
				new Locale("ja", "JP"),
				new Locale("en", "US")
				);
		
		String stringVal = config.getAsString("test.string");
		int intVal = config.getAsInt("test.int");
		boolean booleanVal = config.getAsBoolean("test.boolean");
		String[] arrayVal = config.getAsStringArray("test.array");
		
		String localeJaJP = config.getAsString("test.locale.ja_JP");
		String localeJa = config.getAsString("test.locale.ja");
		String localeEnUS = config.getAsString("test.locale.en_US");
		String localeEn = config.getAsString("test.locale.en");
		String localeDefault = config.getAsString("test.locale.default");
		
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

	@Test
	public void testSampleCodeAppConfig() throws Exception {
		final String configName = "settings.properties";
		final String appName = "sampleapp";
		final String groupName = "mifmi.org";
		
		// Load
		Config config = Config.loadFromAppConfig(configName, appName, groupName);
		
		// Set
		config.set("test.string", "テスト,:\\\"][][}{}{テスト");
		config.set("test.int", 12345);
		config.set("test.long", 123456789012345L);
		config.set("test.double", 12345.6789);
		config.set("test.boolean", true);
		config.set("test.datetime", OffsetDateTime.of(2000, 1, 23, 1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
		config.set("test.date", LocalDate.of(2000, 1, 23));
		config.set("test.time", OffsetTime.of(1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
		config.set("test.binary", new byte[]{0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF});
		config.set("test.array", new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"});
		config.set("test.map", new LinkedHashMap<String, Object>(){
			{
				put("map.string", "テスト,:\\\"][][}{}{テスト");
				put("map.int", 12345);
				put("map.long", 123456789012345L);
				put("map.double", 12345.6789);
				put("map.boolean", true);
				put("map.datetime", OffsetDateTime.of(2000, 1, 23, 1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
				put("map.date", LocalDate.of(2000, 1, 23));
				put("map.time", OffsetTime.of(1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
				put("map.binary", new byte[]{0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF});
				put("map.array", new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"});
			}
		});
		config.set("test.map.,:\\\"][][}{}{nest.escape", new LinkedHashMap<String, Object>(){
			{
				put("map.map.,:\\\"][][}{}{nest.escape", new LinkedHashMap<String, Object>(){
					{
						put("map.map.string.,:\\\"][][}{}{nest.escape", "テスト,:\\\"][][}{}{テスト");
						put("map.map.array.,:\\\"][][}{}{nest.escape", new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"});
					}
				});
			}
		});
		
		// Store
		config.storeToAppConfig(configName, appName, groupName, "comment");
		
		// Reload
		Config config2 = Config.loadFromAppConfig(configName, appName, groupName);
		
		// Get form reloaded configure
		String stringVal = config2.getAsString("test.string");
		int intVal = config2.getAsInt("test.int");
		long longVal = config2.getAsLong("test.long");
		double doubleVal = config2.getAsDouble("test.double");
		boolean booleanVal = config2.getAsBoolean("test.boolean");
		OffsetDateTime datetimeVal = config2.getAsOffsetDateTime("test.datetime");
		OffsetDateTime temporalDatetimeVal = config2.getAsTemporal("test.datetime", OffsetDateTime.class);
		LocalDate temporalDateVal = config2.getAsTemporal("test.date", LocalDate.class);
		OffsetTime temporalTimeVal = config2.getAsTemporal("test.time", OffsetTime.class);
		byte[] binaryVal = config2.getAsBinary("test.binary");
		String[] arrayVal = config2.getAsStringArray("test.array");
		Map<String, Object> mapVal = config2.getAsMap("test.map");
		Map<String, Object> mapEscapeVal = config2.getAsMap("test.map.,:\\\"][][}{}{nest.escape");
		
		assertEquals("テスト,:\\\"][][}{}{テスト", stringVal);
		assertEquals(12345, intVal);
		assertEquals(123456789012345L, longVal);
		assertEquals(0, Double.compare(12345.6789, doubleVal));
		assertEquals(true, booleanVal);
		assertEquals(OffsetDateTime.of(2000, 1, 23, 1, 23, 45, 678901234, ZoneOffset.ofHours(9)), datetimeVal);
		assertEquals(OffsetDateTime.of(2000, 1, 23, 1, 23, 45, 678901234, ZoneOffset.ofHours(9)), temporalDatetimeVal);
		assertEquals(LocalDate.of(2000, 1, 23), temporalDateVal);
		assertEquals(OffsetTime.of(1, 23, 45, 678901234, ZoneOffset.ofHours(9)), temporalTimeVal);
		assertArrayEquals(new byte[]{0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF}, binaryVal);
		assertArrayEquals(new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"}, arrayVal);
		assertMapEquals(new LinkedHashMap<String, Object>() {
			{
				put("map.string", "テスト,:\\\"][][}{}{テスト");
				put("map.int", new BigInteger("12345"));
				put("map.long", new BigInteger("123456789012345"));
				put("map.double", new BigDecimal("12345.6789"));
				put("map.boolean", Boolean.TRUE);
				put("map.datetime", OffsetDateTime.of(2000, 1, 23, 1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
				put("map.date", LocalDate.of(2000, 1, 23));
				put("map.time", OffsetTime.of(1, 23, 45, 678901234, ZoneOffset.ofHours(9)));
				put("map.binary", new byte[]{0x01, 0x23, 0x45, 0x67, (byte)0x89, (byte)0xAB, (byte)0xCD, (byte)0xEF});
				put("map.array", new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"});
			}
		}, mapVal);
		assertMapEquals(new LinkedHashMap<String, Object>() {
			{
				put("map.map.,:\\\"][][}{}{nest.escape", new LinkedHashMap<String, Object>(){
					{
						put("map.map.string.,:\\\"][][}{}{nest.escape", "テスト,:\\\"][][}{}{テスト");
						put("map.map.array.,:\\\"][][}{}{nest.escape", new String[]{"ふー", "ばー", "ばず,:\\\"][][}{}{ほげ"});
					}
				});
			}
		}, mapEscapeVal);
	}
	
	private static void assertMapEquals(Map<?, ?> expected, Map<?, ?> actual) {
		assertEquals(expected.size(), actual.size());
		for (Object key : expected.keySet()) {
			Object expectedValue = expected.get(key);
			Object actualValue = actual.get(key);
			
			if (expectedValue == null) {
				assertEquals(expectedValue, actualValue);
			} else if (expectedValue instanceof byte[]) {
				assertArrayEquals((byte[])expectedValue, (byte[])actualValue);
			} else if (expectedValue instanceof Object[]) {
				assertArrayEquals((Object[])expectedValue, (Object[])actualValue);
			} else if (expectedValue instanceof Map) {
				assertMapEquals((Map<?, ?>)expectedValue, (Map<?, ?>)actualValue);
			} else {
				assertEquals(expectedValue, actualValue);
			}
		}
	}
}
