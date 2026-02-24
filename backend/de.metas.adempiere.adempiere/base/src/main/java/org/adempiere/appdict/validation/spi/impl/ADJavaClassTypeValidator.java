package org.adempiere.appdict.validation.spi.impl;

 

import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

public class ADJavaClassTypeValidator extends AbstractADValidator<I_AD_JavaClass_Type>
{

	@Override
	public String getLogMessage(@NonNull final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_JavaClass_Type javaClassType = InterfaceWrapperHelper.create(violation.getItem(), I_AD_JavaClass_Type.class);

			message.append("Error on ").append(javaClassType).append(" (IsActive=").append(javaClassType.isActive()).append("): ");
		}
		catch(final Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_JavaClass_Type> getType()
	{
		return I_AD_JavaClass_Type.class;
	}

	@Override
	public void validate(@NonNull final I_AD_JavaClass_Type item)
	{
		if (Check.isBlank(item.getClassname()))
		{
			return;
		}

		Util.validateJavaClassname(item.getClassname(), null);
	}

}
