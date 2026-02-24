package org.compiere.report.core;

 


import java.util.Map;

public interface IRModelMetadata
{

	int getGroupsCount();

	/**
	 * 
	 * @return "column index" to "function name"s map
	 */
	Map<Integer, String> getFunctions();

	/**
	 * Find index for ColumnName
	 * 
	 * @param columnName
	 * @return index or -1 if not found
	 */
	int getRColumnIndex(String columnName);

}
