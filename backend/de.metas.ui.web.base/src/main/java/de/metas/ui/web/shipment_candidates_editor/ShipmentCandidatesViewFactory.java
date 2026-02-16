package de.metas.ui.web.shipment_candidates_editor;

import de.metas.i18n.IMsgBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ViewFactory(windowId = ShipmentCandidatesViewFactory.WINDOWID_String)
public class ShipmentCandidatesViewFactory implements IViewFactory
{
	public static final String WINDOWID_String = "540674"; // FIXME: HARDCODED
	public static final WindowId WINDOWID = WindowId.fromJson(WINDOWID_String);

	private final IShipmentScheduleBL shipmentScheduleBL;

	private final ShipmentCandidateRowsRepository rowsRepo;

	public ShipmentCandidatesViewFactory(
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.rowsRepo = ShipmentCandidateRowsRepository.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				.lookupDataSourceFactory(lookupDataSourceFactory)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(WindowId windowId, JSONViewDataType viewDataType, ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(Services.get(IMsgBL.class).translatable("M_ShipmentSchedule_ID"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(ShipmentCandidateRow.class, viewDataType)
				.build();
	}

	@Override
	public ShipmentCandidatesView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final Set<ShipmentScheduleId> shipmentScheduleIds = ShipmentScheduleId.fromIntSet(request.getFilterOnlyIds());
		final ShipmentCandidateRows rows = rowsRepo.getByShipmentScheduleIds(shipmentScheduleIds);

		return ShipmentCandidatesView.builder()
				.shipmentScheduleBL(shipmentScheduleBL)
				//
				.viewId(viewId)
				.rows(rows)
				.build();
	}
}
