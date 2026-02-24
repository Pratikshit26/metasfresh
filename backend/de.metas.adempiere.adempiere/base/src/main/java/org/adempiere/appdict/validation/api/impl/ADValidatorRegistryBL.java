package org.adempiere.appdict.validation.api.impl;

 


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.appdict.validation.api.IADValidatorDAO;
import org.adempiere.appdict.validation.api.IADValidatorRegistryBL;
import org.adempiere.appdict.validation.api.IADValidatorResult;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.model.validator.ApplicationDictionaryGenericModelValidator;
import org.adempiere.appdict.validation.spi.IADValidator;
import org.adempiere.appdict.validation.spi.impl.ADColumnCalloutADValidator;
import org.adempiere.appdict.validation.spi.impl.ADFormADValidator;
import org.adempiere.appdict.validation.spi.impl.ADModelValidatorADValidator;
import org.adempiere.appdict.validation.spi.impl.ADProcessADValidator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_ColumnCallout;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.model.I_AD_Process;
import org.compiere.model.ModelValidationEngine;

import de.metas.util.Services;

public class ADValidatorRegistryBL implements IADValidatorRegistryBL
{
	private final Map<Class<?>, IADValidator<?>> validators = new ConcurrentHashMap<>();

	public ADValidatorRegistryBL()
	{
		registerStandardValidators();
	}

	@Override
	public <T> void registerValidator(final Class<T> interfaceClass, final IADValidator<T> validator)
	{
		if (validators.containsKey(interfaceClass))
		{
			throw new AdempiereException("A validator is already registered for " + interfaceClass);
		}
		validators.put(interfaceClass, validator);

		ApplicationDictionaryGenericModelValidator<T> modelValidator = new ApplicationDictionaryGenericModelValidator<>(interfaceClass, validator);
		ModelValidationEngine.get().addModelValidator(modelValidator);
	}

	@Override
	public <T> IADValidatorResult validate(final Properties ctx, final Class<T> appDictClass)
	{
		final Iterator<T> items = Services.get(IADValidatorDAO.class).retrieveApplicationDictionaryItems(ctx, appDictClass);

		final IADValidatorResult errorLog = new ADValidatorResult();

		while (items.hasNext())
		{
			final T item = items.next();

			try
			{
				validateItem(appDictClass, item);
			}
			catch (Exception e)
			{
				final IADValidatorViolation violation = new ADValidatorViolation(item, e);

				errorLog.addViolation(violation);
			}
		}

		return errorLog;
	}

	@Override
	public List<Class<?>> getRegisteredClasses()
	{
		return new ArrayList<>(validators.keySet());
	}

	@Override
	public IADValidator<?> getValidator(final Class<?> registeredClass)
	{
		return validators.get(registeredClass);
	}

	private <T> void validateItem(final Class<T> appDictClass, final T item)
	{
		@SuppressWarnings("unchecked")
		final IADValidator<T> validator = (IADValidator<T>)validators.get(appDictClass);

		validator.validate(item);
	}

	private void registerStandardValidators()
	{
		registerValidator(I_AD_ColumnCallout.class, new ADColumnCalloutADValidator());
		registerValidator(I_AD_Form.class, new ADFormADValidator());
		registerValidator(I_AD_ModelValidator.class, new ADModelValidatorADValidator());
		registerValidator(I_AD_Process.class, new ADProcessADValidator());
	}
}
