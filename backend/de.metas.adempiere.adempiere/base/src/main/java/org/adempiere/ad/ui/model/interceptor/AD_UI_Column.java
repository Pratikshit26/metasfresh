package org.adempiere.ad.ui.model.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Interceptor(I_AD_UI_Column.class)
@Component
public class AD_UI_Column
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onBeforeElementDelete(@NonNull final I_AD_UI_Column uiColumnRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_AD_UI_ElementGroup.class)
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMN_AD_UI_Column_ID, uiColumnRecord.getAD_UI_Column_ID())
				.create()
				.delete();
	}
}
