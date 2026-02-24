package org.compiere.apps.search;

 


import java.util.Set;

/**
 * Contains a collection of {@link IGridTabRowBuilder}s to be applied to several records.
 * 
 * @author tsa
 * 
 */
public interface IInfoWindowGridRowBuilders
{
	/**
	 * Gets a {@link Set} of Record_IDs where we have builders which are able to create new records (see {@link IGridTabRowBuilder#isCreateNewRecord()}).
	 * 
	 * NOTE: this object is created by some Info Windows which are triggered from some lookup fields. Those Info Windows can have multi-selection and we need to add custom builders for each Selected
	 * row.
	 * 
	 * e.g. If we call a custom Info Product window from a Product lookup field and user selects several products that list of M_Product_ID will be contained here.
	 * 
	 * @return set of Record_IDs
	 */
	Set<Integer> getRecordIds();

	/**
	 * Gets record customizer ({@link IGridTabRowBuilder}) to be used for given recordId.
	 * 
	 * NOTE: Record_IDs are fetched by using {@link #getRecordIds()}.
	 * 
	 * @param recordId
	 * @return
	 */
	IGridTabRowBuilder getGridTabRowBuilder(final int recordId);

	/**
	 * Used by Info Windows pluggable code in order to register custom builders for each selected item.
	 * 
	 * @param recordId
	 * @param builder
	 */
	void addGridTabRowBuilder(final int recordId, final IGridTabRowBuilder builder);

}
