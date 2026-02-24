package de.metas.adempiere.model;

 


import java.math.BigDecimal;

public interface ILineNetAmtAware
{
	BigDecimal getLineNetAmt();
	
	void setLineNetAmt(BigDecimal lineNetAmt);
}
