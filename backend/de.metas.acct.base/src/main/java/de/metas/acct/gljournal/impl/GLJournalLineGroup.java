package de.metas.acct.gljournal.impl;

 


import java.math.BigDecimal;

import de.metas.acct.gljournal.IGLJournalLineGroup;
import de.metas.util.Check;

public class GLJournalLineGroup implements IGLJournalLineGroup
{
	private final int groupNo;
	private final BigDecimal amtDr;
	private final BigDecimal amtCr;

	public GLJournalLineGroup(int groupNo, BigDecimal amtDr, BigDecimal amtCr)
	{
		super();

		Check.assume(groupNo > 0, "groupNo > 0");
		this.groupNo = groupNo;

		Check.assumeNotNull(amtDr, "amtDr not null");
		this.amtDr = amtDr;

		Check.assumeNotNull(amtCr, "amtCr not null");
		this.amtCr = amtCr;
	}

	@Override
	public String toString()
	{
		return "GLJournalLineGroup [groupNo=" + groupNo + ", amtDr=" + amtDr + ", amtCr=" + amtCr + "]";
	}
	
	@Override
	public BigDecimal getBalance()
	{
		return getAmtDr().subtract(getAmtCr());
	}

	@Override
	public BigDecimal getAmtDr()
	{
		return amtDr;
	}

	@Override
	public BigDecimal getAmtCr()
	{
		return amtCr;
	}

	@Override
	public int getGroupNo()
	{
		return groupNo;
	}

}
