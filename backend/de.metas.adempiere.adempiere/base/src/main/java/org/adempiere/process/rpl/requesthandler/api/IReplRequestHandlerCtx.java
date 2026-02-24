package org.adempiere.process.rpl.requesthandler.api;

 


import java.util.Properties;

import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.process.rpl.requesthandler.spi.IReplRequestHandler;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;

/**
 * Instances are created by {@link IReplRequestHandlerBL#createCtx(Properties, I_EXP_Format, I_IMP_RequestHandler)} and
 * contain context info for {@link IReplRequestHandler#handleRequest(org.compiere.model.PO, IReplRequestHandlerCtx)}.
 * 
 * @author ts
 * 
 */
public interface IReplRequestHandlerCtx
{
	public Properties getEnvCtxToUse();

	public I_EXP_Format getImportFormat();

	public I_IMP_RequestHandler getRequestHandlerRecord();
}
