package de.metas.ui.web.payment_allocation;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class InvoicesView extends AbstractCustomView<InvoiceRow> implements IEditableView
{
	public static InvoicesView cast(final IView view)
	{
		return (InvoicesView)view;
	}

	private final ImmutableList<RelatedProcessDescriptor> processes;

	@Builder
	private InvoicesView(
			final ViewId viewId,
			final InvoiceRows rows,
			@Nullable final List<RelatedProcessDescriptor> processes)
	{
		super(viewId,
				TranslatableStrings.empty(),
				rows,
				NullDocumentFilterDescriptorsProvider.instance);

		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}

	@Override
	protected InvoiceRows getRowsData()
	{
		return InvoiceRows.cast(super.getRowsData());
	}

	public void addInvoice(@NonNull final InvoiceId invoiceId)
	{
		final InvoiceRows invoiceRows = getRowsData();
		invoiceRows.addInvoice(invoiceId);
	}

	public void markPreparedForAllocation(@NonNull final DocumentIdsSelection rowIds)
	{
		getRowsData().markPreparedForAllocation(rowIds);
		invalidateAll();
	}

	public void unmarkPreparedForAllocation(@NonNull final DocumentIdsSelection rowIds)
	{
		getRowsData().unmarkPreparedForAllocation(rowIds);
		invalidateAll();
	}
}
