/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class DhlSequenceNumber
{
	private final String sequenceNumber;

	private DhlSequenceNumber(@NonNull final String sequenceNumber)
	{
		this.sequenceNumber = sequenceNumber;
	}

	@NonNull
	public static DhlSequenceNumber of(final int number)
	{
		return new DhlSequenceNumber(Integer.toString(number));
	}

	@NonNull
	public static DhlSequenceNumber of(@NonNull final String sequenceNumber)
	{
		return new DhlSequenceNumber(sequenceNumber);
	}

}
