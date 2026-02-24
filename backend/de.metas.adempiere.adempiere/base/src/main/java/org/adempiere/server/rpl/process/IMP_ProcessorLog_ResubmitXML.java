package org.adempiere.server.rpl.process;

 


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.Query;

import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;

public class IMP_ProcessorLog_ResubmitXML extends JavaProcess
{
	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_IMP_ProcessorLog> logs = retrieveLogs();

		Services.get(IIMPProcessorBL.class).resubmit(logs, false, this);

		return "OK";
	}

	private Iterator<I_IMP_ProcessorLog> retrieveLogs()
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		// Window selection
		final String processWhereClause = getProcessInfo().getWhereClause();
		if (!Check.isEmpty(processWhereClause, true))
		{
			whereClause.append(" AND (").append(processWhereClause).append(")");
		}

		// Only those who have errors
		whereClause.append(" AND ").append(I_IMP_ProcessorLog.COLUMNNAME_IsError).append("=?");
		params.add(true);

		return new Query(getCtx(), I_IMP_ProcessorLog.Table_Name, whereClause.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				// .setApplyAccessFilterRW(true) // if a user can open the window and see the error-log records, we want to let him/her resubmit them
				.setOrderBy(I_IMP_ProcessorLog.COLUMNNAME_IMP_ProcessorLog_ID)
				.iterate(I_IMP_ProcessorLog.class, false); // guaranteed=false
	}

}
