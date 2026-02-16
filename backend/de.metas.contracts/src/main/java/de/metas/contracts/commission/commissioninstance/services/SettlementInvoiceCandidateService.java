package de.metas.contracts.commission.commissioninstance.services;

import static java.math.BigDecimal.ZERO;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementState;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionSettlementShareRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Service
public class SettlementInvoiceCandidateService
{
	private final CommissionSettlementShareRepository commissionSettlementShareRepository;

	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	public SettlementInvoiceCandidateService(
			@NonNull final CommissionSettlementShareRepository commissionSettlementShareRepository)
	{
		this.commissionSettlementShareRepository = commissionSettlementShareRepository;
	}

	public void syncSettlementICToCommissionInstance(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			final boolean candidateDeleted)
	{
		final I_C_Invoice_Candidate settlementICRecord = invoiceCandDAO.getById(invoiceCandidateId);

		final CommissionSettlementShare settlementShare = commissionSettlementShareRepository.getByInvoiceCandidateId(invoiceCandidateId);

		//
		// pointsToSettle fact
		final CommissionPoints newPointsToSettle = CommissionPoints.of(candidateDeleted ? ZERO : settlementICRecord.getQtyToInvoice());
		final CommissionPoints pointsToSettleDelta = newPointsToSettle.subtract(settlementShare.getPointsToSettleSum());
		if (!pointsToSettleDelta.isZero())
		{
			final CommissionSettlementFact fact = CommissionSettlementFact.builder()
					.settlementInvoiceCandidateId(invoiceCandidateId)
					.timestamp(TimeUtil.asInstant(settlementICRecord.getUpdated()))
					.state(CommissionSettlementState.TO_SETTLE)
					.points(pointsToSettleDelta)
					.build();
			settlementShare.addFact(fact);
		}

		//
		// settledPoints fact
		final CommissionPoints settledPoints = CommissionPoints.of(candidateDeleted ? ZERO : settlementICRecord.getQtyInvoiced());
		final CommissionPoints settledPointsDelta = settledPoints.subtract(settlementShare.getSettledPointsSum());
		if (!settledPointsDelta.isZero())
		{
			final CommissionSettlementFact fact = CommissionSettlementFact.builder()
					.settlementInvoiceCandidateId(invoiceCandidateId)
					.timestamp(TimeUtil.asInstant(settlementICRecord.getUpdated()))
					.state(CommissionSettlementState.SETTLED)
					.points(settledPointsDelta)
					.build();
			settlementShare.addFact(fact);
		}

		commissionSettlementShareRepository.save(settlementShare);
	}
}
