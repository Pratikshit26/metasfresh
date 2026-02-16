package de.metas.material.cockpit.availableforsales;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

import javax.annotation.Nullable;

import de.metas.util.Check;
import de.metas.util.ColorId;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-cockpit
     
 * #L%
 */

@Value
public class AvailableForSalesConfig
{
	boolean featureEnabled;

	ColorId insufficientQtyAvailableForSalesColorId;

	int shipmentDateLookAheadHours;

	int salesOrderLookBehindHours;

	boolean runAsync;

	int asyncTimeoutMillis;

	@Builder
	private AvailableForSalesConfig(
			@Nullable final ColorId insufficientQtyAvailableForSalesColorId,
			final int shipmentDateLookAheadHours,
			final int salesOrderLookBehindHours,
			final boolean featureEnabled,
			final boolean runAsync,
			final int asyncTimeoutMillis)
	{
		this.featureEnabled = featureEnabled;
		this.insufficientQtyAvailableForSalesColorId = insufficientQtyAvailableForSalesColorId;
		this.shipmentDateLookAheadHours = shipmentDateLookAheadHours;
		this.salesOrderLookBehindHours = salesOrderLookBehindHours;
		this.runAsync = runAsync;

		// we allow zero so people can check the async-error-handling
		this.asyncTimeoutMillis = assumeGreaterOrEqualToZero(asyncTimeoutMillis, "asyncTimeoutMillis");

		if (featureEnabled)
		{
			Check.assumeNotNull(insufficientQtyAvailableForSalesColorId, "If the feature is enabled, then insufficientQtyAvailableForSalesColorId may not be null");
			Check.assume(shipmentDateLookAheadHours >= 0, "If the feature is enabled, then shipmentDateLookAheadHours={} may not be < 0", shipmentDateLookAheadHours);
			Check.assume(salesOrderLookBehindHours >= 0, "If the feature is enabled, then shipmentDateLookAheadHours={} may not be < 0", salesOrderLookBehindHours);
		}
	}

}
