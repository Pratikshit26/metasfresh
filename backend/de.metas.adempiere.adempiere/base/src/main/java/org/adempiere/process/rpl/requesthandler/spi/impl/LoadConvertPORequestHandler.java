package org.adempiere.process.rpl.requesthandler.spi.impl;

 


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.compiere.model.PO;

import de.metas.util.Check;
import de.metas.util.collections.Converter;

/**
 * An extension of {@link LoadPORequestHandler} which takes an {@link Converter} for creating the response based on request
 * 
 * @author tsa
 * 
 * @param <IT> request model interface class
 * @param <OT> response model interface class
 */
public class LoadConvertPORequestHandler<IT, OT> extends LoadPORequestHandler
{
	private final Class<IT> requestModelClass;
	private final Converter<OT, IT> converter;

	public LoadConvertPORequestHandler(final Class<IT> requestModelClass, final Converter<OT, IT> converter)
	{
		Check.assumeNotNull(requestModelClass, "requestModelClass not null");
		Check.assumeNotNull(converter, "converter not null");
		this.requestModelClass = requestModelClass;
		this.converter = converter;
	}

	@Override
	protected PO createResponse(final IReplRequestHandlerCtx ctx, final PO requestPO)
	{
		Check.assume(requestPO != null, "requestPO not null");

		final IT requestModel = InterfaceWrapperHelper.create(requestPO, requestModelClass);

		final OT responseModel = converter.convert(requestModel);

		final PO responsePO = InterfaceWrapperHelper.getPO(responseModel);
		return responsePO;
	}
}
