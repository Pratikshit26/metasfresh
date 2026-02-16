package de.metas.ui.web.order.sales.hu.reservation.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.reservation.RetrieveHUsQtyRequest;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.ui.web.handlingunits.HUEditorView;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@UtilityClass
final class WEBUI_C_OrderLineSO_Util
{
	public Optional<SalesOrderLine> retrieveSalesOrderLine(
			@NonNull final HUEditorView huEditorView,
			@NonNull final SalesOrderLineRepository salesOrderLineRepository)
	{
		final Optional<OrderLineId> orderLineId = huEditorView
				.getParameterAsId(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);

		return orderLineId.map(salesOrderLineRepository::getById);
	}

	public RetrieveHUsQtyRequest createHuQuantityRequest(
			@NonNull final Stream<HuId> selectedHUIdStream,
			@NonNull final ProductId productId)
	{
		final ImmutableList<HuId> selectedHuIds = selectedHUIdStream.collect(ImmutableList.toImmutableList());

		return RetrieveHUsQtyRequest.builder()
				.huIds(selectedHuIds)
				.productId(productId)
				.build();
	}
}
