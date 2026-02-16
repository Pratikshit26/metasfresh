package de.metas.material.cockpit.availableforsales.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo.ConfigQuery;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil.CheckAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.model.I_C_OrderLine;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * metasfresh-material-cockpit
     
 * #L%
 */

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final AvailableForSalesUtil availableForSalesUtil;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;

	public C_OrderLine(
			@NonNull final AvailableForSalesUtil availableForSalesUtil,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo)
	{
		this.availableForSalesUtil = availableForSalesUtil;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_QtyOrdered,
					I_C_OrderLine.COLUMNNAME_M_Product_ID,
					I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID })
	public void validateQtyAvailableForSale(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (!availableForSalesUtil.isOrderLineEligibleForFeature(orderLineRecord))
		{
			return; // nothing to do
		}

		if (!availableForSalesUtil.isOrderEligibleForFeature(orderLineRecord.getC_Order()))
		{
			return; // nothing to do
		}

		final OrgId orgId = OrgId.ofRepoId(orderLineRecord.getAD_Org_ID());

		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(orderLineRecord.getAD_Client_ID()))
						.orgId(orgId)
						.build());
		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		// has to contain everything that the method to be invoked after commit needs
		final CheckAvailableForSalesRequest checkAvailableForSalesRequest = availableForSalesUtil.createRequest(orderLineRecord);

		availableForSalesUtil.checkAndUpdateOrderLineRecords(ImmutableList.of(checkAvailableForSalesRequest), config, orgId);
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyOrdered,
					I_C_OrderLine.COLUMNNAME_M_Product_ID,
					I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID,
					I_C_OrderLine.COLUMNNAME_AD_Org_ID })
	public void triggerSyncAvailableForSales(@NonNull final I_C_OrderLine orderLineRecord)
	{
		syncOrderLineWithAvailableForSales(orderLineRecord);

		if (!InterfaceWrapperHelper.isNew(orderLineRecord))
		{
			final I_C_OrderLine orderLineOld = InterfaceWrapperHelper.createOld(orderLineRecord, I_C_OrderLine.class);

			syncOrderLineWithAvailableForSales(orderLineOld);
		}
	}

	private void syncOrderLineWithAvailableForSales(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (!availableForSalesUtil.isOrderLineEligibleForFeature(orderLineRecord))
		{
			return;
		}

		final boolean isOrderEligibleForFeature = availableForSalesUtil.isOrderEligibleForFeature(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()));

		if (!isOrderEligibleForFeature)
		{
			return;
		}

		final AvailableForSalesConfig config = getAvailableForSalesConfig(orderLineRecord);

		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		availableForSalesUtil.syncAvailableForSalesForOrderLine(orderLineRecord, config);
	}

	@NonNull
	private AvailableForSalesConfig getAvailableForSalesConfig(@NonNull final I_C_OrderLine orderLineRecord)
	{
		return availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(orderLineRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(orderLineRecord.getAD_Org_ID()))
						.build());
	}
}
