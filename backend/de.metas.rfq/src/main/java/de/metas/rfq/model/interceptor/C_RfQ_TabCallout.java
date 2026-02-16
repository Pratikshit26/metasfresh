/*
 * #%L
 * de.metas.rfq
     
 * #L%
 */

package de.metas.rfq.model.interceptor;

import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;

public class C_RfQ_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_RfQ request = calloutRecord.getModel(I_C_RfQ.class);

		final String documentNo = Services.get(IDocumentNoBuilderFactory.class)
				.forTableName(I_C_RfQ.Table_Name, request.getAD_Client_ID(), request.getAD_Org_ID())
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
