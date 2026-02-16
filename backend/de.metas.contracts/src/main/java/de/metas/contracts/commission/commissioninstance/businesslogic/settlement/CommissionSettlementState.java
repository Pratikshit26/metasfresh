package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.contracts.commission.model.X_C_Commission_Fact;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/** Please keep in sync with the respective values of {@code AD_Reference_ID=541042}. */
public enum CommissionSettlementState
{
	/** basically this is the effective qty to invoice of the sales rep's commission settlement invoice candidate. */
	TO_SETTLE(X_C_Commission_Fact.COMMISSION_FACT_STATE_TO_SETTLE),

	/** related to the sales rep's invoice candidate where he/she got his commission settlement invoice. */
	SETTLED(X_C_Commission_Fact.COMMISSION_FACT_STATE_SETTLED);

	private static ImmutableMap<String, CommissionSettlementState> recordCode2State = ImmutableMap.of(
			SETTLED.getRecordCode(), SETTLED,
			TO_SETTLE.getRecordCode(), TO_SETTLE);

	@Getter
	private final String recordCode;

	private CommissionSettlementState(String recordCode)
	{
		this.recordCode = recordCode;
	}

	public static CommissionSettlementState forRecordCode(@NonNull final String recordCode)
	{
		return recordCode2State.get(recordCode);
	}

	public static Collection<String> allRecordCodes()
	{
		return Arrays.asList(CommissionSettlementState.values())
				.stream()
				.map(CommissionSettlementState::getRecordCode)
				.collect(ImmutableList.toImmutableList());
	}
}
