package de.metas.contracts.commission;

import de.metas.contracts.ConditionsId;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.Duration;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@UtilityClass
public class CommissionConstants
{
	public static final ConditionsId FLATRATE_CONDITION_0_COMMISSION_ID = ConditionsId.ofRepoId(540047);
	public static final Duration NO_COMMISSION_AGREEMENT_DEFAULT_CONTRACT_DURATION = Duration.ofDays(365);


	public static final String SYSCONFIG_UPDATE_SALESPARTNER_IN_MASTER_DATA = "de.metas.contracts.commission.UpdateSalesPartnerInCustomerMaterdata";

	@AllArgsConstructor
	@Getter
	public enum CommissionDocType
	{
		COMMISSION(DocBaseType.PurchaseInvoice, DocSubType.CommissionSettlement),
		MEDIATED_COMMISSION(DocBaseType.SalesInvoice, DocSubType.MEDIATED_COMMISSION),
		LICENSE_COMMISSION(DocBaseType.SalesInvoice, DocSubType.LICENSE_COMMISSION);

		private final DocBaseType docBaseType;
		private final DocSubType docSubType;
	}
}
