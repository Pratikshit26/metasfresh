package org.adempiere.appdict.validation.api;

 


public interface IADValidatorViolation
{
	Throwable getError();

	Object getItem();
}
