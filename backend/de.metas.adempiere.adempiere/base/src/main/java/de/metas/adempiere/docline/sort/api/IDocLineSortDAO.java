package de.metas.adempiere.docline.sort.api;

 


import java.util.List;

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort_Item;

import de.metas.util.ISingletonService;

/**
 * Document Line Sort Preferences DAO
 *
 * @author al
 */
public interface IDocLineSortDAO extends ISingletonService
{
	/**
	 * @param docLineSort
	 * @return document line sort items for sort header configuration
	 */
	List<I_C_DocLine_Sort_Item> retrieveItems(I_C_DocLine_Sort docLineSort);

	/**
	 * @return {@link I_C_DocLine_Sort_Item} finder
	 */
	IDocLineSortItemFinder findDocLineSort();
}
