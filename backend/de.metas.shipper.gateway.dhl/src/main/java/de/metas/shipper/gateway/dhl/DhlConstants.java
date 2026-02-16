/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl;

import org.compiere.model.X_M_Shipper;

public class DhlConstants
{
	public static final String SHIPPER_GATEWAY_ID = X_M_Shipper.SHIPPERGATEWAY_DHL;

	//they don't use X12DE355
	public static final String KILOGRAM_UOM = "kg";

	public static final String DHL_API_KEY_HTTP_HEADER = "Dhl-Api-Key";
	public static final String STANDARD_GRUPPENPROFIL = "STANDARD_GRUPPENPROFIL";
	public static final String REMOVED = "<<removed>>";
}
