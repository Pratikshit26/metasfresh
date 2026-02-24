package org.adempiere.server.rpl.model.validator;

 


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_IMP_Processor.class)
public class IMP_Processor
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteParametersAndLogs(final I_IMP_Processor impProcessor)
	{
		final IIMPProcessorDAO dao = Services.get(IIMPProcessorDAO.class);
		
		dao.deleteParameters(impProcessor);
		dao.deleteLogs(impProcessor, true); // deleteAll=true
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }
			, ifColumnsChanged = I_IMP_Processor.COLUMNNAME_IMP_Processor_Type_ID)
	public void recreateParameters(final I_IMP_Processor impProcessor)
	{
		final IIMPProcessorDAO dao = Services.get(IIMPProcessorDAO.class);
		
		dao.deleteParameters(impProcessor);
		final IImportProcessor proc = Services.get(IIMPProcessorBL.class).getIImportProcessor(impProcessor);
		proc.createInitialParameters(impProcessor);
	}
}
