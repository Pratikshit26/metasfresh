package de.metas.vertical.pharma.securpharm.service;

import java.util.ArrayList;
import java.util.List;

import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionsDispatcher;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmActionsHandler;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRequest;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

public final class DirectSecurPharmActionsDispatcher implements SecurPharmActionsDispatcher
{
	private final List<SecurPharmActionsHandler> handlers = new ArrayList<>();

	@Override
	public void post(@NonNull final SecurPharmaActionRequest request)
	{
		handlers.forEach(handler -> handler.handleActionRequest(request));
	}

	@Override
	public void subscribe(@NonNull final SecurPharmActionsHandler handler)
	{
		handlers.add(handler);
	}

}
