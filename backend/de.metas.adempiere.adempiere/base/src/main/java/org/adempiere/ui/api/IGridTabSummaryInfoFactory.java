package org.adempiere.ui.api;

 


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.compiere.model.GridTab;

import de.metas.util.ISingletonService;

public interface IGridTabSummaryInfoFactory extends ISingletonService
{
	/**
	 * Get the Summary Info provider that fits your grid tab (window).
	 *
	 * For the normal tabs the default provider is used. It displays what is specified in the AT_Tab.AD_Message.
	 *
	 * For the tabs that have particular providers defined, these are the ones that are taken and the default is not used anymroe
	 *
	 * @param gridTab
	 * @return summary provider; never returns <code>null</code>
	 */
	IGridTabSummaryInfoProvider getSummaryInfoProvider(GridTab gridTab);

	/**
	 * Registers a new {@link IGridTabSummaryInfoFactory} which will be used when {@link GridTab}'s table name is <code>tableName</code>.
	 *
	 * @param tableName
	 * @param summaryInfoProvider
	 *
	 * @throws AdempiereException if provider is already registered for given <code>tableName</code>
	 */
	void register(String tableName, IGridTabSummaryInfoProvider summaryInfoProvider);

	/**
	 * Registers a new {@link IGridTabSummaryInfoFactory} which will be used when {@link GridTab}'s table name is <code>tableName</code>.
	 *
	 * @param tableName
	 * @param summaryInfoProvider
	 * @param forceOverride if true then this invocation may override and replace an already registered provider
	 *
	 * @throws AdempiereException if provider is already registered for given <code>tableName</code> and <code>forceOverride</code> is false.
	 */
	void register(String tableName, IGridTabSummaryInfoProvider summaryInfoProvider, boolean forceOverride);
}
