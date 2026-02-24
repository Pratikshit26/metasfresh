package org.eevolution.exceptions;

 


import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

public class BOMCycleException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6859323608419524916L;

	public BOMCycleException(final I_PP_Product_BOM bom, final ProductId componentProductId)
	{
		super(buildMsg(bom, componentProductId));
	}

	private static final String buildMsg(I_PP_Product_BOM bom, ProductId componentProductId)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Cycle BOM & Formula:");
		sb.append(bom.getValue()).append("_").append(bom.getName()).append(" (").append(bom.getPP_Product_BOM_ID()).append(")");

		if (componentProductId != null)
		{
			final String componentName = Services.get(IProductBL.class).getProductValueAndName(componentProductId);
			sb.append(" - Component: ").append(componentName);
		}

		return sb.toString();
	}
}
