package de.metas.payment.paypal.client;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.payment.reservation.PaymentReservationId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
     
 * #L%
 */

@Service
public class PayPalOrderService
{
	private final PayPalOrderRepository paypalOrderRepo;

	public PayPalOrderService(@NonNull final PayPalOrderRepository paypalOrderRepository)
	{
		this.paypalOrderRepo = paypalOrderRepository;
	}

	public PayPalOrder getById(@NonNull final PayPalOrderId id)
	{
		return paypalOrderRepo.getById(id);
	}

	public PayPalOrder getByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		return getByReservationIdIfExists(reservationId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @PayPal_Order_ID@: " + reservationId));
	}

	public PayPalOrder getByExternalId(@NonNull final PayPalOrderExternalId externalId)
	{
		return paypalOrderRepo.getByExternalId(externalId);
	}

	public Optional<PayPalOrder> getByReservationIdIfExists(@NonNull final PaymentReservationId reservationId)
	{
		return paypalOrderRepo.getByReservationId(reservationId);
	}

	public PayPalOrder create(@NonNull final PaymentReservationId reservationId)
	{
		return paypalOrderRepo.create(reservationId);
	}

	public PayPalOrder save(
			@NonNull final PayPalOrderId id,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		return paypalOrderRepo.save(id, apiOrder);
	}

	public PayPalOrder markRemoteDeleted(@NonNull final PayPalOrderId id)
	{
		return paypalOrderRepo.markRemoteDeleted(id);
	}

}
