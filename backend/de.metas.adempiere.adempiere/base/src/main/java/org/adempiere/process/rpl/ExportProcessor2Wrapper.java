package org.adempiere.process.rpl;

 

import org.adempiere.server.rpl.exceptions.ExportProcessorException;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.PO;
import org.compiere.util.Trx;
import org.w3c.dom.Document;

import java.util.Properties;

public class ExportProcessor2Wrapper implements IExportProcessor2
{
	private final IExportProcessor exportProcessor;

	public ExportProcessor2Wrapper(IExportProcessor exportProcessor)
	{
		this.exportProcessor = exportProcessor;
	}

	@Override
	public void createInitialParameters(MEXPProcessor processor)
	{
		exportProcessor.createInitialParameters(processor);
	}
	
	@Override
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx) throws ExportProcessorException
	{
		try
		{
			exportProcessor.process(ctx, expProcessor, document, trx);
		}
		catch (Exception e)
		{
			// TODO add AD_Message
			throw new ExportProcessorException(null, e);
		}
	}

	@Override
	public void process(MEXPProcessor expProcessor, Document document, PO po) throws ExportProcessorException
	{
		final Properties ctx = po.getCtx();
		final Trx trx = Trx.get(po.get_TrxName(), false);
		try
		{
			exportProcessor.process(ctx, expProcessor, document, trx);
		}
		catch (final Exception e)
		{
			// TODO add AD_Message
			throw new ExportProcessorException(e.getClass().getSimpleName(), e);
		}
	}
}
