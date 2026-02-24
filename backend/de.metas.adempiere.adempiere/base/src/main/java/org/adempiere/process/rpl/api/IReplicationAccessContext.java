package org.adempiere.process.rpl.api;

 


/**
 * IReplicationAccessContext holds properties which are used when filtering exported data.
 * 
 * @author lc
 */
public interface IReplicationAccessContext
{
	/**
	 * @return query limit when retrieving data
	 */
	int getLimit();

	/**
	 * @return true, if export will filter by organisation and client for the current login, false otherwise
	 */
	boolean isApplyAccessFilter();
}
