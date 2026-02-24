package org.adempiere.appdict.validation.spi.impl;

 

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

public class ADJavaClassValidator extends AbstractADValidator<I_AD_JavaClass>
{

	@Override
	public void validate(@NonNull final I_AD_JavaClass item)
	{
		final I_AD_JavaClass_Type type = item.getAD_JavaClass_Type();

		if (Check.isEmpty(type.getClassname(), true))
		{
			Util.validateJavaClassname(item.getClassname(), null);
		}
		else
		{
			final Class<?> typeClass = Util.validateJavaClassname(type.getClassname(), null);

			Util.validateJavaClassname(item.getClassname(), typeClass);
		}

	}

	@Override
	public String getLogMessage(@NonNull final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_JavaClass javaClass = InterfaceWrapperHelper.create(violation.getItem(), I_AD_JavaClass.class);

			message.append("Error on ").append(javaClass).append(" (IsActive=").append(javaClass.isActive()).append("): ");
		}
		catch (final Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_JavaClass> getType()
	{
		return I_AD_JavaClass.class;
	}

}
