package org.adempiere.ad.trx.api.impl;

 


import org.adempiere.ad.trx.api.ITrxNameGenerator;

import de.metas.util.Check;

/**
 * An {@link ITrxNameGenerator} where generated names are predictable.
 * 
 * On constructions it takes the <code>trxNamePrefix</code> and an starting index.
 * 
 * Each time it needs to generate a transaction name, it will use the given <code>trxNamePrefix</code> as a prefix and concatenates current index.
 * 
 * @author tsa
 * 
 */
public class PredictableTrxNameGenerator implements ITrxNameGenerator
{
	private final String trxNamePrefix;
	private final int startIndex;
	private int index;

	public PredictableTrxNameGenerator(final String trxNamePrefix, final int startIndex)
	{
		Check.assumeNotEmpty(trxNamePrefix, "trxNamePrefix not empty");
		this.trxNamePrefix = trxNamePrefix;

		Check.assume(startIndex >= 0, "Invalid startIndex value: {}", startIndex);
		this.startIndex = startIndex;
		this.index = startIndex;
	}

	@Override
	public String createTrxName(String prefix)
	{
		final String trxName = trxNamePrefix + index;
		index++;

		return trxName;
	}
	
	public void resetIndex()
	{
		this.index = startIndex;
	}

}
