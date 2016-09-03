/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.mifmi.commons4j.util.BooleanUtilz;
import org.mifmi.commons4j.util.NumberUtilz;
import org.mifmi.commons4j.util.StringUtilz;

public abstract class AbstractConfig implements Config {
	
	private static final char ESCAPE_CHAR = '\\';
	
	private static final char MULTIPLE_VALUES_SEPARATOR = ',';
	private static final char MAP_KEY_VALUE_SEPARATOR = ':';

	private static final char[] ARRAY_ESCAPE_TARGET_CHARS = {MULTIPLE_VALUES_SEPARATOR};
	private static final char[] MAP_ESCAPE_TARGET_CHARS = {MAP_KEY_VALUE_SEPARATOR, MULTIPLE_VALUES_SEPARATOR};

	protected abstract boolean handleSupportsObjectValue();
	
	protected abstract Object handleGetValue(String key);

	protected abstract Object handleSetValue(String key, Object value);
	
	protected abstract Object handleRemoveValue(String key);
	
	protected abstract boolean handleContainsKey(String key);
	
	protected abstract Set<String> handleGetKeySet();
	
	protected abstract int handleGetSize();
	
	private boolean supportsObjectValue() {
		return handleSupportsObjectValue();
	}
	
	protected Object getAsObject(String key) {
		if (!handleContainsKey(key)) {
			throw new ConfigNotFoundException("Config not found: key=\"" + key + "\"");
		}
		
		Object value = handleGetValue(key);
		return value;
	}
	
	protected Object getAsObject(String key, Object defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return value;
	}
	
	public String get(String key) {
		Object value = getAsObject(key);
		return toString(value);
	}

	@Override
	public String get(String key, String defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return toString(value);
	}
	
	public int getAsInt(String key) {
		Object value = getAsObject(key);
		return parseInt(value);
	}
	
	public int getAsInt(String key, int defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseInt(value);
	}
	
	public long getAsLong(String key) {
		Object value = getAsObject(key);
		return parseLong(value);
	}
	
	public long getAsLong(String key, long defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseLong(value);
	}
	
	public double getAsDouble(String key) {
		Object value = getAsObject(key);
		return parseDouble(value);
	}
	
	public double getAsDouble(String key, double defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseDouble(value);
	}
	
	@Override
	public <T extends Number> T getAsNumber(String key, Class<T> numberClass) {
		Object value = getAsObject(key);
		return parseNumber(value, numberClass);
	}

	@Override
	public <T extends Number> T getAsNumber(String key, Class<T> numberClass, T defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseNumber(value, numberClass);
	}

	public boolean getAsBoolean(String key) {
		Object value = getAsObject(key);
		return parseBoolean(value);
	}
	
	public boolean getAsBoolean(String key, boolean defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseBoolean(value);
	}

	@Override
	public OffsetDateTime getAsOffsetDateTime(String key) {
		Object value = getAsObject(key);
		return parseTemporal(value, OffsetDateTime.class, OffsetDateTime::parse);
	}

	@Override
	public OffsetDateTime getAsOffsetDateTime(String key, OffsetDateTime defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseTemporal(value, OffsetDateTime.class, OffsetDateTime::parse);
	}

	@Override
	public <T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass) {
		Object value = getAsObject(key);
		return parseTemporal(value, temporalClass);
	}

	@Override
	public <T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass, T defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseTemporal(value, temporalClass);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		Object value = getAsObject(key);
		return parseEnum(enumType, value);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseEnum(enumType, value);
	}

	@Override
	public byte[] getAsBinary(String key) {
		Object value = getAsObject(key);
		return parseBinary(value);
	}

	@Override
	public byte[] getAsBinary(String key, byte[] defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseBinary(value);
	}

	public String[] getAsArray(String key) {
		Object value = getAsObject(key);
		return parseStringArray(value);
	}
	
	public String[] getAsArray(String key, String[] defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseStringArray(value);
	}

	@Override
	public Map<String, Object> getAsMap(String key) {
		Object value = getAsObject(key);
		return parseMap(value);
	}

	@Override
	public Map<String, Object> getAsMap(String key, Map<String, Object> defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		return parseMap(value);
	}
	
	public Object set(String key, String value) {
		return handleSetValue(key, value);
	}
	
	public Object set(String key, int value) {
		Object objValue = Integer.valueOf(value);
		if (supportsObjectValue()) {
			return handleSetValue(key, objValue);
		} else {
			return handleSetValue(key, toString(objValue));
		}
	}
	
	public Object set(String key, long value) {
		Object objValue = Long.valueOf(value);
		if (supportsObjectValue()) {
			return handleSetValue(key, objValue);
		} else {
			return handleSetValue(key, toString(objValue));
		}
	}
	
	public Object set(String key, double value) {
		Object objValue = Double.valueOf(value);
		if (supportsObjectValue()) {
			return handleSetValue(key, objValue);
		} else {
			return handleSetValue(key, toString(objValue));
		}
	}
	
	public Object set(String key, Number value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}
	
	public Object set(String key, boolean value) {
		Object objValue = Boolean.valueOf(value);
		if (supportsObjectValue()) {
			return handleSetValue(key, objValue);
		} else {
			return handleSetValue(key, toString(objValue));
		}
	}
	
	public Object set(String key, TemporalAccessor value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}
	
	public Object set(String key, Enum<?> value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}
	
	@Override
	public Object set(String key, byte... value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}

	public Object set(String key, String... value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}

	@Override
	public Object set(String key, Map<String, Object> value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value));
		}
	}
	
	public Object remove(String key) {
		return handleRemoveValue(key);
	}
	
	public boolean containsKey(String key) {
		return handleContainsKey(key);
	}
	
	public Set<String> keySet() {
		return handleGetKeySet();
	}
	
	public Enumeration<String> getKeys() {
		return Collections.enumeration(handleGetKeySet());
	}
	
	public Map<String, Object> asMap() {
		Set<String> keySet = handleGetKeySet();
		
		Map<String, Object> map = new LinkedHashMap<String, Object>(keySet.size());
		for (String key : keySet) {
			Object value = handleGetValue(key);
			map.put(key, value);
		}
		return map;
	}
	
	public int size() {
		return handleGetSize();
	}
	
	public boolean isEmpty() {
		return (size() == 0);
	}

	public void storeToAppConfig(String configName, String appName, String groupName, String comments) throws IOException {
		Config.storeToAppConfig(this, configName, appName, groupName, comments);
	}
	
	protected static int parseInt(Object value) {
		try {
			return NumberUtilz.toInt(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static long parseLong(Object value) {
		try {
			return NumberUtilz.toLong(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static double parseDouble(Object value) {
		try {
			return NumberUtilz.toDouble(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static <T extends Number> T parseNumber(Object value, Class<T> numberClass) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof Number) {
				return NumberUtilz.castNumber((Number)value, numberClass);
			} else {
				return NumberUtilz.parseNumber(toString(value), "0.#", numberClass);
			}
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static boolean parseBoolean(Object value) {
		try {
			return BooleanUtilz.toBoolean(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static <T extends TemporalAccessor> T parseTemporal(Object value, Class<T> temporalClass) {
		try {
			if (value == null) {
				return null;
			}
			
			if (temporalClass.isInstance(value)) {
				return (T)value;
			}
			
			Method parseMethod = temporalClass.getMethod("parse", CharSequence.class);
			return (T)parseMethod.invoke(null, toString(value));
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static <T extends TemporalAccessor> T parseTemporal(Object value, Class<T> temporalClass, Function<CharSequence, T> parseFunc) {
		try {
			if (value == null) {
				return null;
			}
			
			if (temporalClass.isInstance(value)) {
				return (T)value;
			}
			
			return parseFunc.apply(toString(value));
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	public <T extends Enum<T>> T parseEnum(Class<T> enumType, Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof Enum) {
				return (T)value;
			}
			
			String name = toString(value);
			
			return Enum.valueOf(enumType, name);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static byte[] parseBinary(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof byte[]) {
				return (byte[])value;
			} else if (value instanceof Number) {
				return NumberUtilz.toByteArray((Number)value);
			} else if (value instanceof Boolean) {
				return new byte[]{1};
			} else {
				return hexToBin(toString(value));
			}
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static String[] parseStringArray(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value.getClass().isArray()) {
				return (String[])value;
			}
			
			return StringUtilz.split(toString(value), MULTIPLE_VALUES_SEPARATOR, true, -1, ESCAPE_CHAR, true);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static Map<String, Object> parseMap(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof Map) {
				return (Map)value;
			}
			
			String[] strMapEntries = StringUtilz.split(toString(value), MULTIPLE_VALUES_SEPARATOR, true, -1, ESCAPE_CHAR, false);
			Map<String, Object> map = new LinkedHashMap<>(strMapEntries.length);
			for (String strMapEntry : strMapEntries) {
				String[] strKeyValue = StringUtilz.split(strMapEntry, MAP_KEY_VALUE_SEPARATOR, true, 2, ESCAPE_CHAR, true);
				String strKey = strKeyValue[0];
				String strValue = strKeyValue[1];
				
				map.put(strKey, strValue);
			}
			
			return map;
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static String toString(Object value) {
		if (value == null) {
			return "";
		} else if (value instanceof Enum) {
			return ((Enum<?>)value).name();
		} else if (value instanceof byte[]) {
			byte[] binary = (byte[])value;
			return binToHex(binary);
		} else if (value.getClass().isArray()) {
			Object[] array = (Object[])value;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < array.length; i++) {
				if (i != 0) {
					sb.append(MULTIPLE_VALUES_SEPARATOR);
				}
				sb.append(StringUtilz.escape(toString(array[i]), ESCAPE_CHAR, ARRAY_ESCAPE_TARGET_CHARS));
			}
			return sb.toString();
		} else if (value instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)value;
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (!first) {
					sb.append(MULTIPLE_VALUES_SEPARATOR);
				}
				first = false;
				
				String k = entry.getKey();
				String v = toString(entry.getValue());
				
				sb.append(StringUtilz.escape(k, ESCAPE_CHAR, MAP_ESCAPE_TARGET_CHARS));
				sb.append(MAP_KEY_VALUE_SEPARATOR);
				sb.append(StringUtilz.escape(v, ESCAPE_CHAR, MAP_ESCAPE_TARGET_CHARS));
			}
			return sb.toString();
		} else {
			return value.toString();
		}
	}
	
	private static String binToHex(byte[] bin) {
		if (bin == null) {
			return null;
		}
		if (bin.length == 0) {
			return "";
		}
		
		int len = bin.length;
		
		StringBuilder sb = new StringBuilder(len * 2);
		for (byte b : bin) {
			int high = ((b >>> 4) & 0xF);
			int low = (b & 0xF);
			if (high < 10) {
				sb.append((char)('0' + high));
			} else {
				sb.append((char)('A' + (high - 10)));
			}
			if (low < 10) {
				sb.append((char)('0' + low));
			} else {
				sb.append((char)('A' + (low - 10)));
			}
		}
		return sb.toString();
	}

	private static byte[] hexToBin(String hex) {
		int len = hex.length();
		byte[] binValue = new byte[len / 2];
		int binIdx = 0;
		for (int i = 0; i < len; ) {
			int b = 0;
			if (i != 0 || len % 2 == 0) {
				b = parseHex(hex.charAt(i++)) << 4;
			}
			b = b | parseHex(hex.charAt(i++));
			binValue[binIdx++] = (byte)b;
		}
		
		return binValue;
	}
	
	private static int parseHex(char ch) {
		if ('0' <= ch && ch <= '9') {
			return (byte)(ch - '0');
		} else if ('A' <= ch && ch <= 'F') {
			return (byte)(ch - 'A' + 10);
		} else if ('a' <= ch && ch <= 'f') {
			return (byte)(ch - 'a' + 10);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
