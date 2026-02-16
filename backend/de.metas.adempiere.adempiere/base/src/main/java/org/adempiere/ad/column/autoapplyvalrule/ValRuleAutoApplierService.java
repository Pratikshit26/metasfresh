package org.adempiere.ad.column.autoapplyvalrule;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Service
@ToString
public class ValRuleAutoApplierService
{
	private final ConcurrentHashMap<String, ValRuleAutoApplier> tableName2Applier = new ConcurrentHashMap<>();

	public void registerApplier(@NonNull final ValRuleAutoApplier valRuleAutoApplier)
	{
		tableName2Applier.put(valRuleAutoApplier.getTableName(), valRuleAutoApplier);
	}

	public void invokeApplierFor(@NonNull final Object recordModel)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(recordModel);
		final ValRuleAutoApplier applier = tableName2Applier.get(tableName);
		if (applier == null)
		{
			return; // no applier registered; nothing to do
		}

		try
		{
			applier.handleRecord(recordModel);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("valRuleAutoApplier", applier)
					.setParameter("recordModel", recordModel);
		}
	}

	public void unregisterForTableName(@NonNull final String tableName)
	{
		tableName2Applier.remove(tableName);

	}
}
