/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2016 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ObjectTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	@FunctionalInterface
	public interface GetMapper<T> {
		Object get(T obj, int rowIndex, int columnIndex);
	}

	@FunctionalInterface
	public interface SetMapper<T> {
		void set(Object aValue, T obj, int rowIndex, int columnIndex);
	}
	
	private String[] columnNames;
	private List<? super T> data;
	private Class<?>[] columnClasses;
	private GetMapper<? super T> getMapper;
	private SetMapper<? super T> setMapper;

	public ObjectTableModel(String[] columnNames, Class<?>[] columnClasses, List<? super T> data, GetMapper<? super T> getMapper) {
		this(columnNames, columnClasses, data, getMapper, null);
	}
	public ObjectTableModel(String[] columnNames, Class<?>[] columnClasses, List<? super T> data, GetMapper<? super T> getMapper, SetMapper<? super T> setMapper) {
		this.columnNames = columnNames;
		this.data = (data == null) ? new ArrayList<>() : data;
		this.columnClasses = columnClasses;
		this.getMapper = getMapper;
		this.setMapper = setMapper;
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (this.columnClasses != null) {
			return this.columnClasses[columnIndex];
		} else {
			return super.getColumnClass(columnIndex);
		}
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		T obj = (T)this.data.get(rowIndex);
		return this.getMapper.get(obj, rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (this.setMapper != null) {
			T obj = (T)this.data.get(rowIndex);
			this.setMapper.set(aValue, obj, rowIndex, columnIndex);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (this.setMapper != null);
	}
	
	public T getRow(int rowIndex) {
		return (T)this.data.get(rowIndex);
	}
	
	public void addRow(T rowData) {
		this.data.add(rowData);
		fireTableRowsInserted(this.data.size() - 1, this.data.size() - 1);
	}
	
	public void insertRow(int rowIndex, T rowData) {
		this.data.add(rowIndex, rowData);
		fireTableRowsInserted(rowIndex, rowIndex);
	}
	
	public void updateRow(int rowIndex, T rowData) {
		this.data.set(rowIndex, rowData);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	public void updateRow(int rowIndex) {
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	public void updateRows(int firstRowIndex, int lastRowIndex) {
		fireTableRowsUpdated(firstRowIndex, lastRowIndex);
	}
	
	public void deleteRow(int rowIndex) {
		this.data.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}
}
