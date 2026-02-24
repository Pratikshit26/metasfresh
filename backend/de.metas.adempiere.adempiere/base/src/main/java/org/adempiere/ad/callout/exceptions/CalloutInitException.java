package org.adempiere.ad.callout.exceptions;

import de.metas.util.Check;

 


/**
 * Thrown on any error during callouts load or initialization.
 * 
 * @author tsa
 */
public class CalloutInitException extends CalloutException
{
	public static CalloutInitException wrapIfNeeded(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "throwable not null");

		if (throwable instanceof CalloutInitException)
		{
			return (CalloutInitException)throwable;
		}
		
		final Throwable cause = extractCause(throwable);
		if(cause != throwable)
		{
			return wrapIfNeeded(cause);
		}
		
		return new CalloutInitException(extractMessage(throwable), cause);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5929639632737615796L;

	public CalloutInitException(final String message)
	{
		super(message);
	}

	public CalloutInitException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
