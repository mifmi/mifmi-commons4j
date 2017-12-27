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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class MultiLocaleResourceBundleControl extends Control {

	private Control baseControl;
	
	private List<Locale> candidateLocales;
	
	public MultiLocaleResourceBundleControl(Locale... locales) {
		this((Control)null, locales);
	}
	
	public MultiLocaleResourceBundleControl(Control baseControl, Locale... locales) {
		this(baseControl, Arrays.asList(locales));
	}

	public MultiLocaleResourceBundleControl(List<Locale> locales) {
		this((Control)null, locales);
	}
	
	public MultiLocaleResourceBundleControl(Control baseControl, List<Locale> locales) {
		this(baseControl, Collections.enumeration(locales));
	}
	
	public MultiLocaleResourceBundleControl(Enumeration<Locale> locales) {
		this((Control)null, locales);
	}
	
	public MultiLocaleResourceBundleControl(Control baseControl, Enumeration<Locale> locales) {
		if (baseControl == null) {
			this.baseControl = new Control() {
				// NOP
			};
		} else {
			this.baseControl = baseControl;
		}
		
		this.candidateLocales = new ArrayList<Locale>();
		if (locales != null) {
			while (locales.hasMoreElements()) {
				Locale locale = locales.nextElement();
				if (locale == null) {
					continue;
				}
				
				for (Locale l : this.baseControl.getCandidateLocales("", locale)) {
					if (Locale.ROOT.equals(l)) {
						continue;
					}
					
					this.candidateLocales.add(l);
				}
			}
		}
		this.candidateLocales.add(Locale.ROOT);
	}

	/** {@inheritDoc} */
	@Override
	public List<String> getFormats(String baseName) {
		return this.baseControl.getFormats(baseName);
	}

	@Override
	public List<Locale> getCandidateLocales(String baseName, Locale locale) {
		return this.candidateLocales;
	}

	/** {@inheritDoc} */
	@Override
	public Locale getFallbackLocale(String baseName, Locale locale) {
		return this.baseControl.getFallbackLocale(baseName, locale);
	}

	/** {@inheritDoc} */
	@Override
	public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		return this.baseControl.newBundle(baseName, locale, format, loader, reload);
	}

	/** {@inheritDoc} */
	@Override
	public long getTimeToLive(String baseName, Locale locale) {
		return this.baseControl.getTimeToLive(baseName, locale);
	}

	/** {@inheritDoc} */
	@Override
	public boolean needsReload(String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle,
			long loadTime) {
		return this.baseControl.needsReload(baseName, locale, format, loader, bundle, loadTime);
	}

	/** {@inheritDoc} */
	@Override
	public String toBundleName(String baseName, Locale locale) {
		return this.baseControl.toBundleName(baseName, locale);
	}

}
