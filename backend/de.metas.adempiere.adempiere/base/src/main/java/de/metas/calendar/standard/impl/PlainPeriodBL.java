package de.metas.calendar.standard.impl;

 


import de.metas.document.DocBaseType;

import java.sql.Timestamp;
import java.util.Properties;

public class PlainPeriodBL extends PeriodBL
{
	@Override
	public boolean isOpen(Properties ctx, Timestamp DateAcct, DocBaseType DocBaseType, int AD_Org_ID)
	{
		// consider all period opens because we don't have plain support
		return true;
	}
}
