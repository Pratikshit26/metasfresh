package de.metas.acct.gljournal;

 


import java.util.List;

import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;

import de.metas.util.ISingletonService;

public interface IGLJournalLineDAO extends ISingletonService
{

	IGLJournalLineGroup retrieveFirstUnballancedJournalLineGroup(I_GL_Journal glJournal);

	int retrieveLastGroupNo(I_GL_Journal glJournal);

	List<I_GL_JournalLine> retrieveLines(I_GL_Journal glJournal);

	int retrieveLastLineNo(I_GL_Journal glJournal);

	void save(I_GL_JournalLine glJournalLine);
}
