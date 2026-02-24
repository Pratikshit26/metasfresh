package de.metas.acct.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;

 


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

/**
 * @author al
 */
public class GL_Journal_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_GL_Journal glJournal = calloutRecord.getModel(I_GL_Journal.class);

		//
		// 07569: copy description from glJournalBatch to glJournal
		final I_GL_JournalBatch glJournalBatch = glJournal.getGL_JournalBatch();
		glJournal.setDescription(glJournalBatch.getDescription());
	}
}
