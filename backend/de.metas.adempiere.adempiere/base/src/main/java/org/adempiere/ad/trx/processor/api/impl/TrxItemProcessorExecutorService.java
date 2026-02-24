package org.adempiere.ad.trx.processor.api.impl;

 

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.api.IParams;

public class TrxItemProcessorExecutorService implements ITrxItemProcessorExecutorService
{
	@Override
	public ITrxItemProcessorContext createProcessorContext(final Properties ctx, final ITrx trx)
	{
		final IParams params = null;
		return createProcessorContext(ctx, trx, params);
	}

	@Override
	public ITrxItemProcessorContext createProcessorContext(final Properties ctx, final ITrx trx, final IParams params)
	{
		final TrxItemProcessorContext processorCtx = new TrxItemProcessorContext(ctx);
		processorCtx.setTrx(trx);
		processorCtx.setParams(params);
		return processorCtx;
	}

	@Override
	public <IT, RT> ITrxItemProcessorExecutor<IT, RT> createExecutor(final ITrxItemProcessorContext processorCtx, final ITrxItemProcessor<IT, RT> processor)
	{
		final ITrxItemExecutorBuilder<IT, RT> builder = createExecutor();
		return builder
				.setContext(processorCtx)
				.setProcessor(processor)
				.build();
	}

	@Override
	public <IT, RT> ITrxItemExecutorBuilder<IT, RT> createExecutor()
	{
		return new TrxItemExecutorBuilder<IT, RT>(this);
	}
}
