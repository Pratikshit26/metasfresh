package org.adempiere.server.rpl.exceptions;

 


public class ExportProcessorException extends ReplicationException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5770848538735280815L;

	public ExportProcessorException(final String adMessage, final Throwable cause)
	{
		super(adMessage, cause);
	}

	public ExportProcessorException(final String adMessage)
	{
		super(adMessage);
	}
}
