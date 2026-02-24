package org.adempiere.process.rpl.requesthandler.api.impl;

 


import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.compiere.model.PO;

public class ReplRequestHandlerResult implements IReplRequestHandlerResult
{
	private I_EXP_Format formatToUse;
	
	private PO poToExport;

	public I_EXP_Format getFormatToUse()
	{
		return formatToUse;
	}

	public void setFormatToUse(I_EXP_Format formatToUse)
	{
		this.formatToUse = formatToUse;
	}

	public PO getPOToExport()
	{
		return poToExport;
	}

	public void setPOToExport(PO poToExport)
	{
		this.poToExport = poToExport;
	}
}
