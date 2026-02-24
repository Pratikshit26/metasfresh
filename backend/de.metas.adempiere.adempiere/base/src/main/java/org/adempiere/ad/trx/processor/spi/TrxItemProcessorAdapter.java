package org.adempiere.ad.trx.processor.spi;

 


import java.util.Properties;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.util.api.IParams;

/**
 * Implement what you need adapter for {@link ITrxItemProcessor}.
 * 
 * @author tsa
 *
 * @param <IT> input type
 * @param <RT> result type
 */
public abstract class TrxItemProcessorAdapter<IT, RT> implements ITrxItemProcessor<IT, RT>
{
	private ITrxItemProcessorContext processorCtx;

	@Override
	public final void setTrxItemProcessorCtx(ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	protected final ITrxItemProcessorContext getTrxItemProcessorCtx()
	{
		return processorCtx;
	}

	protected final Properties getCtx()
	{
		return getTrxItemProcessorCtx().getCtx();
	}

	protected final String getTrxName()
	{
		return getTrxItemProcessorCtx().getTrxName();
	}
	
	protected final IParams getParams()
	{
		return getTrxItemProcessorCtx().getParams();
	}

	@Override
	public abstract void process(IT item) throws Exception;

	@Override
	public RT getResult()
	{
		// nothing at this level
		return null;
	}

}
