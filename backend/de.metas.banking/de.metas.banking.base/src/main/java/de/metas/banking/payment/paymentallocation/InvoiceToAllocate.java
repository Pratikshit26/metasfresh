package de.metas.banking.payment.paymentallocation;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.banking.base
     
 * #L%
 */

@Value
@Builder
public class InvoiceToAllocate
{
	// invoiceId or preparyOrderId shall non null
	@Nullable
	InvoiceId invoiceId;
	@Nullable
	OrderId prepayOrderId;

	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	String documentNo;

	@NonNull
	BPartnerId bpartnerId;
	@NonNull
	String bpartnerName;

	@NonNull
	LocalDate dateInvoiced;
	@NonNull
	LocalDate dateAcct;

	@NonNull
	CurrencyCode documentCurrencyCode;

	/**
	 * Date used to calculate the currency conversion and discount
	 */
	@NonNull
	ZonedDateTime evaluationDate;

	@NonNull
	InvoiceAmtMultiplier multiplier;
	@NonNull
	Amount grandTotal;
	@NonNull
	Amount openAmountConverted;
	@NonNull
	Amount discountAmountConverted;

	@NonNull
	DocTypeId docTypeId;
	@NonNull
	InvoiceDocBaseType docBaseType;

	@Nullable
	String poReference;

	@Nullable
	CurrencyConversionTypeId currencyConversionTypeId;
	
	public boolean grantDiscount(@NonNull final Amount amountToAllocate)
	{
		return openAmountConverted.subtract(discountAmountConverted).equals(amountToAllocate);
	}
}
