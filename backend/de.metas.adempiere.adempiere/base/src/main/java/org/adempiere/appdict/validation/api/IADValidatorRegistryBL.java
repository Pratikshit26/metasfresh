package org.adempiere.appdict.validation.api;

 


import java.util.List;
import java.util.Properties;

import org.adempiere.appdict.validation.spi.IADValidator;

import de.metas.util.ISingletonService;

public interface IADValidatorRegistryBL extends ISingletonService
{
	<T> IADValidatorResult validate(Properties ctx, Class<T> appDictClass);

	<T> void registerValidator(Class<T> interfaceClass, IADValidator<T> validator);

	List<Class<?>> getRegisteredClasses();

	IADValidator<?> getValidator(Class<?> registeredClass);
}
