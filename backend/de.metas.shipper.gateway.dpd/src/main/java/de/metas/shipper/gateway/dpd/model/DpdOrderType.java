/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

/**
 * Defines the shipment type.
 * It is somehow related to service type (aka dpd product), but i don't understand how.
 */
public class DpdOrderType
{
	public static final String CONSIGNMENT = "consignment"; // i think this is the default
	public static final String COLLECTION_REQUEST_ORDER = "collection request order";
	public static final String PICKUP_INFORMATION = "pickup information";
}
