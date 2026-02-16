/*
 * #%L
 * de.metas.adempiere.libero.libero
     
 * #L%
 */

package org.eevolution.callout;

import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.eevolution.model.I_PP_Product_BOM;

public class PP_Product_BOM_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_PP_Product_BOM request = calloutRecord.getModel(I_PP_Product_BOM.class);

		final String documentNo = Services.get(IDocumentNoBuilderFactory.class)
				.forTableName(I_PP_Product_BOM.Table_Name, request.getAD_Client_ID(), request.getAD_Org_ID())
				.setDocumentModel(request)
				.setFailOnError(false)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			return;
		}

		request.setDocumentNo(documentNo);
	}
}
