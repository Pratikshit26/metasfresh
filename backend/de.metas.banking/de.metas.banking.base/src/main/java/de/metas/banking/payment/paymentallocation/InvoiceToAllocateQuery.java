package de.metas.banking.payment.paymentallocation;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.banking.base
     
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class InvoiceToAllocateQuery
{
	/**
	 * Date used to calculate the currency conversion and discount
	 */
	@NonNull
	ZonedDateTime evaluationDate;
	
	@Nullable
	BPartnerId bpartnerId;

	@Nullable
	CurrencyId currencyId;

	@Nullable
	ClientAndOrgId clientAndOrgId;

	@NonNull
	@Singular
	ImmutableSet<InvoiceId> onlyInvoiceIds;

	@NonNull
	@Singular
	ImmutableSet<InvoiceId> excludeInvoiceIds;
}
