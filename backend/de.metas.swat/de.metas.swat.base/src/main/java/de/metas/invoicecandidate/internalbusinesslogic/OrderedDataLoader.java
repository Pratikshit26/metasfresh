package de.metas.invoicecandidate.internalbusinesslogic;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
@Builder
public class OrderedDataLoader
{
	private static final Logger logger = LogManager.getLogger(OrderedDataLoader.class);

	@NonNull
	I_C_Invoice_Candidate invoiceCandidateRecord;

	@NonNull
	UomId stockUomId;

	/** Needed to get the product types of other order lines' products. */
	@NonNull
	IProductBL productBL;

	public OrderedData loadOrderedQtys()
	{
		final UomId icUomId = UomId.ofRepoId(invoiceCandidateRecord.getC_UOM_ID());
		final I_C_Order order = InterfaceWrapperHelper.create(invoiceCandidateRecord.getC_Order(), I_C_Order.class);

		final boolean hasInvalidOrder = null != order && !DocStatus.ofCode(order.getDocStatus()).isCompletedOrClosed();

		final OrderedData.OrderedDataBuilder result = OrderedData.builder()
				.orderFullyDelivered(isOrderFullyDelivered());

		if (hasInvalidOrder)
		{
			result.qty(Quantitys.zero(icUomId));
			result.qtyInStockUom(Quantitys.zero(stockUomId));
		}
		else
		{
			result.qty(Quantitys.of(invoiceCandidateRecord.getQtyEntered(), icUomId));
			result.qtyInStockUom(Quantitys.of(invoiceCandidateRecord.getQtyOrdered(), stockUomId));
		}

		return result.build();
	}

	private boolean isOrderFullyDelivered()
	{
		if (invoiceCandidateRecord.getC_Order_ID() <= 0)
		{
			logger.debug("C_Order_ID={}; -> return false");
			return false;
		}
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		for (final I_C_OrderLine oLine : orderDAO.retrieveOrderLines(invoiceCandidateRecord.getC_Order()))
		{
			try (final MDCCloseable oLineMDC = TableRecordMDC.putTableRecordReference(oLine))
			{
				final BigDecimal toInvoice = oLine.getQtyOrdered().subtract(oLine.getQtyInvoiced());
				if (toInvoice.signum() == 0 && oLine.getM_Product_ID() > 0)
				{
					logger.debug("C_OrderLine has QtyOrdered={}, QtyInvoiced={}, M_Product_ID={} and remaining qty to invoice={} -> consider fully delivered",
							oLine.getQtyOrdered(), oLine.getQtyInvoiced(), oLine.getM_Product_ID(), toInvoice);
					continue;
				}

				final ProductId olProductId = ProductId.ofRepoIdOrNull(oLine.getM_Product_ID()); // in the DB it's mandatory, but in many unit tests it's not relevant and not set
				if (!productBL.isStocked(olProductId))
				{
					logger.debug("C_OrderLine has M_Product_ID={} which is not a (physical) item -> consider fully delivered",
							oLine.getM_Product_ID());
				}

				final boolean fullyDelivered = oLine.getQtyOrdered().compareTo(oLine.getQtyDelivered()) == 0;
				if (!fullyDelivered)
				{
					logger.debug("C_OrderLine has QtyOrdered={} is <= QtyDelivered={} -> return false",
							oLine.getQtyOrdered(), oLine.getQtyDelivered());
					return false;
				}
			}
		}
		logger.debug("C_Order_ID={}; has all lines fully delivered -> return true", invoiceCandidateRecord.getC_Order_ID());
		return true;
	}
}
