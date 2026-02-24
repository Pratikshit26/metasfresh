package org.adempiere.ad.callout.api;

 

import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_ColumnCallout;

import com.google.common.collect.ListMultimap;

import de.metas.util.ISingletonService;

public interface IADColumnCalloutDAO extends ISingletonService
{
	/**
	 * 
	 * @param ctx
	 * @param adTableId
	 * @return ColumnName to List of callout defs
	 */
	ListMultimap<String, I_AD_ColumnCallout> retrieveAvailableCalloutsToRun(Properties ctx, final String tableName);

	List<I_AD_ColumnCallout> retrieveAllColumnCallouts(Properties ctx, int adColumnId);

	int retrieveColumnCalloutLastSeqNo(Properties ctx, int adColumnId);
}
