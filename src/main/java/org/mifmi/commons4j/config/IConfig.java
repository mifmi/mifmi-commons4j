/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IConfig {

	String get(String key);
	
	String get(String key, String defaultValue);
	
	int getAsInt(String key);
	
	int getAsInt(String key, int defaultValue);
	
	long getAsLong(String key);
	
	long getAsLong(String key, int defaultValue);
	
	boolean getAsBoolean(String key);
	
	boolean getAsBoolean(String key, boolean defaultValue);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType);
	
	<T extends Enum<T>> T getEnum(String key, Class<T> enumType, T defaultValue);
	
	String[] getAsArray(String key);
	
	String[] getAsArray(String key, String[] defaultValue);
	
	List<String> getAsList(String key);
	
	List<String> getAsList(String key, List<String> defaultValue);
	
	String set(String key, String value);
	
	String set(String key, int value);
	
	String set(String key, long value);
	
	String set(String key, double value);
	
	String set(String key, boolean value);
	
	String set(String key, Enum<?> value);
	
	String set(String key, String[] values);
	
	String set(String key, List<String> values);
	
	String remove(String key);
	
	boolean containsKey(String key);
	
	Set<String> keySet();
	
	Enumeration<String> getKeys();
	
	Map<String, String> asMap();
	
	int size();
	
	boolean isEmpty();
}
