package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;
import java.util.Set;

/**
 * Stores meta data informations about a model class.
 * 
 * @author tsa
 *
 */
public interface IModelClassInfo
{
	Class<?> getModelClass();

	String getTableName();

	IModelMethodInfo getMethodInfo(Method method);

	/**
	 * Gets defined column names from underlying model.
	 * 
	 * Defined column names are those those "COLUMNNAME_MyColumnName" fields.
	 * 
	 * Please note, this method is NOT checking the database.
	 * 
	 * @return column names
	 */
	Set<String> getDefinedColumnNames();
}
