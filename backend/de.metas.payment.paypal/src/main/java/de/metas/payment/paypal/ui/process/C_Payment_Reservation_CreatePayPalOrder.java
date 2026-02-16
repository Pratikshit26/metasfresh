package de.metas.payment.paypal.ui.process;

import org.compiere.SpringContextHolder;

import de.metas.payment.paypal.PayPal;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/*
 * #%L
 * de.metas.payment.paypal
     
 * #L%
 */

public class C_Payment_Reservation_CreatePayPalOrder extends JavaProcess implements IProcessPrecondition
{
	private final PayPal paypal = SpringContextHolder.instance.getBean(PayPal.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final PaymentReservationId reservationId = PaymentReservationId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (reservationId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (paypal.hasActivePaypalOrder(reservationId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("active paypal order already exists");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PaymentReservationId reservationId = getPaymentReservationId();
		paypal.createPayPalOrderAndRequestPayerApproval(reservationId);
		return MSG_OK;
	}

	private PaymentReservationId getPaymentReservationId()
	{
		return PaymentReservationId.ofRepoId(getRecord_ID());
	}
}
