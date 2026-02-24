package de.metas.quantity;

 


import org.adempiere.exceptions.AdempiereException;

public class QuantitiesUOMNotMatchingExpection extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3843474782990339183L;

	public QuantitiesUOMNotMatchingExpection(final String message)
	{
		super(message);
	}
}
