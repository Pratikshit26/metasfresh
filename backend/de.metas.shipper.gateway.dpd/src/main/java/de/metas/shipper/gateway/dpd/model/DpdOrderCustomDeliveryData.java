/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder(toBuilder = true)
@Value
public class DpdOrderCustomDeliveryData implements CustomDeliveryData
{
	String orderType;
	String sendingDepot;
	String printerLanguage;
	@NonNull
	DpdPaperFormat paperFormat;
	@NonNull
	DpdNotificationChannel notificationChannel;

	@Nullable
	byte[] pdfData;

	@NonNull
	public static DpdOrderCustomDeliveryData cast(@Nullable final CustomDeliveryData customDeliveryData)
	{
		if (customDeliveryData == null)
		{
			throw new AdempiereException("DPD custom delivery data should not be null");
		}
		return (DpdOrderCustomDeliveryData)customDeliveryData;
	}
}
