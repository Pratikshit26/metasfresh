package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoicecandidate.InvoiceCandidateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

@Value
public class CommissionSettlementFact
{
	InvoiceCandidateId settlementInvoiceCandidateId;

	/** This fact's timestamp; note that we need chronology, but don't care for a particular timezone. */
	Instant timestamp;

	CommissionSettlementState state;

	CommissionPoints points;

	@JsonCreator
	@Builder
	private CommissionSettlementFact(
			@JsonProperty("settlementInvoiceCandidateId") @NonNull final InvoiceCandidateId settlementInvoiceCandidateId,
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("state") @NonNull final CommissionSettlementState state,
			@JsonProperty("points") @NonNull final CommissionPoints points)
	{
		this.settlementInvoiceCandidateId = settlementInvoiceCandidateId;
		this.timestamp = timestamp;
		this.state = state;
		this.points = points;
	}
}
