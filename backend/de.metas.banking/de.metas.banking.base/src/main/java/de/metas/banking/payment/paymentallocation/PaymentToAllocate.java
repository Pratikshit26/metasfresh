package de.metas.banking.payment.paymentallocation;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentAmtMultiplier;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.banking.base
     
 * #L%
 */

@Value
@Builder
public class PaymentToAllocate
{
	@NonNull
	PaymentId paymentId;

	@NonNull
	ClientAndOrgId clientAndOrgId;

	@NonNull
	String documentNo;

	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	LocalDate dateTrx;
	@NonNull
	LocalDate dateAcct;

	@NonNull
	PaymentAmtMultiplier paymentAmtMultiplier;
	@NonNull
	Amount payAmt;
	@NonNull
	Amount openAmt;

	@NonNull
	PaymentDirection paymentDirection;

	@NonNull
	PaymentCurrencyContext paymentCurrencyContext;
}
