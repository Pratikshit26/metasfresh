package org.adempiere.server.rpl.api;

 


import java.util.List;
import java.util.Properties;

import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;

import de.metas.util.ISingletonService;

public interface IIMPProcessorDAO extends ISingletonService
{

	List<AdempiereProcessorLog> retrieveAdempiereProcessorLogs(final org.compiere.model.I_IMP_Processor impProcessor);

	/**
	 * Delete attached logs.
	 * 
	 * NOTE: only logs older then {@link I_IMP_Processor#getKeepLogDays()} will be deleted.
	 * 
	 * @param impProcessor
	 * @return number of logs deleted
	 */
	int deleteLogs(I_IMP_Processor impProcessor);

	/**
	 * Delete attached logs
	 * 
	 * @param impProcessor
	 * @param deleteAll if false only those logs that are older then {@link I_IMP_Processor#getKeepLogDays()} will be deleted
	 * @return number of logs deleted
	 */
	int deleteLogs(I_IMP_Processor impProcessor, boolean deleteAll);

	List<I_IMP_ProcessorParameter> retrieveParameters(I_IMP_Processor impProcessor, String trxName);

	void deleteParameters(I_IMP_Processor impProcessor);

	I_IMP_ProcessorParameter retrieveParameter(I_IMP_Processor impProcessor, String parameterName);

	/**
	 * Retrieve ALL (from all clients) import processors which are active
	 * 
	 * @param ctx
	 * @return
	 */
	List<org.adempiere.server.rpl.interfaces.I_IMP_Processor> retrieveAllActive(Properties ctx);

}
