package org.adempiere.ui.api;

 


import java.util.Properties;

/**
 * Implementation of {@link IGridTabSummaryInfo} which returns empty message.
 *
 * @author tsa
 *
 */
/* package */final class NullGridTabSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	public static final transient NullGridTabSummaryInfo instance = new NullGridTabSummaryInfo();

	private NullGridTabSummaryInfo()
	{
		super();
	}

	/**
	 * @return empty string
	 */
	@Override
	public String getSummaryMessageTranslated(final Properties ctx)
	{
		return "";
	}
}
