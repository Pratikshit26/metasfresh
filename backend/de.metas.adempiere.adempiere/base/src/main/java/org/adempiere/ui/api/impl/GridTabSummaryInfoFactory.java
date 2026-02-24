package org.adempiere.ui.api.impl;

 


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ui.api.IGridTabSummaryInfoFactory;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.adempiere.ui.spi.impl.DefaultGridTabSummaryInfoProvider;
import org.compiere.model.GridTab;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.util.Check;

public class GridTabSummaryInfoFactory implements IGridTabSummaryInfoFactory
{
	private final Map<ArrayKey, IGridTabSummaryInfoProvider> providers = new HashMap<>();
	private final IGridTabSummaryInfoProvider defaultProvider = new DefaultGridTabSummaryInfoProvider();

	@Override
	public void register(final String tableName, final IGridTabSummaryInfoProvider summaryInfoProvider)
	{
		final boolean forceOverride = false;
		register(tableName, summaryInfoProvider, forceOverride);
	}

	@Override
	public void register(final String tableName, final IGridTabSummaryInfoProvider summaryInfoProvider, final boolean forceOverride)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotNull(summaryInfoProvider, "summaryInfoProvider not null");

		final ArrayKey key = mkKey(tableName);
		if (!forceOverride
				&& providers.containsKey(key))
		{
			throw new AdempiereException("The provider " + summaryInfoProvider + " is already registered: ");
		}
		providers.put(key, summaryInfoProvider);
	}

	private ArrayKey mkKey(final String tableName)
	{
		return Util.mkKey(tableName);
	}

	@Override
	public IGridTabSummaryInfoProvider getSummaryInfoProvider(final GridTab gridTab)
	{
		Check.assumeNotNull(gridTab, "gridTab not null");

		final ArrayKey key = mkKey(gridTab.getTableName());
		final IGridTabSummaryInfoProvider provider = providers.get(key);
		if (provider == null)
		{
			return defaultProvider;
		}

		return provider;
	}
}
