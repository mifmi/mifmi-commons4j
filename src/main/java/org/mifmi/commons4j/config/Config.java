/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.mifmi.commons4j.util.StringUtilz;

public abstract class Config implements IConfig {
	
	private static final String MULTIPLE_VALUES_SEPARATOR = ",";
	
	private static final Charset APP_CONFIG_CHARSET = Charset.forName("UTF-8");
	
	protected abstract String handleGetValue(String key, String defaultValue);

	protected abstract String handleSetValue(String key, String value);
	
	protected abstract String handleRemoveValue(String key);
	
	protected abstract boolean handleContainsKey(String key);
	
	protected abstract Set<String> handleGetKeySet();
	
	protected abstract int handleGetSize();
	
	public static Config from(Map<String, String> map) {
		return new MapConfig(map);
	}
	
	public static Config from(Properties properties) {
		return new PropertiesConfig(properties);
	}

	public static Config from(ResourceBundle resourceBundle) {
		return new ResourceBundleConfig(resourceBundle);
	}
	
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

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType) {
		String value = get(key);
		return parseEnum(enumType, value);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue) {
		String value = get(key, null);
		if (value == null) {
			return defaultValue;
		}
		return parseEnum(enumType, value);
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
	
	public String set(String key, String value) {
		if (value == null) {
			value = "";
		}
		
		return handleSetValue(key, value);
	}
	
	public String set(String key, int value) {
		return set(key, Integer.toString(value));
	}
	
	public String set(String key, long value) {
		return set(key, Long.toString(value));
	}
	
	public String set(String key, double value) {
		return set(key, Double.toString(value));
	}
	
	public String set(String key, boolean value) {
		return set(key, Boolean.toString(value));
	}
	
	public String set(String key, Enum<?> value) {
		String strValue = null;
		if (value != null) {
			strValue = value.name();
		}
		return set(key, strValue);
	}
	
	public String set(String key, String[] values) {
		String strValue = null;
		if (values != null) {
			strValue = StringUtilz.join(MULTIPLE_VALUES_SEPARATOR, (Object[])values);
		}
		return set(key, strValue);
	}
	
	public String set(String key, List<String> values) {
		String strValue = null;
		if (values != null) {
			strValue = StringUtilz.join(MULTIPLE_VALUES_SEPARATOR, values);
		}
		return set(key, strValue);
	}
	
	public String remove(String key) {
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
	
	public Map<String, String> asMap() {
		Set<String> keySet = handleGetKeySet();
		
		Map<String, String> map = new LinkedHashMap<String, String>(keySet.size());
		for (String key : keySet) {
			String value = handleGetValue(key, null);
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

	public static Config loadFromAppConfig(String configName, String appName, String groupName) throws InvalidPropertiesFormatException, IOException {
		Path filePath = buildAppConfigFilePath(configName, appName, groupName);
		if (Files.exists(filePath)) {
			// File exists
			try (BufferedReader br = Files.newBufferedReader(filePath, APP_CONFIG_CHARSET)) {
				return new PropertiesConfig(br);
			}
		} else {
			// File no exists
			return new PropertiesConfig(new OrderedProperties());
		}
	}

	public void storeToAppConfig(String configName, String appName, String groupName, String comments) throws IOException {
		Path filePath = buildAppConfigFilePath(configName, appName, groupName);
		
		Files.createDirectories(filePath.getParent());
		
		Properties properties;
		if (this instanceof PropertiesConfig) {
			properties = ((PropertiesConfig)this).getProperties();
		} else {
			Map<String, String> map = this.asMap();
			properties = new OrderedProperties();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				properties.setProperty(entry.getKey(), entry.getValue());
			}
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(filePath, APP_CONFIG_CHARSET)) {
			properties.store(bw, comments);
		}
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
	
	public <T extends Enum<T>> T parseEnum(Class<T> enumType, String value) {
		if (value.isEmpty()) {
			return null;
		}
		return Enum.valueOf(enumType, value);
	}
	
	protected static String[] parseArray(String value) {
		try {
			return StringUtilz.split(value, MULTIPLE_VALUES_SEPARATOR, true);
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

	
	private static Path buildAppConfigFilePath(String configName, String appName, String groupName) {
		String osName = System.getProperty("os.name").toLowerCase();

		Path filePath;
		if (osName.startsWith("windows")) {
			String roamingAppData = System.getenv("AppData");
			if (roamingAppData != null && !roamingAppData.isEmpty()) {
				// C:/Users/username/AppData/Local
				// C:/Users/username/AppData/Roaming
				// C:/Documents and Settings/username/Local Settings/Application Data
				// C:/Documents and Settings/username/Application Data
				filePath = Paths.get(roamingAppData);
			} else {
				filePath = Paths.get(System.getProperty("user.home"), "Application Data");
			}
		} else if (osName.startsWith("mac")) {
			// /Users/username/Library/Preferences
			filePath = Paths.get(System.getProperty("user.home"), "Library", "Preferences");
		} else {
			filePath = null;
		}
		
		if (filePath == null || !Files.exists(filePath)) {
			// /Users/username/.config/
			filePath = Paths.get(System.getProperty("user.home"), ".config");
		}
		
		if (groupName == null) {
			if (appName == null) {
				filePath = filePath.resolve(Paths.get(configName));
			} else {
				filePath = filePath.resolve(Paths.get(appName, configName));
			}
		} else {
			if (appName == null) {
				filePath = filePath.resolve(Paths.get(groupName, configName));
			} else {
				filePath = filePath.resolve(Paths.get(groupName, appName, configName));
			}
		}
		
		return filePath;
	}
}
