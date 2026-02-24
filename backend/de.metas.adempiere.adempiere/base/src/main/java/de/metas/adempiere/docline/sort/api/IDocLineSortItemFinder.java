package de.metas.adempiere.docline.sort.api;

 


import java.util.Comparator;
import java.util.Properties;

import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.I_C_DocType;

/**
 * Document Line Sort finder builder used in data retrieval. Just add more criteria when needed.
 *
 * @author al
 */
public interface IDocLineSortItemFinder
{
	/**
	 * @return DocLine sort header
	 */
	I_C_DocLine_Sort find();

	/**
	 * Returns the comparator suitable to order document lines by their M_Product_IDs. If no such comparator is available, then the {@link org.adempiere.util.comparator.NullComparator} is returned.
	 * 
	 * @return productId comparator of finder. Never returns <code>null</code>.
	 */
	Comparator<Integer> findProductIdsComparator();

	/**
	 * @param docBaseType
	 * @return IDocLineSortItemFinder
	 */
	IDocLineSortItemFinder setDocBaseType(String docBaseType);

	/**
	 * @param ctx
	 * @return finder
	 */
	IDocLineSortItemFinder setContext(Properties ctx);

	/**
	 * @param bpartnerId
	 * @return finder
	 */
	IDocLineSortItemFinder setC_BPartner_ID(int bpartnerId);

	/**
	 * @param docType
	 * @return finder
	 */
	IDocLineSortItemFinder setC_DocType(I_C_DocType docType);
}
