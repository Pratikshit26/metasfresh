package org.adempiere.appdict.validation.process;

 


import java.util.List;

import org.adempiere.appdict.validation.api.IADValidatorRegistryBL;
import org.adempiere.appdict.validation.api.IADValidatorResult;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.IADValidator;

import de.metas.process.JavaProcess;
import de.metas.util.Services;

/**
 * 
 * @author tsa
 */
public class AppDictValidator extends JavaProcess
{
	@Override
	protected void prepare()
	{
		return;
	}

	@Override
	protected String doIt() throws Exception
	{
		final IADValidatorRegistryBL validatorRegistry = Services.get(IADValidatorRegistryBL.class);

		final List<Class<?>> registeredClasses = validatorRegistry.getRegisteredClasses();

		for (final Class<?> registeredClass : registeredClasses)
		{
			final IADValidatorResult errorLog = validatorRegistry.validate(getCtx(), registeredClass);

			logAllExceptions(errorLog, validatorRegistry.getValidator(registeredClass));
		}

		return "OK";
	}

	private void logAllExceptions(final IADValidatorResult errorLog, final IADValidator<?> validator)
	{
		for (final IADValidatorViolation violation : errorLog.getViolations())
		{
			addLog(validator.getLogMessage(violation));
		}
	}
}
