/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.util.Map;
import java.util.Set;

public class MapConfig extends Config {
	
	private Map<String, String> map;
	
	public MapConfig(Map<String, String> map) {
		this.map = map;
	}

	@Override
	protected String handleGetValue(String key, String defaultValue) {
		return this.map.getOrDefault(key, defaultValue);
	}
	
	@Override
	protected String handleSetValue(String key, String value) {
		return this.map.put(key, value);
	}

	@Override
	protected String handleRemoveValue(String key) {
		return this.map.remove(key);
	}

	@Override
	protected boolean handleContainsKey(String key) {
		return this.map.containsKey(key);
	}

	@Override
	protected Set<String> handleGetKeySet() {
		return this.map.keySet();
	}

	@Override
	protected int handleGetSize() {
		return this.map.size();
	}

}
