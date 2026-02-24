package de.metas.calendar.standard.impl;

 

import com.google.common.collect.ImmutableMap;
import de.metas.cache.annotation.CacheCtx;
import de.metas.calendar.standard.IPeriodDAO;
import de.metas.document.DocBaseType;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_PeriodControl;

import java.util.Map;
import java.util.Properties;

public class PeriodDAO implements IPeriodDAO
{
	@Override
	@Cached(cacheName = I_C_PeriodControl.Table_Name + "#by#" + I_C_PeriodControl.COLUMNNAME_C_Period_ID, expireMinutes = 120)
	public Map<DocBaseType, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(final @CacheCtx Properties ctx, final int periodId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PeriodControl.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_PeriodControl.COLUMN_C_Period_ID, periodId)
				.addOnlyActiveRecordsFilter()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
					record -> DocBaseType.ofCode(record.getDocBaseType()),
					record -> record
				));
	}
}
