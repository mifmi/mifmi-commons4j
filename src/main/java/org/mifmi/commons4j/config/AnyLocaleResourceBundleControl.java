/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2017 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class AnyLocaleResourceBundleControl extends Control {

	private Control baseControl;

	private Map<Locale, Locale> localeMap = null;
	
	public AnyLocaleResourceBundleControl(Locale... locales) {
		this((Control)null, locales);
	}
	
	public AnyLocaleResourceBundleControl(Control baseControl, Locale... locales) {
		this(baseControl, Arrays.asList(locales));
	}

	public AnyLocaleResourceBundleControl(List<Locale> locales) {
		this((Control)null, locales);
	}
	
	public AnyLocaleResourceBundleControl(Control baseControl, List<Locale> locales) {
		this(baseControl, Collections.enumeration(locales));
	}
	
	public AnyLocaleResourceBundleControl(Enumeration<Locale> locales) {
		this((Control)null, locales);
	}
	
	public AnyLocaleResourceBundleControl(Control baseControl, Enumeration<Locale> locales) {
		this.baseControl = baseControl;

		this.localeMap = new HashMap<Locale, Locale>();
		Locale prevLocale = null;
		if (locales != null) {
			while (locales.hasMoreElements()) {
				Locale locale = locales.nextElement();
				if (locale == null) {
					continue;
				}
				
				this.localeMap.put(prevLocale, locale);
				prevLocale = locale;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<String> getFormats(String baseName) {
		if (this.baseControl == null) {
			return super.getFormats(baseName);
		} else {
			return this.baseControl.getFormats(baseName);
		}
	}

	/** {@inheritDoc} */
	@Override
	public List<Locale> getCandidateLocales(String baseName, Locale locale) {
		if (this.baseControl == null) {
			return super.getCandidateLocales(baseName, locale);
		} else {
			return this.baseControl.getCandidateLocales(baseName, locale);
		}
	}

	@Override
	public Locale getFallbackLocale(String baseName, Locale locale) {
		Locale fallbackLocale = this.localeMap.get(locale);
		
		if (fallbackLocale == null) {
			if (this.baseControl == null) {
				return super.getFallbackLocale(baseName, locale);
			} else {
				return this.baseControl.getFallbackLocale(baseName, locale);
			}
		}
		
		return fallbackLocale;
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if (this.baseControl == null) {
			return super.newBundle(baseName, locale, format, loader, reload);
		} else {
			return this.baseControl.newBundle(baseName, locale, format, loader, reload);
		}
	}

	/** {@inheritDoc} */
	@Override
	public long getTimeToLive(String baseName, Locale locale) {
		if (this.baseControl == null) {
			return super.getTimeToLive(baseName, locale);
		} else {
			return this.baseControl.getTimeToLive(baseName, locale);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle,
			long loadTime) {
		if (this.baseControl == null) {
			return super.needsReload(baseName, locale, format, loader, bundle, loadTime);
		} else {
			return this.baseControl.needsReload(baseName, locale, format, loader, bundle, loadTime);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toBundleName(String baseName, Locale locale) {
		if (this.baseControl == null) {
			return super.toBundleName(baseName, locale);
		} else {
			return this.baseControl.toBundleName(baseName, locale);
		}
	}

}
