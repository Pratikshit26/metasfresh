package org.adempiere.ad.service;

 


import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_InfoWindow_From;

import de.metas.util.ISingletonService;

public interface IADInfoWindowDAO extends ISingletonService
{

	List<I_AD_InfoWindow_From> retrieveFroms(I_AD_InfoWindow infoWindow);

	I_AD_InfoWindow retrieveInfoWindowByTableId(Properties ctx, int table_id);

	/**
	 * 
	 * @param ctx
	 * @param tableName
	 * @return the first match (preferring one with <code>IsDefault='Y'</code>) or <code>null</code>.
	 */
	I_AD_InfoWindow retrieveInfoWindowByTableName(Properties ctx, String tableName);

	List<I_AD_InfoColumn> retrieveInfoColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Retrieves the info column with the given <code>infoWindow</code> and whose <code>AD_Element</code>'s <code>ColumnName</code> is equal to the given <code>columnName</code>.
	 * <p>
	 * <b>Important:</b> the info columns's own <code>ColumnName</code> does not play any role in the retrieval. Note that this is a more or less arbitrary decision. I wanted it to be simple (no
	 * unnecessary fallbacks etc) and AD_Element_ID seemed to be the more stable candidate to do retrievals by.
	 * 
	 * @param infoWindow
	 * @param columnName
	 * @return the retrieved record or <code>null</code>.
	 * @throws {@link DBException} if there is more than one match.
	 */
	I_AD_InfoColumn retrieveInfoColumnByColumnName(I_AD_InfoWindow infoWindow, String columnName);

	I_AD_InfoColumn retrieveTreeInfoColumn(I_AD_InfoWindow infoWindow);

	/**
	 * Get query info columns for a specific Info Window
	 * 
	 * @param infoWindow
	 * @return
	 */
	List<I_AD_InfoColumn> retrieveQueryColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Get displayed info columns for a specific Info Window
	 * 
	 * @param infoWindow
	 * @return
	 */
	List<I_AD_InfoColumn> retrieveDisplayedColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Gets Info Windows that need to be added in main menu.
	 * <p>
	 * <b>IMPORTANT:</b> method will never return "Info Product" and "Info BPartner", because their appearing is already controlled from AD_Role.
	 * 
	 * @param ctx
	 * @return
	 */
	List<I_AD_InfoWindow> retrieveInfoWindowsInMenu(Properties ctx);
}
