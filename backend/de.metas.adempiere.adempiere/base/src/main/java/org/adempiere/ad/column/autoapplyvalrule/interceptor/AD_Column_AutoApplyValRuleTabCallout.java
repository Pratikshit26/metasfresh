package org.adempiere.ad.column.autoapplyvalrule.interceptor;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.column.autoapplyvalrule.ValRuleAutoApplierService;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.SpringContextHolder;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class AD_Column_AutoApplyValRuleTabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final Object model = calloutRecord.getModel(Object.class);

		final ValRuleAutoApplierService valRuleAutoApplierService = SpringContextHolder.instance.getBean(ValRuleAutoApplierService.class);
		valRuleAutoApplierService.invokeApplierFor(model);
	}
}
