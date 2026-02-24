package org.adempiere.server.rpl.api;

 

import org.w3c.dom.Document;

import java.util.Properties;

/**
 * Helper used to actual import an XML document
 * 
 * @author tsa
 * 
 */
public interface IImportHelper
{

	/**
	 * After initialization API calls this method to configure initial context
	 */
	void setInitialCtx(Properties initialCtx);

	/**
	 * Import XML document
	 * 
	 * @return response document or null if there is no response
	 */
	Document importXMLDocument(StringBuilder result, Document documentToBeImported, String trxName);
}
