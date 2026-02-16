package de.metas.contracts.commission.commissioninstance.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import javax.annotation.Nullable;

import de.metas.organization.OrgId;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;

import de.metas.bpartner.BPGroupId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Value
@Builder
public class TestCommissionConfigLine
{
	/** Not persisted in the C_CommissionSettingsLine; just needed for the case that a particular line shall to be accessed later in the test */
	@NonNull
	String name;

	@Nullable
	BPGroupId customerBGroupId;

	@Nullable
	ProductCategoryId salesProductCategoryId;

	@NonNull
	String percentOfBasePoints;

	@NonNull
	Integer seqNo;

	/** supposed to be invoked from {@link TestCommissionConfig}. */
	IPair<String, CommissionSettingsLineId> createConfigLineData(
			@NonNull final OrgId orgId,
			final int C_HierarchyCommissionSettings)
	{
		final I_C_CommissionSettingsLine settingsLineRecord = newInstance(I_C_CommissionSettingsLine.class);
		settingsLineRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		settingsLineRecord.setC_HierarchyCommissionSettings_ID(C_HierarchyCommissionSettings);
		settingsLineRecord.setSeqNo(seqNo);
		settingsLineRecord.setPercentOfBasePoints(new java.math.BigDecimal(percentOfBasePoints));
		settingsLineRecord.setCustomer_Group_ID(BPGroupId.toRepoId(customerBGroupId));
		settingsLineRecord.setM_Product_Category_ID(ProductCategoryId.toRepoId(salesProductCategoryId));

		saveRecord(settingsLineRecord);

		return ImmutablePair.of(name, CommissionSettingsLineId.ofRepoId(settingsLineRecord.getC_CommissionSettingsLine_ID()));
	}
}
