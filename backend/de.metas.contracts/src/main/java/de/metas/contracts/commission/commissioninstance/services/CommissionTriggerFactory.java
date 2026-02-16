package de.metas.contracts.commission.commissioninstance.services;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData.CommissionTriggerDataBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocument;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Service
public class CommissionTriggerFactory
{

	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);

	/**
	 * @param documentDeleted might be true for invoice candidates
	 */
	@NonNull
	public CommissionTrigger createForDocument(
			@NonNull final CommissionTriggerDocument commissionTriggerDocument,
			final boolean documentDeleted)
	{
		final CommissionTriggerData triggerData = createForRequest(commissionTriggerDocument, documentDeleted);

		final Customer customer = Customer.of(commissionTriggerDocument.getCustomerBPartnerId());

		final BPartnerId orgBPartnerId = bPartnerOrgBL.retrieveLinkedBPartnerId(commissionTriggerDocument.getOrgId())
				.orElseThrow(() -> new AdempiereException("NO BPartner found for org:" + commissionTriggerDocument.getOrgId()));

		final CommissionTrigger trigger = CommissionTrigger.builder()
				.customer(customer)
				.salesRepId(commissionTriggerDocument.getSalesRepBPartnerId())
				.orgBPartnerId(orgBPartnerId)
				.commissionTriggerData(triggerData)
				.build();

		return trigger;
	}

	@NonNull
	private CommissionTriggerData createForRequest(
			@NonNull final CommissionTriggerDocument commissionTriggerDocument,
			final boolean documentDeleted)
	{
		final CommissionTriggerDataBuilder builder = CommissionTriggerData
				.builder()
				.orgId(commissionTriggerDocument.getOrgId())
				.invoiceCandidateWasDeleted(documentDeleted)
				.triggerType(commissionTriggerDocument.getTriggerType())
				.triggerDocumentId(commissionTriggerDocument.getId())
				.triggerDocumentDate(commissionTriggerDocument.getCommissionDate())
				.timestamp(commissionTriggerDocument.getUpdated())
				.productId(commissionTriggerDocument.getProductId())
				.totalQtyInvolved(commissionTriggerDocument.getTotalQtyInvolved())
				.documentCurrencyId(commissionTriggerDocument.getDocumentCurrencyId());

		if (documentDeleted)
		{
			builder
					.forecastedBasePoints(CommissionPoints.ZERO)
					.invoiceableBasePoints(CommissionPoints.ZERO)
					.invoicedBasePoints(CommissionPoints.ZERO);
		}
		else
		{
			builder
					.forecastedBasePoints(commissionTriggerDocument.getForecastCommissionPoints())
					.invoiceableBasePoints(commissionTriggerDocument.getCommissionPointsToInvoice())
					.invoicedBasePoints(commissionTriggerDocument.getInvoicedCommissionPoints());
		}
		return builder.build();
	}

}
