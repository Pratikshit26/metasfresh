package org.adempiere.process.rpl.requesthandler.api;

 


import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.compiere.model.PO;

/**
 * @see IReplRequestHandlerBL#createInitialRequestHandlerResult().
 */
public interface IReplRequestHandlerResult
{
	PO getPOToExport();

	void setPOToExport(PO poToExport);

	I_EXP_Format getFormatToUse();

	void setFormatToUse(I_EXP_Format formattoUse);

}
