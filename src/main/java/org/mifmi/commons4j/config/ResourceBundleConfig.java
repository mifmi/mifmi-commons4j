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
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceBundleConfig extends AbstractConfig {
	
	private ResourceBundle resourceBundle;
	
	public ResourceBundleConfig(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}
	
	public ResourceBundleConfig(String baseName) {
		this(baseName, (Enumeration<Locale>)null);
	}
	
	public ResourceBundleConfig(String baseName, Locale... locales) {
		this(baseName, (locales == null) ? null : Arrays.asList(locales));
	}
	
	public ResourceBundleConfig(String baseName, List<Locale> locales) {
		this(baseName, (locales == null) ? null : Collections.enumeration(locales));
	}
	
	public ResourceBundleConfig(String baseName, Enumeration<Locale> locales) {
		this.resourceBundle = ResourceBundle.getBundle(baseName, new MultiLocaleResourceBundleControl(locales));
	}

	@Override
	protected boolean supportsObjectValue() {
		return false;
	}
	
	@Override
	protected Object handleGetValue(String key) {
		try {
			return this.resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}
	
	@Override
	protected Object handleSetValue(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object handleRemoveValue(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean handleContainsKey(String key) {
		return this.resourceBundle.containsKey(key);
	}
	
	@Override
	protected Set<String> handleGetKeySet() {
		return this.resourceBundle.keySet();
	}

	@Override
	protected int handleGetSize() {
		return this.resourceBundle.keySet().size();
	}

	public ResourceBundle getBundle() {
		return this.resourceBundle;
	}
	
	public Locale getLocale() {
		return this.resourceBundle.getLocale();
	}
}
