package org.adempiere.process.rpl.requesthandler.api;

 


import java.util.Properties;

import org.adempiere.process.rpl.exp.ExportHelper;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType;
import org.adempiere.process.rpl.requesthandler.spi.IReplRequestHandler;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.w3c.dom.Document;

import de.metas.util.ISingletonService;

public interface IReplRequestHandlerBL extends ISingletonService
{

	/**
	 * Makes sure that a request handler type with the given <code>clazz</code> and is registered in ADempiere. If the clazz
	 * is already registered the method assumes that it has the given 'entityType' and <code>AD_Client_ID=0</code>.
	 * 
	 * @param name
	 * @param value
	 * @param clazz
	 * @param entityType
	 * @return
	 */
	I_IMP_RequestHandlerType registerHandlerType(String name, Class<? extends IReplRequestHandler> clazz, String entityType);

	/**
	 * Instantiates and returns the SPI implementer class that is defined in the given <code>I_IMP_RequestHandler</code>'s 
	 * <code>I_IMP_RequestHandlerType</code>.
	 * 
	 * @param requestHandler
	 * @return
	 */
	IReplRequestHandler getRequestHandlerInstance(I_IMP_RequestHandler requestHandler);

	/**
	 * Creates an initial (empty) request handler result. It is expected that {@link IReplRequestHandler} implementations use
	 * this method to obtain a result instance which they can fill.
	 * 
	 * @return
	 * @see {@link IReplRequestHandler}
	 */
	IReplRequestHandlerResult createInitialRequestHandlerResult();

	/**
	 * Creates the request handler context with information to be used by a request handler implementation. It is
	 * expected that this SPI implementation doesn't attempt to change the context.
	 * 
	 * @param ctxForHandler
	 *            the environment CTX that the SPI implementation shall use when accessing ADempiere data
	 * @param importFormat
	 *            the <code>EXP_Format</code> that was used to import the request
	 * @param requestHandlerPO
	 *            the <code>IMP_RequestHandler</code> record whose implementation class will be called to do the
	 *            handling.
	 * 
	 * @return
	 * @see {@link IReplRequestHandler}
	 */
	IReplRequestHandlerCtx createCtx(Properties ctxForHandler, I_EXP_Format importFormat, I_IMP_RequestHandler requestHandlerPO);

	/**
	 * Processes the given <code>result</code> by calling {@link ExportHelper} to create a DOM tree for the result.
	 * 
	 * @param result
	 * @return
	 */
	Document processRequestHandlerResult(IReplRequestHandlerResult result);
}
