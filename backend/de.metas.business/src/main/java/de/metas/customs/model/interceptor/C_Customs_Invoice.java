package de.metas.customs.model.interceptor;

import de.metas.customs.CustomsInvoiceId;
import de.metas.customs.CustomsInvoiceRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */
@Interceptor(I_C_Customs_Invoice.class)
@Component
public class C_Customs_Invoice
{
	private static final AdMessageKey MSG_C_Customs_Invoice_Tariff_NotSet = AdMessageKey.of("C_Customs_Invoice_Tariff_NotSet");

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void doNotCompleteUnlessCustomsTariff(final I_C_Customs_Invoice customsInvoiceRecord)
	{
		final CustomsInvoiceRepository repo = Adempiere.getBean(CustomsInvoiceRepository.class);

		final CustomsInvoiceId customsInvoiceId = CustomsInvoiceId.ofRepoId(customsInvoiceRecord.getC_Customs_Invoice_ID());

		final Set<ProductId> productIdsWithNoCustomsTariff = repo.retrieveProductIdsWithNoCustomsTariff(customsInvoiceId);

		if (!productIdsWithNoCustomsTariff.isEmpty())
		{

			throw new AdempiereException(MSG_C_Customs_Invoice_Tariff_NotSet)
					.markAsUserValidationError();
		}
	}
}
