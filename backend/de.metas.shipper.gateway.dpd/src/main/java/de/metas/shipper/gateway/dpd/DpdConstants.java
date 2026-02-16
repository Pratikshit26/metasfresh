/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd;

import org.compiere.model.X_M_Shipper;

import de.metas.uom.X12DE355;

public class DpdConstants
{
	public static final String SHIPPER_GATEWAY_ID = X_M_Shipper.SHIPPERGATEWAY_DPD;

	public static final String DEFAULT_MESSAGE_LANGUAGE = "en_EN";

	public static final String DEFAULT_PRINTER_LANGUAGE = "PDF";

	public static final String TRACKING_URL = "https://tracking.dpd.de/status/en_US/parcel/";

	public static final X12DE355 DEFAULT_PACKAGE_DIMENSIONS_UOM = X12DE355.CENTIMETRE;
}
