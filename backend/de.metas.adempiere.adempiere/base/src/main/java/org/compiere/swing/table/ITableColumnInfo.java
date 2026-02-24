package org.compiere.swing.table;

 


import java.lang.reflect.Method;

public interface ITableColumnInfo
{
	int getSeqNo();

	Method getWriteMethod();

	Method getReadMethod();

	boolean isEditable();

	String getDisplayName();

	Class<?> getColumnClass();

	String getColumnName();

	String getLookupTableName();

	String getLookupColumnName();

	/**
	 * @return optional prototype value used to calculate the column width
	 */
	String getPrototypeValue();
}
