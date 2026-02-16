/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

package de.metas.ui.web.quickinput.field;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.lang.SOTrx;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@Builder
public class DefaultPackingItemCriteria
{
	@NonNull
	private ProductId productId;

	@NonNull
	private BPartnerLocationAndCaptureId bPartnerLocationId;

	@NonNull
	private ZonedDateTime date;

	@Nullable
	private PricingSystemId pricingSystemId;

	@Nullable
	private PriceListId priceListId;

	@Nullable
	private SOTrx soTrx;
	
	@NonNull
	private ClientId clientId;

	public static Optional<DefaultPackingItemCriteria> of(final I_C_Order order, final ProductId productId)
	{
		final BPartnerLocationAndCaptureId bpartnerLocationId = OrderDocumentLocationAdapterFactory.locationAdapter(order).getBPartnerLocationAndCaptureId();
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(order.getDatePromised());
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());

		final boolean anyNull = Stream.of(bpartnerLocationId, pricingSystemId, date, productId).anyMatch(Objects::isNull);

		if (anyNull) {
			return Optional.empty();
		}

		return Optional.of(
				builder()
						.bPartnerLocationId(bpartnerLocationId)
						.productId(productId)
						.pricingSystemId(pricingSystemId)
						.date(date)
						.soTrx(soTrx)
						.clientId(clientId)
						.build() );
	}

	public static Optional<DefaultPackingItemCriteria> of(@NonNull final I_C_Invoice invoice, @NonNull final ProductId productId)
	{
		final BPartnerLocationAndCaptureId bpartnerLocationId = InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureId();
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(invoice.getM_PriceList_ID());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(invoice.getDateInvoiced());
		final ClientId clientId = ClientId.ofRepoId(invoice.getAD_Client_ID());
		
		final boolean anyNull = Stream.of(bpartnerLocationId,priceListId,date, productId).anyMatch(Objects::isNull);

		if (anyNull) {
			return Optional.empty();
		}

		return Optional.of(
				builder()
						.productId(productId)
						.priceListId(priceListId)
						.date(date)
						.bPartnerLocationId(bpartnerLocationId)
						.clientId(clientId)
						.build());
	}
}
