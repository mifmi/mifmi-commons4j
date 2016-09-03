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
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

public interface Config {
	static Config from(Map<String, Object> map) {
		return new MapConfig(map);
	}
	
	static Config from(Properties properties) {
		return new PropertiesConfig(properties);
	}

	static Config from(ResourceBundle resourceBundle) {
		return new ResourceBundleConfig(resourceBundle);
	}

	static Config loadFromAppConfig(String configName, String appName, String groupName) throws InvalidPropertiesFormatException, IOException {
		Path filePath = buildAppConfigFilePath(configName, appName, groupName);
		if (Files.exists(filePath)) {
			// File exists
			try (BufferedReader br = Files.newBufferedReader(filePath, Charset.forName("UTF-8"))) {
				return new PropertiesConfig(br);
			}
		} else {
			// File no exists
			return new PropertiesConfig(new OrderedProperties());
		}
	}

	static void storeToAppConfig(Config config, String configName, String appName, String groupName, String comments) throws IOException {
		Path filePath = buildAppConfigFilePath(configName, appName, groupName);
		
		Files.createDirectories(filePath.getParent());
		
		Properties properties;
		if (config instanceof PropertiesConfig) {
			properties = ((PropertiesConfig)config).getProperties();
		} else {
			Map<String, Object> map = config.asMap();
			properties = new OrderedProperties();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				properties.setProperty(entry.getKey(), Objects.toString(entry.getValue()));
			}
		}
		
		try (BufferedWriter bw = Files.newBufferedWriter(filePath, Charset.forName("UTF-8"))) {
			properties.store(bw, comments);
		}
	}
	
	
	static Path buildAppConfigFilePath(String configName, String appName, String groupName) {
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
	
	String get(String key);
	
	String get(String key, String defaultValue);
	
	int getAsInt(String key);
	
	int getAsInt(String key, int defaultValue);
	
	long getAsLong(String key);
	
	long getAsLong(String key, long defaultValue);
	
	double getAsDouble(String key);
	
	double getAsDouble(String key, double defaultValue);
	
	<T extends Number> T getAsNumber(String key, Class<T> numberClass);
	
	<T extends Number> T getAsNumber(String key, Class<T> numberClass, T defaultValue);
	
	boolean getAsBoolean(String key);
	
	boolean getAsBoolean(String key, boolean defaultValue);
	
	OffsetDateTime getAsOffsetDateTime(String key);
	
	OffsetDateTime getAsOffsetDateTime(String key, OffsetDateTime defaultValue);
	
	<T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass);
	
	<T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass, T defaultValue);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue);
	
	byte[] getAsBinary(String key);
	
	byte[] getAsBinary(String key, byte[] defaultValue);
	
	String[] getAsArray(String key);
	
	String[] getAsArray(String key, String[] defaultValue);
	
	Map<String, Object> getAsMap(String key);

	Map<String, Object> getAsMap(String key, Map<String, Object> defaultValue);
	
	Object set(String key, String value);
	
	Object set(String key, int value);
	
	Object set(String key, long value);
	
	Object set(String key, double value);
	
	Object set(String key, Number value);
	
	Object set(String key, boolean value);
	
	Object set(String key, TemporalAccessor value);
	
	Object set(String key, Enum<?> value);

	Object set(String key, byte... value);
	
	Object set(String key, String... value);
	
	Object set(String key, Map<String, Object> value);
	
	Object remove(String key);
	
	boolean containsKey(String key);
	
	Set<String> keySet();
	
	Enumeration<String> getKeys();
	
	Map<String, Object> asMap();
	
	int size();
	
	boolean isEmpty();
	
	void storeToAppConfig(String configName, String appName, String groupName, String comments) throws IOException;
}
