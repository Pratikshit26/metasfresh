package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/** one instance has one commission trigger and {@code n} commission shares (according to the sales rep hierarchy). */
@Data
public class CommissionInstance
{
	/** null if this instance was not (yet) persisted */
	private final CommissionInstanceId id;

	private CommissionTriggerData currentTriggerData;

	/** Each share means that commission will be paid to some {@link Beneficiary} in accordance to some commission contract and hierarchy. */
	private final List<CommissionShare> shares;

	@JsonCreator
	@Builder(toBuilder = true)
	private CommissionInstance(
			@JsonProperty("id") @Nullable final CommissionInstanceId id,
			@JsonProperty("currentTriggerData") @NonNull final CommissionTriggerData currentTriggerData,
			@JsonProperty("shares") @Singular final List<CommissionShare> shares)
	{
		this.id = id;
		this.currentTriggerData = currentTriggerData;
		this.shares = new ArrayList<>(shares);
	}

	public void addShares(@NonNull final ImmutableList<CommissionShare> shares)
	{
		this.shares.addAll(shares);
	}

	public ImmutableList<CommissionShare> getShares()
	{
		return ImmutableList.copyOf(shares);
	}

	public boolean hasSimulationContracts()
	{
		return shares != null && shares.stream().anyMatch( share -> share.getContract().isSimulation());
	}
}
