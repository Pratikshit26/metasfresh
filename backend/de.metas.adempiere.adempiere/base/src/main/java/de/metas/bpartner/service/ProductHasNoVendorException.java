package de.metas.bpartner.service;

 


public final class ProductHasNoVendorException extends Exception
{
	private static final long serialVersionUID = -7583112372829053131L;

	public ProductHasNoVendorException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ProductHasNoVendorException(final String message)
	{
		super(message);
	}
}
