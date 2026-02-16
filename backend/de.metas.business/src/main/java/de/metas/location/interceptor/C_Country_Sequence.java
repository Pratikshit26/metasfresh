package de.metas.location.interceptor;

import de.metas.location.impl.CountryDAO;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Interceptor(I_C_Country_Sequence.class)
@Component
public class C_Country_Sequence
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_Country_Sequence.COLUMNNAME_DisplaySequence, I_C_Country_Sequence.COLUMNNAME_DisplaySequenceLocal })
	public void onChangeCountryDisplaySequence(@NonNull final I_C_Country_Sequence record)
	{
		CountryDAO.toCountrySequences(record).assertDisplaySequencesValid();
	}
}