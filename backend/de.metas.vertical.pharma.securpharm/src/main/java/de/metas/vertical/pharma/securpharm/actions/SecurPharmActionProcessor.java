package de.metas.vertical.pharma.securpharm.actions;

import javax.annotation.PostConstruct;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.metas.Profiles;
import de.metas.inventory.InventoryId;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

/**
 * Gets {@link SecurPharmaActionRequest}s from {@link SecurPharmService#subscribeOnActions(SecurPharmActionsHandler)} and process them.
 */
@Component
@Profile(Profiles.PROFILE_App)
public class SecurPharmActionProcessor implements SecurPharmActionsHandler
{
	private static final Logger logger = LogManager.getLogger(SecurPharmActionProcessor.class);
	private final SecurPharmService securPharmService;

	public SecurPharmActionProcessor(@NonNull final SecurPharmService securPharmService)
	{
		this.securPharmService = securPharmService;
		logger.info("Started");
	}

	@PostConstruct
	public void postConstruct()
	{
		securPharmService.subscribeOnActions(this);
	}

	@Override
	public void handleActionRequest(final SecurPharmaActionRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		try
		{
			trxManager.runInThreadInheritedTrx(() -> handleActionRequest0(request));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed processing {}", request, AdempiereException.extractCause(ex));
		}
	}

	private void handleActionRequest0(@NonNull final SecurPharmaActionRequest request)
	{
		final SecurPharmAction action = request.getAction();
		final InventoryId inventoryId = request.getInventoryId();
		final SecurPharmProductId productId = request.getProductId();

		if (SecurPharmAction.DECOMMISSION.equals(action))
		{
			if (productId == null)
			{
				securPharmService.decommissionProductsByInventoryId(inventoryId);
			}
			else
			{
				securPharmService.decommissionProductIfEligible(productId, inventoryId);
			}
		}
		else if (SecurPharmAction.UNDO_DECOMMISSION.equals(action))
		{
			if (productId == null)
			{
				securPharmService.undoDecommissionProductsByInventoryId(inventoryId);
			}
			else
			{
				securPharmService.undoDecommissionProductIfEligible(productId, inventoryId);
			}
		}
		else
		{
			throw new AdempiereException("Unknown action: " + action)
					.setParameter("event", request);
		}
	}
}
