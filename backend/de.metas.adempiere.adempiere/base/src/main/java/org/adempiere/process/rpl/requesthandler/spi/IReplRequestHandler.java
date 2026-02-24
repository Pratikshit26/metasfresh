package org.adempiere.process.rpl.requesthandler.spi;

 


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.compiere.model.PO;

/**
 * SPI to be extended by request handler implementations.
 * <p/>
 * Please consider extending the adapter: {@link ReplRequestHandlerAdapter}.
 * 
 * @author ts
 * 
 */
public interface IReplRequestHandler
{
	/**
	 * Note that an instance can use {@link IReplRequestHandlerBL#createInitialRequestHandlerResult()} to get and
	 * initial result instance to fill.
	 * 
	 * @param po
	 *            the PO that was just received via replication interface and that therefore represents the request
	 * @param ctx
	 *            context info to be used when processing the request
	 */
	IReplRequestHandlerResult handleRequest(PO po, IReplRequestHandlerCtx ctx);
}
