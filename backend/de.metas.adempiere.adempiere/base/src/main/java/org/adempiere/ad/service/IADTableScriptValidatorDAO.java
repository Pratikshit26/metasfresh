package org.adempiere.ad.service;

 


import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_Table_ScriptValidator;

import de.metas.util.ISingletonService;

public interface IADTableScriptValidatorDAO extends ISingletonService
{

	List<I_AD_Table_ScriptValidator> retrieveTableScriptValidators(Properties ctx, int adTableId, String eventModelValidator);

}
