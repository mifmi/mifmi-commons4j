/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.logic;

import java.util.HashMap;
import java.util.Map;

public class LogicContext {

	private Map<String, Object> attributeMap;
	
	public LogicContext() {
		this(null);
	}
	
	public LogicContext(Map<String, Object> attributeMap) {
		if (attributeMap == null) {
			this.attributeMap = new HashMap<String, Object>();
		} else {
			this.attributeMap = new HashMap<String, Object>(attributeMap);
		}
	}
	
	public <T> T getAttribute(String key) {
		@SuppressWarnings("unchecked")
		T attribute = (T)this.attributeMap.get(key);
		return attribute;
	}
	
	public void setAttribute(String key, Object value) {
		this.attributeMap.put(key, value);
	}
	
	public void removeAttribute(String key) {
		this.attributeMap.remove(key);
	}
	
	public boolean hasAttribute(String key) {
		return this.attributeMap.containsKey(key);
	}
}
