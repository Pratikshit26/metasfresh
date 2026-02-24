package org.adempiere.server.rpl.api.impl;

 


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.server.rpl.interfaces.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;

import de.metas.util.Services;

public abstract class AbstractIMPProcessorDAO implements IIMPProcessorDAO
{
	@Override
	public int deleteLogs(final org.compiere.model.I_IMP_Processor impProcessor)
	{
		final boolean deleteAll = false;
		return deleteLogs(impProcessor, deleteAll);
	}

	@Override
	public List<I_IMP_ProcessorParameter> retrieveParameters(final org.compiere.model.I_IMP_Processor impProcessor, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(impProcessor);

		return Services.get(IQueryBL.class).createQueryBuilder(I_IMP_ProcessorParameter.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_IMP_ProcessorParameter>(I_IMP_ProcessorParameter.COLUMNNAME_IMP_Processor_ID, impProcessor.getIMP_Processor_ID()))
				.filter(ActiveRecordQueryFilter.getInstance(I_IMP_ProcessorParameter.class))
				.create()
				.list(I_IMP_ProcessorParameter.class);
	}

	@Override
	public void deleteParameters(final org.compiere.model.I_IMP_Processor impProcessor)
	{
		final String trxName = InterfaceWrapperHelper.getModelTableName(impProcessor);

		for (final I_IMP_ProcessorParameter para : retrieveParameters(impProcessor, trxName))
		{
			InterfaceWrapperHelper.delete(para);
		}
	}

	@Override
	public I_IMP_ProcessorParameter retrieveParameter(final org.compiere.model.I_IMP_Processor impProcessor, final String parameterName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_IMP_ProcessorParameter.class, impProcessor)
				.filter(new EqualsQueryFilter<I_IMP_ProcessorParameter>(I_IMP_ProcessorParameter.COLUMNNAME_IMP_Processor_ID, impProcessor.getIMP_Processor_ID()))
				.filter(new EqualsQueryFilter<I_IMP_ProcessorParameter>(I_IMP_ProcessorParameter.COLUMNNAME_Value, parameterName))
				.create()
				.firstOnly(I_IMP_ProcessorParameter.class);
	}

	@Override
	public List<I_IMP_Processor> retrieveAllActive(final Properties ctx)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_IMP_Processor.class, ctx, ITrx.TRXNAME_None)
				.filter(ActiveRecordQueryFilter.getInstance(I_IMP_Processor.class))
				.create()
				.list(I_IMP_Processor.class);
	}
}
