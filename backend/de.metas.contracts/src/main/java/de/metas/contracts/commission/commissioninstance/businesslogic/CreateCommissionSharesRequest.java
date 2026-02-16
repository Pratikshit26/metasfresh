package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class CreateCommissionSharesRequest
{
	@NonNull
	CommissionTrigger trigger;

	/**
	 * Contains the bpartners and their commission configs (contracts/settings).
	 * Not every bpartner from the {@link #hierarchy} needs to have one.
	 */
	@NonNull
	@Singular
	ImmutableList<CommissionConfig> configs;

	/** 
	 * Contains the bpartners for which we might create commission shares. 
	 * Does not tell if they will or won't get a share, but tells at which level they will get it.
	 * The root node is supposed to be the actual endcustomer, because it might have a a contract indicating that metasfresh shall create a 0% commission share for it.
	 */
	@NonNull
	Hierarchy hierarchy;

	@NonNull
	HierarchyLevel startingHierarchyLevel;

	public CreateCommissionSharesRequest withoutConfigs(@NonNull final ImmutableSet<CommissionConfig> existingConfigs)
	{
		if (existingConfigs.isEmpty())
		{
			return this;
		}

		final ImmutableList<CommissionConfig> remainingConfigs = configs.stream()
				.filter(config -> !existingConfigs.contains(config))
				.collect(ImmutableList.toImmutableList());

		return CreateCommissionSharesRequest.builder()
				.configs(remainingConfigs)
				.trigger(trigger)
				.hierarchy(hierarchy)
				.startingHierarchyLevel(startingHierarchyLevel)
				.build();
	}
}
