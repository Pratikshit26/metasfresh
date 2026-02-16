/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class DhlCustomsDocument
{
	@Nullable String shipperEORI;
	@Nullable String consigneeEORI;
	@NonNull List<DhlCustomsItem> items;
}
