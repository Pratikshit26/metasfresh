/*
 * #%L
 * de.metas.business
     
 * #L%
 */

package de.metas.requisition;

import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MCharge;
import org.compiere.model.MProductPricing;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RequisitionService
{
	private final RequisitionRepository requisitionRepository;

	public RequisitionService(@NonNull final RequisitionRepository requisitionRepository)
	{
		this.requisitionRepository = requisitionRepository;
	}

	@Nullable
	public BigDecimal computePrice(final I_M_Requisition requisition, final I_M_RequisitionLine line)
	{
		if (line.getC_Charge_ID() > 0)
		{
			final MCharge charge = MCharge.get(Env.getCtx(), line.getC_Charge_ID());
			return charge.getChargeAmt();
		}
		else if (line.getM_Product_ID() > 0)
		{
			final int priceListId = requisition.getM_PriceList_ID();
			if (priceListId <= 0)
			{
				// TODO: metas: we already changed the whole pricing engine, so for now we accept to not have a price here
				// throw new AdempiereException("PriceList unknown!");
				return BigDecimal.ZERO;
			}
			else
			{
				final MProductPricing pp = new MProductPricing(
						OrgId.ofRepoId(requisition.getAD_Org_ID()),
						line.getM_Product_ID(),
						line.getC_BPartner_ID(),
						null,
						line.getQty(),
						SOTrx.PURCHASE.toBoolean());
				pp.setM_PriceList_ID(priceListId);
				// pp.setPriceDate(getDateOrdered());
				//
				return pp.getPriceStd();
			}
		}
		else
		{
			return null;
		}
	}

	public void updateLineNetAmt(final I_M_RequisitionLine line)
	{
		final BigDecimal lineNetAmt = line.getQty().multiply(line.getPriceActual());
		line.setLineNetAmt(lineNetAmt);
	}

	public void updateTotalAmtFromLines( final RequisitionId requisitionId)
	{
		final List<I_M_RequisitionLine> lines = requisitionRepository.getLinesByRequisitionId(requisitionId);

		BigDecimal calculatedTotalAmt = BigDecimal.ZERO;
		for (final I_M_RequisitionLine line : lines)
		{
			calculatedTotalAmt = calculatedTotalAmt.add(line.getLineNetAmt());
		}

		final I_M_Requisition requisition = requisitionRepository.getById(requisitionId);
		requisition.setTotalLines(calculatedTotalAmt);

		requisitionRepository.save(requisition);
	}
}
