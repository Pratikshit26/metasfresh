package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.InvoicesView;
import de.metas.ui.web.payment_allocation.InvoicesViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

abstract class InvoicesViewBasedProcess extends ViewBasedProcessTemplate
{

	@Override
	protected InvoicesView getView()
	{
		return InvoicesView.cast(super.getView());
	}
}
