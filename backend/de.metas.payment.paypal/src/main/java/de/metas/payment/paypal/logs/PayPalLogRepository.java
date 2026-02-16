package de.metas.payment.paypal.logs;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.payment.paypal.model.I_PayPal_Log;
import de.metas.payment.reservation.PaymentReservationId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
     
 * #L%
 */

@Repository
public class PayPalLogRepository
{
	private static final Logger logger = LogManager.getLogger(PayPalLogRepository.class);

	private final ObjectMapper jsonObjectMapper;

	public PayPalLogRepository(
			@NonNull final Optional<ObjectMapper> jsonObjectMapper)
	{
		if (jsonObjectMapper.isPresent())
		{
			this.jsonObjectMapper = jsonObjectMapper.get();
		}
		else
		{
			this.jsonObjectMapper = new ObjectMapper();
			logger.warn("Using internal JSON object mapper");
		}
	}

	public void log(@NonNull final PayPalCreateLogRequest log)
	{
		final I_PayPal_Log record = newInstanceOutOfTrx(I_PayPal_Log.class);

		record.setRequestPath(log.getRequestPath());
		record.setRequestMethod(log.getRequestMethod());
		record.setRequestHeaders(toJson(log.getRequestHeaders()));

		record.setResponseCode(log.getResponseStatusCode());
		record.setResponseHeaders(toJson(log.getResponseHeaders()));
		record.setResponseBody(log.getResponseBodyAsJson());

		record.setC_Order_ID(OrderId.toRepoId(log.getSalesOrderId()));
		record.setC_Payment_Reservation_ID(PaymentReservationId.toRepoId(log.getPaymentReservationId()));
		record.setPayPal_Order_ID(PayPalOrderId.toRepoId(log.getInternalPayPalOrderId()));

		saveRecord(record);
	}

	private String toJson(final Object obj)
	{
		if (obj == null)
		{
			return "";
		}

		try
		{
			return jsonObjectMapper.writeValueAsString(obj);
		}
		catch (JsonProcessingException ex)
		{
			logger.warn("Failed converting object to JSON. Returning toString(): {}", obj, ex);
			return obj.toString();
		}
	}
}
