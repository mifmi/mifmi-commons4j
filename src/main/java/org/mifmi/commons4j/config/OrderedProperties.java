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
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OrderedProperties extends Properties {
	
	private static final long serialVersionUID = -4115521286597670887L;
	
	private LinkedHashMap<Object, Object> propertiesMap = new LinkedHashMap<>();

	public OrderedProperties() {
	}

	public OrderedProperties(Properties defaults) {
		super(defaults);
	}

	@Override
	public synchronized int size() {
		return this.propertiesMap.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return this.propertiesMap.isEmpty();
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(this.propertiesMap.keySet());
	}

	@Override
	public synchronized Enumeration<Object> elements() {
		return Collections.enumeration(this.propertiesMap.values());
	}

	@Override
	public synchronized boolean contains(Object value) {
		return this.propertiesMap.containsValue(value);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.propertiesMap.containsValue(value);
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		return this.propertiesMap.containsKey(key);
	}

	@Override
	public synchronized Object get(Object key) {
		return this.propertiesMap.get(key);
	}

	@Override
	protected void rehash() {
		// NOP
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		return this.propertiesMap.put(key, value);
	}

	@Override
	public synchronized Object remove(Object key) {
		return this.propertiesMap.remove(key);
	}

	@Override
	public synchronized void putAll(Map<? extends Object, ? extends Object> t) {
		this.propertiesMap.putAll(t);
	}

	@Override
	public synchronized void clear() {
		this.propertiesMap.clear();
	}

	@Override
	public synchronized Object clone() {
		OrderedProperties clone = (OrderedProperties)super.clone();
		clone.propertiesMap = (LinkedHashMap)this.propertiesMap.clone();
		return clone;
	}

	@Override
	public synchronized String toString() {
		return this.propertiesMap.toString();
	}

	@Override
	public Set<Object> keySet() {
		return this.propertiesMap.keySet();
	}

	@Override
	public Set<Map.Entry<Object, Object>> entrySet() {
		return this.propertiesMap.entrySet();
	}

	@Override
	public Collection<Object> values() {
		return this.propertiesMap.values();
	}

	@Override
	public synchronized Object getOrDefault(Object key, Object defaultValue) {
		return this.propertiesMap.getOrDefault(key, defaultValue);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((this.propertiesMap == null) ? 0 : this.propertiesMap.hashCode());
		result = prime * result + ((super.defaults == null) ? 0 : super.defaults.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		OrderedProperties other = (OrderedProperties) obj;
		if (!this.propertiesMap.equals(other.propertiesMap)) {
			return false;
		}
		if (!super.defaults.equals(other.defaults)) {
			return false;
		}
		
		return true;
	}

	@Override
	public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
		this.propertiesMap.forEach(action);
	}

	@Override
	public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
		this.propertiesMap.replaceAll(function);
	}

	@Override
	public synchronized Object putIfAbsent(Object key, Object value) {
		return this.propertiesMap.putIfAbsent(key, value);
	}

	@Override
	public synchronized boolean remove(Object key, Object value) {
		return this.propertiesMap.remove(key, value);
	}

	@Override
	public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
		return this.propertiesMap.replace(key, oldValue, newValue);
	}

	@Override
	public synchronized Object replace(Object key, Object value) {
		return this.propertiesMap.replace(key, value);
	}

	@Override
	public synchronized Object computeIfAbsent(Object key, Function<? super Object, ? extends Object> mappingFunction) {
		return this.propertiesMap.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public synchronized Object computeIfPresent(Object key,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return this.propertiesMap.computeIfPresent(key, remappingFunction);
	}

	@Override
	public synchronized Object compute(Object key,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return this.propertiesMap.compute(key, remappingFunction);
	}

	@Override
	public synchronized Object merge(Object key, Object value,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return this.propertiesMap.merge(key, value, remappingFunction);
	}

	@Override
	public String getProperty(String key) {
		Object objVal = get(key);
		String strVal = (objVal instanceof String) ? (String)objVal : null;
		if (strVal == null && this.defaults != null) {
			return this.defaults.getProperty(key);
		} else {
			return strVal;
		}
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String val = getProperty(key);
		
		if (val == null) {
			return defaultValue;
		}
		
		return val;
	}

	@Override
	public synchronized Object setProperty(String key, String value) {
		return put(key, value);
	}

	@Override
	public Enumeration<?> propertyNames() {
		return Collections.enumeration(stringPropertyNames());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> stringPropertyNames() {
		if (this.defaults == null) {
			return (Set)this.propertiesMap.keySet();
		} else {
			Set<String> stringPropertyNameSet = new LinkedHashSet<String>(this.defaults.size() + this.propertiesMap.size());
			
			stringPropertyNameSet.addAll(this.defaults.stringPropertyNames());
			for (Object key : this.propertiesMap.keySet()) {
				stringPropertyNameSet.add((String)key);
			}
			
			return stringPropertyNameSet;
		}
	}

	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		// Call super class method
		super.load(inStream);
	}

	@Override
	public synchronized void load(Reader reader) throws IOException {
		// Call super class method
		super.load(reader);
	}

	@Deprecated
	@Override
	public void save(OutputStream out, String comments) {
		try {
			store(out, comments);
		} catch (IOException e) {
			// NOP
		}
	}

	@Override
	public void store(OutputStream out, String comments) throws IOException {
		// Call super class method
		super.store(out, comments);
	}

	@Override
	public void store(Writer writer, String comments) throws IOException {
		// Call super class method
		super.store(writer, comments);
	}

	@Override
	public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		// Call super class method
		super.loadFromXML(in);
	}

	@Override
	public void storeToXML(OutputStream os, String comment) throws IOException {
		storeToXML(os, comment, "UTF-8");
	}

	@Override
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
		// Call super class method
		super.storeToXML(os, comment, encoding);
	}

	@Override
	public void list(PrintStream out) {
		out.println("-- listing properties --");
		
		for (String key : stringPropertyNames()) {
			String val = (String)get(key);
			
			out.print(key);
			out.print('=');
			if (40 < val.length()) {
				out.print(val.substring(0, 37));
				out.print("...");
			} else {
				out.print(val);
			}
			out.println();
		}
	}

	@Override
	public void list(PrintWriter out) {
		out.println("-- listing properties --");
		
		for (String key : stringPropertyNames()) {
			String val = (String)get(key);
			
			out.print(key);
			out.print('=');
			if (40 < val.length()) {
				out.print(val.substring(0, 37));
				out.print("...");
			} else {
				out.print(val);
			}
			out.println();
		}
	}
}
