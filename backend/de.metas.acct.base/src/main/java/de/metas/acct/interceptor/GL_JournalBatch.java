package de.metas.acct.interceptor;

 


import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import de.metas.copy_with_details.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.ModelValidator;

import de.metas.acct.gljournal.IGLJournalDAO;
import de.metas.util.Services;

@Interceptor(I_GL_JournalBatch.class)
public class GL_JournalBatch
{
	private final IGLJournalDAO glJournalDAO = Services.get(IGLJournalDAO.class);
	
	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_GL_JournalBatch.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_GL_JournalBatch.COLUMNNAME_DateAcct, I_GL_JournalBatch.COLUMNNAME_DateDoc })
	public void updateDateAcct(final I_GL_JournalBatch journalBatch)
	{
		final List<I_GL_Journal> journals = glJournalDAO.retrieveJournalsForBatch(journalBatch);

		if (journals.isEmpty())
		{
			// do nothing
			return;
		}

		final Timestamp dateAcct = journalBatch.getDateAcct();
		final Timestamp dateDoc = journalBatch.getDateDoc();

		for (final I_GL_Journal journal : journals)
		{
			// Don't change processed journals
			if (journal.isProcessed())
			{
				continue;
			}

			journal.setDateAcct(dateAcct);
			journal.setDateDoc(dateDoc);
			InterfaceWrapperHelper.save(journal);
		}
	}

}
