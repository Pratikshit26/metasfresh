package org.adempiere.appdict.validation.spi;

 


import org.adempiere.appdict.validation.api.IADValidatorViolation;

public interface IADValidator<T>
{
	void validate(T item);

	String getLogMessage(IADValidatorViolation violation);

	Class<T> getType();
}
