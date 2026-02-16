package de.metas.material.cockpit.availableforsales.interceptor;

import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo.ConfigQuery;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil.CheckAvailableForSalesRequest;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * #%L
 * metasfresh-material-cockpit
     
 * #L%
 */

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final AvailableForSalesUtil availableForSalesUtil;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;

	public C_Order(
			@NonNull final AvailableForSalesUtil availableForSalesUtil,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo)
	{
		this.availableForSalesUtil = availableForSalesUtil;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, // no need to run after new, because there are no orderLines yet
			ifColumnsChanged = I_C_Order.COLUMNNAME_PreparationDate)
	public void vaildateQtyAvailableForSale(@NonNull final I_C_Order orderRecord)
	{
		if (!availableForSalesUtil.isOrderEligibleForFeature(orderRecord))
		{
			return; // nothing to do
		}

		final OrgId orgId = OrgId.ofRepoId(orderRecord.getAD_Org_ID());

		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(orderRecord.getAD_Client_ID()))
						.orgId(orgId)
						.build());
		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		// has to contain everything that the method to be invoked after commit needs
		final List<CheckAvailableForSalesRequest> requests = availableForSalesUtil.createRequests(orderRecord);

		availableForSalesUtil.checkAndUpdateOrderLineRecords(requests, config, orgId);
	}


	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_Order.COLUMNNAME_PreparationDate)
	public void syncAvailableForSales(@NonNull final I_C_Order orderRecord)
	{
		if (!availableForSalesUtil.isOrderEligibleForFeature(orderRecord))
		{
			return; // nothing to do
		}

		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(orderRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(orderRecord.getAD_Org_ID()))
						.build());

		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		availableForSalesUtil.syncAvailableForSalesForOrder(orderRecord, config);
	}
}
