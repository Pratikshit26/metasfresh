package de.metas.invoicecandidate.internalbusinesslogic;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class DeliveredData
{
	ShipmentData shipmentData;

	ReceiptData receiptData;

	@Builder
	@JsonCreator
	private DeliveredData(
			@JsonProperty("shipmentData") @Nullable final ShipmentData shipmentData,
			@JsonProperty("receiptData") @Nullable final ReceiptData receiptData)
	{
		this.shipmentData = shipmentData;
		this.receiptData = receiptData;

		if (shipmentData != null ^ /* XOR */ receiptData != null)
		{
			return; // OK
		}

		throw new AdempiereException("Exactly one of either shippedData or receiptQualityData needs to be not-null")
				.appendParametersToMessage()
				.setParameter("shippedData", shipmentData)
				.setParameter("receiptData", receiptData);
	}

}
