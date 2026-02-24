package org.adempiere.appdict.validation.api.impl;

 


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.appdict.validation.api.IADValidatorDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;

public class ADValidatorDAO implements IADValidatorDAO
{
	@Override
	public <T> Iterator<T> retrieveApplicationDictionaryItems(final Properties ctx, final Class<T> appDictClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(appDictClass);
		final String whereClause = null;

		return new Query(ctx, tableName, whereClause, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.iterate(appDictClass, false);
	}
}
