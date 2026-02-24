package org.adempiere.ui.spi;

 


import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.compiere.model.GridTab;

/**
 * Implementations of this interface are responsible for providing {@link IGridTabSummaryInfo} for a given {@link GridTab}.
 * 
 * NOTE: the {@link IGridTabSummaryInfo} contains the message which is displayed on window tab's bottom.
 * 
 * @author tsa
 *
 */
public interface IGridTabSummaryInfoProvider
{
	/**
	 * Transaction info that is displayed on the window footer panel
	 * 
	 * @param gridTab
	 * @return
	 */
	IGridTabSummaryInfo getSummaryInfo(final GridTab gridTab);
}
