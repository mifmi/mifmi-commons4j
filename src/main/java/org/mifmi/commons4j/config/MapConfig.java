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

public class MapConfig extends AbstractConfig {
	
	private Map<String, Object> map;
	
	public MapConfig(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	protected boolean supportsObjectValue() {
		return true;
	}

	@Override
	protected Object handleGetValue(String key) {
		return this.map.get(key);
	}
	
	@Override
	protected Object handleSetValue(String key, Object value) {
		return this.map.put(key, value);
	}

	@Override
	protected Object handleRemoveValue(String key) {
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
