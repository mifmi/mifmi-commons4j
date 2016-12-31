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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.temporal.TemporalAccessor;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.mifmi.commons4j.io.file.FileUtilz;
import org.mifmi.commons4j.util.EnvUtilz;

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
		Path filePath = FileUtilz.getPath(EnvUtilz.getConfigDir(), groupName, appName, configName);
		Config config;
		if (Files.exists(filePath)) {
			// File exists
			try (BufferedReader br = Files.newBufferedReader(filePath, Charset.forName("UTF-8"))) {
				config = new PropertiesConfig(br);
			}
		} else {
			// File no exists
			config = new PropertiesConfig(new OrderedProperties());
		}
		config.setUseStringSyntax(true);
		return config;
	}
	
	boolean isUseStringSyntax();
	
	void setUseStringSyntax(boolean useStringSyntax);
	
	<T> T get(String key);
	
	<T> T get(String key, T defaultValue);
	
	String getAsString(String key);
	
	String getAsString(String key, String defaultValue);
	
	int getAsInt(String key);
	
	int getAsInt(String key, int defaultValue);
	
	long getAsLong(String key);
	
	long getAsLong(String key, long defaultValue);
	
	double getAsDouble(String key);
	
	double getAsDouble(String key, double defaultValue);
	
	BigInteger getAsBigInteger(String key);
	
	BigInteger getAsBigInteger(String key, BigInteger defaultValue);
	
	BigDecimal getAsBigDecimal(String key);
	
	BigDecimal getAsBigDecimal(String key, BigDecimal defaultValue);
	
	<T extends Number> T getAsNumber(String key, Class<T> numberClass);
	
	<T extends Number> T getAsNumber(String key, Class<T> numberClass, T defaultValue);
	
	boolean getAsBoolean(String key);
	
	boolean getAsBoolean(String key, boolean defaultValue);
	
	OffsetDateTime getAsOffsetDateTime(String key);
	
	OffsetDateTime getAsOffsetDateTime(String key, OffsetDateTime defaultValue);
	
	OffsetTime getAsOffsetTime(String key);
	
	OffsetTime getAsOffsetTime(String key, OffsetTime defaultValue);

	LocalDate getAsLocalDate(String key);
	
	LocalDate getAsLocalDate(String key, LocalDate defaultValue);
	
	<T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass);
	
	<T extends TemporalAccessor> T getAsTemporal(String key, Class<T> temporalClass, T defaultValue);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue);
	
	byte[] getAsBinary(String key);
	
	byte[] getAsBinary(String key, byte[] defaultValue);
	
	Object[] getAsArray(String key);
	
	Object[] getAsArray(String key, Object[] defaultValue);
	
	String[] getAsStringArray(String key);
	
	String[] getAsStringArray(String key, String[] defaultValue);
	
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
