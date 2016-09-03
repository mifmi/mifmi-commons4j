/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class PropertiesConfig extends AbstractConfig {
	
	private Properties properties;
	
	public PropertiesConfig(Properties properties) {
		this.properties = properties;
	}

	public PropertiesConfig(Path path) throws InvalidPropertiesFormatException, IOException {
		this(path, false, null);
	}

	public PropertiesConfig(Path path, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		this(path, false, defaults);
	}

	public PropertiesConfig(Path path, boolean loadFromXML) throws InvalidPropertiesFormatException, IOException {
		this(path, loadFromXML, null);
	}

	public PropertiesConfig(Path path, boolean loadFromXML, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		try (InputStream is = Files.newInputStream(path)) {
			load(is, loadFromXML, defaults);
		}
	}

	public PropertiesConfig(InputStream is) throws InvalidPropertiesFormatException, IOException {
		this(is, false, null);
	}

	public PropertiesConfig(InputStream is, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		this(is, false, defaults);
	}

	public PropertiesConfig(InputStream is, boolean loadFromXML) throws InvalidPropertiesFormatException, IOException {
		this(is, loadFromXML, null);
	}

	public PropertiesConfig(InputStream is, boolean loadFromXML, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		load(is, loadFromXML, defaults);
	}

	public PropertiesConfig(Reader reader) throws InvalidPropertiesFormatException, IOException {
		this(reader, null);
	}

	public PropertiesConfig(Reader reader, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		load(reader, defaults);
	}

	private void load(InputStream is, boolean loadFromXML, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		this.properties = new OrderedProperties(defaults);
		if (loadFromXML) {
			this.properties.loadFromXML(is);
		} else {
			this.properties.load(is);
		}
	}

	private void load(Reader reader, Properties defaults) throws InvalidPropertiesFormatException, IOException {
		this.properties = new OrderedProperties(defaults);
		this.properties.load(reader);
	}

	@Override
	protected boolean handleSupportsObjectValue() {
		return false;
	}

	@Override
	protected Object handleGetValue(String key) {
		return this.properties.getProperty(key);
	}
	
	@Override
	protected Object handleSetValue(String key, Object value) {
		return this.properties.setProperty(key, Objects.toString(value));
	}

	@Override
	protected String handleRemoveValue(String key) {
		return (String)this.properties.remove(key);
	}

	@Override
	protected boolean handleContainsKey(String key) {
		return this.properties.containsKey(key);
	}

	@Override
	protected Set<String> handleGetKeySet() {
		return this.properties.stringPropertyNames();
	}

	@Override
	protected int handleGetSize() {
		return this.properties.size();
	}

	public Properties getProperties() {
		return this.properties;
	}
}
