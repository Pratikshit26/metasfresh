package de.metas.cache.model;

 


import java.util.Date;

public interface ITableCacheStatistics
{
	void reset();

	boolean isCacheEnabled();

	String getTableName();
	Date getStartDate();

	void incrementHitCount();
	long getHitCount();

	void incrementHitInTrxCount();
	long getHitInTrxCount();

	void incrementMissCount();
	long getMissCount();
	
	void incrementMissInTrxCount();
	long getMissInTrxCount();
}
