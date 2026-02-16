package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Customer;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/**
 * "basically" an invoice candidate; but can be other things in future as well.
 */
@Value
public class CommissionTrigger
{
	Customer customer;

	/**
	 * The direct sales rep;
	 * <p>
	 * Note: used to be the customer's "direct" sales rep or the customer himself. Now it's always the sales rep as it's up to the commission algorithm to decide
	 * whether the customer can get something out of it or not.
	 */
	BPartnerId salesRepId;

	BPartnerId orgBPartnerId;

	CommissionTriggerData commissionTriggerData;

	@Builder
	private CommissionTrigger(
			@NonNull final Customer customer,
			@NonNull final BPartnerId salesRepId,
			@NonNull final BPartnerId orgBPartnerId,
			@NonNull final CommissionTriggerData commissionTriggerData)
	{
		this.customer = customer;
		this.salesRepId = salesRepId;
		this.orgBPartnerId = orgBPartnerId;
		this.commissionTriggerData = commissionTriggerData;
	}
}
