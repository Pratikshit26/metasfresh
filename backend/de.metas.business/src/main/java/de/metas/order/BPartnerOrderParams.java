package de.metas.order;

import java.util.Optional;

import de.metas.freighcost.FreightCostRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

/**
 * Holds bpartner related parameters that are required for an oder.
 * Note that e.g. the delivery rule might be related to the order's drop ship partner, while the pricing system id might be related to the order's bill partner.
 */
@Value
@Builder
public class BPartnerOrderParams
{
	@NonNull
	Optional<DeliveryRule> deliveryRule;

	@NonNull
	Optional<DeliveryViaRule> deliveryViaRule;

	@NonNull
	Optional<FreightCostRule> freightCostRule;

	@NonNull
	Optional<InvoiceRule> invoiceRule;

	@NonNull
	PaymentRule paymentRule;

	@NonNull
	Optional<PaymentTermId> paymentTermId;

	@NonNull
	Optional<PricingSystemId> pricingSystemId;

	@NonNull
	Optional<ShipperId> shipperId;
}
