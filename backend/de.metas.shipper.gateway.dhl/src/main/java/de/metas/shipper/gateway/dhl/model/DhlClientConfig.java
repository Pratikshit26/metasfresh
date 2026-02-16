/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString(of = { "baseUrl" })
public class DhlClientConfig
{
	@NonNull
	String baseUrl;

	@NonNull
	String applicationID; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@NonNull
	String applicationToken; // CIG Auth (https://entwickler.dhl.de/en/group/ep/authentifizierung)

	@NonNull
	String accountNumber; // DHL Business Customer Portal (also presented as EKP)

	@NonNull
	String username; // DHL Business Customer Portal

	@NonNull
	String signature; // DHL Business Customer Portal

	@NonNull
	UomId lengthUomId;

	@NonNull
	String trackingUrlBase;

}
