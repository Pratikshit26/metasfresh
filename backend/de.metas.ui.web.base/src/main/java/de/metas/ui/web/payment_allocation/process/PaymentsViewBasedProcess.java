package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.InvoicesView;
import de.metas.ui.web.payment_allocation.InvoicesViewFactory;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.ui.web.payment_allocation.PaymentsView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

abstract class PaymentsViewBasedProcess extends ViewBasedProcessTemplate
{
	private transient ImmutableList<PaymentRow> _paymentRowsSelectedForAllocation;
	private transient ImmutableList<InvoiceRow> _invoiceRowsSelectedForAllocation;

	@Override
	protected final PaymentsView getView()
	{
		return PaymentsView.cast(super.getView());
	}

	protected final PaymentsView getPaymentsView()
	{
		return getView();
	}

	protected final DocumentIdsSelection getSelectedPaymentRowIdsIncludingDefaultRow()
	{
		return getSelectedRowIds();
	}

	protected final InvoicesView getInvoicesView()
	{
		return getPaymentsView().getInvoicesView();
	}

	private DocumentIdsSelection getSelectedInvoiceRowIds()
	{
		return getChildViewSelectedRowIds();
	}

	//
	//
	//

	protected final ImmutableList<PaymentRow> getPaymentRowsSelectedForAllocation()
	{
		ImmutableList<PaymentRow> paymentRowsSelectedForAllocation = this._paymentRowsSelectedForAllocation;
		if (paymentRowsSelectedForAllocation == null)
		{
			paymentRowsSelectedForAllocation = this._paymentRowsSelectedForAllocation = computePaymentRowsSelectedForAllocation();
		}
		return paymentRowsSelectedForAllocation;
	}

	private ImmutableList<PaymentRow> computePaymentRowsSelectedForAllocation()
	{
		final DocumentIdsSelection selectedPaymentRowIds = getSelectedPaymentRowIdsIncludingDefaultRow();

		return getPaymentsView().streamByIds(selectedPaymentRowIds)
				.filter(row -> !row.equals(PaymentRow.DEFAULT_PAYMENT_ROW))
				.collect(ImmutableList.toImmutableList());
	}

	protected final ImmutableList<InvoiceRow> getInvoiceRowsSelectedForAllocation()
	{
		ImmutableList<InvoiceRow> invoiceRowsSelectedForAllocation = this._invoiceRowsSelectedForAllocation;
		if (invoiceRowsSelectedForAllocation == null)
		{
			invoiceRowsSelectedForAllocation = this._invoiceRowsSelectedForAllocation = computeInvoiceRowsSelectedForAllocation();
		}
		return invoiceRowsSelectedForAllocation;
	}

	private ImmutableList<InvoiceRow> computeInvoiceRowsSelectedForAllocation()
	{
		final InvoicesView invoicesView = getInvoicesView();
		if (InvoicesViewFactory.isEnablePreparedForAllocationFlag())
		{
			return invoicesView
					.streamByIds(DocumentIdsSelection.ALL)
					.filter(InvoiceRow::isPreparedForAllocation)
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return invoicesView
					.streamByIds(getSelectedInvoiceRowIds())
					.collect(ImmutableList.toImmutableList());
		}
	}

	protected final void invalidatePaymentsAndInvoicesViews()
	{
		final InvoicesView invoicesView = getInvoicesView();

		invoicesView.unmarkPreparedForAllocation(DocumentIdsSelection.ALL);

		invalidateView(invoicesView);
		invalidateView(getPaymentsView());
	}
}
