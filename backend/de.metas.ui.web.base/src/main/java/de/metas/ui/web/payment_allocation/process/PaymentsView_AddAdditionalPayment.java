package de.metas.ui.web.payment_allocation.process;

import de.metas.payment.PaymentId;
import de.metas.process.Param;
import de.metas.ui.web.payment_allocation.PaymentsView;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class PaymentsView_AddAdditionalPayment extends PaymentsViewBasedProcess
{
	@Param(parameterName = "C_Payment_ID", mandatory = true)
	private PaymentId paymentId;

	@Override
	protected String doIt()
	{
		final PaymentsView view = getView();
		view.addPayment(paymentId);
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (success)
		{
			invalidateView();
		}
	}
}
