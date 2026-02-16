/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import com.google.common.collect.ImmutableList;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Builder(toBuilder = true)
@Value
public class DhlCustomDeliveryData implements CustomDeliveryData
{
	@Singular
	@NonNull
	private final ImmutableList<DhlCustomDeliveryDataDetail> details;

	@NonNull
	public static DhlCustomDeliveryData cast(@NonNull final CustomDeliveryData customDeliveryData)
	{
		return (DhlCustomDeliveryData)customDeliveryData;
	}

	@NonNull
	public ImmutableList<DhlCustomDeliveryDataDetail> getDetails()
	{
		return ImmutableList.copyOf(details);
	}

	@NonNull
	public DhlCustomDeliveryDataDetail getDetailByPackageId(final PackageId packageId)
	{
		//noinspection OptionalGetWithoutIsPresent
		return details.stream()
				.filter(it -> Objects.equals(it.getPackageId(),  packageId))
				.findFirst()
				.get();
	}

	@NonNull
	public DhlCustomDeliveryDataDetail getDetailBySequenceNumber(@NonNull final DhlSequenceNumber sequenceNumber)
	{
		//noinspection OptionalGetWithoutIsPresent
		return details.stream()
				.filter(it -> it.getSequenceNumber().equals(sequenceNumber))
				.findFirst()
				.get();
	}

	@NonNull
	public DhlCustomDeliveryData withDhlCustomDeliveryDataDetails(@Nullable final ImmutableList<DhlCustomDeliveryDataDetail> details)
	{
		if (details == null)
		{
			return this;
		}
		return toBuilder()
				.clearDetails()
				.details(details)
				.build();
	}

}
