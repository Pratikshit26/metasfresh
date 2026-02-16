package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

@Value
public class CommissionFact
{
	/** This fact's timestamp; note that we need chronology, but don't care for a particular timezone. */
	Instant timestamp;

	CommissionState state;

	CommissionPoints points;

	@JsonCreator
	@Builder
	private CommissionFact(
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("state") @NonNull final CommissionState state,
			@JsonProperty("points") @NonNull final CommissionPoints points)
	{
		this.timestamp = timestamp;
		this.state = state;
		this.points = points;
	}

	@NonNull
	public static Optional<CommissionFact> createFact(
			@NonNull final Instant timestamp,
			@NonNull final CommissionState state,
			@NonNull final CommissionPoints currentCommissionPoints,
			@NonNull final CommissionPoints previousCommissionPoints)
	{
		final CommissionPoints points = currentCommissionPoints.subtract(previousCommissionPoints);

		if (points.isZero())
		{
			return Optional.empty(); // a zero-points fact would not change anything, so don't bother creating it
		}

		final CommissionFact fact = CommissionFact.builder()
				.state(state)
				.points(points)
				.timestamp(timestamp)
				.build();
		return Optional.of(fact);
	}
}
