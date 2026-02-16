package de.metas.banking.payment.paymentallocation;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
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
@Builder
public class PaymentToAllocateQuery
{
	@Nullable
	BPartnerId bpartnerId;

	@NonNull
	ZonedDateTime evaluationDate;

	@NonNull
	@Singular("additionalPaymentIdToInclude")
	ImmutableSet<PaymentId> additionalPaymentIdsToInclude;
}
