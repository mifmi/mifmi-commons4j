/*!
 * mifmi-commons4j
 * https://github.com/mifmi/mifmi-commons4j
 *
 * Copyright (c) 2015 mifmi.org and other contributors
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package org.mifmi.commons4j.swing;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public final class TableUtilz {

	private TableUtilz() {
		// NOP
	}

	public static TableColumn getTableColumn(JTable table, int columnIndex) {
		if (table == null) {
			throw new NullPointerException();
		}
		
		String columnName = table.getColumnName(columnIndex);
		if (columnName == null) {
			return null;
		}
		
		TableColumn column = table.getColumn(columnName);
		
		return column;
	}
	
	public static void setColumnWidth(JTable table, int columnIndex, int preferredWidth) {
		TableColumn column = getTableColumn(table, columnIndex);
		if (column == null) {
			return;
		}
		
		column.setPreferredWidth(preferredWidth);
	}
	
	public static void setColumnWidth(JTable table, int columnIndex, int preferredWidth, boolean resizable) {
		TableColumn column = getTableColumn(table, columnIndex);
		if (column == null) {
			return;
		}
		
		column.setPreferredWidth(preferredWidth);
		column.setResizable(resizable);
	}
	
	public static void setColumnWidth(JTable table, int columnIndex, int preferredWidth, int maxWidth, int minWidth) {
		TableColumn column = getTableColumn(table, columnIndex);
		if (column == null) {
			return;
		}
		
		column.setPreferredWidth(preferredWidth);
		column.setMaxWidth(maxWidth);
		column.setMinWidth(minWidth);
	}
}
