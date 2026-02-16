package de.metas.ui.web.order.products_proposal.process;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.order.products_proposal.view.OrderProductsProposalViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class WEBUI_Order_ProductsProposal_Launcher extends WEBUI_ProductsProposal_Launcher_Template implements IProcessPrecondition
{
	private final OrderProductsProposalViewFactory productsProposalViewFactory = SpringContextHolder.instance.getBean(OrderProductsProposalViewFactory.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one and only one order shall be selected");
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order salesOrder = orderDAO.getById(orderId, I_C_Order.class);
		if(salesOrder == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Order not yet persisted");
		}
		final DocStatus docStatus = DocStatus.ofCode(salesOrder.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only Drafted or InProgress orders are allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected CreateViewRequest createViewRequest(final TableRecordReference recordRef)
	{
		return productsProposalViewFactory.createViewRequest(recordRef);
	}
}
