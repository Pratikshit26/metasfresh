/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dhl.model;

import de.metas.mpackage.PackageId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
public class DhlCustomDeliveryDataDetail
{
	@NonNull PackageId packageId;

	@Nullable
	DhlSequenceNumber sequenceNumber;

	@Nullable
	byte[] pdfLabelData;

	@Nullable
	String awb;

	@Nullable
	String trackingUrl;

	boolean internationalDelivery;

	@Nullable
	DhlCustomsDocument customsDocument;
}

