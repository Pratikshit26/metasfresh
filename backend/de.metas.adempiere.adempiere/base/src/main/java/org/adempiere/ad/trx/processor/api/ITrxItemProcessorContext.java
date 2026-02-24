package org.adempiere.ad.trx.processor.api;

 


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.IContextAware;

/**
 * Item processing context
 * 
 * @author tsa
 * 
 */
public interface ITrxItemProcessorContext extends IContextAware
{
	@Override
	Properties getCtx();
	
	@Override
	String getTrxName();

	ITrx getTrx();

	/**
	 * NOTE: don't call it directly. It will be called ONLY by framework
	 * 
	 * @param trx
	 */
	void setTrx(ITrx trx);

	/**
	 * 
	 * @return new copy of this context
	 */
	ITrxItemProcessorContext copy();
	
	IParams getParams();
}
