/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.mifmi.commons4j.io.file.FileUtilz;
import org.mifmi.commons4j.text.token.StringToken;
import org.mifmi.commons4j.text.token.StringTokenParser;
import org.mifmi.commons4j.util.BinaryUtilz;
import org.mifmi.commons4j.util.BooleanUtilz;
import org.mifmi.commons4j.util.EnvUtilz;
import org.mifmi.commons4j.util.NumberUtilz;
import org.mifmi.commons4j.util.StringUtilz;

public abstract class AbstractConfig implements Config {
	
	private static final char ESCAPE_CHAR = '\\';
	
	private static final String STRING_PREFIX = "\"";
	private static final String STRING_SUFFIX = "\"";
	private static final char STRING_PREFIX_CHAR = '"';
	private static final char STRING_SUFFIX_CHAR = '"';
	
	private static final String BINARY_PREFIX = "<";
	private static final String BINARY_SUFFIX = ">";
	
	private static final String ARRAY_PREFIX = "[";
	private static final String ARRAY_SUFFIX = "]";
	private static final char ARRAY_PREFIX_CHAR = '[';
	private static final char ARRAY_SUFFIX_CHAR = ']';
	
	private static final String MAP_PREFIX = "{";
	private static final String MAP_SUFFIX = "}";
	private static final char MAP_PREFIX_CHAR = '{';
	private static final char MAP_SUFFIX_CHAR = '}';

	private static final String MULTIPLE_VALUES_SEPARATOR = ",";
	private static final char MULTIPLE_VALUES_SEPARATOR_CHAR = ',';
	private static final String MAP_KEY_VALUE_SEPARATOR = ":";
	private static final char MAP_KEY_VALUE_SEPARATOR_CHAR = ':';
	
	private static final char[] ESCAPE_TARGET_CHARS = {'0', 'a', 'b', 'f', 'n', 'r', 't', 'v'};
	private static final char[] ESCAPED_CHARS = {'\0', '\u0007', '\b', '\f', '\n', '\r', '\t', '\u000B'};
	
	private static final char[] ARRAY_ESCAPE_TARGET_CHARS = {MULTIPLE_VALUES_SEPARATOR_CHAR, ARRAY_PREFIX_CHAR, ARRAY_SUFFIX_CHAR};
	private static final char[] MAP_KEY_ESCAPE_TARGET_CHARS = {MAP_KEY_VALUE_SEPARATOR_CHAR, MULTIPLE_VALUES_SEPARATOR_CHAR, MAP_PREFIX_CHAR, MAP_SUFFIX_CHAR};
	private static final char[] MAP_VALUE_ESCAPE_TARGET_CHARS = {MULTIPLE_VALUES_SEPARATOR_CHAR, MAP_PREFIX_CHAR, MAP_SUFFIX_CHAR};
	
	private static final String[][] BLOCKQUOTE_SETS = {
			{STRING_PREFIX, STRING_SUFFIX},
			{BINARY_PREFIX, BINARY_SUFFIX},
	};

	private static final Pattern DATETIME_VALUE_PATTERN = Pattern.compile("^[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(?:\\.[0-9]{1,9})?[\\+\\-][0-9]{1,2}:[0-9]{1,2}$");
	private static final Pattern DATE_VALUE_PATTERN = Pattern.compile("^[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}$");
	private static final Pattern TIME_VALUE_PATTERN = Pattern.compile("^[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}(?:\\.[0-9]{1,9})?[\\+\\-][0-9]{1,2}:[0-9]{1,2}$");
	private static final Pattern NUMBER_VALUE_PATTERN = Pattern.compile("^[\\+\\-]?[0-9]+(?:\\.[0-9]+)?$");
	
	private boolean useStringSyntax = false;

	protected abstract boolean supportsObjectValue();
	
	protected abstract Object handleGetValue(String key);

	protected abstract Object handleSetValue(String key, Object value);
	
	protected abstract Object handleRemoveValue(String key);
	
	protected abstract boolean handleContainsKey(String key);
	
	protected abstract Set<String> handleGetKeySet();
	
	protected abstract int handleGetSize();
	

	public boolean isUseStringSyntax() {
		return useStringSyntax;
	}

	public void setUseStringSyntax(boolean useStringSyntax) {
		this.useStringSyntax = useStringSyntax;
	}
	
	protected Object getValue(String key) {
		if (!handleContainsKey(key)) {
			throw new ConfigNotFoundException("Config not found: key=\"" + key + "\"");
		}
		
		Object value = handleGetValue(key);
		
		return value;
	}
	
	protected Object getValue(String key, Object defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = handleGetValue(key);
		
		return value;
	}
	
	public <T> T get(String key) {
		Object value = getValue(key);
		
		if (value != null && value instanceof String && isUseStringSyntax()) {
			return (T)parse((String)value, true);
		} else {
			return (T)value;
		}
	}
	
	@Override
	public <T> T get(String key, T defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		
		Object value = getValue(key);
		
		if (value != null && value instanceof String && isUseStringSyntax()) {
			return (T)parse((String)value, true);
		} else {
			return (T)value;
		}
	}
	
	public String getAsString(String key) {
		Object value = getValue(key);
		
		if (value == null) {
			return null;
		} else if (isUseStringSyntax()) {
			return parseString(value);
		} else {
			return toString(value, false);
		}
	}

	@Override
	public String getAsString(String key, String defaultValue) {
		Object value = getValue(key, defaultValue);
		
		if (value == null) {
			return null;
		} else if (isUseStringSyntax()) {
			return parseString(value);
		} else {
			return toString(value, false);
		}
	}
	
	public int getAsInt(String key) {
		Object value = getValue(key);
		return parseInt(value);
	}
	
	public int getAsInt(String key, int defaultValue) {
		Object value = getValue(key, Integer.valueOf(defaultValue));
		return parseInt(value);
	}
	
	public long getAsLong(String key) {
		Object value = getValue(key);
		return parseLong(value);
	}
	
	public long getAsLong(String key, long defaultValue) {
		Object value = getValue(key, Long.valueOf(defaultValue));
		return parseLong(value);
	}
	
	public double getAsDouble(String key) {
		Object value = getValue(key);
		return parseDouble(value);
	}
	
	public double getAsDouble(String key, double defaultValue) {
		Object value = getValue(key, Double.valueOf(defaultValue));
		return parseDouble(value);
	}
	
	public BigInteger getAsBigInteger(String key) {
		Object value = getValue(key);
		return parseBigInteger(value);
	}
	
	public BigInteger getAsBigInteger(String key, BigInteger defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseBigInteger(value);
	}
	
	public BigDecimal getAsBigDecimal(String key) {
		Object value = getValue(key);
		return parseBigDecimal(value);
	}
	
	public BigDecimal getAsBigDecimal(String key, BigDecimal defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseBigDecimal(value);
	}
	
	@Override
	public <T extends Number> T getAsNumber(String key, Class<T> numberClass) {
		Object value = getValue(key);
		return parseNumber(value, numberClass);
	}

	@Override
	public <T extends Number> T getAsNumber(String key, Class<T> numberClass, T defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseNumber(value, numberClass);
	}

	public boolean getAsBoolean(String key) {
		Object value = getValue(key);
		return parseBoolean(value);
	}
	
	public boolean getAsBoolean(String key, boolean defaultValue) {
		Object value = getValue(key, Boolean.valueOf(defaultValue));
		return parseBoolean(value);
	}

	@Override
	public OffsetDateTime getAsOffsetDateTime(String key) {
		Object value = getValue(key);
		return parseOffsetDateTime(value);
	}

	@Override
	public OffsetDateTime getAsOffsetDateTime(String key, OffsetDateTime defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseOffsetDateTime(value);
	}

	@Override
	public LocalDate getAsLocalDate(String key) {
		Object value = getValue(key);
		return parseLocalDate(value);
	}

	@Override
	public LocalDate getAsLocalDate(String key, LocalDate defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseLocalDate(value);
	}

	@Override
	public OffsetTime getAsOffsetTime(String key) {
		Object value = getValue(key);
		return parseOffsetTime(value);
	}

	@Override
	public OffsetTime getAsOffsetTime(String key, OffsetTime defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseOffsetTime(value);
	}

	@Override
	public <T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass) {
		Object value = getValue(key);
		return parseTemporal(value, temporalClass);
	}

	@Override
	public <T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass, T defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseTemporal(value, temporalClass);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		Object value = getValue(key);
		return parseEnum(enumType, value);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseEnum(enumType, value);
	}

	@Override
	public byte[] getAsBinary(String key) {
		Object value = getValue(key);
		return parseBinary(value);
	}

	@Override
	public byte[] getAsBinary(String key, byte[] defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseBinary(value);
	}

	public Object[] getAsArray(String key) {
		Object value = getValue(key);
		return parseArray(value);
	}
	
	public Object[] getAsArray(String key, Object[] defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseArray(value);
	}

	public String[] getAsStringArray(String key) {
		Object value = getValue(key);
		return parseStringArray(value, isUseStringSyntax());
	}
	
	public String[] getAsStringArray(String key, String[] defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseStringArray(value, isUseStringSyntax());
	}

	@Override
	public Map<String, Object> getAsMap(String key) {
		Object value = getValue(key);
		return parseMap(value);
	}

	@Override
	public Map<String, Object> getAsMap(String key, Map<String, Object> defaultValue) {
		Object value = getValue(key, defaultValue);
		return parseMap(value);
	}
	
	public Object setVslue(String key, Object value) {
		if (supportsObjectValue()) {
			return handleSetValue(key, value);
		} else {
			return handleSetValue(key, toString(value, isUseStringSyntax()));
		}
	}
	
	public Object set(String key, String value) {
		return setVslue(key, value);
	}
	
	public Object set(String key, int value) {
		return setVslue(key, Integer.valueOf(value));
	}
	
	public Object set(String key, long value) {
		return setVslue(key, Long.valueOf(value));
	}
	
	public Object set(String key, double value) {
		return setVslue(key, Double.valueOf(value));
	}
	
	public Object set(String key, Number value) {
		return setVslue(key, value);
	}
	
	public Object set(String key, boolean value) {
		return setVslue(key, Boolean.valueOf(value));
	}
	
	public Object set(String key, TemporalAccessor value) {
		return setVslue(key, value);
	}
	
	public Object set(String key, Enum<?> value) {
		return setVslue(key, value);
	}
	
	@Override
	public Object set(String key, byte... value) {
		return setVslue(key, value);
	}

	public Object set(String key, String... value) {
		return setVslue(key, value);
	}

	@Override
	public Object set(String key, Map<String, Object> value) {
		return setVslue(key, value);
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
		storeToAppConfig(this, configName, appName, groupName, comments);
	}

	protected static Object parse(String value, boolean cast) {
		StringTokenParser parser = new StringTokenParser(value)
				.setEscape(ESCAPE_CHAR)
				.setBlockquote(BLOCKQUOTE_SETS, true, false)
				.setTrim(true);
		return readValue(parser, cast);
	}

	protected static Object readValue(StringTokenParser parser, boolean cast, String... terminators) {
		
		parser.skipWhitespaces();
		
		if (parser.isNext(ARRAY_PREFIX)) {
			parser.offset(1);
			List<Object> arrayList = new ArrayList<>();
			StringToken token;
			do {
				Object elementValue = readValue(parser, cast, MULTIPLE_VALUES_SEPARATOR, ARRAY_SUFFIX);
				arrayList.add(elementValue);
				token = parser.getLastToken();
			} while (MULTIPLE_VALUES_SEPARATOR.equals(token.getTerminator()));
			return arrayList.toArray(new Object[arrayList.size()]);
		} else if (parser.isNext(MAP_PREFIX)) {
			parser.offset(1);
			Map<String, Object> map = new LinkedHashMap<>();
			StringToken valueToken;
			do {
				String mapKey = (String)readValue(parser, false, MAP_KEY_VALUE_SEPARATOR, MAP_SUFFIX);
				if (parser.getLastToken().isEos()) {
					map.put(mapKey, null);
					break;
				}
				
				Object mapValue = readValue(parser, cast, MULTIPLE_VALUES_SEPARATOR, MAP_SUFFIX);
				map.put(mapKey, mapValue);
				valueToken = parser.getLastToken();
			} while (MULTIPLE_VALUES_SEPARATOR.equals(valueToken.getTerminator()));
			return map;
		} else {
			StringToken token = parser.nextToken(terminators);
			String literalValue = token.getToken();

			if (cast) {
				return parseLiteralObject(literalValue);
			} else {
				return parseString(literalValue);
			}
		}
	}
	
	/**
	 * Null null
	 * Boolean true/false
	 * Number BigInteger/BigDecimal
	 * DateTime OffsetDateTime/LocalDate/OffsetTime
	 * String ""
	 * Binary &lt;&gt;
	 * @param value an object value
	 * @return parsed value
	 */
	protected static Object parseLiteralObject(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof String) {
			String v = (String)value;
			
			try {
				if (v.isEmpty()) {
					return v;
				} if (StringUtilz.surroundsWith(v, STRING_PREFIX, STRING_SUFFIX)) {
					return parseString(v);
				} else if (StringUtilz.surroundsWith(v, BINARY_PREFIX, BINARY_SUFFIX)) {
					return parseBinary(v);
				} else {
					String unescapedValue = StringUtilz.unescape(v, ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
					try {
						if (unescapedValue.equals("null")) {
							return null;
						} else if (unescapedValue.equals("true")) {
							return Boolean.TRUE;
						} else if (unescapedValue.equals("false")) {
							return Boolean.FALSE;
						} else {
							char firstCh = unescapedValue.charAt(0);
							if ((('0' <= firstCh && firstCh <= '9') || firstCh == '+' || firstCh == '-')) {
								if (unescapedValue.indexOf('T') != -1 && DATETIME_VALUE_PATTERN.matcher(unescapedValue).matches()) {
									return parseOffsetDateTime(unescapedValue);
								} else if (unescapedValue.indexOf(':') != -1 && TIME_VALUE_PATTERN.matcher(unescapedValue).matches()) {
									return parseOffsetTime(unescapedValue);
								} else if (unescapedValue.indexOf('-') != -1 && DATE_VALUE_PATTERN.matcher(unescapedValue).matches()) {
									return parseLocalDate(unescapedValue);
								} else if (NUMBER_VALUE_PATTERN.matcher(unescapedValue).matches()) {
									if (unescapedValue.indexOf('.') == -1) {
										return parseBigInteger(unescapedValue);
									} else {
										return parseBigDecimal(unescapedValue);
									}
								}
							}
						}
					} catch (Exception e) {
						return unescapedValue;
					}
					
					return unescapedValue;
				}
			} catch (Exception e) {
				return value;
			}
		}
		
		return value;
	}
	
	protected static String parseString(Object value) {
		if (value == null) {
			return null;
		}
		
		if (value instanceof String) {
			return parseString((String)value);
		}
		
		return toString(value, true);
	}
	
	protected static String parseString(String value) {
		if (value == null) {
			return null;
		}

		if (StringUtilz.surroundsWith(value, STRING_PREFIX, STRING_SUFFIX)) {
			StringToken token = new StringTokenParser(value)
					.setEscape(ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS)
					.setTrim(false)
					.seek(1)
					.nextToken(STRING_SUFFIX);
			if (token.isEos()) {
				return token.getToken();
			} else {
				return value;
			}
		} else {
			return StringUtilz.unescape(value, ESCAPE_CHAR, ESCAPE_TARGET_CHARS, ESCAPED_CHARS);
		}
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
	
	protected static BigInteger parseBigInteger(Object value) {
		try {
			return NumberUtilz.toBigInteger(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static BigDecimal parseBigDecimal(Object value) {
		try {
			return NumberUtilz.toBigDecimal(value);
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
				return NumberUtilz.castNumber(new BigDecimal(Objects.toString(value)), numberClass);
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
	
	protected static OffsetDateTime parseOffsetDateTime(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof OffsetDateTime) {
				return (OffsetDateTime)value;
			}
			
			return OffsetDateTime.parse(Objects.toString(value));
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static LocalDate parseLocalDate(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof LocalDate) {
				return (LocalDate)value;
			}
			
			return LocalDate.parse(Objects.toString(value));
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static OffsetTime parseOffsetTime(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof OffsetTime) {
				return (OffsetTime)value;
			}
			
			return OffsetTime.parse(Objects.toString(value));
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
				return temporalClass.cast(value);
			}
			
			Method parseMethod = temporalClass.getMethod("parse", CharSequence.class);
			return temporalClass.cast(parseMethod.invoke(null, Objects.toString(value)));
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
				return enumType.cast(value);
			}
			
			String name = Objects.toString(value);
			
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
			}
			
			if (value instanceof Number) {
				return NumberUtilz.toByteArray((Number)value);
			} else if (value instanceof Boolean) {
				return new byte[]{(byte)(((Boolean)value).booleanValue() ? 1 : 0)};
			} else {
				String v = Objects.toString(value);
				if (StringUtilz.surroundsWith(v, BINARY_PREFIX, BINARY_SUFFIX)) {
					v = v.substring(1, v.length() - 1);
				}
				return BinaryUtilz.parseHexStr(v);
			}
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static Object[] parseArray(Object value) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof Object[]) {
				return (Object[])value;
			}
			
			if (value instanceof String) {
				String v = (String)value;
				
				int sIdx;
				String[] terminators;
				if (StringUtilz.surroundsWith(v, ARRAY_PREFIX, ARRAY_SUFFIX)) {
					sIdx = 1;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR, ARRAY_SUFFIX};
				} else {
					sIdx = 0;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR};
				}
				
				StringTokenParser parser = new StringTokenParser(v)
						.setEscape(ESCAPE_CHAR)
						.setBlockquote(BLOCKQUOTE_SETS, true, false)
						.setTrim(true)
						.seek(sIdx);
				
				List<Object> arrayList = new ArrayList<>();
				StringToken token;
				do {
					Object elementValue = readValue(parser, true, terminators);
					arrayList.add(elementValue);
					token = parser.getLastToken();
				} while (MULTIPLE_VALUES_SEPARATOR.equals(token.getTerminator()));
				
				if (!token.isEos()) {
					// NOP
				}
				
				return arrayList.toArray(new Object[arrayList.size()]);
			} else {
				return new Object[]{value};
			}
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static String[] parseStringArray(Object value, boolean useStringSyntax) {
		try {
			if (value == null) {
				return null;
			}
			
			if (value instanceof String[]) {
				return (String[])value;
			}
			
			if (value instanceof String) {
				String v = (String)value;
				
				int sIdx;
				String[] terminators;
				if (StringUtilz.surroundsWith(v, ARRAY_PREFIX, ARRAY_SUFFIX)) {
					sIdx = 1;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR, ARRAY_SUFFIX};
				} else {
					sIdx = 0;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR};
				}
				
				StringTokenParser parser = new StringTokenParser(v)
						.setEscape(ESCAPE_CHAR)
						.setBlockquote(BLOCKQUOTE_SETS, true, false)
						.setTrim(true)
						.seek(sIdx);
				
				List<String> arrayList = new ArrayList<>();
				StringToken token;
				do {
					String elementValue = (String)readValue(parser, false, terminators);
					arrayList.add(elementValue);
					token = parser.getLastToken();
				} while (MULTIPLE_VALUES_SEPARATOR.equals(token.getTerminator()));

				if (!token.isEos()) {
					// NOP
				}
				
				return arrayList.toArray(new String[arrayList.size()]);
			} else {
				return new String[]{toString(value, useStringSyntax)};
			}
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
			
			if (value instanceof String) {
				String v = (String)value;
				
				int sIdx;
				String[] terminators;
				if (StringUtilz.surroundsWith(v, MAP_PREFIX, MAP_SUFFIX)) {
					sIdx = 1;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR, MAP_SUFFIX};
				} else {
					sIdx = 0;
					terminators = new String[]{MULTIPLE_VALUES_SEPARATOR};
				}
				
				StringTokenParser parser = new StringTokenParser(v)
						.setEscape(ESCAPE_CHAR)
						.setBlockquote(BLOCKQUOTE_SETS, true, false)
						.setTrim(true)
						.seek(sIdx);
				
				Map<String, Object> map = new LinkedHashMap<>();
				StringToken valueToken;
				do {
					String mapKey = (String)readValue(parser, false, MAP_KEY_VALUE_SEPARATOR, MAP_SUFFIX);
					if (parser.getLastToken().isEos()) {
						map.put(mapKey, null);
						break;
					}
					
					Object mapValue = readValue(parser, true, terminators);
					map.put(mapKey, mapValue);
					valueToken = parser.getLastToken();
				} while (MULTIPLE_VALUES_SEPARATOR.equals(valueToken.getTerminator()));

				if (!parser.getLastToken().isEos()) {
					// NOP
				}
				
				return map;
			} else {
				return new LinkedHashMap<String, Object>() {
					{
						put("", value);
					}
				};
			}
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}

	private static void storeToAppConfig(Config config, String configName, String appName, String groupName, String comments) throws IOException {
		Path filePath = FileUtilz.getPath(EnvUtilz.getConfigDir(), groupName, appName, configName);
		
		Files.createDirectories(filePath.getParent());
		
		Properties properties;
		if (config instanceof PropertiesConfig) {
			properties = ((PropertiesConfig)config).getProperties();
		} else {
			Map<String, Object> map = config.asMap();
			properties = new OrderedProperties();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				properties.setProperty(entry.getKey(), toString(entry.getValue(), true));
			}
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(filePath, Charset.forName("UTF-8"))) {
			properties.store(bw, comments);
		}
	}

	protected static String toString(Object value, boolean useStringSyntax) {
		return toString(new StringBuilder(), value, useStringSyntax).toString();
	}
	
	protected static StringBuilder toString(StringBuilder sb, Object value, boolean useStringSyntax, char... escapeTargetChars) {
		if (useStringSyntax) {
			if (value == null) {
				sb.append("null");
			} else if (value instanceof String) {
				String v = (String)value;
				if (needsStringEscaping(v)) {
					char[] escapeTargetChars2 = Arrays.copyOf(escapeTargetChars, escapeTargetChars.length + 2);
					escapeTargetChars2[escapeTargetChars2.length - 2] = STRING_PREFIX_CHAR;
					escapeTargetChars2[escapeTargetChars2.length - 1] = STRING_SUFFIX_CHAR;

					sb.append(STRING_PREFIX);
					StringUtilz.escape(sb, v, ESCAPE_CHAR, escapeTargetChars2);
					sb.append(STRING_SUFFIX);
				} else {
					StringUtilz.escape(sb, v, ESCAPE_CHAR, escapeTargetChars);
				}
			} else if (value instanceof Enum) {
				sb.append(((Enum<?>)value).name());
			} else if (value instanceof byte[]) {
				byte[] binary = (byte[])value;
				sb.append(BINARY_PREFIX);
				sb.append(BinaryUtilz.toHexStr(binary));
				sb.append(BINARY_SUFFIX);
			} else if (value.getClass().isArray()) {
				Object[] array = (Object[])value;
				sb.append(ARRAY_PREFIX);
				for (int i = 0; i < array.length; i++) {
					if (i != 0) {
						sb.append(MULTIPLE_VALUES_SEPARATOR_CHAR);
					}
					Object v = array[i];
					toString(sb, v, useStringSyntax, ARRAY_ESCAPE_TARGET_CHARS);
				}
				sb.append(ARRAY_SUFFIX);
			} else if (value instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)value;
				sb.append(MAP_PREFIX);
				boolean first = true;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (!first) {
						sb.append(MULTIPLE_VALUES_SEPARATOR_CHAR);
					}
					first = false;
					
					String k = entry.getKey();
					Object v = entry.getValue();
					
					StringUtilz.escape(sb, k, ESCAPE_CHAR, MAP_KEY_ESCAPE_TARGET_CHARS);
					sb.append(MAP_KEY_VALUE_SEPARATOR_CHAR);
					toString(sb, v, useStringSyntax, MAP_VALUE_ESCAPE_TARGET_CHARS);
				}
				sb.append(MAP_SUFFIX);
			} else {
				String v = value.toString();
				StringUtilz.escape(sb, v, ESCAPE_CHAR, escapeTargetChars);
			}
		} else {
			if (value == null) {
				// NOP
			} else {
				sb.append(value);
			}
		}
		
		return sb;
	}
	
	private static boolean needsStringEscaping(String value) {
		if (value.isEmpty()) {
			return false;
		} if (StringUtilz.surroundsWith(value, STRING_PREFIX, STRING_SUFFIX)) {
			return true;
		} else if (StringUtilz.surroundsWith(value, BINARY_PREFIX, BINARY_SUFFIX)) {
			return true;
		} else if (value.equals("null")) {
			return true;
		} else if (value.equals("true")) {
			return true;
		} else if (value.equals("false")) {
			return true;
		} else {
			char firstCh = value.charAt(0);
			if ((('0' <= firstCh && firstCh <= '9') || firstCh == '+' || firstCh == '-')) {
				if (value.indexOf('T') != -1 && DATETIME_VALUE_PATTERN.matcher(value).matches()) {
					return true;
				} else if (value.indexOf(':') != -1 && TIME_VALUE_PATTERN.matcher(value).matches()) {
					return true;
				} else if (value.indexOf('-') != -1 && DATE_VALUE_PATTERN.matcher(value).matches()) {
					return true;
				} else if (NUMBER_VALUE_PATTERN.matcher(value).matches()) {
					return true;
				}
			}
		}
		
		return false;
	}
}
