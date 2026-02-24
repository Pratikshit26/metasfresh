package org.adempiere.process.rpl.requesthandler.api.impl;

 


import java.util.Properties;

import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;

public class ReplRequestHandlerCtx implements IReplRequestHandlerCtx
{

	private Properties envCtxToUse;
	
	private I_EXP_Format importFormat;
	
	private I_IMP_RequestHandler requestHandlerRecord;
	
	
	@Override
	public Properties getEnvCtxToUse()
	{
		return envCtxToUse;
	}

	@Override
	public I_EXP_Format getImportFormat()
	{
		return importFormat;
	}

	@Override
	public I_IMP_RequestHandler getRequestHandlerRecord()
	{
		return requestHandlerRecord;
	}

	public void setEnvCtxToUse(Properties envCtxToUse)
	{
		this.envCtxToUse = envCtxToUse;
	}

	public void setImportFormat(I_EXP_Format importFormat)
	{
		this.importFormat = importFormat;
	}

	public void setRequestHandlerRecord(I_IMP_RequestHandler requestHandlerRecord)
	{
		this.requestHandlerRecord = requestHandlerRecord;
	}

}
