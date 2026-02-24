package org.adempiere.model.validator;

 

import de.metas.util.Check;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.ModelValidator;

@Interceptor(I_AD_InfoColumn.class)
public class AD_InfoColumn
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_AD_InfoColumn infoColumn)
	{
		if (Check.isEmpty(infoColumn.getName(), true))
		{
			infoColumn.setName(infoColumn.getAD_Element().getName());
		}
	}

}
