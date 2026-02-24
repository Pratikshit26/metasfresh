package de.metas.adempiere.docline.sort.model.validator;

 


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_DocLine_Sort;
import org.compiere.model.I_C_DocLine_Sort;
import org.compiere.model.ModelValidator;

import de.metas.util.Services;

@Validator(I_C_BP_DocLine_Sort.class)
public class C_BP_DocLine_Sort
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE
			, ifColumnsChanged = {
					I_C_BP_DocLine_Sort.COLUMNNAME_C_BPartner_ID
					, I_C_BP_DocLine_Sort.COLUMNNAME_IsActive
			})
	public void validateNoConflictingBPConfiguration(final I_C_BP_DocLine_Sort bpSort)
	{
		if (!bpSort.isActive())
		{
			return; // if it was deactivated
		}

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(bpSort);
		final String trxName = InterfaceWrapperHelper.getTrxName(bpSort);

		//
		// Header query builder
		final IQueryBuilder<I_C_DocLine_Sort> docLineQueryBuilder = queryBL.createQueryBuilder(I_C_DocLine_Sort.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		final I_C_DocLine_Sort sort = bpSort.getC_DocLine_Sort();

		//
		// DocBaseType
		docLineQueryBuilder.addEqualsFilter(I_C_DocLine_Sort.COLUMN_DocBaseType, sort.getDocBaseType());

		//
		// Collect BPartner links
		final IQueryBuilder<I_C_BP_DocLine_Sort> bpQueryBuilder = docLineQueryBuilder
				.andCollectChildren(I_C_BP_DocLine_Sort.COLUMN_C_DocLine_Sort_ID, I_C_BP_DocLine_Sort.class)
				.addOnlyActiveRecordsFilter();

		//
		// Not same BP configuration
		bpQueryBuilder.addNotEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_BP_DocLine_Sort_ID, bpSort.getC_BP_DocLine_Sort_ID());

		//
		// Same BP
		bpQueryBuilder.addEqualsFilter(I_C_BP_DocLine_Sort.COLUMN_C_BPartner_ID, bpSort.getC_BPartner_ID());

		final boolean existsDuplicateBPConfig = bpQueryBuilder
				.create()
				.anyMatch();
		if (existsDuplicateBPConfig)
		{
			throw new AdempiereException("@DuplicateBPDocLineSort@");
		}
	}
}
