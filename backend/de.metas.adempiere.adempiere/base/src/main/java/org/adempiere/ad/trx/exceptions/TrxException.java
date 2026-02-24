package org.adempiere.ad.trx.exceptions;

 


import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown by transaction manager when something went wrong.
 * 
 * @author tsa
 * 
 */
public class TrxException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8558441605639046411L;

	// NOTE: this constructor is needed if we want to use it with Check methods
	public TrxException(final String msg)
	{
		super(msg);
	}
	
	public TrxException(final String msg, Throwable cause)
	{
		super(msg, cause);
	}

}
