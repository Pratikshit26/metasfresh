package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;
import java.util.Set;

/**
 * Implementations of this interface are model wrappers which want to expose low level methods to {@link IModelMethodInfo#invoke(IModelInternalAccessor, Object[])} calls.
 * 
 * Mainly we use this interface to homogenously write business logic.
 * 
 * @author tsa
 *
 */
public interface IModelInternalAccessor
{
	Set<String> getColumnNames();

	int getColumnIndex(String columnName);

	boolean isVirtualColumn(String columnName);

	boolean isKeyColumnName(String columnName);

	boolean isCalculated(String columnName);

	boolean hasColumnName(String columnName);

	/**
	 * Gets the value of given <code>columnName</code>
	 * 
	 * @param columnName
	 * @param columnIndex column index of <code>columnName</code>; required for optimization purposes.
	 * @param returnType
	 * @return column value
	 */
	Object getValue(String columnName, int columnIndex, Class<?> returnType);

	/**
	 * Same as {@link #getValue(String, int, Class)} but the <code>columnIndex</code> will be autodetected.
	 * 
	 * @param columnName
	 * @param returnType
	 * @return column value
	 */
	Object getValue(String columnName, Class<?> returnType);

	/**
	 * @param columnName
	 * @param value
	 * @return true if value was set; false if value was not set for different reasons
	 */
	boolean setValue(String columnName, Object value);

	/**
	 * Same as {@link #setValue(String, Object)} but it will not check if the column is readonly or something, it will try to directly set the value.
	 * 
	 * @param columnName
	 * @param value
	 * @return true if value was set; false if value was not set for different reasons
	 */
	boolean setValueNoCheck(String columnName, Object value);

	Object getReferencedObject(String columnName, Method interfaceMethod) throws Exception;

	void setValueFromPO(String idColumnName, Class<?> parameterType, Object value);

	boolean invokeEquals(Object[] methodArgs);

	Object invokeParent(Method method, Object[] methodArgs) throws Exception;
}
