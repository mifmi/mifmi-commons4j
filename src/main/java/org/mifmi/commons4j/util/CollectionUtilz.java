/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper methods for working with Collection instances.
 * 
 * @author mozq
 */
public final class CollectionUtilz {
	
	public interface EachList<T> {
		T function(int index, T value);
	}
	
	public interface EachConvList<T, R> {
		R function(int index, T value);
	}

	private CollectionUtilz() {
		// NOP
	}
	
	public static <T> List<T> each(List<T> list, EachList<T> func) {
		if (list == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			list.set(i, func.function(i, list.get(i)));
		}
		return list;
	}
	
	public static <T, R> List<R> eachConv(List<T> list, EachConvList<T, R> func) {
		if (list == null) {
			return null;
		}
		List<R> newList = new ArrayList<R>(list.size());
		for (int i = 0; i < list.size(); i++) {
			newList.add(func.function(i, list.get(i)));
		}
		return newList;
	}
	
	public static <T> boolean remove(Collection<T> col, T value) {
		if (col == null) {
			return false;
		}
		
		return col.remove(value);
	}
	
	public static <K, V> Map<V, K> swapKeyValue(Map<K, V> map, Map<V, K> newMap) {
		if (map == null) {
			return null;
		}
		
		for (Map.Entry<K, V> entry : map.entrySet()) {
			newMap.put(entry.getValue(), entry.getKey());
		}
		return newMap;
	}
	
	public static <T> List<T> cast(List<?> list) {
		@SuppressWarnings("unchecked")
		List<T> castList = (List<T>)list;
		return castList;
	}
	
	public static <T> Set<T> cast(Set<?> set) {
		@SuppressWarnings("unchecked")
		Set<T> castSet = (Set<T>)set;
		return castSet;
	}
	
	public static <T> List<T> list(Enumeration<? extends T> enumeration) {
		List<T> list = new ArrayList<T>();
		while (enumeration.hasMoreElements()) {
			list.add(enumeration.nextElement());
		}
		return list;
	}
}
