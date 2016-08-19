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

public interface Config {

	String get(String key);
	
	String get(String key, String defaultValue);
	
	int getAsInt(String key);
	
	int getAsInt(String key, int defaultValue);
	
	long getAsLong(String key);
	
	long getAsLong(String key, int defaultValue);
	
	boolean getAsBoolean(String key);
	
	boolean getAsBoolean(String key, boolean defaultValue);
	
	String[] getAsArray(String key);
	
	String[] getAsArray(String key, String[] defaultValue);
	
	List<String> getAsList(String key);
	
	List<String> getAsList(String key, List<String> defaultValue);
	
	boolean has(String key);
	
	Set<String> keySet();
	
	Enumeration<String> getKeys();
	
	Map<String, String> asMap();
}
