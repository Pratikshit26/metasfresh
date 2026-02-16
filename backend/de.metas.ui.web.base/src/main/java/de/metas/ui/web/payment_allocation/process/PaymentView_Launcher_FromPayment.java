package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableSet;
import de.metas.payment.PaymentId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.PaymentsViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class PaymentView_Launcher_FromPayment extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<PaymentId> paymentIds = getSelectedPaymentIds();
		if (paymentIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ImmutableSet<PaymentId> paymentIds = getSelectedPaymentIds();
		if (paymentIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final ViewId viewId = viewsFactory.createView(CreateViewRequest.builder(PaymentsViewFactory.WINDOW_ID)
				.setParameter(PaymentsViewFactory.PARAMETER_TYPE_SET_OF_PAYMENT_IDS, paymentIds)
				.build())
				.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
				.viewId(viewId.getViewId())
				.target(ViewOpenTarget.ModalOverlay)
				.build());

		return MSG_OK;
	}

	private ImmutableSet<PaymentId> getSelectedPaymentIds()
	{
		return getSelectedRowIds()
				.stream()
				.map(rowId -> PaymentId.ofRepoId(rowId.toInt()))
				.collect(ImmutableSet.toImmutableSet());
	}
}
