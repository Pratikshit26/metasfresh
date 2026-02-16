/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(exclude = { "delisID", "delisPassword" })
public class DpdClientConfig
{
	String loginApiUrl;
	String shipmentServiceApiUrl;
	String delisID;
	String delisPassword;
	String trackingUrlBase;
	DpdPaperFormat paperFormat;
	DpdShipperProduct shipperProduct;
}
