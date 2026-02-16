/*
 * #%L
 * de.metas.business
     
 * #L%
 */

package de.metas.requisition.callout;

import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MProductPricing;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Callout(I_M_RequisitionLine.class)
public class M_RequisitionLine
{
	@CalloutMethod(columnNames = I_M_RequisitionLine.COLUMNNAME_M_Product_ID)
	public void onProductChanged(final I_M_RequisitionLine line)
	{
		if (line.getM_Product_ID() <= 0)
		{
			return;
		}

		updatePrice(line);

		final UomId uomId = Services.get(IProductBL.class).getStockUOMId(line.getM_Product_ID());
		line.setC_UOM_ID(uomId.getRepoId());
	}    // product

	@CalloutMethod(columnNames = I_M_RequisitionLine.COLUMNNAME_Qty)
	public void onQtyChanged(final I_M_RequisitionLine line)
	{
		updatePrice(line);

	}

	@CalloutMethod(columnNames = I_M_RequisitionLine.COLUMNNAME_PriceActual)
	public void onPriceActualChanged(final I_M_RequisitionLine line)
	{
		updateLineNetAmt(line);
	}

	private void updatePrice(final I_M_RequisitionLine line)
	{
		final int C_BPartner_ID = line.getC_BPartner_ID();
		final BigDecimal Qty = line.getQty();
		final boolean isSOTrx = false;
		final MProductPricing pp = new MProductPricing(
				OrgId.ofRepoId(line.getAD_Org_ID()),
				line.getM_Product_ID(),
				C_BPartner_ID,
				null,
				Qty,
				isSOTrx);

		//
		final I_M_Requisition req = line.getM_Requisition();
		final int M_PriceList_ID = req.getM_PriceList_ID();
		pp.setM_PriceList_ID(M_PriceList_ID);
		final Timestamp orderDate = req.getDateRequired();
		pp.setPriceDate(orderDate);
		//
		line.setPriceActual(pp.getPriceStd());

		updateLineNetAmt(line);
	}

	private void updateLineNetAmt(final I_M_RequisitionLine line)
	{
		final BigDecimal qty = line.getQty();
		final BigDecimal priceActual = line.getPriceActual();

		// Multiply
		final BigDecimal lineNetAmt = qty.multiply(priceActual);

		// int stdPrecision = Env.getContextAsInt(ctx, WindowNo, "StdPrecision");
		// if (lineNetAmt.scale() > stdPrecision)
		// lineNetAmt = lineNetAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);

		line.setLineNetAmt(lineNetAmt);
	}
}
