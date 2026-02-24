package org.adempiere.exceptions;

 


/**
 * Exception thrown where no database connection was found.
 * 
 * @author tsa
 *
 */
public class DBNoConnectionException extends DBException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2725091243007392188L;

	public static final DBNoConnectionException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof DBNoConnectionException)
		{
			return (DBNoConnectionException)throwable;
		}
		else
		{
			return new DBNoConnectionException(throwable.getLocalizedMessage(), throwable);
		}

	}

	private static final String MSG = "@NoDBConnection@";

	public DBNoConnectionException()
	{
		super(MSG);
	}

	public DBNoConnectionException(final String additionalMessage)
	{
		super(MSG + ": " + additionalMessage);
	}

	public DBNoConnectionException(final String additionalMessage, final Throwable cause)
	{
		super(MSG + ": " + additionalMessage, cause);
	}

	private DBNoConnectionException(Throwable cause)
	{
		super(MSG, cause);
	}
}
