package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.ui.web.window.datatypes.LookupValue;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Value
@Builder
public class ShipmentCandidateRowUserChangeRequest
{
	@Nullable
	BigDecimal qtyToDeliverUserEntered;

	@Nullable
	BigDecimal qtyToDeliverCatchOverride;

	@Nullable
	LookupValue asi;
}
