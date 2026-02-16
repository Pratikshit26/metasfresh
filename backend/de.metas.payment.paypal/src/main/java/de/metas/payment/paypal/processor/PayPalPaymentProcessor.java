package de.metas.payment.paypal.processor;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import de.metas.payment.PaymentRule;
import de.metas.payment.paypal.PayPal;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationCapture;
import de.metas.payment.reservation.PaymentReservationStatus;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
     
 * #L%
 */

@Component
public class PayPalPaymentProcessor implements PaymentProcessor
{
	private final PayPal paypal;

	public PayPalPaymentProcessor(@NonNull final PayPal payPal)
	{
		this.paypal = payPal;
	}

	@Override
	public PaymentRule getPaymentRule()
	{
		return PaymentRule.PayPal;
	}

	@Override
	public boolean canReserveMoney()
	{
		return true;
	}

	@Override
	public void processReservation(@NonNull final PaymentReservation reservation)
	{
		final PaymentReservationStatus status = reservation.getStatus();

		if (PaymentReservationStatus.WAITING_PAYER_APPROVAL.equals(status))
		{
			paypal.createPayPalOrderAndRequestPayerApproval(reservation);
		}
		else if (PaymentReservationStatus.APPROVED_BY_PAYER.equals(status))
		{
			paypal.authorizePayPalOrder(reservation);
		}
		else if (PaymentReservationStatus.COMPLETED.equals(status))
		{
			throw new AdempiereException("already completed: " + reservation);
		}
		else if (PaymentReservationStatus.VOIDED.equals(status))
		{
			throw new AdempiereException("Request for approval for a voided reservation makes no sense: " + reservation);
		}
		else
		{
			throw new AdempiereException("Unknown status: " + reservation);
		}
	}

	@Override
	public void processCapture(
			@NonNull final PaymentReservation reservation,
			@NonNull final PaymentReservationCapture capture)
	{
		paypal.processCapture(reservation, capture);
	}
}
