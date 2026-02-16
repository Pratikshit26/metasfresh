package de.metas.vertical.pharma.model.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.vertical.pharma.PharmaModulo11Validator;
import de.metas.vertical.pharma.model.I_M_Product;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * metasfresh-pharma
     
 * #L%
 */
@Interceptor(I_M_Product.class)
@Component
public class M_Product
{
	private final static AdMessageKey ERR_Invalid_PZN = AdMessageKey.of("de.metas.vertical.pharma.model.interceptor.M_Product.Invalid_PZN");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_M_Product.COLUMNNAME_Value,
			I_M_Product.COLUMNNAME_IsPharmaProduct
	})
	public void ValidatePZN(final I_M_Product product)
	{

		if (!product.isPharmaProduct())
		{
			// nothing to do
			return;
		}

		final String pzn = product.getValue();

		final boolean isValidPZN = PharmaModulo11Validator.isValid(pzn);

		if (!isValidPZN)
		{
			throw new AdempiereException(ERR_Invalid_PZN, pzn)
					.markAsUserValidationError();
		}
	}

}
