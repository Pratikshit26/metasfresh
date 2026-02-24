package org.adempiere.appdict.validation.api.impl;

 


import java.util.ArrayList;
import java.util.List;

import org.adempiere.appdict.validation.api.IADValidatorResult;
import org.adempiere.appdict.validation.api.IADValidatorViolation;

public class ADValidatorResult implements IADValidatorResult
{
	private final List<IADValidatorViolation> violations = new ArrayList<IADValidatorViolation>();

	public ADValidatorResult()
	{
		super();
	}

	@Override
	public void addViolation(final IADValidatorViolation violation)
	{
		violations.add(violation);
	}

	@Override
	public List<IADValidatorViolation> getViolations()
	{
		return violations;
	}
}
