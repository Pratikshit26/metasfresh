package org.adempiere.appdict.validation.api;

 


import java.util.List;

public interface IADValidatorResult
{
	void addViolation(IADValidatorViolation violation);

	List<IADValidatorViolation> getViolations();
}
