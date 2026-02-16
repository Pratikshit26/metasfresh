package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

public interface CommissionAlgorithm
{
	/**
	 * Create a new commission instance with the given {@code trigger} and {@link CommissionShare}s.
	 * The method is invoked by the framework and can safely assume that no commission instance exists yet (in case that matters).
	 */
	ImmutableList<CommissionShare> createCommissionShares(CreateCommissionSharesRequest request);

	/**
	 * Apply the given {@code change}'s {@code newCommissionTriggerData} to its {@link CommissionInstance}.
	 */
	void applyTriggerChangeToShares(CommissionTriggerChange change);
}
