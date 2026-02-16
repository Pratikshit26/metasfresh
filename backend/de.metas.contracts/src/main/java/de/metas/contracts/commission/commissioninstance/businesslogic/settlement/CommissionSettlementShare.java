package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShareId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Data
public class CommissionSettlementShare
{
	/** a settlement share doesn't make sense without a sales commission share. */
	private CommissionShareId salesCommissionShareId;

	@Setter(AccessLevel.NONE)
	private CommissionPoints pointsToSettleSum;

	@Setter(AccessLevel.NONE)
	private CommissionPoints settledPointsSum;

	/** Chronological list of facts that make it clear what happened when */
	private final ArrayList<CommissionSettlementFact> facts;

	@JsonCreator
	@Builder
	private CommissionSettlementShare(
			@JsonProperty("salesCommissionShareId") @NonNull final CommissionShareId salesCommissionShareId,
			@JsonProperty("facts") @NonNull final List<CommissionSettlementFact> facts)
	{
		this.salesCommissionShareId = salesCommissionShareId;
		this.facts = new ArrayList<>();

		this.pointsToSettleSum = CommissionPoints.ZERO;
		this.settledPointsSum = CommissionPoints.ZERO;

		for (final CommissionSettlementFact fact : facts)
		{
			addFact(fact);
		}
	}

	public CommissionSettlementShare addFact(@NonNull final CommissionSettlementFact fact)
	{
		facts.add(fact);

		switch (fact.getState())
		{
			case TO_SETTLE:
				pointsToSettleSum = pointsToSettleSum.add(fact.getPoints());
				break;
			case SETTLED:
				settledPointsSum = settledPointsSum.add(fact.getPoints());
				break;
			default:
				throw new AdempiereException("fact has unsupported state " + fact.getState())
						.appendParametersToMessage()
						.setParameter("fact", fact);
		}
		return this;
	}

	public ImmutableList<CommissionSettlementFact> getFacts()
	{
		return ImmutableList.copyOf(facts);
	}

}
