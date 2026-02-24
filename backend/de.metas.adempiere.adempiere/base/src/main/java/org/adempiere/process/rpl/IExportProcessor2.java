package org.adempiere.process.rpl;

 


import org.adempiere.server.rpl.exceptions.ExportProcessorException;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.PO;
import org.w3c.dom.Document;

/**
 * 
 * @author tsa
 * 
 */
public interface IExportProcessor2 extends IExportProcessor
{
	void process(MEXPProcessor expProcessor, Document document, PO po) throws ExportProcessorException;
}
