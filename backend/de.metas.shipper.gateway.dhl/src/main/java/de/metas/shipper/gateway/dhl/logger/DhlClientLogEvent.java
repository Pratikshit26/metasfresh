/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.logger;

import de.metas.shipper.gateway.dhl.json.JSONDhlCreateOrderRequest;
import de.metas.shipper.gateway.dhl.json.JSONDhlCreateOrderResponse;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DhlClientLogEvent
{
	/**
	 * Regexp explanation: .*? means ungreedy (while .* means greedy).
	 */

	DeliveryOrderId deliveryOrderId;
	DhlClientConfig config;
	@NonNull JSONDhlCreateOrderRequest requestElement;

	@Nullable
	JSONDhlCreateOrderResponse responseElement;

	@Nullable
	Exception responseException;

	long durationMillis;

	String getConfigSummary()
	{
		return config != null ? config.toString() : "";
	}

	@NonNull
	String getRequestAsString()
	{
		return String.valueOf(requestElement);
	}

	@Nullable
	String getResponseAsString()
	{
		return responseElement == null ? null : String.valueOf(responseElement.withNoLabelData());
	}

}
