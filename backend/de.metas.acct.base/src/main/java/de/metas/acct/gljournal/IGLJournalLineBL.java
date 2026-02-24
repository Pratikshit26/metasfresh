package de.metas.acct.gljournal;

 


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_GL_JournalLine;

import de.metas.acct.tax.ITaxAccountable;
import de.metas.currency.CurrencyPrecision;
import de.metas.util.ISingletonService;

public interface IGLJournalLineBL extends ISingletonService
{

	void setAmtSourcePrecision(I_GL_JournalLine line);

	/**
	 * Calculate and set AmtAcctDr and AmtAcctCr based on AmtSourceDr/AmtSourceCr and CurrencyRate.
	 * 
	 * @param glJournalLine
	 */
	void setAmtAcct(I_GL_JournalLine glJournalLine);

	/**
	 * 
	 * @param glJournalLine
	 * @return currency precision
	 */
	CurrencyPrecision getPrecision(I_GL_JournalLine glJournalLine);

	/**
	 * Checks if given {@link I_GL_JournalLine} shall be part of a group of transactions (i.e. it's a split booking) or not.
	 * 
	 * Sets:
	 * <ul>
	 * <li> {@link I_GL_JournalLine#COLUMN_GL_JournalLine_Group} - group number in which this line belongs or a new group
	 * <li> {@link I_GL_JournalLine#COLUMN_IsAllowAccountDR}, {@link I_GL_JournalLine#COLUMN_IsAllowAccountCR} - if DR/CR accounts and amounts are allowed to be recorded.
	 * <li> {@link I_GL_JournalLine#COLUMN_IsSplitAcctTrx}
	 * </ul>
	 * 
	 * @param glJournalLine
	 */
	void setGroupNoAndFlags(I_GL_JournalLine glJournalLine);

	/**
	 * Asserts given account is valid and can be used in given GL Journal Line.
	 * 
	 * @param account
	 * @param glJournalLine journal line (used as a reference when error message is built)
	 */
	void assertAccountValid(I_C_ValidCombination account, I_GL_JournalLine glJournalLine);

	/**
	 * Wraps given {@link I_GL_JournalLine} to {@link ITaxAccountable}.
	 * 
	 * @param glJournalLine
	 * @param accountSignDR
	 * @return tax accountable; never returns <code>null</code>
	 * @task http://dewiki908/mediawiki/index.php/08351_Automatikibuchung_Steuer_in_Hauptbuchjournal_%28106598648165%29
	 */
	ITaxAccountable asTaxAccountable(I_GL_JournalLine glJournalLine, boolean accountSignDR);

	/**
	 * Wraps given {@link I_GL_JournalLine} to {@link ITaxAccountable}.
	 * 
	 * @param glJournalLine
	 * @param accountSignDR
	 * @return tax accountable; never returns <code>null</code>
	 * @throws AdempiereException if given journal line is not about tax accouting
	 * @task http://dewiki908/mediawiki/index.php/08351_Automatikibuchung_Steuer_in_Hauptbuchjournal_%28106598648165%29
	 */
	ITaxAccountable asTaxAccountable(I_GL_JournalLine glJournalLine);

	/**
	 * Builds an returns a identifiable user friendly document no of given {@link I_GL_JournalLine}.
	 * 
	 * @param glJournalLine
	 * @return GL_JournalBatch.DocumentNo / GL_Journal.DocumentNo / GL_JournalLine.Line
	 */
	String getDocumentNo(I_GL_JournalLine glJournalLine);

	/**
	 * Checks if current state of {@link I_GL_JournalLine#isSplitAcctTrx()} is compatible the other settings from given GL Journal Line.
	 * 
	 * @param glJournalLine
	 * @throws AdempiereException if not valid
	 */
	void checkValidSplitAcctTrxFlag(I_GL_JournalLine glJournalLine);
}
