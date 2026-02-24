package org.adempiere.ui.api;

 


import java.io.Serializable;
import java.util.Properties;

import org.compiere.model.GridTab;

/**
 * {@link GridTab}'s transaction info (the message which is displayed Window Tab's bottom)
 * 
 * @author tsa
 *
 */
public interface IGridTabSummaryInfo extends Serializable
{
	IGridTabSummaryInfo NULL = NullGridTabSummaryInfo.instance;

	/**
	 * @param ctx
	 * @return translated summary message of this object (user friendly)
	 */
	String getSummaryMessageTranslated(final Properties ctx);
}
