package org.adempiere.appdict.validation.spi.impl;

 

import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Form;
import org.compiere.util.Util;

public class ADFormADValidator extends AbstractADValidator<I_AD_Form>
{
	@Override
	public void validate(@NonNull final I_AD_Form form)
	{
		// In case of forms, this field may be empty
		if (Check.isBlank(form.getClassname()))
		{
			return;
		}

		Util.validateJavaClassname(form.getClassname(), null);
	}

	@Override
	public String getLogMessage(@NonNull final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_Form form = InterfaceWrapperHelper.create(violation.getItem(), I_AD_Form.class);

			message.append("Error on ").append(form).append(" (IsActive=").append(form.isActive()).append("): ");
		}
		catch (final Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_Form> getType()
	{
		return I_AD_Form.class;
	}
}
