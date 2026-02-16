/*
 * #%L
 * de.metas.shipper.gateway.spi
     
 * #L%
 */

package de.metas.shipper.gateway.spi.model;

import de.metas.mpackage.PackageId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * 1 DeliveryOrderLine represents 1 Package
 */
@Value
public class DeliveryOrderLine
{
	@Nullable
	String content;

	@NonNull BigDecimal grossWeightKg;

	@NonNull
	PackageDimensions packageDimensions;

	@Nullable
	CustomDeliveryData customDeliveryData;

	@NonNull
	PackageId packageId;

	@Builder(toBuilder = true)
	private DeliveryOrderLine(
			@Nullable final String content,
			@NonNull final BigDecimal grossWeightKg,
			@NonNull final PackageDimensions packageDimensions,
			@Nullable final CustomDeliveryData customDeliveryData,
			@NonNull final PackageId packageId)
	{

		Check.assume(grossWeightKg.signum() > 0, "grossWeightKg > 0");

		this.grossWeightKg = grossWeightKg;
		this.content = content;
		this.packageDimensions = packageDimensions;
		this.customDeliveryData = customDeliveryData;
		this.packageId = packageId;
	}
}
