package org.adempiere.process.rpl.requesthandler.spi;

 


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.compiere.model.PO;

public abstract class ReplRequestHandlerAdapter implements IReplRequestHandler
{

	@Override
	public abstract IReplRequestHandlerResult handleRequest(PO po, IReplRequestHandlerCtx ctx);
}
