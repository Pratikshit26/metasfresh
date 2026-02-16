package de.metas.contracts.commission.commissioninstance.businesslogic;

import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierachyAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginConfig;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeAlgorithm;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeConfig;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionAlgorithm;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionConfig;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

public enum CommissionType
{
	HIERARCHY_COMMISSION(HierachyAlgorithm.class, HierarchyConfig.class),
	MEDIATED_COMMISSION(MediatedCommissionAlgorithm.class, MediatedCommissionConfig.class),
	MARGIN_COMMISSION(MarginAlgorithm.class, MarginConfig.class),
	LICENSE_FEE_COMMISSION(LicenseFeeAlgorithm.class, LicenseFeeConfig.class),
	;

	@Getter
	private final Class<? extends CommissionAlgorithm> algorithmClass;

	@Getter
	private final Class<? extends CommissionConfig> configClass;

	CommissionType(
			@NonNull final Class<? extends CommissionAlgorithm> algorithmClass,
			@NonNull final Class<? extends CommissionConfig> configClass)
	{
		this.algorithmClass = algorithmClass;
		this.configClass = configClass;
	}
}
