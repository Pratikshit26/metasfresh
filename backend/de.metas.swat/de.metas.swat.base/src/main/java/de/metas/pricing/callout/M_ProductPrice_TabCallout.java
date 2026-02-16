package de.metas.pricing.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_M_ProductPrice;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

public class M_ProductPrice_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_M_ProductPrice productPrice = calloutRecord.getModel(I_M_ProductPrice.class);
		M_ProductPrice.setTaxCategoryIdFromPriceListVersion(productPrice);
	}

}
