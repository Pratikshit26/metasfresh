package org.adempiere.appdict.validation.spi.impl;

 

import lombok.NonNull;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.util.Util;

public class ADModelValidatorADValidator extends AbstractADValidator<I_AD_ModelValidator>
{
	@Override
	public void validate(@NonNull final I_AD_ModelValidator modelValidator)
	{
		Util.validateJavaClassname(modelValidator.getModelValidationClass(), null);
	}

	@Override
	public String getLogMessage(final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_ModelValidator modelValidator = InterfaceWrapperHelper.create(violation.getItem(), I_AD_ModelValidator.class);

			message.append("Error on ").append(modelValidator).append(" (IsActive=").append(modelValidator.isActive()).append("): ");
		}
		catch (final Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_ModelValidator> getType()
	{
		return I_AD_ModelValidator.class;
	}
}
