package de.metas.acct.gljournal;

 


import java.math.BigDecimal;

public interface IGLJournalLineGroup
{

	public BigDecimal getAmtDr();

	public BigDecimal getAmtCr();

	public int getGroupNo();

	/**
	 * 
	 * @return DR - CR
	 */
	BigDecimal getBalance();

}
