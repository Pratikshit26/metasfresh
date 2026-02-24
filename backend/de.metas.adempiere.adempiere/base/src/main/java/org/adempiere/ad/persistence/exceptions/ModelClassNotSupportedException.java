package org.adempiere.ad.persistence.exceptions;

 


import org.adempiere.exceptions.AdempiereException;

/**
 * Thrown by persistance layer when a model class is not supported.
 * 
 * @author tsa
 *
 */
public class ModelClassNotSupportedException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3746201325167390358L;

	public ModelClassNotSupportedException(final Object model)
	{
		super(buildMsg(model));
	}

	private static final String buildMsg(final Object model)
	{
		return "Model class is not supported: " + (model == null ? "null" : model.getClass());
	}
}
