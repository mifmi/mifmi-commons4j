/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceBundleConfig extends Config {
	
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
		final List<Locale> candidateLocales = new ArrayList<Locale>();
		if (locales != null) {
			while (locales.hasMoreElements()) {
				Locale locale = locales.nextElement();
				if (locale == null) {
					continue;
				}
				
				String language = locale.getLanguage();
				String country = locale.getCountry();
				String variant = locale.getVariant();
				
				if (!country.isEmpty()) {
					if (!variant.isEmpty()) {
						candidateLocales.add(new Locale(language, country, variant));
					}
					candidateLocales.add(new Locale(language, country));
				}
				candidateLocales.add(new Locale(language));
			}
		}
		candidateLocales.add(Locale.ROOT);
		
		this.resourceBundle = ResourceBundle.getBundle(
				baseName,
				new ResourceBundle.Control() {
					@Override
					public List<Locale> getCandidateLocales(String baseName, Locale locale) {
						return candidateLocales;
					}
					
					@Override
					public Locale getFallbackLocale(String baseName, Locale locale) {
						return null;
					}
				});
	}

	/* // begin temporary code
	public ResourceBundleConfig(String baseName, Enumeration<Locale> locales) {
		
		if (locales != null) {
			while (locales.hasMoreElements()) {
				Locale locale = locales.nextElement();
				ResourceBundle rb = ResourceBundle.getBundle(baseName, locale);
				if (rb != null) {
					Locale bundleLocale = rb.getLocale();
					if (bundleLocale.getLanguage().equals(locale.getLanguage())) {
						this.resourceBundle = rb;
						break;
					}
				}
			}
		}
		if (this.resourceBundle == null) {
			// Use Default
			this.resourceBundle = ResourceBundle.getBundle(baseName, Locale.ROOT);
		}
	}
	// end temporary code */
		
	
	@Override
	protected String handleGetValue(String key, String defaultValue) {
		if (!handleContainsKey(key)) {
			return defaultValue;
		}
		return this.resourceBundle.getString(key);
	}
	
	@Override
	protected String handleSetValue(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String handleRemoveValue(String key) {
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
