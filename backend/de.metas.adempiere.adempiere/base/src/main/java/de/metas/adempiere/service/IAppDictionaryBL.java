/**
 * 
 */
package de.metas.adempiere.service;

 


import java.util.Properties;

import org.compiere.model.MTable;

import de.metas.util.ISingletonService;

/**
 * @author tsa
 * 
 */
public interface IAppDictionaryBL extends ISingletonService
{

	MTable getReferencedTable(Properties ctx, String tableName, String columnName);

	MTable getReferencedTable(MTable parentTable, String columnName);

}
