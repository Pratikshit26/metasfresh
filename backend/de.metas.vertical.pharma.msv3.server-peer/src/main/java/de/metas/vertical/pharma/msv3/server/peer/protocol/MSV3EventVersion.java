package de.metas.vertical.pharma.msv3.server.peer.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
     
 * #L%
 */

/**
 * Currently used in {@link MSV3StockAvailabilityUpdatedEvent}. Might likewise be used in {@link MSV3UserChangedEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class MSV3EventVersion
{
	public static MSV3EventVersion of(final int asInt)
	{
		return new MSV3EventVersion(asInt);
	}

	int asInt;

	@JsonCreator
	private MSV3EventVersion(@JsonProperty("asInt") final int asInt)
	{
		if (asInt <= 0)
		{
			throw new IllegalArgumentException("asInt shall be > 0");
		}
		this.asInt = asInt;
	}
}
