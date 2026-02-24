package org.adempiere.appdict.validation.api.impl;

 


import org.adempiere.appdict.validation.api.IADValidatorViolation;

public class ADValidatorViolation implements IADValidatorViolation
{
	private final Object item;
	private final Throwable error;

	public ADValidatorViolation(Object item, Throwable error)
	{
		this.item = item;
		this.error = error;
	}

	@Override
	public Object getItem()
	{
		return item;
	}

	@Override
	public Throwable getError()
	{
		return error;
	}
}
