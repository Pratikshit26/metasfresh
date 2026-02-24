package org.adempiere.ui.api;

 


import java.util.Properties;

import de.metas.i18n.IMsgBL;
import de.metas.util.Services;

/**
 * Immutable implementation of {@link IGridTabSummaryInfo} which wraps a {@link String}.
 * 
 * @author tsa
 *
 */
public final class StringGridTabSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	private final String message;
	private final boolean translated;

	/**
	 *
	 * @param message
	 * @param translated true if the message is already translated
	 */
	public StringGridTabSummaryInfo(final String message, final boolean translated)
	{
		super();
		this.message = message;
		this.translated = translated;
	}

	@Override
	public String getSummaryMessageTranslated(final Properties ctx)
	{
		if (translated)
		{
			return message;
		}

		return Services.get(IMsgBL.class).parseTranslation(ctx, message);
	}

}
