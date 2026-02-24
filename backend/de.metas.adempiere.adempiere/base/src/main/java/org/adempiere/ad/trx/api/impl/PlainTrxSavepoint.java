package org.adempiere.ad.trx.api.impl;

 


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxSavepoint;

/**
 * Plain (i.e. nothing) implementation of {@link ITrxSavepoint}.
 * 
 * @author tsa
 * 
 */
public class PlainTrxSavepoint implements ITrxSavepoint
{
	private final ITrx trx;
	private final String name;

	public PlainTrxSavepoint(final ITrx trx, final String name)
	{
		this.trx = trx;
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "name=" + name
				+ ", trx=" + trx.getTrxName() // prevent stackoverflow
				+ "]";
	}

	@Override
	public Object getNativeSavepoint()
	{
		// dummy
		return name;
	}

	@Override
	public ITrx getTrx()
	{
		return trx;
	}
}
