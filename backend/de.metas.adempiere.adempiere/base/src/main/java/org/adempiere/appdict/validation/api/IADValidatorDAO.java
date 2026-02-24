package org.adempiere.appdict.validation.api;

 


import java.util.Iterator;
import java.util.Properties;

import de.metas.util.ISingletonService;

public interface IADValidatorDAO extends ISingletonService
{
	<T> Iterator<T> retrieveApplicationDictionaryItems(Properties ctx, Class<T> appDictClass);
}
