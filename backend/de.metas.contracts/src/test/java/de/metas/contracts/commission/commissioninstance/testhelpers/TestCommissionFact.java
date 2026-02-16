package de.metas.contracts.commission.commissioninstance.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.Instant;

import javax.annotation.Nullable;

import org.compiere.util.TimeUtil;

import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.invoicecandidate.InvoiceCandidateId;
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
public class TestCommissionFact
{
	@NonNull
	Long timestamp;

	@NonNull
	String commissionPoints;

	@NonNull
	String state;

	@Nullable
	InvoiceCandidateId C_Invoice_Candidate_Commission_ID;

	public void createCommissionData(int C_Commission_Share_ID)
	{
		final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
		factRecord.setC_Commission_Share_ID(C_Commission_Share_ID);
		factRecord.setCommissionFactTimestamp(TimeUtil.serializeInstant(Instant.ofEpochMilli(timestamp)));
		factRecord.setCommission_Fact_State(state);
		factRecord.setCommissionPoints(new BigDecimal(commissionPoints));
		factRecord.setC_Invoice_Candidate_Commission_ID(InvoiceCandidateId.toRepoId(C_Invoice_Candidate_Commission_ID));
		saveRecord(factRecord);
	}
}
