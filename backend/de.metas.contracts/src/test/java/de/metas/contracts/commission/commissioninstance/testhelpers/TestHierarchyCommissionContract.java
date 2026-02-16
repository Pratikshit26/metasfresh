package de.metas.contracts.commission.commissioninstance.testhelpers;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Value
@Builder
public class TestHierarchyCommissionContract
{
	/**
	 * Name of the sales rep. If no sales rep with this name exists, one is created on the fly
	 */
	@NonNull
	String salesRepName;

	/**
	 * If not set, then use the sales rep's name also for the contract.
	 */
	@Nullable
	String contractName;

	/**
	 * mostly used to construct a hierarchy with the contracts' BPartners.
	 */
	@Nullable
	String parentSalesRepName;

	/**
	 * The flatrate term is created with start = date minus 10 days and end = date plus 10 days.
	 */
	LocalDate date;

	private TestHierarchyCommissionContract(
			@NonNull final String salesRepName,
			@Nullable final String contractName,
			@Nullable final String parentSalesRepName,
			@Nullable final LocalDate date)
	{
		this.salesRepName = salesRepName;
		this.contractName = contractName;
		this.parentSalesRepName = parentSalesRepName;
		this.date = coalesceSuppliers(() -> date, SystemTime::asLocalDate);
	}

	/**
	 * Supposed to be invoked from {@link TestCommissionConfig}.
	 */
	I_C_Flatrate_Term createContractData(
			@NonNull final OrgId orgId,
			@NonNull final Integer C_Flatrate_Conditions_ID,
			@NonNull final ProductId commissionProductId)
	{
		final String effectiveContractName = coalesce(contractName, salesRepName);

		final I_C_Flatrate_Term termRecord = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(termRecord, effectiveContractName);
		termRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		
		final I_C_BPartner exitingBPartnerRecord = POJOLookupMap.get().getFirstOnly(I_C_BPartner.class, bpRecord -> salesRepName.equals(bpRecord.getName()));
		if (exitingBPartnerRecord == null)
		{
			final I_C_BP_Group bpGroup = BusinessTestHelper.createBPGroup("group-of" + salesRepName, false);
			
			final I_C_BPartner salesRepBPartnerRecord = newInstance(I_C_BPartner.class);
			POJOWrapper.setInstanceName(salesRepBPartnerRecord, salesRepName);
			salesRepBPartnerRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
			salesRepBPartnerRecord.setName(salesRepName);
			salesRepBPartnerRecord.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
			saveRecord(salesRepBPartnerRecord);
			termRecord.setBill_BPartner_ID(salesRepBPartnerRecord.getC_BPartner_ID());
		}
		else
		{
			termRecord.setBill_BPartner_ID(exitingBPartnerRecord.getC_BPartner_ID());
		}
		termRecord.setC_Flatrate_Conditions_ID(C_Flatrate_Conditions_ID);
		termRecord.setNote("name=" + effectiveContractName + " (parentSalesRepName=" + parentSalesRepName + ")");
		termRecord.setDocStatus(IDocument.STATUS_Completed);
		termRecord.setType_Conditions(TypeConditions.COMMISSION.getCode());
		termRecord.setM_Product_ID(commissionProductId.getRepoId());
		termRecord.setStartDate(TimeUtil.asTimestamp(date.minusDays(10)));
		termRecord.setEndDate(TimeUtil.asTimestamp(date.plusDays(10)));

		saveRecord(termRecord);

		return termRecord;
	}
}
