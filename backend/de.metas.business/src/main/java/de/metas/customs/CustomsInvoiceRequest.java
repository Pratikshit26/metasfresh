package de.metas.customs;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.SetMultimap;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutAndLineId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class CustomsInvoiceRequest
{
	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	String bpartnerAddress;

	@Nullable
	UserId userId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	SetMultimap<ProductId, InOutAndLineId> linesToExportMap;

	@NonNull
	LocalDate invoiceDate;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	String documentNo;

}
