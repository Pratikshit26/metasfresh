package de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy;

import de.metas.contracts.commission.Beneficiary;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Value
public class HierarchyNode
{
	public static HierarchyNode of(@NonNull final Beneficiary beneficiary)
	{
		return new HierarchyNode(beneficiary);
	}

	Beneficiary beneficiary;

	private HierarchyNode(@NonNull final Beneficiary beneficiary)
	{
		this.beneficiary = beneficiary;
	}

}
