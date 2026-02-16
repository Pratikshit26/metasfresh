package de.metas.ui.web.shipment_candidates_editor;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.ShipmentScheduleUserChangeRequestsList;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public final class ShipmentCandidatesView extends AbstractCustomView<ShipmentCandidateRow> implements IEditableView
{
	private final IShipmentScheduleBL shipmentScheduleBL;

	@Builder
	private ShipmentCandidatesView(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			//
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final ShipmentCandidateRows rows)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);

		this.shipmentScheduleBL = shipmentScheduleBL;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	protected ShipmentCandidateRows getRowsData()
	{
		return ShipmentCandidateRows.cast(super.getRowsData());
	}

	@Override
	public void close(final ViewCloseAction closeAction)
	{
		if (closeAction.isDone())
		{
			saveChanges();
		}
	}

	private void saveChanges()
	{
		final ShipmentScheduleUserChangeRequestsList userChanges = getRowsData().createShipmentScheduleUserChangeRequestsList().orElse(null);
		if (userChanges == null)
		{
			return;
		}

		shipmentScheduleBL.applyUserChangesInTrx(userChanges);
	}
}
