package org.adempiere.ad.trx.api;

 


/**
 * Interface responsible for generating new transaction names
 * 
 * @author tsa
 * 
 */
public interface ITrxNameGenerator
{

	/**
	 * Generate an unique transaction name which starts with given prefix.
	 * 
	 * NOTE: if the <code>prefix</code> is really used depends on implementation.
	 * 
	 * @param prefix
	 * @return generated transaction name
	 */
	String createTrxName(String prefix);
}
