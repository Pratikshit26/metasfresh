package de.metas.banking.payment.paymentallocation.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.util.OptionalDeferredException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
     
 * #L%
 */

@Value
@Builder
public class PaymentAllocationResult
{
	@NonNull ImmutableList<AllocationLineCandidate> candidates;
	@NonNull OptionalDeferredException<PaymentAllocationException> fullyAllocatedCheck;
	@NonNull ImmutableMap<PaymentAllocationId, AllocationLineCandidate> paymentAllocationIds;

	public boolean isOK()
	{
		return fullyAllocatedCheck.isNoError();
	}
}
