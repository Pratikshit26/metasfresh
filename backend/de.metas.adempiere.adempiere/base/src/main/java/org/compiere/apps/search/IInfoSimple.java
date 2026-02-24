/**
 * 
 */
package org.compiere.apps.search;

 


import java.util.Properties;

/**
 * @author tsa
 * 
 */
public interface IInfoSimple
{
	public Properties getCtx();

	public int getWindowNo();

	public Object getContextVariable(String columnName);

	public int getContextVariableAsInt(String columnName);

	public void setCtxAttribute(String column, Object value);

	public void executeQuery();

	public void setJoinClauseAnd(boolean isAND);

	public Integer getSelectedRowKey();

	/**
	 * Gets grid table value.
	 * 
	 * @param row
	 * @param columnName
	 * @return value
	 */
	public <T> T getValue(int row, String columnName);

	/**
	 * Gets row record ID or -1.
	 * 
	 * @param rowIndexModel
	 * @return row key or -1
	 */
	public int getRecordId(final int rowIndexModel);

	/**
	 * Sets grid table value.
	 * 
	 * @param infoColumn
	 * @param rowIndexModel
	 * @param value
	 */
	public void setValue(Info_Column infoColumn, int rowIndexModel, Object value);

	/**
	 * Sets grid table value
	 * 
	 * @param columnName
	 * @param rowIndexModel
	 * @param value
	 */
	public void setValueByColumnName(String columnName, int rowIndexModel, Object value);
	
	/**
	 * Overrides the "isLoading" functionality.
	 * 
	 * @param ignoreLoading
	 */
	void setIgnoreLoading(boolean ignoreLoading);

	<T extends IInfoColumnController> T getInfoColumnControllerOrNull(String columnName, Class<T> controllerClass);
	
	/**
	 * 
	 * @return true if this window is currently disposing or it was already disposed
	 */
	public boolean isDisposed();
}
