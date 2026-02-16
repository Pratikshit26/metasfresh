package de.metas.bpartner.product.stats;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.product.stats.InOutChangedEvent.InOutChangedEventBuilder;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@JsonDeserialize(builder = InOutChangedEventBuilder.class)
public class InOutChangedEvent
{
	BPartnerId bpartnerId;
	Instant movementDate;
	SOTrx soTrx;
	boolean reversal;

	Set<ProductId> productIds;

	@Builder
	private InOutChangedEvent(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant movementDate,
			@NonNull final SOTrx soTrx,
			final boolean reversal,
			@NonNull final Set<ProductId> productIds)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		this.bpartnerId = bpartnerId;
		this.movementDate = movementDate;
		this.soTrx = soTrx;
		this.reversal = reversal;
		this.productIds = productIds;
	}

	@JsonPOJOBuilder(withPrefix = "")
	public static class InOutChangedEventBuilder
	{
	}

}
