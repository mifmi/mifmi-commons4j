/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mifmi.commons4j.util.StringUtilz;

public abstract class AbstractConfig implements Config {
	
	private Map<String, String> configMap = null;

	protected abstract String handleGetValue(String key, String defaultValue);
	
	protected abstract boolean handleHasKey(String key);
	
	protected abstract Set<String> handleGetKeySet();

	public String get(String key) {
		String value = handleGetValue(key, null);
		if (value == null) {
			throw new ConfigNotFoundException("Config not found: key=\"" + key + "\"");
		}
		return value;
	}
	
	public String get(String key, String defaultValue) {
		return handleGetValue(key, defaultValue);
	}
	
	public int getAsInt(String key) {
		String value = get(key);
		return parseInt(value);
	}
	
	public int getAsInt(String key, int defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseInt(value);
	}
	
	public long getAsLong(String key) {
		String value = get(key);
		return parseLong(value);
	}
	
	public long getAsLong(String key, int defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseLong(value);
	}
	
	public boolean getAsBoolean(String key) {
		String value = get(key);
		return parseBoolean(value);
	}
	
	public boolean getAsBoolean(String key, boolean defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseBoolean(value);
	}
	
	public String[] getAsArray(String key) {
		String value = get(key);
		return parseArray(value);
	}
	
	public String[] getAsArray(String key, String[] defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseArray(value);
	}
	
	public List<String> getAsList(String key) {
		String value = get(key);
		return parseList(value);
	}
	
	public List<String> getAsList(String key, List<String> defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseList(value);
	}
	
	public boolean has(String key) {
		return handleHasKey(key);
	}
	
	public Set<String> keySet() {
		return handleGetKeySet();
	}
	
	public Enumeration<String> getKeys() {
		return Collections.enumeration(handleGetKeySet());
	}
	
	public Map<String, String> asMap() {
		if (this.configMap == null) {
			Set<String> keySet = handleGetKeySet();
			
			Map<String, String> map = new LinkedHashMap<String, String>(keySet.size());
			for (String key : keySet) {
				String value = handleGetValue(key, null);
				map.put(key, value);
			}
			this.configMap = map;
		}
		
		return this.configMap;
	}
	
	protected static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static long parseLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static boolean parseBoolean(String value) {
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static String[] parseArray(String value) {
		try {
			return StringUtilz.split(value, ",", true);
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
	
	protected static List<String> parseList(String value) {
		try {
			return Arrays.asList(parseArray(value));
		} catch (Exception e) {
			throw new ConfigParseException(e);
		}
	}
}
