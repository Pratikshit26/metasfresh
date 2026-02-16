package de.metas.inoutcandidate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.inout.ShipmentScheduleId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
@Builder
public class ShipmentScheduleUserChangeRequest
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;

	@Nullable
	BigDecimal qtyToDeliverStockOverride;

	@Nullable
	BigDecimal qtyToDeliverCatchOverride;

	@Nullable
	AttributeSetInstanceId asiId;

	@Nullable
	LocalDate bestBeforeDate;
}
