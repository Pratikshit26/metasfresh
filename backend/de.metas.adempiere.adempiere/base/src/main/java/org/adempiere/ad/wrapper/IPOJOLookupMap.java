package org.adempiere.ad.wrapper;

 


/**
 * Interface used by {@link POJOWrapper} when it needs to lookup for records
 * 
 * @author tsa
 * 
 */
public interface IPOJOLookupMap
{
	int nextId(String tableName);

	<T> T lookup(Class<T> clazz, int id);

	<T> T lookup(String tableName, int recordId, Class<T> modelClass);

	void save(Object model);

	boolean delete(Object model);

	boolean isCopyOnSave();

}
