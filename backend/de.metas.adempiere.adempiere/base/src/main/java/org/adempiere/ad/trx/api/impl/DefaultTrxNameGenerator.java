package org.adempiere.ad.trx.api.impl;

 


import java.util.UUID;

import org.adempiere.ad.trx.api.ITrxNameGenerator;

/**
 * Default transaction name generator implementation.
 * 
 * Generated transaction name will have following format: [Prefix or {@value #TRXNAME_PREFIX_DEFAULT}]_[generated UUID]
 * 
 * @author tsa
 * 
 */
public class DefaultTrxNameGenerator implements ITrxNameGenerator
{
	public static final transient DefaultTrxNameGenerator instance = new DefaultTrxNameGenerator();

	public static final String TRXNAME_PREFIX_DEFAULT = "Trx";

	@Override
	public String createTrxName(final String prefix)
	{
		final StringBuilder trxName = new StringBuilder();

		if (prefix == null || prefix.isEmpty())
		{
			trxName.append(TRXNAME_PREFIX_DEFAULT);
		}
		else
		{
			trxName.append(prefix);
		}

		trxName.append("_").append(UUID.randomUUID());

		return trxName.toString();
	}
}
